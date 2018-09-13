package com.example.myview.bean;

/**
 * Created by luchunhao on 2018/5/9.
 */

public class DataBean {
    private String name;
    private int value;
    private int date;
    private String time;

    public DataBean(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public DataBean() {
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
