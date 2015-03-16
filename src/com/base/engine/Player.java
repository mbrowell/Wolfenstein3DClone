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

/**
 *
 * @author Michael Browell <mbrowell1984@gmail.com>
 */
public class Player {
    
    private static final float SCALE = 0.0625f;
    private static final float SIZEY = SCALE;
    private static final float SIZEX = (float)((double)SIZEY / (1.0379746835443037974683544303797 * 2));
    private static final float START = 0;
    private static final float OFFSET = -0.0125f;
    
    private static final Vector2f CENTRE_POSITION = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);
    private static final float MOUSE_SENSITIVITY = 0.2f;
    private static final float MOVE_SPEED = 7;
    
    private static final float PLAYER_SIZE = 0.2f;
    private static final Vector2f PLAYER_DIMENSIONS = new Vector2f(PLAYER_SIZE, PLAYER_SIZE);
    private static final Vector3f ZERO_VECTOR = new Vector3f(0, 0, 0);
    
    private static final float TEX_MIN_X = 0;
    private static final float TEX_MAX_X = -1;
    private static final float TEX_MIN_Y = 0;
    private static final float TEX_MAX_Y = 1;
    
    private static final float SHOOT_DISTANCE = 1000;
    private static final int MAX_HEALTH = 100;
    
    private int m_health;
    
    private static Mesh m_mesh;
    private static Material m_gunMaterial;
    
    private final Transform m_gunTransform;
    private final Camera m_camera;
    
    private static boolean m_mouseLocked = false;
    
    private Vector3f m_movementVector;
    
    public Player(Vector3f position) {
        
         m_camera = new Camera(position, new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
         m_health = MAX_HEALTH;
         
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
         
         if(m_gunMaterial == null) {
             
             m_gunMaterial = new Material(new Texture("PISGB0.png"));
             
         }
         
         m_gunTransform = new Transform();
         m_gunTransform.setM_translation(new Vector3f(7, 0, 7));
         m_movementVector = ZERO_VECTOR;
        
    }
    
    public void damage(int amt) {
        
        m_health -= amt;
        
        if(m_health > MAX_HEALTH) {
            
            m_health = MAX_HEALTH;
            
        }
        
        System.out.println("Health: " + m_health);
        
        if(m_health <= 0) {
            
            Game.setM_isRunning(false);
            System.out.println("You just died!  GAME OVER!");
            
        }
        
    }
    
    public void input(){
        
        if(Input.getKeyDown(Input.KEY_E)) {
            
            Game.getM_level().openDoors(m_camera.getM_pos(), true);
            
        }
        
        if(Input.getKey(Input.KEY_ESCAPE)) {
            
            Input.setCursor(true);
            m_mouseLocked = false;
            
        }
        if(Input.getMouseDown(0)) {
            
            if(!m_mouseLocked) {
                
                Input.setMousePosition(CENTRE_POSITION);
                Input.setCursor(false);
                m_mouseLocked = true;
                
            } else {
                
                Vector2f lineStart = new Vector2f(getM_camera().getM_pos().getX(), getM_camera().getM_pos().getZ());
                Vector2f castDirection = new Vector2f(getM_camera().getM_forward().getX(), getM_camera().getM_pos().getZ()).normalized();
                Vector2f lineEnd = lineStart.add(castDirection.multiply(SHOOT_DISTANCE));

                Game.getM_level().checkIntersections(lineStart, lineEnd, true);
                
            }
            
        }
        
        m_movementVector = ZERO_VECTOR;
        
        if(Input.getKey(Input.KEY_W)) {
            
            m_movementVector = m_movementVector.add(m_camera.getM_forward());//m_camera.move(m_camera.getM_forward(), moveAmt);
            
        }
        if(Input.getKey(Input.KEY_A)) {
            
            m_movementVector = m_movementVector.add(m_camera.getLeft());//m_camera.move(m_camera.getLeft(), moveAmt);
            
        }
        if(Input.getKey(Input.KEY_S)) {
            
            m_movementVector = m_movementVector.subtract(m_camera.getM_forward());//m_camera.move(m_camera.getM_forward(), -moveAmt);
            
        }if(Input.getKey(Input.KEY_D)) {
            
            m_movementVector = m_movementVector.add(m_camera.getRight());//m_camera.move(m_camera.getRight(), moveAmt);
            
        }
        
        if(m_mouseLocked) {
            
            Vector2f deltaPos = Input.getMousePosition().subtract(CENTRE_POSITION);
            
            boolean rotY = deltaPos.getX() != 0;
            boolean rotX = deltaPos.getY() != 0;
            
            if(rotY) {
                
                m_camera.rotateY(deltaPos.getX() * MOUSE_SENSITIVITY);
                
            }
            if(rotX) {
                
                m_camera.rotateX(-deltaPos.getY() * MOUSE_SENSITIVITY);
                
            }
            
            if (rotY || rotX) {
                
                Input.setMousePosition(CENTRE_POSITION);
                
            }
            
        }
        
    }
    
    public void update() {
        
        float moveAmt = (float)(MOVE_SPEED * Time.getM_delta());
        
        m_movementVector.setY(0);
        if(m_movementVector.length() > 0) {
            
            m_movementVector = m_movementVector.normalized();
            
        }
        
        Vector3f oldPos = m_camera.getM_pos();
        Vector3f newPos = oldPos.add(m_movementVector.multiply(moveAmt));
        
        Vector3f collisionVector = Game.getM_level().checkCollision(oldPos, newPos, PLAYER_DIMENSIONS);
        m_movementVector = m_movementVector.multiply(collisionVector);
        
        if(m_movementVector.length() > 0) {
            
            m_camera.move(m_movementVector, moveAmt);
            
        }
        
        m_gunTransform.setM_translation(m_camera.getM_pos().add(m_camera.getM_forward().normalized().multiply(0.105f)));
        m_gunTransform.getM_translation().setY(m_gunTransform.getM_translation().getY() + OFFSET);
        
        Vector3f directionToCamera = Transform.getM_camera().getM_pos().subtract(m_gunTransform.getM_translation());
        
        float angleToFaceCamera = (float)Math.toDegrees(Math.atan(directionToCamera.getZ()/directionToCamera.getX()));
        
        if(directionToCamera.getX() < 0) {
            
            angleToFaceCamera += 180;
            
        }
        
        m_gunTransform.getM_rotation().setY(angleToFaceCamera + 90);
        
        
    }
    
    public void render() {
        
        Shader shader = GameLevel.getM_shader();
        shader.updateUniforms(m_gunTransform.getTransformation(), m_gunTransform.getProjectedTransformation(), m_gunMaterial);
        m_mesh.draw();
        
    }

    public Camera getM_camera() {
        
        return m_camera;
        
    }


    public static Vector2f getPLAYER_DIMENSIONS() {
        
        return PLAYER_DIMENSIONS;
        
    }

    public static int getMAX_HEALTH() {
        
        return MAX_HEALTH;
        
    }

    public int getM_health() {
        
        return m_health;
        
    }
    
}
