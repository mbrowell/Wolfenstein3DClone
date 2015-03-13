/*
 * Copyright (C) 2015 Michael Browell <mbrowell1984@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.base.engine;

import java.util.Random;

/**
 *
 * @author Michael Browell <mbrowell1984@gmail.com>
 */
public class Enemy {
    
    public static final float SCALE = 0.7f;
    public static final float SIZEY = SCALE;
    public static final float SIZEX = (float)((double)SIZEY / (1.91034487258620696551724137931 * 2));
    public static final float SIZEZ = 0.2f;
    public static final float START = 0;
    private static final float MOVE_SPEED = 2.8f;
    private static final float MOVEMENT_STOP_DISTANCE = 0.4f;
    private static final Vector2f SIZE = new Vector2f(SIZEX, SIZEZ);
    
    private static final float SHOOT_DISTANCE = 1000;
    
    public static final float TEX_MIN_X = 0;
    public static final float TEX_MAX_X = -1;
    public static final float TEX_MIN_Y = 0;
    public static final float TEX_MAX_Y = 1;
    
    public static final int STATE_IDLE   = 0;
    public static final int STATE_CHASE  = 1;
    public static final int STATE_ATTACK = 2;
    public static final int STATE_DYING  = 3;
    public static final int STATE_DEAD   = 4;
    
    private static final float SHOT_ANGLE = 10;

    private static Mesh m_mesh;
    private final Material m_material;
    private final Transform m_transform;
    private int m_state;
    private Random m_random;
    
    public Enemy(Transform transform) {
        
        this.m_transform = transform;
        m_material = new Material(new Texture("SSWVA1.png"));
        this.m_state = STATE_ATTACK;
        this.m_random = new Random();
        
        if(m_mesh == null) {
            
            Vertex[] vertices = new Vertex[] {
                
                new Vertex(new Vector3f(-SIZEX, START, START), new Vector2f(TEX_MAX_X, TEX_MAX_Y)),
                new Vertex(new Vector3f(-SIZEX, SIZEY, START), new Vector2f(TEX_MAX_X, TEX_MIN_Y)),
                new Vertex(new Vector3f(SIZEX, SIZEY, START), new Vector2f(TEX_MIN_X, TEX_MIN_Y)),
                new Vertex(new Vector3f(SIZEX, START, START), new Vector2f(TEX_MIN_X, TEX_MAX_Y))
                
                };
            
            int[] indices = new int[]{
                
                0,1,2,
                0,2,3
                
            };
            
            m_mesh = new Mesh(vertices, indices);
            
        }
        
    }
    
    private void idleUpdate(Vector3f orientation, float distance) {
        
        m_state = 1;
        
    }
    int i = 0;
    private void chaseUpdate(Vector3f orientation, float distance) {
        
        float moveAmount = MOVE_SPEED * (float)Time.getM_delta();
        
        if(distance > MOVEMENT_STOP_DISTANCE) {
            
            Vector3f oldPos = m_transform.getM_translation();
            Vector3f newPos = oldPos.add(orientation.multiply(moveAmount));
            
            Vector3f collisionVector = Game.getM_level().checkCollision(oldPos, newPos, SIZE);
            Vector3f movementVector = collisionVector.multiply(orientation);
            
            if (movementVector.length() > 0) {
                
                m_transform.setM_translation(m_transform.getM_translation().add(movementVector.multiply(moveAmount)));
            
            }
            
            if(movementVector.subtract(orientation).length() != 0) {
                
                Game.getM_level().openDoors(newPos);
                
            }
               
        }
        
    }
    
    private void attackUpdate(Vector3f orientation, float distance) {
        
        Vector2f lineStart = new Vector2f (m_transform.getM_translation().getX(), m_transform.getM_translation().getZ());
        Vector2f castDirection = new Vector2f(orientation.getX(), orientation.getZ()).rotate((m_random.nextFloat() - 0.5f) * SHOT_ANGLE);
        Vector2f lineEnd = lineStart.add(castDirection.multiply(SHOOT_DISTANCE));
        
        Vector2f collisionVector = Game.getM_level().checkIntersections(lineStart, lineEnd);
        
        Vector2f playerIntersectVector = Game.getM_level().lineIntersectRect(lineStart, lineEnd,
                                                                                        new Vector2f(Transform.getM_camera().getM_pos().getX(), Transform.getM_camera().getM_pos().getZ()),
                                                                                        Player.getPLAYER_DIMENSIONS());
        
        if(playerIntersectVector != null && (collisionVector == null ||
                playerIntersectVector.subtract(lineStart).length() < collisionVector.subtract(lineStart).length())) {
            
            System.out.println("We've just hit the player");
            m_state = STATE_CHASE;
            
        }
        
        if(collisionVector == null) {
                    
            System.out.println("We've hit nothing");
                    
        } else {
                    
            System.out.println("We've hit something");
                    
        }

    }
    
    private void dyingUpdate(Vector3f orientation, float distance) {
        
        m_state = 4;
        
    }
    
    private void deadUpdate(Vector3f orientation, float distance) {
        
        m_state = 0;
        
    }
    
    private void faceCamera(Vector3f directionToCamera) {
        
        
        float angleToFaceCamera = (float)Math.toDegrees(Math.atan(directionToCamera.getZ()/directionToCamera.getX()));
        
        if(directionToCamera.getX() < 0) {
            
            angleToFaceCamera += 180;
            
        }
        
        m_transform.getM_rotation().setY(angleToFaceCamera + 90);
        
    }
    
    private void alignWithGround() {
        
        m_transform.getM_translation().setY(0);
        
    }
    
    public void update() {
        
        Vector3f directionToCamera = Transform.getM_camera().getM_pos().subtract(m_transform.getM_translation());
        
        float distance = directionToCamera.length();
        Vector3f orientation = directionToCamera.divide(distance);
        
        faceCamera(orientation);
        alignWithGround();
        
        switch(m_state) {
            
            case STATE_IDLE:
                
                idleUpdate(orientation, distance);
                break;
                
            case STATE_CHASE:
                
                chaseUpdate(orientation, distance);
                break;
                
            case STATE_ATTACK:
                
                attackUpdate(orientation, distance);
                break;
                
            case STATE_DYING:
                
                dyingUpdate(orientation, distance);
                break;
                
            case STATE_DEAD:
                
                deadUpdate(orientation, distance);
                break;
            
        }
        
    }
    
    public void render() {
        
        Shader shader = GameLevel.getM_shader();
        shader.updateUniforms(m_transform.getTransformation(), m_transform.getProjectedTransformation(), m_material);
        m_mesh.draw();
        
    }
    
}
