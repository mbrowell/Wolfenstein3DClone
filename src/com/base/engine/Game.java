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

/**
 *
 * @author Michael Browell <mbrowell1984@gmail.com>
 */
public class Game extends Camera {
    
    private static final float SPOT_WIDTH = 1;
    private static final float SPOT_HEIGHT = 1;
    private static final float SPOT_DEPTH = 1;
    
    private static final int NUM_TEXT_EXP = 4;
    private static final int NUM_TEXTURES = (int)Math.pow(2, NUM_TEXT_EXP);
    
    Bitmap m_level;
    Shader m_shader;
    Material m_material;
    Mesh m_mesh;
    Transform m_transform;
        
    /**
     *
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public Game() {
        
        super();
            
        m_level = new Bitmap("level1.png").reflectOnX();
        
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Integer> indices = new ArrayList<>();
        
        for(int i = 0; i < m_level.getM_width(); i++) {
            
            for(int j = 0; j < m_level.getM_height(); j++) {
                
                if((m_level.getPixel(i, j) & 0xFFFFFF) == 0) {
                
                    continue;
                
                }
                
                // Generate floor
                int textX = ((m_level.getPixel(i, j) & 0x00FF00) >> 8) / NUM_TEXTURES;
                int textY = textX % NUM_TEXT_EXP;
                textX /= NUM_TEXT_EXP;
                
                float xHigher = 1 - ((float)textX / (float)NUM_TEXT_EXP);
                float xLower = xHigher - (1 / (float)NUM_TEXT_EXP);
                float yLower = 1 - ((float)textY / (float)NUM_TEXT_EXP);
                float yHigher = yLower - (1 / (float)NUM_TEXT_EXP);
                
                indices.add(vertices.size() + 2);
                indices.add(vertices.size() + 1);
                indices.add(vertices.size() + 0);
                indices.add(vertices.size() + 3);
                indices.add(vertices.size() + 2);
                indices.add(vertices.size() + 0);
                
                vertices.add(new Vertex(new Vector3f(i * SPOT_WIDTH, 0, j * SPOT_DEPTH), new Vector2f(xLower, yLower)));
                vertices.add(new Vertex(new Vector3f((i + 1) * SPOT_WIDTH, 0, j * SPOT_DEPTH), new Vector2f(xHigher, yLower)));
                vertices.add(new Vertex(new Vector3f((i + 1) * SPOT_WIDTH, 0, (j + 1) * SPOT_DEPTH), new Vector2f(xHigher, yHigher)));
                vertices.add(new Vertex(new Vector3f(i * SPOT_WIDTH, 0, (j + 1) * SPOT_DEPTH), new Vector2f(xLower, yHigher)));
                
                // Generate ceiling                
                xHigher = 1 - ((float)textX / (float)NUM_TEXT_EXP);
                xLower = xHigher - (1 / (float)NUM_TEXT_EXP);
                yLower = 1 - ((float)textY / (float)NUM_TEXT_EXP);
                yHigher = yLower - (1 / (float)NUM_TEXT_EXP);
                
                indices.add(vertices.size() + 0);
                indices.add(vertices.size() + 1);
                indices.add(vertices.size() + 2);
                indices.add(vertices.size() + 0);
                indices.add(vertices.size() + 2);
                indices.add(vertices.size() + 3);
                
                vertices.add(new Vertex(new Vector3f(i * SPOT_WIDTH, SPOT_HEIGHT, j * SPOT_DEPTH), new Vector2f(xLower, yLower)));
                vertices.add(new Vertex(new Vector3f((i + 1) * SPOT_WIDTH, SPOT_HEIGHT, j * SPOT_DEPTH), new Vector2f(xHigher, yLower)));
                vertices.add(new Vertex(new Vector3f((i + 1) * SPOT_WIDTH, SPOT_HEIGHT, (j + 1) * SPOT_DEPTH), new Vector2f(xHigher, yHigher)));
                vertices.add(new Vertex(new Vector3f(i * SPOT_WIDTH, SPOT_HEIGHT, (j + 1) * SPOT_DEPTH), new Vector2f(xLower, yHigher)));
                
                // Generate walls
                textX = ((m_level.getPixel(i, j) & 0xFF0000) >> 16) / NUM_TEXTURES;
                textY = textX % NUM_TEXT_EXP;
                textX /= NUM_TEXT_EXP;
                
                xHigher = 1 - ((float)textX / (float)NUM_TEXT_EXP);
                xLower = xHigher - (1 / (float)NUM_TEXT_EXP);
                yLower = 1 - ((float)textY / (float)NUM_TEXT_EXP);
                yHigher = yLower - (1 / (float)NUM_TEXT_EXP);
                
                if ((m_level.getPixel(i, j - 1) & 0xFFFFFF) == 0) {
                    
                    indices.add(vertices.size() + 0);
                    indices.add(vertices.size() + 1);
                    indices.add(vertices.size() + 2);
                    indices.add(vertices.size() + 0);
                    indices.add(vertices.size() + 2);
                    indices.add(vertices.size() + 3);
                    
                    vertices.add(new Vertex(new Vector3f(i * SPOT_WIDTH, 0, j * SPOT_DEPTH), new Vector2f(xLower, yLower)));
                    vertices.add(new Vertex(new Vector3f((i + 1) * SPOT_WIDTH, 0, j * SPOT_DEPTH), new Vector2f(xHigher, yLower)));
                    vertices.add(new Vertex(new Vector3f((i + 1) * SPOT_WIDTH, SPOT_HEIGHT, j * SPOT_DEPTH), new Vector2f(xHigher, yHigher)));
                    vertices.add(new Vertex(new Vector3f(i * SPOT_WIDTH, SPOT_HEIGHT, j * SPOT_DEPTH), new Vector2f(xLower, yHigher)));
                    
                }
                if ((m_level.getPixel(i, j + 1) & 0xFFFFFF) == 0) {
                    
                    indices.add(vertices.size() + 2);
                    indices.add(vertices.size() + 1);
                    indices.add(vertices.size() + 0);
                    indices.add(vertices.size() + 3);
                    indices.add(vertices.size() + 2);
                    indices.add(vertices.size() + 0);
                    
                    vertices.add(new Vertex(new Vector3f(i * SPOT_WIDTH, 0, (j + 1) * SPOT_DEPTH), new Vector2f(xLower+10, yLower+10)));
                    vertices.add(new Vertex(new Vector3f((i + 1) * SPOT_WIDTH, 0, (j + 1) * SPOT_DEPTH), new Vector2f(xHigher+10, yLower+10)));
                    vertices.add(new Vertex(new Vector3f((i + 1) * SPOT_WIDTH, SPOT_HEIGHT, (j + 1) * SPOT_DEPTH), new Vector2f(xHigher+10, yHigher+10)));
                    vertices.add(new Vertex(new Vector3f(i * SPOT_WIDTH, SPOT_HEIGHT, (j + 1) * SPOT_DEPTH), new Vector2f(xLower+10, yHigher+10)));
                    
                }
                
                if ((m_level.getPixel(i - 1, j) & 0xFFFFFF) == 0) {
                    
                    indices.add(vertices.size() + 2);
                    indices.add(vertices.size() + 1);
                    indices.add(vertices.size() + 0);
                    indices.add(vertices.size() + 3);
                    indices.add(vertices.size() + 2);
                    indices.add(vertices.size() + 0);
                    
                    vertices.add(new Vertex(new Vector3f(i * SPOT_WIDTH, 0, j * SPOT_DEPTH), new Vector2f(xLower, yLower)));
                    vertices.add(new Vertex(new Vector3f(i * SPOT_WIDTH, 0, (j+ 1) * SPOT_DEPTH), new Vector2f(xHigher, yLower)));
                    vertices.add(new Vertex(new Vector3f(i * SPOT_WIDTH, SPOT_HEIGHT, (j + 1) * SPOT_DEPTH), new Vector2f(xHigher, yHigher)));
                    vertices.add(new Vertex(new Vector3f(i * SPOT_WIDTH, SPOT_HEIGHT, j * SPOT_DEPTH), new Vector2f(xLower, yHigher)));
                    
                }
                if ((m_level.getPixel(i + 1, j) & 0xFFFFFF) == 0) {
                    
                    indices.add(vertices.size() + 0);
                    indices.add(vertices.size() + 1);
                    indices.add(vertices.size() + 2);
                    indices.add(vertices.size() + 0);
                    indices.add(vertices.size() + 2);
                    indices.add(vertices.size() + 3);
                    
                    vertices.add(new Vertex(new Vector3f((i + 1) * SPOT_WIDTH, 0, j * SPOT_DEPTH), new Vector2f(xLower, yLower)));
                    vertices.add(new Vertex(new Vector3f((i + 1) * SPOT_WIDTH, 0, (j + 1) * SPOT_DEPTH), new Vector2f(xHigher, yLower)));
                    vertices.add(new Vertex(new Vector3f((i + 1) * SPOT_WIDTH, SPOT_HEIGHT, (j + 1) * SPOT_DEPTH), new Vector2f(xHigher, yHigher)));
                    vertices.add(new Vertex(new Vector3f((i + 1) * SPOT_WIDTH, SPOT_HEIGHT, j * SPOT_DEPTH), new Vector2f(xLower, yHigher)));
                    
                }
                
            }
            
        }
        
        Vertex[] vertexArray = new Vertex[vertices.size()];
        Integer[] indexArray = new Integer[indices.size()];
        
        vertices.toArray(vertexArray);
        indices.toArray(indexArray);

//        Vertex[] vertices = new Vertex[] {new Vertex(new Vector3f(0, 0, 0), new Vector2f(0, 0)),
//                                          new Vertex(new Vector3f(0, 1, 0), new Vector2f(0, 1)),
//                                          new Vertex(new Vector3f(1, 1, 0), new Vector2f(1, 1)),
//                                          new Vertex(new Vector3f(1, 0, 0), new Vector2f(1, 0))};
//        
//        int[] indices = new int[] {0,1,2,
//                                   0,2,3};
        
        m_shader = BasicShader.getM_instance();
        m_material = new Material(new Texture("WolfCollection.png"));
        m_mesh = new Mesh(vertexArray, Util.toIntArray(indexArray));
        m_transform = new Transform();
        Transform.setM_camera(this);
        Transform.setProjection(70, Window.getWidth(), Window.getHeight(), .01f, 1000);
            
    }
    
    /**
     *
     */
    public void updateGame() {
        
        Transform.getM_camera().input();
        
    }
    
    /**
     *
     */
    public void render() {
        
        m_shader.bind();
        m_shader.updateUniforms(m_transform.getTransformation(), m_transform.getProjectedTransformation(), m_material);
        m_mesh.draw();
        
    }
    
}
