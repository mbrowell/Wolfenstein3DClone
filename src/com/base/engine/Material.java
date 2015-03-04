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
public class Material {

    private Texture m_texture;
    private Vector3f m_colour;
    private float m_specularIntensity;
    private float m_specularExponent;
    
    /**
     *
     * @param texture
     */
    public Material(Texture texture) {
        
        this(texture, new Vector3f(1, 1, 1));
        
    }

    /**
     *
     * @param texture
     * @param colour
     */
    public Material(Texture texture, Vector3f colour) {
        
        this(texture, colour, 2, 32);
        
    }
    
    /**
     *
     * @param texture
     * @param colour
     * @param specularIntensity
     * @param specularExponent
     */
    public Material(Texture texture, Vector3f colour, float specularIntensity, float specularExponent) {
        
        this.m_texture = texture;
        this.m_colour = colour;
        this.m_specularIntensity = specularIntensity;
        this.m_specularExponent = specularExponent;
        
    }

    /**
     *
     * @return
     */
    public Texture getM_texture() {
        
        return m_texture;
        
    }

    /**
     *
     * @param texture
     */
    public void setM_texture(Texture texture) {
        
        this.m_texture = texture;
        
    }

    /**
     *
     * @return
     */
    public Vector3f getM_colour() {
        
        return m_colour;
        
    }

    /**
     *
     * @param colour
     */
    public void setM_colour(Vector3f colour) {
        
        this.m_colour = colour;
        
    }

    public float getM_specularIntensity() {
        
        return m_specularIntensity;
        
    }

    public void setM_specularIntensity(float specularIntensity) {
        
        this.m_specularIntensity = specularIntensity;
        
    }

    public float getM_specularExponent() {
        
        return m_specularExponent;
        
    }

    public void setM_specularExponent(float specularExponent) {
        
        this.m_specularExponent = specularExponent;
        
    }
    
}
