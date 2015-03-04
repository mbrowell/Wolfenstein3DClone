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
public class Quaternion extends Vector3f {
    
    private float m_w;

    /**
     *
     * @param x
     * @param y
     * @param z
     * @param w
     */
    public Quaternion(float x, float y, float z, float w) {
            
        super(x, y, z);
        this.m_w = w;
                
    }

    /**
     *
     * @return
     */
    @Override
    public float length() {
            
        return (float)Math.sqrt(super.getX() * super.getX() + super.getY() * super.getY() + super.getZ() * super.getZ() + m_w * m_w);
                
    }
	
    /**
     *
     * @return
     */
    @Override
    public Quaternion normalized() {
            
        float length = length();
		
        return new Quaternion(super.getX() / length, super.getY() / length, super.getZ() / length, m_w / length);
                
    }
	
    /**
     *
     * @return
     */
    public Quaternion conjugate() {
            
		return new Quaternion(-super.getX(), -super.getY(), -super.getZ(), m_w);
                
    }

    /**
     *
     * @param r
     * @return
     */
    public Quaternion multiply(Quaternion r) {
            
        float w_ = m_w * r.getW() - super.getX() * r.getX() - super.getY() * r.getY() - super.getZ() * r.getZ();
        float x_ = super.getX() * r.getW() + m_w * r.getX() + super.getY() * r.getZ() - super.getZ() * r.getY();
        float y_ = super.getY() * r.getW() + m_w * r.getY() + super.getZ() * r.getX() - super.getX() * r.getZ();
        float z_ = super.getZ() * r.getW() + m_w * r.getZ() + super.getX() * r.getY() - super.getY() * r.getX();
	
        return new Quaternion(x_, y_, z_, w_);
                
    }
	
    /**
     *
     * @param r
     * @return
     */
    @Override
    public Quaternion multiply(Vector3f r) {
            
        float w_ = -super.getX() * r.getX() - super.getY() * r.getY() - super.getZ() * r.getZ();
        float x_ =  m_w * r.getX() + super.getY() * r.getZ() - super.getZ() * r.getY();
        float y_ =  m_w * r.getY() + super.getZ() * r.getX() - super.getX() * r.getZ();
        float z_ =  m_w * r.getZ() + super.getX() * r.getY() - super.getY() * r.getX();
		
	return new Quaternion(x_, y_, z_, w_);
                
    }

    /**
     *
     * @return
     */
    public float getW() {
            
    	return m_w;
                
    }

    /**
     *
     * @param w
     */
    public void setW(float w) {
            
        this.m_w = w;
                
    }

}