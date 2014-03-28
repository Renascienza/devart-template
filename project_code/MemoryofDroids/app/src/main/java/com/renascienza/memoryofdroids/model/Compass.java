package com.renascienza.memoryofdroids.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by claudio on 27/03/14.
 */
public class Compass implements Serializable {

    private float[] geomagnetics;
    private float[] gravity;

    public float[] getGeomagnetics() {
        return geomagnetics;
    }

    public void setGeomagnetics(float[] geomagnetics) {
        this.geomagnetics = geomagnetics;
    }

    public float[] getGravity() {
        return gravity;
    }

    public void setGravity(float[] gravity) {
        this.gravity = gravity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Compass)) return false;

        Compass compass = (Compass) o;

        if (!Arrays.equals(geomagnetics, compass.geomagnetics)) return false;
        if (!Arrays.equals(gravity, compass.gravity)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = geomagnetics != null ? Arrays.hashCode(geomagnetics) : 0;
        result = 31 * result + (gravity != null ? Arrays.hashCode(gravity) : 0);
        return result;
    }
}
