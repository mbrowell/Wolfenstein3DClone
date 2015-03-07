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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael Browell <mbrowell1984@gmail.com>
 */
public class GameLevel {

    private static final float SPOT_WIDTH = 1;
    private static final float SPOT_HEIGHT = 1;
    private static final float SPOT_DEPTH = 1;
    private static final Vector2f BLOCK_SIZE = new Vector2f(SPOT_WIDTH, SPOT_DEPTH);
    
    private static final int NUM_TEXT_EXP = 4;
    private static final int NUM_TEXTURES = (int)Math.pow(2, NUM_TEXT_EXP);
    
    private static Bitmap m_level;
    private static Shader m_shader;
    private static Material m_material;
    private static Mesh m_mesh;
    private static Transform m_transform;
    
    private final ArrayList<Door> m_doors;
    
    public GameLevel(String levelName, String textureName) {
        
        m_level = new Bitmap(levelName).reflectOnX();
        
        m_shader = BasicShader.getM_instance();
        m_material = new Material(new Texture(textureName));
        
        m_doors = new ArrayList<>();
        
        generateLevel();
        
        m_transform = new Transform();
        
    }
    
    public void input() {
        
        
        
    }
    
    public void update() {
        
        for(Door door : m_doors) {
            
            door.update();
            
        }
        
    }
    
    public void render() {
        
        m_shader.bind();
        m_shader.updateUniforms(m_transform.getTransformation(), m_transform.getProjectedTransformation(), m_material);
        m_mesh.draw();
        for(Door door : m_doors) {
            
            door.render();
            
        }
        
    }

    public Vector3f checkCollision(Vector3f oldPos, Vector3f newPos, Vector2f PLAYER_DIMENSIONS) {
        
        Vector2f collisionVector = new Vector2f(1, 1);
        Vector3f movementVector = newPos.subtract(oldPos);
        
        if(movementVector.length() > 0) {
            
            for(int i = 0; i < m_level.getM_width(); i++) {
                
                for(int j = 0; j < m_level.getM_height(); j++) {
                    
                    if((m_level.getPixel(i, j) & 0xFFFFFF) == 0) {
                        
                        collisionVector = collisionVector.multiply(rectCollide(oldPos, newPos, PLAYER_DIMENSIONS, BLOCK_SIZE.multiply(new Vector2f(i, j)), BLOCK_SIZE));
                        
                    }
                    
                }
                
                //collisionVector = collisionVector.multiply(rectCollide(oldPos, newPos, PLAYER_DIMENSIONS, doorPos, doorSize));
                
            }
            
        }
        
        return new Vector3f(collisionVector.getX(), 0, collisionVector.getY());
        
    }
    
    private Vector2f rectCollide(Vector3f oldPos, Vector3f newPos, Vector2f playerSize, Vector2f objPos, Vector2f objSize) {
        
        Vector2f result = new Vector2f(0, 0);
        
        if(newPos.getX() + playerSize.getX() < objPos.getX() ||
           newPos.getX() - playerSize.getX() > objPos.getX() + objSize.getX() * objSize.getX() ||
           oldPos.getZ() + playerSize.getY() < objPos.getY() ||
           oldPos.getZ() - playerSize.getY() > objPos.getY() + objSize.getY() * objSize.getY()) {
            
            result.setX(1);
            
        }
        if(oldPos.getX() + playerSize.getX() < objPos.getX() ||
           oldPos.getX() - playerSize.getX() > objPos.getX() + objSize.getX() * objSize.getX() ||
           newPos.getZ() + playerSize.getY() < objPos.getY() ||
           newPos.getZ() - playerSize.getY() > objPos.getY() + objSize.getY() * objSize.getY()) {
            
            result.setY(1);
            
        }
        
        return result;
        
    }
    
    private void addFace(ArrayList<Integer> indices, int startLocation, boolean direction){
        
        if(direction) {
        
            indices.add(startLocation + 2);
            indices.add(startLocation + 1);
            indices.add(startLocation + 0);
            indices.add(startLocation + 3);
            indices.add(startLocation + 2);
            indices.add(startLocation + 0);
        
        } else {
            
            indices.add(startLocation + 0);
            indices.add(startLocation + 1);
            indices.add(startLocation + 2);
            indices.add(startLocation + 0);
            indices.add(startLocation + 2);
            indices.add(startLocation + 3);
            
        }
        
    }
    
    private float[] calcTextCoords(int value) {
        
        int textX = value / NUM_TEXTURES;
        int textY = textX % NUM_TEXT_EXP;
        textX /= NUM_TEXT_EXP;
        
        float[] result = new float[4];
        
        result[0] = 1 - ((float)textX / (float)NUM_TEXT_EXP);
        result[1] = result[0] - (1 / (float)NUM_TEXT_EXP);
        result[3] = 1 - ((float)textY / (float)NUM_TEXT_EXP);
        result[2] = result[3] - (1 / (float)NUM_TEXT_EXP);
        
        return result;
    }
        
    private void addVertices(ArrayList<Vertex> vertices, int i, int j, float offset, boolean x, boolean y, boolean z, float[] textCoords) {
        if(x && z) {
            
            vertices.add(new Vertex(new Vector3f(i * SPOT_WIDTH, offset * SPOT_HEIGHT, j * SPOT_DEPTH), new Vector2f(textCoords[1],textCoords[3])));
            vertices.add(new Vertex(new Vector3f((i + 1) * SPOT_WIDTH, offset * SPOT_HEIGHT, j * SPOT_DEPTH), new Vector2f(textCoords[0],textCoords[3])));
            vertices.add(new Vertex(new Vector3f((i + 1) * SPOT_WIDTH, offset * SPOT_HEIGHT, (j + 1) * SPOT_DEPTH), new Vector2f(textCoords[0],textCoords[2])));
            vertices.add(new Vertex(new Vector3f(i * SPOT_WIDTH, offset * SPOT_HEIGHT, (j + 1) * SPOT_DEPTH), new Vector2f(textCoords[1],textCoords[2])));
            
        } else if(x && y) {
            
            vertices.add(new Vertex(new Vector3f(i * SPOT_WIDTH, j * SPOT_HEIGHT, offset * SPOT_DEPTH), new Vector2f(textCoords[1],textCoords[3])));
            vertices.add(new Vertex(new Vector3f((i + 1) * SPOT_WIDTH, j * SPOT_HEIGHT, offset * SPOT_DEPTH), new Vector2f(textCoords[0],textCoords[3])));
            vertices.add(new Vertex(new Vector3f((i + 1) * SPOT_WIDTH, (j + 1) * SPOT_HEIGHT, offset * SPOT_DEPTH), new Vector2f(textCoords[0],textCoords[2])));
            vertices.add(new Vertex(new Vector3f(i * SPOT_WIDTH, (j + 1) * SPOT_HEIGHT, offset * SPOT_DEPTH), new Vector2f(textCoords[1],textCoords[2])));
        
        } else if(y && z) {
            
            vertices.add(new Vertex(new Vector3f(offset * SPOT_WIDTH, i * SPOT_HEIGHT, j * SPOT_DEPTH), new Vector2f(textCoords[1],textCoords[3])));
            vertices.add(new Vertex(new Vector3f(offset * SPOT_WIDTH, i * SPOT_HEIGHT, (j + 1) * SPOT_DEPTH), new Vector2f(textCoords[0],textCoords[3])));
            vertices.add(new Vertex(new Vector3f(offset * SPOT_WIDTH, (i + 1) * SPOT_HEIGHT, (j + 1) * SPOT_DEPTH), new Vector2f(textCoords[0],textCoords[2])));
            vertices.add(new Vertex(new Vector3f(offset * SPOT_WIDTH, (i + 1) * SPOT_HEIGHT, j * SPOT_DEPTH), new Vector2f(textCoords[1],textCoords[2])));
        
        } else {
            
            System.err.println("Invalid plane used in level generator");
            Logger.getLogger(GameLevel.class.getName()).log(Level.SEVERE, null, new Exception());
            System.exit(1);
                        
        }
        
    }
    
    private void addDoor(int x, int y) {
        
        Transform doorTransform = new Transform();
        
        boolean xDoor = (m_level.getPixel(x, y - 1) & 0xFFFFFF) == 0 && (m_level.getPixel(x, y + 1) & 0xFFFFFF) == 0;
        boolean yDoor = (m_level.getPixel(x - 1, y) & 0xFFFFFF) == 0 && (m_level.getPixel(x + 1, y) & 0xFFFFFF) == 0;
        
        if(!xDoor ^ yDoor) {
            
            System.err.println("Level generation failed! You placed a door in an invalid location: " + x + ", " + y);
            Logger.getLogger(GameLevel.class.getName()).log(Level.SEVERE, null, new Exception());
            System.exit(1);
            
        }
        
        if(xDoor) {
            
            doorTransform.setM_translation(new Vector3f(x + (SPOT_WIDTH / 2), 0, y));
            doorTransform.setM_rotation(0, 90, 0);
            
        }
        if(yDoor) {
            
            doorTransform.setM_translation(new Vector3f(x, 0, y + (SPOT_DEPTH / 2)));
            doorTransform.setM_rotation(0, 0, 0);
            
        }
        
        m_doors.add(new Door(doorTransform, m_material));
        
    }
    
    private void addSpecial(int blueValue, int x, int y) {
        
        if(blueValue == 16) {
            
            addDoor(x, y);
            
        }
        
    }
    
    private void generateLevel() {
        
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Integer> indices = new ArrayList<>();
        
        for(int i = 0; i < m_level.getM_width(); i++) {
            
            for(int j = 0; j < m_level.getM_height(); j++) {
                
                if((m_level.getPixel(i, j) & 0xFFFFFF) == 0) {
                
                    continue;
                
                }
                
                float[] textCoords = calcTextCoords((m_level.getPixel(i, j) & 0x00FF00) >> 8);
                
                addSpecial((m_level.getPixel(i, j) & 0x0000FF), i, j);
                
                // Generate floor
                addFace(indices, vertices.size(), true);
                addVertices(vertices, i, j, 0, true, false, true, textCoords);
                
                // Generate ceiling
                addFace(indices, vertices.size(), false);
                addVertices(vertices, i, j, 1, true, false, true, textCoords);
                
                // Generate walls
                textCoords = calcTextCoords((m_level.getPixel(i,j) & 0xFF0000) >> 16);
                
                if((m_level.getPixel(i,j - 1) & 0xFFFFFF) == 0) {
                    
                    addFace(indices, vertices.size(), false);
                    addVertices(vertices, i, 0, j, true, true, false, textCoords);
                
                }
                if((m_level.getPixel(i,j + 1) & 0xFFFFFF) == 0) {
                    
                    addFace(indices, vertices.size(), true);
                    addVertices(vertices, i, 0, (j + 1), true, true, false, textCoords);
                    
                }
                if((m_level.getPixel(i - 1,j) & 0xFFFFFF) == 0) {
                    
                    addFace(indices, vertices.size(), true);
                    addVertices(vertices, 0, j, i, false, true, true, textCoords);
                
                }
                if((m_level.getPixel(i + 1,j) & 0xFFFFFF) == 0) {
                    
                    addFace(indices, vertices.size(), false);
                    addVertices(vertices, 0, j, (i + 1), false, true, true, textCoords);
		
                }
                
            }
            
        }
        
        Vertex[] vertexArray = new Vertex[vertices.size()];
        Integer[] indexArray = new Integer[indices.size()];
        
        vertices.toArray(vertexArray);
        indices.toArray(indexArray);
        
        m_mesh = new Mesh(vertexArray, Util.toIntArray(indexArray));
        
    }
    
}
