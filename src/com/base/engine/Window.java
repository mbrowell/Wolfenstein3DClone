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
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 *
 * @author Michael Browell <mbrowell1984@gmail.com>
 */
public class Window {

    /**
     *
     * @param width
     * @param height
     * @param title
     */
    public static void createWindow(int width, int height, String title) {

        Display.setTitle(title);
        try {
            Display.setDisplayMode(new DisplayMode(width, height));

            Display.create();
            Keyboard.create();
            Mouse.create();
        } catch (LWJGLException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     */
    public static void render() {

        Display.update();

    }

    /**
     *
     */
    public static void dispose() {

        Display.destroy();
        Keyboard.destroy();
        Mouse.destroy();

    }

    /**
     *
     * @return
     */
    public static boolean isCloseRequested() {

        return Display.isCloseRequested();

    }

    /**
     *
     * @return
     */
    public static int getWidth() {

        return Display.getDisplayMode().getWidth();

    }

    /**
     *
     * @return
     */
    public static int getHeight() {

        return Display.getDisplayMode().getHeight();

    }

    /**
     *
     * @return
     */
    public static String getTitle() {

        return Display.getTitle();

    }

    /**
     *
     * @return
     */
    public Vector2f getCenter() {

        return new Vector2f(getWidth() / 2, getHeight() / 2);

    }

}
