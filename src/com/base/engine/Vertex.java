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
public class Vertex {
    
    /**
     *
     */
    public static final int SIZE = 8;
    
    private Vector3f m_pos;
    private Vector2f m_textCoord;
    private Vector3f m_normal;

    /**
     *
     * @param pos
     */
    public Vertex(Vector3f pos) {
        
        this(pos, new Vector2f(0, 0));
        
    }
    
    /**
     *
     * @param pos
     * @param textCoord
     */
    public Vertex(Vector3f pos, Vector2f textCoord) {
        
        this(pos, textCoord, new Vector3f(0, 0, 0));
        
    }
    
    /**
     *
     * @param pos
     * @param textCoord
     * @param normal
     */
    public Vertex(Vector3f pos, Vector2f textCoord, Vector3f normal) {
        
        this.m_pos = pos;
        this.m_textCoord = textCoord;
        this.m_normal = normal;
        
    }

    /**
     *
     * @return
     */
    public Vector3f getM_pos() {
        
        return m_pos;
        
    }

    /**
     *
     * @param pos
     */
    public void setM_pos(Vector3f pos) {
        
        this.m_pos = pos;
        
    }

    /**
     *
     * @return
     */
    public Vector2f getM_textCoord() {
        
        return m_textCoord;
        
    }

    /**
     *
     * @param textCoord
     */
    public void setM_textCoord(Vector2f textCoord) {
        
        this.m_textCoord = textCoord;
        
    }

    /**
     *
     * @return
     */
    public Vector3f getM_normal() {
        
        return m_normal;
        
    }
    
    /**
     *
     * @param normal
     */
    public void setM_normal(Vector3f normal) {
        
        this.m_normal = normal;
        
    }
    
}
