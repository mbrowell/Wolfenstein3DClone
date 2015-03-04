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
public class SpotLight extends PointLight {

    private Vector3f m_direction;
    private float m_cutoff;

    public SpotLight(Vector3f colour, float intensity, Attenuation atten, Vector3f position, float range, Vector3f direction, float cutoff) {
        
        super(colour, intensity, atten, position, range);
        this.m_direction = direction.normalized();
        this.m_cutoff = cutoff;
        
    }

    public Vector3f getM_direction() {
        
        return m_direction;
        
    }

    public void setM_direction(Vector3f direction) {
        
        this.m_direction = direction;
        
    }

    public float getM_cutoff() {
        
        return m_cutoff;
        
    }

    public void setM_cutoff(float cutoff) {
        
        this.m_cutoff = cutoff;
        
    }
    
}
