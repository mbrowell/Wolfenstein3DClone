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
public class Game {
    
    private static GameLevel m_level;
        
    /**
     *
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public Game() {
        
        Player player = new Player(new Vector3f(10, 0.4375f, 20));
        
        m_level = new GameLevel("level1.png", "WolfCollection.png", player);
        
        Transform.setM_camera(player.getM_camera());
        Transform.setProjection(70, Window.getWidth(), Window.getHeight(), .01f, 1000);
            
    }
    
    public void input() {
        
       m_level.input();
        
    }
    
    /**
     *
     */
    public void update() {
        
        m_level.update();
        
    }
    
    /**
     *
     */
    public void render() {
        
        m_level.render();
        
    }

    public static GameLevel getM_level() {
        
        return m_level;
        
    }
    
}
