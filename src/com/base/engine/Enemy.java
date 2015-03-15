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

import java.util.ArrayList;
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
    private static final Vector2f SIZE = new Vector2f(SIZEX, SIZEZ);
    
    private static final float MOVE_SPEED = 2.8f;
    private static final float MOVEMENT_STOP_DISTANCE = 0.4f;
    
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
    private static final double ATTACK_CHANCE = 0.5d;
    public static final int MAX_HEALTH = 100;

    private static Mesh m_mesh;
    private final Material m_material;
    private final Transform m_transform;
    private int m_state;
    private int m_health;
    private boolean m_canLook;
    private boolean m_canAttack;
    private final Random m_random;
    
    private static ArrayList<Texture> m_animFrames;
    
    public Enemy(Transform transform) {
        
        if(m_animFrames == null) {
            
            m_animFrames = new ArrayList<>();
            
            m_animFrames.add(new Texture("SSWVA1.png"));
            m_animFrames.add(new Texture("SSWVB1.png"));
            m_animFrames.add(new Texture("SSWVC1.png"));
            m_animFrames.add(new Texture("SSWVD1.png"));
            
            m_animFrames.add(new Texture("SSWVE0.png"));
            m_animFrames.add(new Texture("SSWVF0.png"));
            m_animFrames.add(new Texture("SSWVG0.png"));
            
            m_animFrames.add(new Texture("SSWVH0.png"));
            m_animFrames.add(new Texture("SSWVI0.png"));
            m_animFrames.add(new Texture("SSWVJ0.png"));
            m_animFrames.add(new Texture("SSWVK0.png"));
            m_animFrames.add(new Texture("SSWVL0.png"));
            
            m_animFrames.add(new Texture("SSWVM0.png"));
            
        }
        
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
        
        m_transform = transform;
        m_material = new Material(m_animFrames.get(0));
        m_state = STATE_IDLE;
        m_health = MAX_HEALTH;
        m_canLook = false;
        m_random = new Random();
        
    }
    
    public void damage(int amt) {
        
        if(m_state == STATE_IDLE) {
            
            m_state = STATE_CHASE;
            
        }
        
        m_health -= amt;
        
        if(m_health <= 0) {
            
            m_state = STATE_DYING;
            
        }
        
    }
    
    private void idleUpdate(Vector3f orientation) {
        
        double time = ((double)Time.getTime())/((double)Time.SECOND);
        double timeDecimals = time - (double)((int)time);
        
        if(timeDecimals < 0.5) {
            
            m_canLook = true;
            m_material.setM_texture(m_animFrames.get(0));
            
        } else {
            
            m_material.setM_texture(m_animFrames.get(1));
            
            if(m_canLook) {
                
                Vector2f lineStart = new Vector2f (m_transform.getM_translation().getX(), m_transform.getM_translation().getZ());
                Vector2f castDirection = new Vector2f(orientation.getX(), orientation.getZ()).rotate((m_random.nextFloat()));
                Vector2f lineEnd = lineStart.add(castDirection.multiply(SHOOT_DISTANCE));
                
                Vector2f collisionVector = Game.getM_level().checkIntersections(lineStart, lineEnd);
                
                Vector2f playerIntersectVector = Game.getM_level().lineIntersectRect(lineStart, lineEnd,
                                                                                     new Vector2f(Transform.getM_camera().getM_pos().getX(), Transform.getM_camera().getM_pos().getZ()),
                                                                                     Player.getPLAYER_DIMENSIONS());
                
                if(playerIntersectVector != null && (collisionVector == null ||
                    playerIntersectVector.subtract(lineStart).length() < collisionVector.subtract(lineStart).length())) {
                    
                    m_state = STATE_CHASE;
                    
                }
                
                m_canLook = false;
                
            }
            
        }
        
    }
    int i = 0;
    private void chaseUpdate(Vector3f orientation, float distance) {
        
        double time = ((double)Time.getTime())/((double)Time.SECOND);
        double timeDecimals = time - (double)((int)time);
        
        if(timeDecimals < 0.25) {
            
            m_material.setM_texture(m_animFrames.get(0));
            
        } else if(timeDecimals < 0.5) {
            
            m_material.setM_texture(m_animFrames.get(1));
            
        } else if(timeDecimals < 0.75) {
            
            m_material.setM_texture(m_animFrames.get(2));
            
        } else {
            
            m_material.setM_texture(m_animFrames.get(3));
            
        }
        
        if(m_random.nextDouble() < ATTACK_CHANCE * Time.getM_delta()) {
            
            m_state = STATE_ATTACK;
            
        }
        
        if(distance > MOVEMENT_STOP_DISTANCE) {
            
            float moveAmount = MOVE_SPEED * (float)Time.getM_delta();
            
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
    
    private void attackUpdate(Vector3f orientation) {
        
        double time = ((double)Time.getTime())/((double)Time.SECOND);
        double timeDecimals = time - (double)((int)time);
        
        if(timeDecimals < 0.25) {
            
            m_canAttack = true;
            m_material.setM_texture(m_animFrames.get(4));
            
        } else if(timeDecimals < 0.5) {
            
            m_material.setM_texture(m_animFrames.get(5));
            
        }else if(timeDecimals < 0.75) {
            
            m_material.setM_texture(m_animFrames.get(6));
            
            if(m_canAttack) {
                
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
                    m_canAttack = false;
                    Game.getM_level().getM_player().damage(10);
            
                }
                
            }
            
        } else {
            
            m_material.setM_texture(m_animFrames.get(5));
            
        }

    }
    
    private void dyingUpdate(Vector3f orientation, float distance) {
        
        double time = ((double)Time.getTime())/((double)Time.SECOND);
        double timeDecimals = time - (double)((int)time);
        
        if(timeDecimals < 0.1) {
            
            m_material.setM_texture(m_animFrames.get(8));
            m_transform.setM_scale(1,0.96428571428571428571428571428571f,1);
            
        } else if(timeDecimals < 0.3) {
            
            m_material.setM_texture(m_animFrames.get(9));
            m_transform.setM_scale(1.7f,0.9f,1);
            
        } else if(timeDecimals < 0.45) {
            
            m_material.setM_texture(m_animFrames.get(10));
            m_transform.setM_scale(1.7f,0.9f,1);
            
        } else if(timeDecimals < 0.6) {
            
            m_material.setM_texture(m_animFrames.get(11));
            m_transform.setM_scale(1.7f,0.5f,1);
            
        } else {
            
            m_state = STATE_DEAD;
            
        }
        
    }
    
    private void deadUpdate(Vector3f orientation, float distance) {
        
        m_material.setM_texture(m_animFrames.get(12));
        m_transform.setM_scale(1.7586206896551724137931034482759f,0.28571428571428571428571428571429f,1);
        
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
                
                idleUpdate(orientation);
                break;
                
            case STATE_CHASE:
                
                chaseUpdate(orientation, distance);
                break;
                
            case STATE_ATTACK:
                
                attackUpdate(orientation);
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

    public static Vector2f getSIZE() {
        
        return SIZE;
        
    }

    public Transform getM_transform() {
        
        return m_transform;
        
    }
    
}
