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
public class Transform {
    
    private static Camera m_camera;
    
    private static float m_zNear;
    private static float m_zFar;
    private static float m_width;
    private static float m_height;
    private static float m_fov; // Field of view.

    private Vector3f m_translation;
    private Vector3f m_rotation;
    private Vector3f m_scale;
    
    /**
     *
     */
    public Transform() {
        
        m_translation = new Vector3f(0, 0, 0);
        m_rotation = new Vector3f(0, 0, 0);
        m_scale = new Vector3f(1, 1, 1);
        
    }
    
    /**
     *
     * @return
     */
    public Matrix4f getTransformation() {
        
        Matrix4f translation = new Matrix4f().initTranslation(m_translation.getX(), m_translation.getY(), m_translation.getZ());
        Matrix4f rotation = new Matrix4f().initRotation(m_rotation.getX(), m_rotation.getY(), m_rotation.getZ());
        Matrix4f scale = new Matrix4f().initScale(m_scale.getX(), m_scale.getY(), m_scale.getZ());
        
        return translation.multiply(rotation.multiply(scale));
        
    }
    
    /**
     *
     * @return
     */
    public Matrix4f getProjectedTransformation() {
        
        Matrix4f transformationMatrix = getTransformation();
        Matrix4f projectionMatrix = new Matrix4f().initProjection(m_fov, m_width, m_height, m_zNear, m_zFar);
        Matrix4f cameraRotation = new Matrix4f().initCamera(m_camera.getM_forward(), m_camera.getM_up());
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(-m_camera.getM_pos().getX(), -m_camera.getM_pos().getY(), -m_camera.getM_pos().getZ());
        
        return projectionMatrix.multiply(cameraRotation.multiply(cameraTranslation.multiply(transformationMatrix)));
        
    }

    /**
     *
     * @return
     */
    public static Camera getM_camera() {
        
        return m_camera;
        
    }

    /**
     *
     * @param camera
     */
    public static void setM_camera(Camera camera) {
        
        Transform.m_camera = camera;
        
    }
    
    

    /**
     *
     * @return
     */
    public Vector3f getM_translation() {
        
        return m_translation;
        
    }
    
    /**
     *
     * @param fov
     * @param width
     * @param height
     * @param zNear
     * @param zFar
     */
    public static void setProjection(float fov, float width, float height, float zNear, float zFar) {
        
        Transform.m_fov = fov;
        Transform.m_width = width;
        Transform.m_height = height;
        Transform.m_zNear = zNear;
        Transform.m_zFar = zFar;
        
    }

    /**
     *
     * @param m_translation
     */
    public void setM_translation(Vector3f m_translation) {
        
        this.m_translation = m_translation;
        
    }
    
    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public void setM_translation(float x, float y, float z) {
        
        this.m_translation = new Vector3f(x, y, z);
        
    }

    /**
     *
     * @return
     */
    public Vector3f getM_rotation() {
        
        return m_rotation;
        
    }

    /**
     *
     * @param rotation
     */
    public void setM_rotation(Vector3f rotation) {
        
        this.m_rotation = rotation;
        
    }
    
    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public void setM_rotation(float x, float y, float z) {
        
        this.m_rotation = new Vector3f(x, y, z);
        
    }

    /**
     *
     * @return
     */
    public Vector3f getM_scale() {
        
        return m_scale;
        
    }

    /**
     *
     * @param m_scale
     */
    public void setM_scale(Vector3f m_scale) {
        
        this.m_scale = m_scale;
        
    }
    
    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public void setM_scale(float x, float y, float z) {
        
        this.m_scale = new Vector3f(x, y, z);
        
    }
    
}
