package com.renascienza.memoryofdroids.model;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.JsonWriter;
import android.util.Log;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by claudio on 27/03/14.
 */
public class TimePhoto implements Serializable {

    private long latitude;
    private long longitude;

    private Compass compass;

    private Orientation position;

    private List<Snapshot> trail = new ArrayList<Snapshot>();

    private String name;

    private int counter = 0;

    public TimePhoto(String name) throws IOException {
        this.name = name;
    }

    private TimePhoto() {

    }

    public static TimePhoto read(InputStream in) {

        TimePhoto me = new TimePhoto();

        return me;

    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public Compass getCompass() {
        return compass;
    }

    public void setCompass(Compass compass) {
        this.compass = compass;
    }

    public Orientation getPosition() {
        return position;
    }

    public void setPosition(Orientation position) {
        this.position = position;
    }

    public List<Snapshot> getTrail() {
        return Collections.unmodifiableList(this.trail);
    }

    public void setTrail(List<Snapshot> trail) {

        if (trail == null) return;

        this.trail.clear();
        this.trail.addAll(trail);
    }

    public void addSnapshot(Snapshot taked) {
        this.trail.add(taked);
    }

    public void removeSnapshot(Snapshot taked) {
        this.trail.remove(taked);
    }

    public void snapshot(Bitmap image) {

        try {

            String imageName = this.name + "_" + this.counter++;

            File file = new File(this.name + "tpz");
            if (file.exists()) {

                ZipFile fromFile = new ZipFile(file);
                File tempFile = File.createTempFile("timephoto_temp", "tmpz");

                ZipOutputStream toStream = new ZipOutputStream(new FileOutputStream(tempFile));
                try {

                    for (Enumeration<? extends ZipEntry> entries = fromFile.entries(); entries.hasMoreElements(); ) {
                        ZipEntry entry = entries.nextElement();
                        toStream.putNextEntry((ZipEntry) entry.clone());
                        this.copyStreams(fromFile.getInputStream(entry), toStream);
                        toStream.closeEntry();
                    }

                    ZipEntry snapshot = new ZipEntry(imageName);
                    toStream.putNextEntry(snapshot);
                    image.compress(Bitmap.CompressFormat.PNG, 100, toStream);
                    toStream.closeEntry();

                } finally {
                    toStream.close();
                }

                file.delete();
                tempFile.renameTo(file);

            } else {

                ZipOutputStream toStream = new ZipOutputStream(new FileOutputStream(file));
                try {
                    ZipEntry snapshot = new ZipEntry(imageName);
                    toStream.putNextEntry(snapshot);
                    image.compress(Bitmap.CompressFormat.PNG, 100, toStream);
                    toStream.closeEntry();
                } finally {
                    toStream.close();
                }

            }

            this.addSnapshot(new Snapshot(imageName, new Date()));

        } catch (IOException e) {
            Log.e("MOD", e.getLocalizedMessage(), e);
        }
    }

    private void copyStreams(InputStream in, OutputStream out) throws IOException {
        try {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        } finally {
            in.close();
            out.close();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void write(OutputStream out) throws IOException {

        PrintWriter pw = new PrintWriter(out);
        JsonWriter writer = new JsonWriter(pw);

        try {

            writer.beginObject();
            writer.name("name").value(this.name);
            writer.name("counter").value(this.counter);
            writer.name("latitude").value(this.latitude);
            writer.name("longitude").value(this.longitude);

            writer.name("compass.geomagnetics");
            writer.beginArray();
            for (float value : this.compass.getGeomagnetics()) {
                writer.value(value);
            }
            writer.endArray();

            writer.name("compass.gravity");
            writer.beginArray();
            for (float value : this.compass.getGravity()) {
                writer.value(value);
            }
            writer.endArray();

            writer.name("position.x").value(this.position.getXAngle());
            writer.name("position.y").value(this.position.getYAngle());
            writer.name("position.z").value(this.position.getZAngle());

            writer.name("trail");
            writer.beginArray();
            for (Snapshot snapshot : this.trail) {
                writer.beginObject();
                writer.name("name").value(snapshot.getName());
                writer.name("timestamp").value(snapshot.getTimestamp().getTime());
                writer.endObject();
            }
            writer.endArray();

            writer.endObject();
        } finally {
            writer.close();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimePhoto)) return false;

        TimePhoto timePhoto = (TimePhoto) o;

        if (latitude != timePhoto.latitude) return false;
        if (longitude != timePhoto.longitude) return false;
        if (compass != null ? !compass.equals(timePhoto.compass) : timePhoto.compass != null) return false;
        if (position != null ? !position.equals(timePhoto.position) : timePhoto.position != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (latitude ^ (latitude >>> 32));
        result = 31 * result + (int) (longitude ^ (longitude >>> 32));
        result = 31 * result + (compass != null ? compass.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }
}
