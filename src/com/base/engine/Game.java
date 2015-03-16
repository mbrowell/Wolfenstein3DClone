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
    private static int m_levelNum = 0;
    private static boolean m_isRunning;
        
    /**
     *
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public Game() {
        
        loadLevel();
            
    }
    
    public void input() {
        
        m_level.input();
        
    }
    
    /**
     *
     */
    public void update() {
        
        if(m_isRunning) {
            
            m_level.update();
            
        }
        
    }
    
    /**
     *
     */
    public void render() {
        
        if(m_isRunning) {
            
            m_level.render();
                    
        }
        
    }

    public static GameLevel getM_level() {
        
        return m_level;
        
    }
    
    public static void loadLevel() {
        
        m_levelNum++;
        m_level = new GameLevel("level" + m_levelNum + ".png", "WolfCollection.png");
        
        m_isRunning = true;
        
        Transform.setM_camera(m_level.getM_player().getM_camera());
        Transform.setProjection(70, Window.getWidth(), Window.getHeight(), .01f, 1000);
        
    }

    public static boolean isM_isRunning() {
        
        return m_isRunning;
        
    }

    public static void setM_isRunning(boolean isRunning) {
        
        Game.m_isRunning = isRunning;
        
    }
    
}
