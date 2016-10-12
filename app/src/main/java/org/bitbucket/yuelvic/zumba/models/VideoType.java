package org.bitbucket.yuelvic.zumba.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yuelvic on 10/10/16.
 */
public class VideoType implements Parcelable {

    private String name;
    private Day day;
    private boolean isDownloaded;

    public VideoType() {

    }

    protected VideoType(Parcel in) {
        name = in.readString();
        isDownloaded = in.readByte() != 0;
    }

    public static final Creator<VideoType> CREATOR = new Creator<VideoType>() {
        @Override
        public VideoType createFromParcel(Parcel in) {
            return new VideoType(in);
        }

        @Override
        public VideoType[] newArray(int size) {
            return new VideoType[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeByte((byte) (isDownloaded ? 1 : 0));
    }
}
