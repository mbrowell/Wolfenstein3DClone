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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Michael Browell <mbrowell1984@gmail.com>
 */
public class Bitmap {

    private int m_width;
    private int m_height;
    private int[] m_pixels;
    
    /**
     *
     * @param fileName
     */
    public Bitmap(String fileName) {
        
        try {
            
            BufferedImage image = ImageIO.read(new File("./res/bitmaps/" + fileName));
            
            m_width = image.getWidth();
            m_height = image.getHeight();
            m_pixels = new int[m_width * m_height];
            
            image.getRGB(0, 0, m_width, m_height, m_pixels, 0, m_width);
            
        } catch (IOException ex) {
            
            Logger.getLogger(Bitmap.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }

    public Bitmap(int width, int height) {
        
        this.m_width = width;
        this.m_height = height;
        this.m_pixels = new int[m_width * m_height];
        
    }
    
    public Bitmap reflectOnX() {
        
        int[] temp = new int[m_pixels.length];
        
        for(int i = 0; i < m_width; i++) {
            
            for(int j = 0; j < m_height; j++) {
                
                temp[i + (j * m_width)] = m_pixels[i + ((m_height- j -1) * m_width)];
                
            }
            
        }
        
        m_pixels = temp;
        
        return this;
        
    }

    public int getM_width() {
        
        return m_width;
        
    }

    public int getM_height() {
        
        return m_height;
        
    }

    public int[] getPixels() {
        
        return m_pixels;
        
    }
    
    public int getPixel(int x, int y) {
        
        return m_pixels[x + (y * m_width)];
        
    }
    
    public void setPixel(int x, int y, int value) {
        
        m_pixels[x + (y * m_width)] = value;
        
    }

}
