package com.example.take_out_app;

public class Record {
    int orderID;
    String userId;
    String resName;
    String resPhone;
    String indentAddress;
    String indentPhone;
    String type;
    Double total;

    public Record(int orderID, String userId, String resName, String resPhone, String indentAddress, String indentPhone, String type, Double total) {
        this.orderID = orderID;
        this.userId = userId;
        this.resName = resName;
        this.resPhone = resPhone;
        this.indentAddress = indentAddress;
        this.indentPhone = indentPhone;
        this.type = type;
        this.total = total;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResPhone() {
        return resPhone;
    }

    public void setResPhone(String resPhone) {
        this.resPhone = resPhone;
    }

    public String getIndentAddress() {
        return indentAddress;
    }

    public void setIndentAddress(String indentAddress) {
        this.indentAddress = indentAddress;
    }

    public String getIndentPhone() {
        return indentPhone;
    }

    public void setIndentPhone(String indentPhone) {
        this.indentPhone = indentPhone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
