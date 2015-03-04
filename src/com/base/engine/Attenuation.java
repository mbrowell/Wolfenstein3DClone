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
public class Attenuation {

    private float m_constant;
    private float m_linear;
    private float m_exponent;
    
    public Attenuation(float constant, float linear, float exponent) {
        
        this.m_constant = constant;
        this.m_linear = linear;
        this.m_exponent = exponent;
        
    }

    public float getM_constant() {
        
        return m_constant;
        
    }

    public void setM_constant(float constant) {
        
        this.m_constant = constant;
        
    }

    public float getM_linear() {
        
        return m_linear;
        
    }

    public void setM_linear(float linear) {
        
        this.m_linear = linear;
        
    }

    public float getM_exponent() {
        
        return m_exponent;
        
    }

    public void setM_exponent(float exponent) {
        
        this.m_exponent = exponent;
        
    }
    
}
