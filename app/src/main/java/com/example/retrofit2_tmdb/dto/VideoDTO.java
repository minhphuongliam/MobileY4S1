package com.example.retrofit2_tmdb.dto;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoDTO implements Parcelable {
    @SerializedName("id")
    private Integer videoId;

    @SerializedName("results")
    private List<VideoResultDTO> results;

    protected VideoDTO(Parcel in) {
        if (in.readByte() == 0) {
            videoId = null;
        } else {
            videoId = in.readInt();
        }
        results = in.createTypedArrayList(VideoResultDTO.CREATOR);
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public List<VideoResultDTO> getResults() {
        return results;
    }

    public void setResults(List<VideoResultDTO> results) {
        this.results = results;
    }

    public static final Creator<VideoDTO> CREATOR = new Creator<VideoDTO>() {
        @Override
        public VideoDTO createFromParcel(Parcel in) {
            return new VideoDTO(in);
        }

        @Override
        public VideoDTO[] newArray(int size) {
            return new VideoDTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        if (videoId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(videoId);
        }
        parcel.writeTypedList(results);
    }
}
