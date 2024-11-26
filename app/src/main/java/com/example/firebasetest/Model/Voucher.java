package com.example.firebasetest.Model;

import java.io.Serializable;
import java.util.Date;

public class Voucher implements Serializable {
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
}
