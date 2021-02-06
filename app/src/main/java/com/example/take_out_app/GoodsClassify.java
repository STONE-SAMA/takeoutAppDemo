package com.example.take_out_app;

import java.util.List;

public class GoodsClassify {
    String typeName;
    List<GoodsDetails> prodList;
    int count;
    public GoodsClassify(String TypeName, List<GoodsDetails> ProdList,int Count){
        this.typeName=TypeName;
        this.prodList=ProdList;
        this.count=Count;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public String getTypeName(){
        return typeName;
    }
    public void setProdList(List<GoodsDetails> prodList) {
        this.prodList = prodList;
    }
    public List<GoodsDetails> getProdList() {
        return prodList;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }
}
