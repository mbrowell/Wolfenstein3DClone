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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 *
 * @author Michael Browell <mbrowell1984@gmail.com>
 */
public class Texture {

    private final int m_id;
    
    /**
     *
     * @param fileName
     */
    public Texture(String fileName) {
        
        this(loadTexture(fileName));
        
    }
    
    /**
     *
     * @param id
     */
    public Texture(int id) {
        
        this.m_id = id;
        
    }
    
    /**
     *
     */
    public void bind() {
        
        glBindTexture(GL_TEXTURE_2D, m_id);
        
    }
    
     private static int loadTexture(String fileName) {
        
        String[] splitArray = fileName.split("\\.");
        String extension = splitArray[splitArray.length - 1];
        
        try {
            
            int id = TextureLoader.getTexture(extension, new FileInputStream(new File("./res/textures/" + fileName))).getTextureID();
            
            return id;
            
        } catch (IOException ex) {
            
            Logger.getLogger(Texture.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return 0;
        
    }

    /**
     *
     * @return
     */
    public int getM_id() {
        
        return m_id;
        
    }
    
}
