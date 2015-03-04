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

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import org.lwjgl.BufferUtils;

/**
 *
 * @author Michael Browell <mbrowell1984@gmail.com>
 */
public class Util {
    
    /**
     *
     * @param size
     * @return
     */
    public static FloatBuffer createFloatBuffer(int size) {
        
        return BufferUtils.createFloatBuffer(size);
        
    }
    
    /**
     *
     * @param size
     * @return
     */
    public static IntBuffer createIntBuffer(int size) {
        
        return BufferUtils.createIntBuffer(size);
        
    }
    
    /**
     *
     * @param values
     * @return
     */
    public static IntBuffer createFlippedBuffer(int... values) {
        
        IntBuffer buffer = createIntBuffer(values.length);  
        buffer.put(values);
        buffer.flip();
        
        return buffer;
        
    }

    /**
     *
     * @param vertices
     * @return
     */
    public static FloatBuffer createFlippedBuffer(Vertex[] vertices) {
        
        FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.SIZE);
        
        for (Vertex vertex : vertices) {
            
            buffer.put(vertex.getM_pos().getX());
            buffer.put(vertex.getM_pos().getY());
            buffer.put(vertex.getM_pos().getZ());
            
            buffer.put(vertex.getM_textCoord().getX());
            buffer.put(vertex.getM_textCoord().getY());
            
            buffer.put(vertex.getM_normal().getX());
            buffer.put(vertex.getM_normal().getY());
            buffer.put(vertex.getM_normal().getZ());
            
        }
        
        buffer.flip();
        
        return buffer;
        
    }
    
    /**
     *
     * @param value
     * @return
     */
    public static FloatBuffer createFlippedBuffer(Matrix4f value) {
        
        FloatBuffer buffer = createFloatBuffer(4 * 4);
        
        for(int i = 0; i < 4; i++) {
            
            for(int j = 0; j < 4; j++) {
                
                buffer.put(value.get(i, j));
                
            }
            
        }
        
        buffer.flip();
        
        return buffer;
        
    }
    
    /**
     *
     * @param data
     * @return
     */
    public static String[] removeEmptyStrings(String[] data) {
        
        ArrayList<String> result = new ArrayList<>();
        
        for (String data1 : data) {
            
            if (!data1.equals("")) {
                
                result.add(data1);
                
            }
            
        }
        
        String[] res = new String[result.size()];
        result.toArray(res);
        
        return res;
        
    }
    
    /**
     *
     * @param data
     * @return
     */
    public static int[] toIntArray(Integer[] data) {
        
        int[] result = new int[data.length];
        
        for(int i = 0; i < data.length; i++) {
            
            result[i] = data[i];
            
        }
        
        return result;
        
    }
    
}
