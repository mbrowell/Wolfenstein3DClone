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
public class Enemy {
    
    public static final float SCALE = 0.7f;
    public static final float SIZEY = SCALE;
    public static final float SIZEX = (float)((double)SIZEY / (1.91034487258620696551724137931 * 2));
    public static final float START = 0;
    
    
    public static final float TEX_MIN_X = 0;
    public static final float TEX_MAX_X = -1;
    public static final float TEX_MIN_Y = 0;
    public static final float TEX_MAX_Y = 1;

    private static Mesh m_mesh;
    private final Material m_material;
    private final Transform m_transform;

    public Enemy(Transform transform) {
        
        this.m_transform = transform;
        m_material = new Material(new Texture("SSWVA1.png"));
        
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
    
    public void update() {
        
        
        
    }
    
    public void render() {
        
        Shader shader = GameLevel.getM_shader();
        shader.updateUniforms(m_transform.getTransformation(), m_transform.getProjectedTransformation(), m_material);
        m_mesh.draw();
        
    }
    
}
