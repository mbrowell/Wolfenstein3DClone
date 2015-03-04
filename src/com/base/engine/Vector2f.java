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
public class Vector2f {
    
    private float m_x;
    private float m_y;
	
    /**
     *
     * @param x
     * @param y
     */
    public Vector2f(float x, float y) {
            
        this.m_x = x;
        this.m_y = y;
                
    }

    /**
     *
     * @return
     */
    public float length() {
            
        return (float)Math.sqrt(m_x * m_x + m_y * m_y);
                
    }

    /**
     *
     * @param r
     * @return
     */
    public float dot(Vector2f r) {
            
        return m_x * r.getX() + m_y * r.getY();
                
    }
	
    /**
     *
     * @return
     */
    public Vector2f normalized() {
            
        float length = length();
		
        return new Vector2f(m_x / length, m_y / length);
                
    }

    /**
     *
     * @param r
     * @return
     */
    public float cross(Vector2f r) {
            
        return m_x * r.getY() - m_y * r.getX();
                
    }

    /**
     *
     * @param angle
     * @return
     */
    public Vector2f rotate(float angle) {
            
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);
		
        return new Vector2f((float)(m_x * cos - m_y * sin),(float)(m_x * sin + m_y * cos));
                
    }
    
    /**
     *
     * @return
     */
    public Vector2f abs() {
        
        return new Vector2f(Math.abs(m_x), Math.abs(m_y));
        
    }
	
    /**
     *
     * @param r
     * @return
     */
    public Vector2f add(Vector2f r) {
            
        return new Vector2f(m_x + r.getX(), m_y + r.getY());
                
    }
	
    /**
     *
     * @param r
     * @return
     */
    public Vector2f add(float r) {
            
        return new Vector2f(m_x + r, m_y + r);
                
    }
	
    /**
     *
     * @param r
     * @return
     */
    public Vector2f subtract(Vector2f r) {
            
        return new Vector2f(m_x - r.getX(), m_y - r.getY());
                
    }
	
    /**
     *
     * @param r
     * @return
     */
    public Vector2f subtract(float r) {
            
        return new Vector2f(m_x - r, m_y - r);
                
    }
	
    /**
     *
     * @param r
     * @return
     */
    public Vector2f multiply(Vector2f r) {
            
        return new Vector2f(m_x * r.getX(), m_y * r.getY());
                
    }
	
    /**
     *
     * @param r
     * @return
     */
    public Vector2f multiply(float r) {
            
        return new Vector2f(m_x * r, m_y * r);
                
    }
	
    /**
     *
     * @param r
     * @return
     */
    public Vector2f divide(Vector2f r) {
            
        return new Vector2f(m_x / r.getX(), m_y / r.getY());
                
    }
	
    /**
     *
     * @param r
     * @return
     */
    public Vector2f divide(float r) {
            
        return new Vector2f(m_x / r, m_y / r);
                
    }
	
    /**
     *
     * @return
     */
    @Override
    public String toString() {
            
        return "(" + m_x + " " + m_y + ")";
                
    }
        
    /**
     *
     * @return
     */
    public float getX() {
            
        return m_x;
                
    }

    /**
     *
     * @param x
     */
    public void setX(float x) {
            
        this.m_x = x;
                
    }

    /**
     *
     * @return
     */
    public float getY() {
            
        return m_y;
                
    }

    /**
     *
     * @param y
     */
    public void setY(float y) {
            
        this.m_y = y;
                
    }

    /**
     *
     * @param r
     * @return
     */
    public boolean equals(Vector2f r) {
            
        return m_x == r.getX() && m_y == r.getY();
                
    }
    
}