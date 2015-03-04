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
public class BasicShader extends Shader {
    
    private static final BasicShader m_instance = new BasicShader();

    /**
     *
     */
    public BasicShader() {
        
        super();
        
        addVertexShaderFromFile("basicShader.vs");
        addFragmentShaderFromFile("basicShader.fs");
        linkShader();
        
        addUniform("transform");
        addUniform("colour");
        
    }
    
    /**
     *
     * @param worldMatrix
     * @param projectedMatrix
     * @param material
     */
    @Override
    public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material) {
        
        if(material.getM_texture() != null) {
            
            material.getM_texture().bind();
            
        } else {
            
            RenderUtil.unbindTextures();
            
        }
        
        setUniform("transform", projectedMatrix);
        setUniform("colour", material.getM_colour());
        
    }
    
    /**
     *
     * @return
     */
    public static BasicShader getM_instance() {
        
        return m_instance;
        
    }

}
