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
public class Vector3f {
    
    private float m_x;
    private float m_y;
    private float m_z;

    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public Vector3f(float x, float y, float z) {
        
        m_x = x;
        m_y = y;
        m_z = z;

    }

    /**
     *
     * @return
     */
    public float length() {

        return (float)Math.sqrt(m_x * m_x + m_y * m_y + m_z * m_z);

    }

    /**
     *
     * @param r
     * @return
     */
    public float dot(Vector3f r) {

        return m_x * r.getM_x() + m_y * r.getM_y() + m_z * r.getM_z();

    }

    /**
     *
     * @param r
     * @return
     */
    public Vector3f cross(Vector3f r) {

        float x_ = m_y * r.getM_z() - m_z * r.getM_y();
        float y_ = m_z * r.getM_x() - m_x * r.getM_z();
        float z_ = m_x * r.getM_y() - m_y * r.getM_x();

        return new Vector3f(x_, y_, z_);

    }

    /**
     *
     * @return
     */
    public Vector3f normalized() {

        float length = length();

        return new Vector3f(m_x / length, m_y / length, m_z / length);

    }

    /**
     *
     * @param angle
     * @param axis
     * @return
     */
    public Vector3f rotate(float angle, Vector3f axis) {
        
        float sinHalfAngle = (float)Math.sin(Math.toRadians(angle / 2));
        float cosHalfAngle = (float)Math.cos(Math.toRadians(angle / 2));
        
        float rX = axis.getM_x() * sinHalfAngle;
        float rY = axis.getM_y() * sinHalfAngle;
        float rZ = axis.getM_z() * sinHalfAngle;
        float rW = cosHalfAngle;
        
        Quaternion rotation = new Quaternion(rX, rY, rZ, rW);
        Quaternion conjugate = rotation.conjugate();
        
        Quaternion w = rotation.multiply(this).multiply(conjugate);

        return new Vector3f(w.getM_x(), w.getM_y(), w.getM_z());

    }

    /**
     *
     * @param r
     * @return
     */
    public Vector3f add(Vector3f r) {

        return new Vector3f(m_x + r.getM_x(), m_y + r.getM_y(), m_z + r.getM_z());

    }

    /**
     *
     * @param r
     * @return
     */
    public Vector3f add(float r) {

        return new Vector3f(m_x + r, m_y + r, m_z + r);

    }

    /**
     *
     * @param r
     * @return
     */
    public Vector3f subtract(Vector3f r) {

        return new Vector3f(m_x - r.getM_x(), m_y - r.getM_y(), m_z - r.getM_z());

    }

    /**
     *
     * @param r
     * @return
     */
    public Vector3f subtract(float r) {

        return new Vector3f(m_x - r, m_y - r, m_z - r);

    }

    /**
     *
     * @param r
     * @return
     */
    public Vector3f multiply(Vector3f r) {

        return new Vector3f(m_x * r.getM_x(), m_y * r.getM_y(), m_z * r.getM_z());

    }

    /**
     *
     * @param r
     * @return
     */
    public Vector3f multiply(float r) {

        return new Vector3f(m_x * r, m_y * r, m_z * r);

    }

    /**
     *
     * @param r
     * @return
     */
    public Vector3f divide(Vector3f r) {

        return new Vector3f(m_x / r.getM_x(), m_y / r.getM_y(), m_z / r.getM_z());

    }

    /**
     *
     * @param r
     * @return
     */
    public Vector3f divide(float r) {

        return new Vector3f(m_x / r, m_y / r, m_z / r);

    }
    
    /**
     *
     * @return
     */
    public Vector3f abs() {
        
        return new Vector3f(Math.abs(m_x), Math.abs(m_y), Math.abs(m_z));
        
    }

    public float getM_x() {
        
        return m_x;
        
    }

    public void setM_x(float m_x) {
        
        this.m_x = m_x;
        
    }

    public float getM_y() {
        
        return m_y;
        
    }

    public void setM_y(float m_y) {
        
        this.m_y = m_y;
        
    }
    
    /**
     *
     * @return
     */
    public float getM_z() {

        return m_z;

    }

    /**
     *
     * @param z
     */
    public void setM_z(float z) {

        this.m_z = z;

    }
}