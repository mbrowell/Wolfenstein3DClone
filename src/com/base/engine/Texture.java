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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;

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
            
            BufferedImage image = ImageIO.read(new File("./res/textures/" + fileName));
            
            boolean hasAlpha = image.getColorModel().hasAlpha();
                        
            int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
           
            ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
            
            for(int y = 0; y < image.getHeight(); y++) {
                
                for(int x = 0; x < image.getWidth(); x++) {
                    
                    int pixel = pixels[(y * image.getWidth()) + x];
                    
                    buffer.put((byte) ((pixel >> 16) & 0xFF));
                    buffer.put((byte) ((pixel >> 8) & 0xFF));
                    buffer.put((byte) ((pixel) & 0xFF));
                    
                    if(hasAlpha) {
                        
                        buffer.put((byte) ((pixel >> 24) & 0xFF));
                        
                    } else {
                        
                        buffer.put((byte) (0xFF));
                        
                    }
                    
                }
                
                
            }
            
            buffer.flip();
            
            int texture = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, texture);
            
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
            
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
            
            return texture;
            
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
