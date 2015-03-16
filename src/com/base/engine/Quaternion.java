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
public class Quaternion {
    
    private float m_x;
    private float m_y;
    private float m_z;
    private float m_w;

    /**
     *
     * @param x
     * @param y
     * @param z
     * @param w
     */
    public Quaternion(float x, float y, float z, float w) {
            
        m_x = x;
        m_y = y;
        m_z = z;
        m_w = w;
                
    }

    /**
     *
     * @return
     */
    public float length() {
            
        return (float)Math.sqrt(m_x * m_x + m_y * m_y + m_z * m_z + m_w * m_w);
                
    }
	
    /**
     *
     * @return
     */
    public Quaternion normalized() {
            
        float length = length();
		
        return new Quaternion(m_x / length, m_y / length, m_z / length, m_w / length);
                
    }
	
    /**
     *
     * @return
     */
    public Quaternion conjugate() {
            
		return new Quaternion(-m_x, -m_y, -m_z, m_w);
                
    }

    /**
     *
     * @param r
     * @return
     */
    public Quaternion multiply(Quaternion r) {
            
        float w_ = m_w * r.getM_w() - m_x * r.getM_x() - m_y * r.getM_y() - m_z * r.getM_z();
        float x_ = m_x * r.getM_w() + m_w * r.getM_x() + m_y * r.getM_z() - m_z * r.getM_y();
        float y_ = m_y * r.getM_w() + m_w * r.getM_y() + m_z * r.getM_x() - m_x * r.getM_z();
        float z_ = m_z * r.getM_w() + m_w * r.getM_z() + m_x * r.getM_y() - m_y * r.getM_x();
	
        return new Quaternion(x_, y_, z_, w_);
                
    }
	
    /**
     *
     * @param r
     * @return
     */
    public Quaternion multiply(Vector3f r) {
            
        float w_ = -m_x * r.getM_x() - m_y * r.getM_y() - m_z * r.getM_z();
        float x_ =  m_w * r.getM_x() + m_y * r.getM_z() - m_z * r.getM_y();
        float y_ =  m_w * r.getM_y() + m_z * r.getM_x() - m_x * r.getM_z();
        float z_ =  m_w * r.getM_z() + m_x * r.getM_y() - m_y * r.getM_x();
		
	return new Quaternion(x_, y_, z_, w_);
                
    }

    public float getM_x() {
        
        return m_x;
        
    }

    public void setM_x(float m_x) {
        
        this.m_x = m_x;
        
    }

    public float getM_y() {
        
        return m_y;
        
    }

    public void setM_y(float m_y) {
        
        this.m_y = m_y;
        
    }

    public float getM_z() {
        
        return m_z;
        
    }

    public void setM_z(float m_z) {
        
        this.m_z = m_z;
        
    }
    
    /**
     *
     * @return
     */
    public float getM_w() {
            
    	return m_w;
                
    }

    /**
     *
     * @param w
     */
    public void setM_w(float w) {
            
        this.m_w = w;
                
    }

}