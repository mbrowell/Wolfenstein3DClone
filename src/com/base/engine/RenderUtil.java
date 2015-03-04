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

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_CW;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

/**
 *
 * @author Michael Browell <mbrowell1984@gmail.com>
 */
public class RenderUtil {
    
    /**
     *
     */
    public static void clearScreen() {
        
        //TODO: Stencil Buffer
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
    }
    
    /**
     *
     * @param enabled
     */
    public static void setTextures(boolean enabled) {
        
        if(enabled) {
            
            glEnable(GL_TEXTURE_2D);
            
        } else {
            
            glDisable(GL_TEXTURE_2D);
            
        }
        
    }
    
    /**
     *
     */
    public static void unbindTextures() {
        
        glBindTexture(GL_TEXTURE_2D, 0);
        
    }
    
    /**
     *
     * @param colour
     */
    public static void setClearColour(Vector3f colour) {
        
        glClearColor(colour.getX(), colour.getY(), colour.getZ(), 1.0f);
        
    }
    
    /**
     *
     */
    public static void initGraphics() {
        
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        
        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);
        
        glEnable(GL_DEPTH_CLAMP);
        
        glEnable(GL_TEXTURE_2D);
        
    }
    
    /**
     *
     * @return
     */
    public static String getOpenGLVersion() {
        
        return glGetString(GL_VERSION);
        
    }
    
}
