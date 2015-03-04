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
public class PointLight extends BaseLight {

    private Attenuation m_atten;
    private Vector3f m_position;
    private float m_range;

    public PointLight(Vector3f colour, float intensity, Attenuation atten, Vector3f position, float range) {
        
        super(colour, intensity);
        this.m_atten = atten;
        this.m_position = position;
        this.m_range = range;
        
    }

    public Attenuation getM_atten() {
        
        return m_atten;
        
    }

    public void setM_atten(Attenuation atten) {
        
        this.m_atten = atten;
        
    }

    public Vector3f getM_position() {
        
        return m_position;
    }

    public void setM_position(Vector3f position) {
        
        this.m_position = position;
        
    }

    public float getM_range() {
        return m_range;
    }

    public void setM_range(float m_range) {
        this.m_range = m_range;
    }
    
}
