package com.renascienza.memoryofdroids.model;

import java.io.Serializable;

/**
 * Created by claudio on 27/03/14.
 */
public class Orientation implements Serializable {
    private float xAngle;
    private float yAngle;
    private float zAngle;

    public float getXAngle() {
        return xAngle;
    }

    public void setXAngle(float xAngle) {
        this.xAngle = xAngle;
    }

    public float getYAngle() {
        return yAngle;
    }

    public void setYAngle(float yAngle) {
        this.yAngle = yAngle;
    }

    public float getZAngle() {
        return zAngle;
    }

    public void setZAngle(float zAngle) {
        this.zAngle = zAngle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Orientation)) return false;

        Orientation position = (Orientation) o;

        if (Float.compare(position.xAngle, xAngle) != 0) return false;
        if (Float.compare(position.yAngle, yAngle) != 0) return false;
        if (Float.compare(position.zAngle, zAngle) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (xAngle != +0.0f ? Float.floatToIntBits(xAngle) : 0);
        result = 31 * result + (yAngle != +0.0f ? Float.floatToIntBits(yAngle) : 0);
        result = 31 * result + (zAngle != +0.0f ? Float.floatToIntBits(zAngle) : 0);
        return result;
    }
}
