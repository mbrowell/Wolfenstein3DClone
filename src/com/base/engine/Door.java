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
public class Door {
    
    private static final float WIDTH = 1;
    private static final float HEIGHT = 1;
    private static final float DEPTH = 0.04f;
    private static final float START = 0;
    private static final double TIME_TO_OPEN = 1;
    private static final double CLOSE_DELAY = 2;
    
    private static Mesh m_mesh;
    private final Material m_material;
    private final Transform m_transform;
    
    private final Vector3f m_openPosition;
    private final Vector3f m_closePosition;
    
    private boolean m_isOpening;
    private double m_openingStartTime;
    private double m_openTime;
    private double m_closingStartTime;
    private double m_closeTime;

    public Door(Transform transform, Material material, Vector3f openPosition) {
        
        m_transform = transform;
        m_material = material;
        m_isOpening = false;
        m_closePosition = m_transform.getM_translation().multiply(1);
        m_openPosition = openPosition;
        
        if(m_mesh == null) {
            
            // NOTE: Make sure to draw top/bottom face if door height < level height.
            Vertex[] vertices = new Vertex[] {
                
                new Vertex(new Vector3f(START, START, START), new Vector2f(0.5f, 1)),
                new Vertex(new Vector3f(START, HEIGHT, START), new Vector2f(0.5f, 0.75f)),
                new Vertex(new Vector3f(WIDTH, HEIGHT, START), new Vector2f(0.75f, 0.75f)),
                new Vertex(new Vector3f(WIDTH, START, START), new Vector2f(0.75f, 1)),
                 
                new Vertex(new Vector3f(START, START, START), new Vector2f(0, 0)),
                new Vertex(new Vector3f(START, HEIGHT, START), new Vector2f(0, 0)),
                new Vertex(new Vector3f(START, HEIGHT, DEPTH), new Vector2f(0, 0)),
                new Vertex(new Vector3f(START, START, DEPTH), new Vector2f(0, 0)),
                 
                new Vertex(new Vector3f(START, START, DEPTH), new Vector2f(0.5f, 1)),
                new Vertex(new Vector3f(START, HEIGHT, DEPTH), new Vector2f(0.5f, 0.75f)),
                new Vertex(new Vector3f(WIDTH, HEIGHT, DEPTH), new Vector2f(0.75f, 0.75f)),
                new Vertex(new Vector3f(WIDTH, START, DEPTH), new Vector2f(0.75f, 1)),
                
                new Vertex(new Vector3f(WIDTH, START, START), new Vector2f(0, 0)),
                new Vertex(new Vector3f(WIDTH, HEIGHT, START), new Vector2f(0, 0)),
                new Vertex(new Vector3f(WIDTH, HEIGHT, DEPTH), new Vector2f(0, 0)),
                new Vertex(new Vector3f(WIDTH, START, DEPTH), new Vector2f(0, 0))
                
                };
            
            int[] indices = new int[]{
                
                0,1,2,
                0,2,3,
                
                6,5,4,
                7,6,4,
                
                10,9,8,
                11,10,8,
                
                12,13,14,
                12,14,15
                
            };
            
            m_mesh = new Mesh(vertices, indices);
            
        }
        
    }
    
    public void open() {
        
        if(m_isOpening) {
            
            return;
            
        }
        
        m_openingStartTime = (double)Time.getTime()/(double)Time.SECOND;
        m_openTime = m_openingStartTime + TIME_TO_OPEN;
        
        m_closingStartTime = m_openTime + CLOSE_DELAY;
        m_closeTime = m_closingStartTime + TIME_TO_OPEN;
        
        m_isOpening = true;
        
    }
    
    private Vector3f vectorLerp(Vector3f startPos, Vector3f endPos, float lerpFactor) {
        
        return startPos.add(endPos.subtract(startPos).multiply(lerpFactor));
        
    }
    
    public void update() {
        
        if(m_isOpening) {
            
            double time = (double)Time.getTime() / Time.SECOND;
            
            if(time < m_openTime) {
                
                getM_transform().setM_translation(vectorLerp(m_closePosition, m_openPosition,
                        (float)((time - m_openingStartTime) / TIME_TO_OPEN)));
                
            } else if(time < m_closingStartTime) {
            
                getM_transform().setM_translation(m_openPosition);
            
            } else if(time < m_closeTime) {
                
                getM_transform().setM_translation(vectorLerp(m_openPosition, m_closePosition,
                        (float)((time - m_closingStartTime) / TIME_TO_OPEN)));
                
            } else {
                
                getM_transform().setM_translation(m_closePosition);
                m_isOpening = false;
                
            }
            
        }
        
    }
    
    public void render() {
        
        Shader shader = GameLevel.getM_shader();
        shader.updateUniforms(m_transform.getTransformation(), m_transform.getProjectedTransformation(), m_material);
        
        // TODO: Bind/update shader
        
        m_mesh.draw();
        
    }
    
    public Vector2f getDoorSize() {
        
        if(getM_transform().getM_rotation().getY() == 90) {
            
            return new Vector2f(Door.DEPTH, Door.WIDTH);
            
        } else {
            
            return new Vector2f(Door.WIDTH, Door.DEPTH);
            
        }
        
    }

    public Transform getM_transform() {
        
        return m_transform;
        
    }
        
}
