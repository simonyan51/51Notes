package com.example.a51notes.pojos;

import android.widget.TextView;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by simonyan51 on 6/4/17.
 */
@Parcel
public class Note implements Serializable {
    static final long serialVersionUID = -1;

    private long id;
    private String title;
    private String description;
    private Date createDate;
    private Date alarmDate;
    private int color;
    private boolean important;

    @ParcelConstructor
    public Note(String title, String description, int color, Date alarmDate, boolean important) {
        this.title = title;
        this.description = description;
        this.color = color;
        this.alarmDate = alarmDate;
        this.important = important;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    public Date getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(Date alarmDate) {
        this.alarmDate = alarmDate;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createDate=" + createDate +
                ", alarmDate=" + alarmDate +
                ", color=" + color +
                ", important=" + important +
                '}';
    }
}