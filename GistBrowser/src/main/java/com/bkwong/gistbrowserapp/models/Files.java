package com.bkwong.gistbrowserapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Files implements Parcelable{


    @SerializedName("filename")
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.fileName);
    }

    private Files(Parcel in) {
        this.fileName = in.readString();
    }

    public static final Parcelable.Creator<Files> CREATOR = new Parcelable.Creator<Files>() {
        @Override
        public Files createFromParcel(Parcel source) {
            return new Files(source);
        }

        @Override
        public Files[] newArray(int size) {
            return new Files[size];
        }
    };
}
