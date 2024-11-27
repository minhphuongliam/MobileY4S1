package com.example.firebasedemo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Voucher implements Serializable, Parcelable {
    private String id;        // Mã định danh duy nhất cho voucher
    private String code;      // Mã voucher mà người dùng sẽ nhập
    private String description;      // Mô tả ngắn gọn về voucher
    private String type;     // Loại giảm giá (Percentage, Fixed Amount)
    private double value;    // Giá trị giảm giá
    private double minimumOrderValue; // Giá trị đơn hàng tối thiểu để áp dụng voucher
    private Date startDate;          // Ngày bắt đầu hiệu lực của voucher
    private Date endDate;            // Ngày hết hạn của voucher
    private boolean isActive;

    public Voucher() {
    }

    public Voucher(String id, String code, String description, String type, double value, double minimumOrderValue, Date startDate, Date endDate, boolean isActive) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.type = type;
        this.value = value;
        this.minimumOrderValue = minimumOrderValue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
    }

    protected Voucher(Parcel in) {
        id = in.readString();
        code = in.readString();
        description = in.readString();
        type = in.readString();
        value = in.readDouble();
        minimumOrderValue = in.readDouble();
        long startDateMillis = in.readLong();
        startDate = startDateMillis == -1 ? null : new Date(startDateMillis);
        long endDateMillis = in.readLong();
        endDate = endDateMillis == -1 ? null : new Date(endDateMillis);
        isActive = in.readByte() != 0;
    }

    public static final Creator<Voucher> CREATOR = new Creator<Voucher>() {
        @Override
        public Voucher createFromParcel(Parcel in) {
            return new Voucher(in);
        }

        @Override
        public Voucher[] newArray(int size) {
            return new Voucher[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getMinimumOrderValue() {
        return minimumOrderValue;
    }

    public void setMinimumOrderValue(double minimumOrderValue) {
        this.minimumOrderValue = minimumOrderValue;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(code);
        parcel.writeString(description);
        parcel.writeString(type);
        parcel.writeDouble(value);
        parcel.writeDouble(minimumOrderValue);
        parcel.writeLong(startDate != null ? startDate.getTime() : -1);
        parcel.writeLong(endDate != null ? endDate.getTime() : -1);
        parcel.writeByte((byte) (isActive ? 1 : 0));
    }
}
