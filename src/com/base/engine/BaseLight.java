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
public class BaseLight {

    private Vector3f m_colour;
    private float m_intensity;
    
    public BaseLight(Vector3f colour, float intensity) {
        
        this.m_colour = colour;
        this.m_intensity = intensity;
        
    }

    public Vector3f getM_colour() {
        return m_colour;
    }

    public void setM_colour(Vector3f m_colour) {
        this.m_colour = m_colour;
    }

    public float getM_intensity() {
        return m_intensity;
    }

    public void setM_intensity(float m_intensity) {
        this.m_intensity = m_intensity;
    }
    
}
