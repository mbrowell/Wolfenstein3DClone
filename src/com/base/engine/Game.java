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
public class Game extends Camera {
    
    private static GameLevel m_level;
        
    /**
     *
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public Game() {
        
        super();
        
        m_level = new GameLevel("level1.png", "WolfCollection.png");
        
        Transform.setM_camera(this);
        Transform.setProjection(70, Window.getWidth(), Window.getHeight(), .01f, 1000);
            
    }
    
    /**
     *
     */
    public void updateGame() {
        
        Transform.getM_camera().input();
        
    }
    
    /**
     *
     */
    public void render() {
        
        m_level.render();
        
    }
    
}
