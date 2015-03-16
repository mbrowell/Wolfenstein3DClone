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
    private static final float OPEN_DISTANCE = 1;
    
    private static Bitmap m_level;
    private static Shader m_shader;
    private static Material m_material;
    private static Mesh m_mesh;
    private static Transform m_transform;
    
    private Player m_player;
    
    private ArrayList<Door> m_doors;
    private ArrayList<Enemy> m_enemies;
    private ArrayList<Medkit> m_medkits;
    
    private final ArrayList<Medkit> m_medkitsToRemove;
    private final ArrayList<Vector3f> m_exitPoints;
    
    private ArrayList<Vector2f> collisionPosStart;
    private ArrayList<Vector2f> collisionPosEnd;
    
    public GameLevel(String levelName, String textureName) {
        
        m_level = new Bitmap(levelName).reflectOnX();
        
        m_shader = BasicShader.getM_instance();
        m_material = new Material(new Texture(textureName));
        m_medkitsToRemove = new ArrayList<>();
        m_exitPoints = new ArrayList<>();
        
        generateLevel();
        
        m_transform = new Transform();
        
        
        
    }
    
    public void openDoors(Vector3f position, boolean exitCheck) {
        
        for(Door door : m_doors) {
                
            if(door.getM_transform().getM_translation().subtract(position).length() < OPEN_DISTANCE) {

                door.open();

            }

        }
        
        if(exitCheck)  {
            
            for(Vector3f exitPoint : m_exitPoints) {
                
                if(exitPoint.subtract(position).length() < OPEN_DISTANCE) {
                    
                    Game.loadLevel();
                    
                }
                
            }
            
        }
        
    }
    
    public void input() {
        
        m_player.input();
        
    }
    
    public void update() {
        
        m_player.update();
        
        for(Door door : m_doors) {
            
            door.update();
            
        }
        
        for(Enemy enemy : m_enemies) {
            
            enemy.update();
            
        }
        
        for(Medkit medkit : m_medkits) {
            
            medkit.update();
            
        }
        
        for(Medkit medkit : m_medkitsToRemove) {
            
            m_medkits.remove(medkit);
            
        }
        
    }
    
    public void render() {
        
        m_shader.bind();
        m_shader.updateUniforms(m_transform.getTransformation(), m_transform.getProjectedTransformation(), m_material);
        m_mesh.draw();
        
        for(Door door : m_doors) {
            
            door.render();
            
        }
        
        for(Enemy enemy : m_enemies) {
            
            enemy.render();
            
        }
        
        for(Medkit medkit : m_medkits) {
            
            medkit.render();
            
        }
        
        m_player.render();
        
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
                
                for(Door door: m_doors) {
                    
                    Vector2f doorPos = new Vector2f(door.getM_transform().getM_translation().getM_x(), door.getM_transform().getM_translation().getM_z());
                    
                    Vector2f doorSize = door.getDoorSize();
                    collisionVector = collisionVector.multiply(rectCollide(oldPos, newPos, PLAYER_DIMENSIONS, doorPos, doorSize));
                    
                }
                
            }
            
        }
        
        return new Vector3f(collisionVector.getM_x(), 0, collisionVector.getM_y());
        
    }
    
    private Vector2f rectCollide(Vector3f oldPos, Vector3f newPos, Vector2f playerSize, Vector2f objPos, Vector2f objSize) {
        
        Vector2f result = new Vector2f(0, 0);
        
        if(newPos.getM_x() + playerSize.getM_x() < objPos.getM_x() ||
           newPos.getM_x() - playerSize.getM_x() > objPos.getM_x() + objSize.getM_x() * objSize.getM_x() ||
           oldPos.getM_z() + playerSize.getM_y() < objPos.getM_y() ||
           oldPos.getM_z() - playerSize.getM_y() > objPos.getM_y() + objSize.getM_y() * objSize.getM_y()) {
            
            result.setM_x(1);
            
        }
        if(oldPos.getM_x() + playerSize.getM_x() < objPos.getM_x() ||
           oldPos.getM_x() - playerSize.getM_x() > objPos.getM_x() + objSize.getM_x() * objSize.getM_x() ||
           newPos.getM_z() + playerSize.getM_y() < objPos.getM_y() ||
           newPos.getM_z() - playerSize.getM_y() > objPos.getM_y() + objSize.getM_y() * objSize.getM_y()) {
            
            result.setM_y(1);
            
        }
        
        return result;
        
    }
    
    public Vector2f checkIntersections(Vector2f lineStart, Vector2f lineEnd, boolean hurtEnemies) {
        
        Vector2f nearestIntersection = null;
        
        for (int i = 0; i < collisionPosStart.size(); i++) {
            
            Vector2f collisionVector = lineIntersect(lineStart, lineEnd, collisionPosStart.get(i), collisionPosEnd.get(i));
            
            nearestIntersection = findNearestVector2f(collisionVector, nearestIntersection, lineStart);
            
        }
        
        for(Door door : m_doors) {
            
            Vector2f collisionVector = lineIntersectRect(lineStart, lineEnd,
                    new Vector2f(door.getM_transform().getM_translation().getM_x(), door.getM_transform().getM_translation().getM_z()), door.getDoorSize());
            
            nearestIntersection = findNearestVector2f(collisionVector, nearestIntersection, lineStart);
            
        }
        
        if(hurtEnemies) {
            
            Vector2f nearestEnemyIntersect = null;
            Enemy nearestEnemy = null;

            for(Enemy enemy : m_enemies) {

                Vector2f monsterSize = Enemy.getSIZE();
                Vector3f monsterPos3f = enemy.getM_transform().getM_translation();
                Vector2f monsterPos2f = new Vector2f(monsterPos3f.getM_x(), monsterPos3f.getM_z());
                Vector2f collisionVector = lineIntersectRect(lineStart, lineEnd, monsterPos2f, monsterSize);

                nearestEnemyIntersect = findNearestVector2f(nearestEnemyIntersect, collisionVector, lineStart);

                if(nearestEnemyIntersect == collisionVector) {

                        nearestEnemy = enemy;

                }

            }

            if(nearestEnemyIntersect != null && (nearestIntersection == null ||
                    nearestEnemyIntersect.subtract(lineStart).length() < nearestIntersection.subtract(lineStart).length())) {

                if(nearestEnemy != null) {

                        nearestEnemy.damage(30);

                }
                
            }
            
        }
        
        return nearestIntersection;
        
    }
    
    private Vector2f lineIntersect(Vector2f lineStart1, Vector2f lineEnd1,
            Vector2f lineStart2, Vector2f lineEnd2) {
                
        Vector2f line1 = lineEnd1.subtract(lineStart1);
        Vector2f line2 = lineEnd2.subtract(lineStart2);

        float cross = line1.cross(line2);

        if(cross == 0) {

            return null;
            
        }
        
        Vector2f distanceBetweenLineStarts = lineStart2.subtract(lineStart1);

        float a = distanceBetweenLineStarts.cross(line2) / cross;
        float b = distanceBetweenLineStarts.cross(line1) / cross;
        
        if(0 < a && a < 1 && 0 < b && b < 1) {
            
            return lineStart1.add(line1.multiply(a));
            
        }
        
        return null;
        
    }
    
    private Vector2f findNearestVector2f(Vector2f a, Vector2f b, Vector2f positionRelativeTo) {
        
        if(b != null && (a == null ||
                    a.subtract(positionRelativeTo).length() >  b.subtract(positionRelativeTo).length())) {
                
                return b;
                
            }
        
        return a;
        
    }
    
    /**
     *
     * @param lineStart
     * @param lineEnd
     * @param rectPos
     * @param rectSize
     * @return
     */
    public Vector2f lineIntersectRect(Vector2f lineStart, Vector2f lineEnd, Vector2f rectPos, Vector2f rectSize) {
        
        Vector2f result = null;
        
        Vector2f collisionVector = lineIntersect(lineStart, lineEnd, rectPos, new Vector2f(rectPos.getM_x() + rectSize.getM_x(),
                                                                                           rectPos.getM_y()));
        result = findNearestVector2f(result, collisionVector, lineStart);
        
        collisionVector = lineIntersect(lineStart, lineEnd, rectPos, new Vector2f(rectPos.getM_x(),
                                                                                  rectPos.getM_y() + rectSize.getM_y()));
        result = findNearestVector2f(result, collisionVector, lineStart);
        
        collisionVector = lineIntersect(lineStart, lineEnd, new Vector2f(rectPos.getM_x() + rectSize.getM_x(),
                                                                                  rectPos.getM_y()), rectPos.add(rectSize));
        result = findNearestVector2f(result, collisionVector, lineStart);
        
        collisionVector = lineIntersect(lineStart, lineEnd, new Vector2f(rectPos.getM_x(),
                                                                         rectPos.getM_y() + rectSize.getM_y()), rectPos.add(rectSize));
        result = findNearestVector2f(result, collisionVector, lineStart);
        
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
        
        Vector3f openPosition = null;
        
        if(xDoor) {
            
            doorTransform.setM_translation(new Vector3f(x + (SPOT_WIDTH / 2), 0, y));
            doorTransform.setM_rotation(0, 90, 0);
            openPosition = doorTransform.getM_translation().subtract(new Vector3f(0, 0, 0.9f));
            
        }
        if(yDoor) {
            
            doorTransform.setM_translation(new Vector3f(x, 0, y + (SPOT_DEPTH / 2)));
            doorTransform.setM_rotation(0, 0, 0);
            openPosition = doorTransform.getM_translation().subtract(new Vector3f(0.9f, 0, 0));
            
        }
        
        m_doors.add(new Door(doorTransform, m_material, openPosition));
        
    }
    
    private void addSpecial(int blueValue, int x, int z) {
        
        if(blueValue == 1) {
            
            m_player = new Player(new Vector3f(((x + 0.5f) * SPOT_WIDTH), 0.4375f, ((z + 0.5f) * SPOT_DEPTH)));
            
        }
        
        if(blueValue == 16) {
            
            addDoor(x, z);
            
        }
        
        if(blueValue == 97) {
            
            m_exitPoints.add(new Vector3f(((x + 0.5f) * SPOT_WIDTH), 0, ((z + 0.5f) * SPOT_DEPTH)));
            
        }
        
        if(blueValue == 128) {
            
            Transform enemyTransform = new Transform();
            enemyTransform.setM_translation(new Vector3f(((x + 0.5f) * SPOT_WIDTH), 0, ((z + 0.5f) * SPOT_DEPTH)));
            m_enemies.add(new Enemy(enemyTransform));
            
        }
        
        if(blueValue == 192) {
            
            m_medkits.add(new Medkit(new Vector3f(((x + 0.5f) * SPOT_WIDTH), 0, ((z + 0.5f) * SPOT_DEPTH))));
            
        }
        
    }
    
    public void removeMedkit(Medkit medkit) {
        
        m_medkitsToRemove.add(medkit);
        
    }
    
    private void generateLevel() {
        
        m_doors = new ArrayList<>();
        m_enemies = new ArrayList<>();
        m_medkits = new ArrayList<>();
        
        collisionPosStart = new ArrayList<>();
        collisionPosEnd = new ArrayList<>();
        
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
                    
                    collisionPosStart.add(new Vector2f(i * SPOT_WIDTH, j * SPOT_DEPTH));
                    collisionPosEnd.add(new Vector2f((i + 1) * SPOT_WIDTH, j * SPOT_DEPTH));
                    addFace(indices, vertices.size(), false);
                    addVertices(vertices, i, 0, j, true, true, false, textCoords);
                
                }
                if((m_level.getPixel(i,j + 1) & 0xFFFFFF) == 0) {
                    
                    collisionPosStart.add(new Vector2f(i * SPOT_WIDTH, (j + 1) * SPOT_DEPTH));
                    collisionPosEnd.add(new Vector2f((i + 1) * SPOT_WIDTH, (j + 1) * SPOT_DEPTH));
                    addFace(indices, vertices.size(), true);
                    addVertices(vertices, i, 0, (j + 1), true, true, false, textCoords);
                    
                }
                if((m_level.getPixel(i - 1,j) & 0xFFFFFF) == 0) {
                    
                    collisionPosStart.add(new Vector2f(i * SPOT_WIDTH, j * SPOT_DEPTH));
                    collisionPosEnd.add(new Vector2f(i * SPOT_WIDTH, (j + 1) * SPOT_DEPTH));
                    addFace(indices, vertices.size(), true);
                    addVertices(vertices, 0, j, i, false, true, true, textCoords);
                
                }
                if((m_level.getPixel(i + 1,j) & 0xFFFFFF) == 0) {
                    
                    collisionPosStart.add(new Vector2f((i + 1) * SPOT_WIDTH, j * SPOT_DEPTH));
                    collisionPosEnd.add(new Vector2f((i + 1) * SPOT_WIDTH, (j + 1) * SPOT_DEPTH));
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

    public static Shader getM_shader() {
        
        return m_shader;
        
    }

    public ArrayList<Enemy> getM_enemies() {
        
        return m_enemies;
        
    }

    public Player getM_player() {
        
        return m_player;
        
    }
    
}
