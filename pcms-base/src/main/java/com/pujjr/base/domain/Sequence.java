package com.pujjr.base.domain;

public class Sequence {
    private String id;

    private String name;

    private String format;

    private Integer curval;

    private Integer increment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getCurval() {
        return curval;
    }

    public void setCurval(Integer curval) {
        this.curval = curval;
    }

    public Integer getIncrement() {
        return increment;
    }

    public void setIncrement(Integer increment) {
        this.increment = increment;
    }
}