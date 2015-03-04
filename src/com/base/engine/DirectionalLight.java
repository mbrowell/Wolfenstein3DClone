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
public class DirectionalLight extends BaseLight {

    private Vector3f m_direction;
    
    /**
     *
     * @param colour
     * @param intensity
     * @param base
     * @param direction
     */
    public DirectionalLight(Vector3f colour, float intensity, Vector3f direction) {
        
        super(colour, intensity);
        this.m_direction = direction.normalized();
        
    }

    public Vector3f getM_direction() {
        
        return m_direction;
        
    }
    
    public void setM_direction(Vector3f direction) {
        
        this.m_direction = direction;
        
    }
    
}
