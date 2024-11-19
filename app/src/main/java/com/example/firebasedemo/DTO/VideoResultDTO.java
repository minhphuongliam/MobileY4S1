package com.example.firebasedemo.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class VideoResultDTO implements Parcelable {
    @SerializedName("name")
    private String name;

    @SerializedName("key")
    private String key;

    @SerializedName("site")
    private String site;

    @SerializedName("type")
    private String type;

    @SerializedName("official")
    private boolean official;

    @SerializedName("id")
    private String id;

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getSite() {
        return site;
    }

    public String getType() {
        return type;
    }

    public boolean isOfficial() {
        return official;
    }

    public String getId() {
        return id;
    }

    protected VideoResultDTO(Parcel in) {
        name = in.readString();
        key = in.readString();
        site = in.readString();
        type = in.readString();
        official = in.readByte() != 0;
        id = in.readString();
    }

    public static final Creator<VideoResultDTO> CREATOR = new Creator<VideoResultDTO>() {
        @Override
        public VideoResultDTO createFromParcel(Parcel in) {
            return new VideoResultDTO(in);
        }

        @Override
        public VideoResultDTO[] newArray(int size) {
            return new VideoResultDTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(key);
        parcel.writeString(site);
        parcel.writeString(type);
        parcel.writeByte((byte) (official ? 1 : 0));
        parcel.writeString(id);
    }
}
