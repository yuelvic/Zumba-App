package org.bitbucket.yuelvic.zumba.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yuelvic on 10/10/16.
 */
public class Video implements Parcelable {

    private String url;
    private String thumbnail;
    private String title;
    private String artist;
    private long duration;
    private Type type;

    public Video () {

    }

    protected Video(Parcel in) {
        url = in.readString();
        thumbnail = in.readString();
        title = in.readString();
        artist = in.readString();
        duration = in.readLong();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
        parcel.writeString(thumbnail);
        parcel.writeString(title);
        parcel.writeString(artist);
        parcel.writeLong(duration);
    }
}
