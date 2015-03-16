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
public class Medkit {
    
    private static final float SCALE = 0.2f;
    private static final float SIZEY = SCALE;
    private static final float SIZEX = (float)((double)SIZEY / (0.67857142857142857142857142857143 * 4.0));
    private static final float START = 0;
    
    private static final float TEX_MIN_X = 0;
    private static final float TEX_MAX_X = -1;
    private static final float TEX_MIN_Y = 0;
    private static final float TEX_MAX_Y = 1;
    
    private static final float PICKUP_DISTANCE = 0.75f;
    private static final int HEAL_AMOUNT = 25;
    
    private static Mesh m_mesh;
    private static Transform m_transform;
    private static Material m_material;
    
    public Medkit(Vector3f position) {
        
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
        
        if(m_material == null) {
            
            m_material = new Material(new Texture("MEDIA0.png"));
            
        }
        
        m_transform = new Transform();
        m_transform.setM_translation(position);
        
    }
    
    private void faceCamera(Vector3f directionToCamera) {
        
        
        float angleToFaceCamera = (float)Math.toDegrees(Math.atan(directionToCamera.getM_z()/directionToCamera.getM_x()));
        
        if(directionToCamera.getM_x() < 0) {
            
            angleToFaceCamera += 180;
            
        }
        
        m_transform.getM_rotation().setM_y(angleToFaceCamera + 90);
        
    }
    
    public void update() {
        
        Vector3f directionToCamera = Transform.getM_camera().getM_pos().subtract(m_transform.getM_translation());
        faceCamera(directionToCamera);
        
        if(directionToCamera.length() < PICKUP_DISTANCE) {
            
            Player player = Game.getM_level().getM_player();
            
            if(player.getM_health() < Player.getMAX_HEALTH()) {
            
                Game.getM_level().removeMedkit(this);
                Game.getM_level().getM_player().damage(-HEAL_AMOUNT);
            
            }
            
        }
        
    }
    
    public void render() {
        
        Shader shader = GameLevel.getM_shader();
        shader.updateUniforms(m_transform.getTransformation(), m_transform.getProjectedTransformation(), m_material);
        m_mesh.draw();
        
    }
    
}
