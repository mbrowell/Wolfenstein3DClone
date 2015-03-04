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
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgram;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

/**
 *
 * @author Michael Browell <mbrowell1984@gmail.com>
 */
public class Shader {

    private final int m_program;
    private final HashMap<String, Integer> m_uniforms;
    
    /**
     *
     */
    public Shader() {
        
        m_program = glCreateProgram();
        m_uniforms = new HashMap<>();
        
        if(m_program == 0) {
            
            System.err.println("Shader creation failed: Could not find valid memory location in constructor");
            System.exit(1);
            
        }
        
    }
    
    /**
     *
     */
    public void bind() {
        
        glUseProgram(m_program);
        
    }
    
    /**
     *
     * @param worldMatrix
     * @param projectedMatrix
     * @param material
     * @param camera
     */
    public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material) {
        
        
        
    }
    
    /**
     *
     * @param uniform
     */
    public void addUniform(String uniform) {
        
        int uniformLocation = glGetUniformLocation(m_program, uniform);
        
        if(uniformLocation == 0xFFFFFFFF) {
            
            System.err.println("Error: Could not find uniform: " + uniform);
            Logger.getLogger(Shader.class.getName()).log(Level.SEVERE, null, new Exception());
            System.exit(1);
            
        }
        
        m_uniforms.put(uniform, uniformLocation);
        
    }
    
    /**
     *
     * @param fileName
     */
    public void addVertexShaderFromFile(String fileName) {
        
        addProgram(loadShader(fileName), GL_VERTEX_SHADER);
        
    }
    
    /**
     *
     * @param fileName
     */
    public void addGeometryShaderFromFile(String fileName) {
        
        addProgram(loadShader(fileName), GL_GEOMETRY_SHADER);
        
    }
    
    /**
     *
     * @param fileName
     */
    public void addFragmentShaderFromFile(String fileName) {
        
        addProgram(loadShader(fileName), GL_FRAGMENT_SHADER);
        
    }
    
    /**
     *
     * @param text
     */
    public void addVertexShader(String text) {
        
        addProgram(text, GL_VERTEX_SHADER);
        
    }
    
    /**
     *
     * @param text
     */
    public void addGeometryShader(String text) {
        
        addProgram(text, GL_GEOMETRY_SHADER);
        
    }
    
    /**
     *
     * @param text
     */
    public void addFragmentShader(String text) {
        
        addProgram(text, GL_FRAGMENT_SHADER);
        
    }
    
    /**
     *
     */
    public void linkShader() {
        
        glLinkProgram(m_program);
        
        if(glGetProgram(m_program, GL_LINK_STATUS) == 0) {
            
            System.err.println(glGetProgramInfoLog(m_program, 1024));
            System.exit(1);
            
        }
        
        glValidateProgram(m_program);
        
        if(glGetProgram(m_program, GL_VALIDATE_STATUS) == 0) {
            
            System.err.println(glGetProgramInfoLog(m_program, 1024));
            System.exit(1);
            
        }
        
    }
    
    private void addProgram(String text, int type) {
        
        int shader = glCreateShader(type);
        
        if(shader == 0) {
            
            System.err.println("Shader creation failed: Could not find valid memory location when adding shader");
            System.exit(1);
            
        }
        
        glShaderSource(shader, text);
        glCompileShader(shader);
        
        if(glGetProgram(shader, GL_COMPILE_STATUS) == 0) {
            
            System.err.println(glGetProgramInfoLog(shader, 1024));
            System.exit(1);
            
        } 
        
        glAttachShader(m_program, shader);
        
    }
    
    @SuppressWarnings("null")
    private static String loadShader(String fileName) {
        
        StringBuilder shaderSource = new StringBuilder();
        BufferedReader shaderReader = null;
        
        try {
            
            shaderReader = new BufferedReader(new FileReader("./res/shaders/" + fileName));
            
        } catch (FileNotFoundException ex) {
            
            Logger.getLogger(Shader.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        String line;
        
        try {
            
            while((line = shaderReader.readLine()) != null) {
                
                shaderSource.append(line).append("\n");
                               
            }
            
        } catch (IOException ex) {
            
            Logger.getLogger(Shader.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return shaderSource.toString();
        
    }
    
    /**
     *
     * @param uniformName
     * @param value
     */
    public void setUniformi(String uniformName, int value) {
        
        glUniform1i(m_uniforms.get(uniformName), value);
        
    }
    
    /**
     *
     * @param uniformName
     * @param value
     */
    public void setUniformf(String uniformName, float value) {
        
        glUniform1f(m_uniforms.get(uniformName), value);
        
    }
    
    /**
     *
     * @param uniformName
     * @param value
     */
    public void setUniform(String uniformName, Vector3f value) {
        
        glUniform3f(m_uniforms.get(uniformName), value.getX(), value.getY(), value.getZ());
        
    }
    
    /**
     *
     * @param uniformName
     * @param value
     */
    public void setUniform(String uniformName, Matrix4f value) {
        
        glUniformMatrix4(m_uniforms.get(uniformName), true, Util.createFlippedBuffer(value));
        
    }

}
