package com.queenzend.marketingplatformandroid.DaoClasses;
import java.io.Serializable;

public class EventDao implements Serializable {
    String title, location,start_date,end_date;

    private static EventDao instance = null;
    public static EventDao getInstance() {
        if (instance == null) {
            instance = new EventDao();
        }
        return instance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }}