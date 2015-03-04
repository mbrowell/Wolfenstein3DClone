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
public class Game extends Camera {
    
    private final Mesh m_mesh;
    private final Material m_material;
    private final Shader m_shader;
    private final Transform m_transform;
    PointLight[] m_pLights = new PointLight[]{new PointLight(new Vector3f(1, 0.5f, 0), 0.8f, new Attenuation(0, 0, 1), new Vector3f(-2, 0, 5f), 10),
                                             new PointLight(new Vector3f(0, 0.5f, 1), 0.8f, new Attenuation(0, 0, 1), new Vector3f(2, 0, 7f), 10)};
    SpotLight m_sLight1 = new SpotLight(new Vector3f(0, 1, 1), 0.8f, new Attenuation(0, 0, 0.01f), new Vector3f(-2, 0, 5f), 30, new Vector3f(1, 1, 1), 0.7f);
    
    /**
     *
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public Game() {
        
        super();
        
        
        m_material = new Material(new Texture("test.png"), new Vector3f(1, 1, 1), 1, 8);
        m_shader = PhongShader.getM_instance();
        m_transform = new Transform();
        
//        Vertex[] vertices = new Vertex[] {new Vertex(new Vector3f(-1, -1, 0.5773f), new Vector2f(0, 0)),
//                                          new Vertex(new Vector3f(0, -1, -1.15475f), new Vector2f(0.5f, 0)),
//                                          new Vertex(new Vector3f(1, -1, 0.5773f), new Vector2f(1, 0)),
//                                          new Vertex(new Vector3f(0, 1, 0), new Vector2f(0.5f, 1))};
//        
//        int[] indices = new int[] {0, 3, 1,
//                                   1, 3, 2,
//                                   2, 3, 0,
//                                   1, 2, 0};
        
        float fieldDepth = 10.0f;
        float fieldWidth = 10.0f;
        
        Vertex[] vertices = new Vertex[] {new Vertex(new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f (0, 0)),
                                          new Vertex(new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f (0, 1)),
                                          new Vertex(new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f (1, 0)),
                                          new Vertex(new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f (1, 1))};
    
        int indices[] = {0, 1, 2,
                         2, 1, 3};
        
        m_mesh = new Mesh(vertices, indices, true); // mesh = ResourceLoader.loadMesh("box.obj");
        
        Transform.setProjection(70f, Window.getWidth(), Window.getHeight(), 0.1f, 1000);
        Transform.setM_camera(this);
        
        PhongShader.setM_ambientLight(new Vector3f(0.1f, 0.1f, 0.1f));
        PhongShader.setM_directionalLight(new DirectionalLight(new Vector3f(1, 1, 1), 0.1f, new Vector3f(1, 1, 1)));
        
        PhongShader.setM_pointLights(m_pLights);
        PhongShader.setM_spotLights(new SpotLight[] {m_sLight1});
        
    }
    
    float temp = 0.0f;
    
    /**
     *
     */
    public void updateGame() {
        
        temp += Time.getM_delta();
        float sinTemp = (float)Math.sin(temp);
        float cosTemp = (float)Math.cos(temp);
        
        m_transform.setM_translation(0, -1, 5);
        //m_transform.setM_rotation(0 , sinTemp * 180, 0);
        
        m_pLights[0].setM_position(new Vector3f(3, 0, 8 * (sinTemp + 1/2) + 10));
        m_pLights[1].setM_position(new Vector3f(7, 0, 8 * (cosTemp + 1/2) + 10));
        
        //m_transform.setM_scale(0.7f * sinTemp, 0.7f * sinTemp, 0.7f * sinTemp);
        m_sLight1.setM_position(super.getM_pos());
        m_sLight1.setM_direction(super.getM_forward());

    }
    
    /**
     *
     */
    public void render() {
        
        RenderUtil.setClearColour(Transform.getM_camera().getM_pos().divide(2048f).abs());
        m_shader.bind();
        m_shader.updateUniforms(m_transform.getTransformation(), m_transform.getProjectedTransformation(), m_material);
        m_mesh.draw();
        
    }
    
}
