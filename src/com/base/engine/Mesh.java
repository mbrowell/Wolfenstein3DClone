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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

/**
 *
 * @author Michael Browell <mbrowell1984@gmail.com>
 */
public class Mesh {

    private int m_vbo;  // Vertex buffer object
    private int m_ibo;  // Index buffer object
    private int m_size;
    
    public Mesh(String fileName) {
        
        initMeshData();
        
        try {
            
            loadMesh("fileName");
            
        } catch (IOException ex) {
            
            Logger.getLogger(Mesh.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }
    
    public Mesh(Vertex[] vertices, int[] indices) {
        
        this(vertices, indices, false);
        
    }
    
    /**
     *
     * @param vertices
     * @param indices
     * @param calcNormals
     */
    public Mesh(Vertex[] vertices, int[] indices, boolean calcNormals) {
        
        initMeshData();
        
        addVertices(vertices, indices, calcNormals);
        
    }
    
    private void initMeshData() {
        
        this.m_vbo = glGenBuffers();
        this.m_ibo = glGenBuffers();
        this.m_size = 0;
        
    }
    
    /**
     *
     * @param vertices
     * @param indices
     * @param calcNormals
     */
    private void addVertices(Vertex[] vertices, int[] indices, boolean calcNormals) {
        
        if(calcNormals) {
            
            calcNormals(vertices, indices);
            
        }
        
        m_size = indices.length;
        
        glBindBuffer(GL_ARRAY_BUFFER, m_vbo);
        glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertices), GL_STATIC_DRAW);
        
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL_STATIC_DRAW);
        
    }
    
    /**
     *
     */
    public void draw() {
        
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        
        glBindBuffer(GL_ARRAY_BUFFER, m_vbo);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
        glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);
        
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_ibo);
        glDrawElements(GL_TRIANGLES, m_size, GL_UNSIGNED_INT, 0);
        
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);

    }
    
    private void calcNormals(Vertex[] vertices, int[] indices) {
        
        for(int i = 0; i < indices.length; i += 3) {
            
            int index0 = indices[i];
            int index1 = indices[i + 1];
            int index2 = indices[i + 2];
            
            Vector3f vector0 = vertices[index1].getM_pos().subtract(vertices[index0].getM_pos());
            Vector3f vector1 = vertices[index2].getM_pos().subtract(vertices[index0].getM_pos());
            
            Vector3f normal = vector0.cross(vector1).normalized();
            
            vertices[index0].setM_normal(vertices[index0].getM_normal().add(normal));
            vertices[index1].setM_normal(vertices[index1].getM_normal().add(normal));
            vertices[index2].setM_normal(vertices[index2].getM_normal().add(normal));
            
        }
        
        for (Vertex vertex : vertices) {
            
            vertex.setM_normal(vertex.getM_normal().normalized());
            
        }
        
    }
    
    @SuppressWarnings("null")
    private Mesh loadMesh(String fileName) throws IOException {
        
        String[] splitArray = fileName.split("\\.");
        String extension = splitArray[splitArray.length - 1];
        
        if(!extension.equals("obj")) {
            
            System.err.println("Error: File format not supported for mesh data: " + extension);
            Logger.getLogger(Mesh.class.getName()).log(Level.SEVERE, null, new Exception());
            System.exit(1);
            
        }
        
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Integer> indices = new ArrayList<>();
        
        String line;
        
        try (BufferedReader meshReader = new BufferedReader(new FileReader("./res/models/" + fileName))) {
            
            while((line = meshReader.readLine()) != null) {
                
                String[] tokens = line.split(" ");
                tokens = Util.removeEmptyStrings(tokens);
                
                if(tokens.length == 0 || tokens[0].equals("#")) {
                } else if(tokens[0].equals("v")) {
                    
                    vertices.add(new Vertex(new Vector3f(Float.valueOf(tokens[1]),
                                                         Float.valueOf(tokens[2]),
                                                         Float.valueOf(tokens[3]))));
                    
                } else if(tokens[0].equals("f")) {
                    
                    indices.add(Integer.parseInt(tokens[1].split("/")[0]) - 1);
                    indices.add(Integer.parseInt(tokens[2].split("/")[0]) - 1);
                    indices.add(Integer.parseInt(tokens[3].split("/")[0]) - 1);
                    
                    if(tokens.length > 4) {
                    
                    indices.add(Integer.parseInt(tokens[1].split("/")[0]) - 1);
                    indices.add(Integer.parseInt(tokens[3].split("/")[0]) - 1);
                    indices.add(Integer.parseInt(tokens[4].split("/")[0]) - 1);
                    
                }
                    
                }
                               
            }
            
        } catch (FileNotFoundException ex) {
            
            Logger.getLogger(Mesh.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
            
        }
            
        Vertex[] vertexData = new Vertex[vertices.size()];
        vertices.toArray(vertexData);
            
        Integer[] indexData = new Integer[indices.size()];
        indices.toArray(indexData);
        
        addVertices(vertexData, Util.toIntArray(indexData), true);
        
        return null;
        
    }
    
    

}
