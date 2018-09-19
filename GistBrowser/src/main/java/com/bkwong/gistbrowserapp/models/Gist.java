package com.bkwong.gistbrowserapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Gist implements Parcelable {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
    }

    protected Gist(Parcel in) {
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Gist> CREATOR = new Parcelable.Creator<Gist>() {
        @Override
        public Gist createFromParcel(Parcel source) {
            return new Gist(source);
        }

        @Override
        public Gist[] newArray(int size) {
            return new Gist[size];
        }
    };
}
