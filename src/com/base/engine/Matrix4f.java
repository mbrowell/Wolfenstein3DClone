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
public class Matrix4f {

    private float[][] m_m;

    /**
     *
     */
    public Matrix4f() {
        
        m_m = new float[4][4];
        
    }

    /**
     *
     * @return
     */
    public Matrix4f initIdentity() {

        m_m[0][0] = 1;    m_m[0][1] = 0;    m_m[0][2] = 0;    m_m[0][3] = 0;
        m_m[1][0] = 0;    m_m[1][1] = 1;    m_m[1][2] = 0;    m_m[1][3] = 0;
        m_m[2][0] = 0;    m_m[2][1] = 0;    m_m[2][2] = 1;    m_m[2][3] = 0;
        m_m[3][0] = 0;    m_m[3][1] = 0;    m_m[3][2] = 0;    m_m[3][3] = 1;

        return this;

    }
    
    /**
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Matrix4f initTranslation(float x, float y, float z) {

        m_m[0][0] = 1;    m_m[0][1] = 0;    m_m[0][2] = 0;    m_m[0][3] = x;
        m_m[1][0] = 0;    m_m[1][1] = 1;    m_m[1][2] = 0;    m_m[1][3] = y;
        m_m[2][0] = 0;    m_m[2][1] = 0;    m_m[2][2] = 1;    m_m[2][3] = z;
        m_m[3][0] = 0;    m_m[3][1] = 0;    m_m[3][2] = 0;    m_m[3][3] = 1;

        return this;

    }
    
    /**
     *
     * @param fov
     * @param width
     * @param height
     * @param zNear
     * @param zFar
     * @return
     */
    public Matrix4f initProjection(float fov, float width, float height, float zNear, float zFar) {
        
        float aspectRatio = width / height;
        float tanHalfFOV = (float)Math.tan(Math.toRadians(fov / 2));
        float zRange = zNear - zFar;

        m_m[0][0] = 1.0f / (tanHalfFOV * aspectRatio); m_m[0][1] = 0;                 m_m[0][2] = 0;                        m_m[0][3] = 0;
        m_m[1][0] = 0;                                 m_m[1][1] = 1.0f / tanHalfFOV; m_m[1][2] = 0;                        m_m[1][3] = 0;
        m_m[2][0] = 0;                                 m_m[2][1] = 0;                 m_m[2][2] = (-zNear - zFar) / zRange; m_m[2][3] = 2 * zNear * zFar / zRange;
        m_m[3][0] = 0;                                 m_m[3][1] = 0;                 m_m[3][2] = 1;                        m_m[3][3] = 1;

        return this;

    }
    
    /**
     *
     * @param forward
     * @param up
     * @return
     */
    public Matrix4f initCamera(Vector3f forward, Vector3f up) {
        
        forward = forward.normalized();
        
        Vector3f right = up;
        right = right.normalized();
        right = right.cross(forward);
        
        up = forward.cross(right);

        m_m[0][0] = right.getX();   m_m[0][1] = right.getY();   m_m[0][2] = right.getZ();   m_m[0][3] = 0;
        m_m[1][0] = up.getX();      m_m[1][1] = up.getY();      m_m[1][2] = up.getZ();      m_m[1][3] = 0;
        m_m[2][0] = forward.getX(); m_m[2][1] = forward.getY(); m_m[2][2] = forward.getZ(); m_m[2][3] = 0;
        m_m[3][0] = 0;              m_m[3][1] = 0;              m_m[3][2] = 0;              m_m[3][3] = 1;

        return this;

    }
    
    /**
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Matrix4f initRotation(float x, float y, float z) {
        
        Matrix4f rotationX = new Matrix4f();
        Matrix4f rotationY = new Matrix4f();
        Matrix4f rotationZ = new Matrix4f();
        
        x = (float)Math.toRadians(x);
        y = (float)Math.toRadians(y);
        z = (float)Math.toRadians(z);

        rotationZ.m_m[0][0] = (float)Math.cos(z); rotationZ.m_m[0][1] = -(float)Math.sin(z); rotationZ.m_m[0][2] = 0;                   rotationZ.m_m[0][3] = 0;
        rotationZ.m_m[1][0] = (float)Math.sin(z); rotationZ.m_m[1][1] = (float)Math.cos(z);  rotationZ.m_m[1][2] = 0;                   rotationZ.m_m[1][3] = 0;
        rotationZ.m_m[2][0] = 0;                  rotationZ.m_m[2][1] = 0;                   rotationZ.m_m[2][2] = 1;                   rotationZ.m_m[2][3] = 0;
        rotationZ.m_m[3][0] = 0;                  rotationZ.m_m[3][1] = 0;                   rotationZ.m_m[3][2] = 0;                   rotationZ.m_m[3][3] = 1;
        
        rotationX.m_m[0][0] = 1;                  rotationX.m_m[0][1] = 0;                   rotationX.m_m[0][2] = 0;                   rotationX.m_m[0][3] = 0;
        rotationX.m_m[1][0] = 0;                  rotationX.m_m[1][1] = (float)Math.cos(x);  rotationX.m_m[1][2] = -(float)Math.sin(x); rotationX.m_m[1][3] = 0;
        rotationX.m_m[2][0] = 0;                  rotationX.m_m[2][1] = (float)Math.sin(x);  rotationX.m_m[2][2] = (float)Math.cos(x);  rotationX.m_m[2][3] = 0;
        rotationX.m_m[3][0] = 0;                  rotationX.m_m[3][1] = 0;                   rotationX.m_m[3][2] = 0;                   rotationX.m_m[3][3] = 1;
        
        rotationY.m_m[0][0] = (float)Math.cos(y); rotationY.m_m[0][1] = 0;                   rotationY.m_m[0][2] = -(float)Math.sin(y); rotationY.m_m[0][3] = 0;
        rotationY.m_m[1][0] = 0;                  rotationY.m_m[1][1] = 1;                   rotationY.m_m[1][2] = 0;                   rotationY.m_m[1][3] = 0;
        rotationY.m_m[2][0] = (float)Math.sin(y); rotationY.m_m[2][1] = 0;                   rotationY.m_m[2][2] = (float)Math.cos(y);  rotationY.m_m[2][3] = 0;
        rotationY.m_m[3][0] = 0;                  rotationY.m_m[3][1] = 0;                   rotationY.m_m[3][2] = 0;                   rotationY.m_m[3][3] = 1;

        m_m = rotationZ.multiply(rotationY.multiply(rotationX)).getM();
        
        return this;

    }
    
    /**
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Matrix4f initScale(float x, float y, float z) {

        m_m[0][0] = x;    m_m[0][1] = 0;    m_m[0][2] = 0;    m_m[0][3] = 0;
        m_m[1][0] = 0;    m_m[1][1] = y;    m_m[1][2] = 0;    m_m[1][3] = 0;
        m_m[2][0] = 0;    m_m[2][1] = 0;    m_m[2][2] = z;    m_m[2][3] = 0;
        m_m[3][0] = 0;    m_m[3][1] = 0;    m_m[3][2] = 0;    m_m[3][3] = 1;

        return this;

    }

    /**
     *
     * @param r
     * @return
     */
    public Matrix4f multiply(Matrix4f r) {

        Matrix4f result = new Matrix4f();

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {

                result.setM_m(i, j, m_m[i][0] * r.get(0, j)
                        + m_m[i][1] * r.get(1, j)
                        + m_m[i][2] * r.get(2, j)
                        + m_m[i][3] * r.get(3, j));

            }

        }

        return result;

    }

    /**
     *
     * @return
     */
    public float[][] getM() {

        float[][] result = new float[4][4];

        for (int i = 0; i < 4; i++) {

            System.arraycopy(m_m[i], 0, result[i], 0, 4);

        }

        return result;

    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public float get(int x, int y) {

        return m_m[x][y];

    }

    /**
     *
     * @param m
     */
    public void setM_m(float[][] m) {

        this.m_m = m;

    }

    /**
     *
     * @param x
     * @param y
     * @param value
     */
    public void setM_m(int x, int y, float value) {

        m_m[x][y] = value;

    }

}
