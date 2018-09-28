package com.assemble.ad.bean;


public class RatioBean {

    private int id;
    private String name;
    private int ratio;
    private String method;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return (name != null) ? name : "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public String getMethod() {
        return (method != null) ? method : "";
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
