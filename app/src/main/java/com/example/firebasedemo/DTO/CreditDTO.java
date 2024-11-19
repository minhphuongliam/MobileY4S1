package com.example.firebasedemo.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreditDTO implements Parcelable {
    @SerializedName("id")
    private Integer creditId;

    @SerializedName("cast")
    private List<CastDTO> castDTOS;

    @SerializedName("crew")
    private List<CrewDTO> crewDTOS;

    protected CreditDTO(Parcel in) {
        if (in.readByte() == 0) {
            creditId = null;
        } else {
            creditId = in.readInt();
        }
        castDTOS = in.createTypedArrayList(CastDTO.CREATOR);
        crewDTOS = in.createTypedArrayList(CrewDTO.CREATOR);
    }

    public Integer getCreditId() {
        return creditId;
    }

    public List<CastDTO> getCastDTOS() {
        return castDTOS;
    }

    public List<CrewDTO> getCrewDTOS() {
        return crewDTOS;
    }

    public static final Creator<CreditDTO> CREATOR = new Creator<CreditDTO>() {
        @Override
        public CreditDTO createFromParcel(Parcel in) {
            return new CreditDTO(in);
        }

        @Override
        public CreditDTO[] newArray(int size) {
            return new CreditDTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        if (creditId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(creditId);
        }
        parcel.writeTypedList(castDTOS);
        parcel.writeTypedList(crewDTOS);
    }

    @Override
    public String toString() {
        return "CreditDTO{" +
                "creditId=" + creditId +
                ", castDTOS=" + castDTOS +
                ", crewDTOS=" + crewDTOS +
                '}';
    }
}
