package com.geektech.taskapplication.ui.models;

import java.io.Serializable;

public class News implements Serializable {

    private String title;
    private long time;

    public News(String title) {
        this.title = title;
        this.time = time;
    }

    public News(String title, long time) {
        this.title = title;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}