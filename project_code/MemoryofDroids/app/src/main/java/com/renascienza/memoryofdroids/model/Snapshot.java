package com.renascienza.memoryofdroids.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by claudio on 27/03/14.
 */
public class Snapshot implements Serializable {

    private String name;
    private Date timestamp;
    private transient Bitmap bitmap;

    public Snapshot(String name, Date timestamp) {
        this.name = name;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Snapshot)) return false;

        Snapshot snapshot = (Snapshot) o;

        if (name != null ? !name.equals(snapshot.name) : snapshot.name != null) return false;
        if (timestamp != null ? !timestamp.equals(snapshot.timestamp) : snapshot.timestamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }
}
