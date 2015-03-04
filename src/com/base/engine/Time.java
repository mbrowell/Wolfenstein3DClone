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
public class Time {
    
    static final long SECOND = 1000000000L;
        
    private static float m_delta;

    /**
     *
     * @return
     */
    public static long getTime() {
            
        return System.nanoTime();
                
    }
        
    /**
     *
     * @return
     */
    public static float getM_delta() {
            
            return m_delta;
            
        }
        
    /**
     *
     * @param delta
     */
    public static void setM_delta(float delta){
            
            Time.m_delta = delta;
            
        }
        
}
