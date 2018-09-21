package com.bkwong.gistbrowserapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Gist implements Parcelable {

    @SerializedName("url")
    private String url;

    @SerializedName("description")
    private String description;

    @SerializedName("owner")
    private Owner owner;

    @SerializedName("files")
    private Map<String, File> files = new HashMap<String, File>();


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @JsonAnyGetter
    public Map<String, File> getAdditionalProperties() {
        return this.files;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, File value) {
        this.files.put(name, value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.description);
    }

    private Gist(Parcel in) {
        this.url = in.readString();
        this.description = in.readString();
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
