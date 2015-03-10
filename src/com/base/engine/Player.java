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
    
    private static final Vector2f CENTRE_POSITION = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);
    private static final float MOUSE_SENSITIVITY = 0.2f;
    private static final float MOVE_SPEED = 7;
    private static final float PLAYER_SIZE = 0.2f;
    private static final Vector2f PLAYER_DIMENSIONS = new Vector2f(PLAYER_SIZE, PLAYER_SIZE);
    private static final Vector3f ZERO_VECTOR = new Vector3f(0, 0, 0);
    
    private final Camera m_camera;
    
    private boolean m_mouseLocked = false;
    
    private Vector3f m_movementVector;
    
    public Player(Vector3f position) {
        
         m_camera = new Camera(position, new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
        
    }
    
    public void input(){
        
        
        
        if(Input.getKey(Input.KEY_ESCAPE)) {
            
            Input.setCursor(true);
            m_mouseLocked = false;
            
        }
        if(Input.getMouseDown(0)) {
            
            Input.setMousePosition(CENTRE_POSITION);
            Input.setCursor(false);
            m_mouseLocked = true;
            
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
        
        m_camera.move(m_movementVector, moveAmt);
        
    }
    
    public void render() {
        
        
        
    }

    public Camera getM_camera() {
        
        return m_camera;
        
    }
    
}
