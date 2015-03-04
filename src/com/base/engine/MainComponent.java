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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael Browell <mbrowell1984@gmail.com>
 */
public class MainComponent extends Game {
    
    /**
     *
     */
    public static final int WIDTH = 1024;

    /**
     *
     */
    public static final int HEIGHT = 768;

    /**
     *
     */
    public static final String TITLE = "3d Game Engine";

    /**
     *
     */
    public static final double FRAME_CAP = 5000.0;
    
    private boolean m_isRunning;
    
    /**
     *
     */
    public MainComponent() {
        
        super();
        
        System.out.println(RenderUtil.getOpenGLVersion());
        RenderUtil.initGraphics();
        m_isRunning = false;
        
    }
    
    /**
     *
     */
    public void start() {
        
        if(m_isRunning) {
            
            return;
            
        }
            
        try {
            
            run();
            
        } catch (InterruptedException e) {
            
            Logger.getLogger(MainComponent.class.getName()).log(Level.SEVERE, null, e);
            
        }
        
    }
    
    /**
     *
     */
    public void stop() {
        
        if(!m_isRunning) {
            
            return;
            
        }
        
        m_isRunning = false;
        
    }
    
    @SuppressWarnings("SleepWhileInLoop")
    private void run() throws InterruptedException {
        
        m_isRunning = true;
        
        int frames = 0;
        long frameCounter = 0;
        
        final double frameTime  = 1.0 / FRAME_CAP;
        
        long lastTime = Time.getTime();
        double unprocessedTime = 0;
        
        while(m_isRunning) {
            
            boolean render = false;
            
            long startTime = Time.getTime();
            long passedTime = startTime - lastTime;
            lastTime = startTime;
            
            unprocessedTime += passedTime / (double)Time.SECOND;
            frameCounter += passedTime;
            
            while(unprocessedTime > frameTime) {
                
                render = true;
                
                unprocessedTime -= frameTime;
                
                if(Window.isCloseRequested()) {
                
                    stop();
                
                }
                
                Time.setM_delta((float)frameTime);
                
                input();
                
                
                updateGame();
                
                if(frameCounter >= Time.SECOND) {
                
                    System.out.println(frames);
                    frames = 0;
                    frameCounter = 0;
                    
                }
                
            }
            
            if(render) {
                
                doRender();
                frames++;
                
            } else {
                
                try {
                    
                    Thread.sleep(1);
                    
                } catch(InterruptedException e) {
                    
                    Logger.getLogger(MainComponent.class.getName()).log(Level.SEVERE, null, e);
                    
                }
                
            }
                
            
        }
        
        cleanUp();
        
    }
    
    private void doRender() {
        
        RenderUtil.clearScreen();
        super.render();
        Window.render();
        
    }
    
    private void cleanUp() {
        
        Window.dispose();
        
    }
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        
        Window.createWindow(WIDTH, HEIGHT, TITLE);
        
        MainComponent mainComponent = new MainComponent();
        
        mainComponent.start();
        
    }
    
}
