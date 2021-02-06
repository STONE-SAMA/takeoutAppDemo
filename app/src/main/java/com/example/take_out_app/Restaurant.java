package com.example.take_out_app;

public class Restaurant {
    String resName;
    int resPic;
    String resLevel;
    String resInfo;//店家简要介绍
    String resOff;//优惠信息
    String resSell;//销量
    String resID;

    public Restaurant(String resName, int resPic, String resLevel, String resInfo, String resOff, String resSell, String resID) {
        this.resName = resName;
        this.resPic = resPic;
        this.resLevel = resLevel;
        this.resInfo = resInfo;
        this.resOff = resOff;
        this.resSell = resSell;
        this.resID = resID;
    }

    public int getResPic() {
        return resPic;
    }

    public void setResPic(int resPic) {
        this.resPic = resPic;
    }

    public String getResLevel() {
        return resLevel;
    }

    public void setResLevel(String resLevel) {
        this.resLevel = resLevel;
    }

    public String getResInfo() {
        return resInfo;
    }

    public void setResInfo(String resInfo) {
        this.resInfo = resInfo;
    }

    public String getResSell() {
        return resSell;
    }

    public void setResSell(String resSell) {
        this.resSell = resSell;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResOff() {
        return resOff;
    }

    public void setResOff(String resOff) {
        this.resOff = resOff;
    }

    public String getResID() {
        return resID;
    }

    public void setResID(String resID) {
        this.resID = resID;
    }
}
