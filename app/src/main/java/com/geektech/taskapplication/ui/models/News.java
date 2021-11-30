package com.geektech.taskapplication.ui.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class News implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private long time;
    private String docId;

    public News(){

    }

    public News(String title, long time) {
        this.title = title;
        this.time = time;
    }

    public String getDocId(){
        return docId;
    }

    public void setDocId(String docId){
        this.docId = docId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
