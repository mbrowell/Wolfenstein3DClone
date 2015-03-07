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

    private Shader m_shader;
    private static Mesh m_mesh;
    private final Material m_material;
    private final Transform m_transform;

    public Door(Transform transform, Material material) {
        
        this.m_transform = transform;
        this.m_material = material;
        
        if(m_mesh == null) {
            
            // NOTE: Make sure to draw top/bottom face if door height < level height.
            Vertex[] vertices = new Vertex[] {
                
                new Vertex(new Vector3f(0, 0, 0), new Vector2f(0.5f, 1)),
                new Vertex(new Vector3f(0, 1, 0), new Vector2f(0.5f, 0.75f)),
                new Vertex(new Vector3f(1, 1, 0), new Vector2f(0.75f, 0.75f)),
                new Vertex(new Vector3f(1, 0, 0), new Vector2f(0.75f, 1)),
                 
                new Vertex(new Vector3f(0, 0, 0), new Vector2f(0, 0)),
                new Vertex(new Vector3f(0, 1, 0), new Vector2f(0, 0)),
                new Vertex(new Vector3f(0, 1, 0.04f), new Vector2f(0, 0)),
                new Vertex(new Vector3f(0, 0, 0.04f), new Vector2f(0, 0)),
                 
                new Vertex(new Vector3f(0, 0, 0.04f), new Vector2f(0, 0.75f)),
                new Vertex(new Vector3f(0, 1, 0.04f), new Vector2f(0, 0.5f)),
                new Vertex(new Vector3f(1, 1, 0.04f), new Vector2f(0.25f, 0.5f)),
                new Vertex(new Vector3f(1, 0, 0.04f), new Vector2f(0.25f, 0.75f)),
                
                new Vertex(new Vector3f(1, 0, 0), new Vector2f(0, 0)),
                new Vertex(new Vector3f(1, 1, 0), new Vector2f(0, 0)),
                new Vertex(new Vector3f(1, 1, 0.04f), new Vector2f(0, 0)),
                new Vertex(new Vector3f(1, 0, 0.04f), new Vector2f(0, 0))
                
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
    
    public void update() {
        
        
        
    }
    
    public void render() {
        
        m_shader = BasicShader.getM_instance();
        m_shader.updateUniforms(m_transform.getTransformation(), m_transform.getProjectedTransformation(), m_material);
        
        // TODO: Bind/update shader
        
        m_mesh.draw();
        
    }

    public Transform getM_transform() {
        
        return m_transform;
        
    }
        
}
