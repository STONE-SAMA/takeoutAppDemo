package com.example.take_out_app;

import java.io.Serializable;

public class GoodsDetails implements Serializable {
    String prodId,prodName;
    double prodPrice,prodTotal;
    int prodCount,prodImg;
    public GoodsDetails(String ProdId,String ProdName,int ProdImg,double ProdPrice,int ProdCount,double ProdTotal){
        this.prodId=ProdId;
        this.prodName=ProdName;
        this.prodImg=ProdImg;
        this.prodPrice=ProdPrice;
        this.prodCount=ProdCount;
        this.prodTotal=ProdTotal;
    }
    public void setProdId(String prodId) {
        this.prodId = prodId;
    }
    public String getProdId() {
        return prodId;
    }
    public void setProdName(String prodName){
        this.prodName=prodName;
    }
    public String getProdName(){
        return prodName;
    }
    public void setProdImg(int prodImg){
        this.prodImg=prodImg;
    }
    public int getProdImg(){
        return prodImg;
    }
    public void setProdPrice(double prodPrice) {
        this.prodPrice = prodPrice;
    }
    public double getProdPrice() {
        return prodPrice;
    }
    public void setProdCount(int prodCount){
        this.prodCount=prodCount;
    }
    public int getProdCount(){
        return prodCount;
    }
    public void setProdTotal(double prodTotal) {
        this.prodTotal = prodTotal;
    }
    public double getProdTotal() {
        return prodTotal;
    }
}
