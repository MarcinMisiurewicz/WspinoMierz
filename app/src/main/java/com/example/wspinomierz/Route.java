package com.example.wspinomierz;

import android.location.Location;

public class Route {
    private String name;
    private Integer grade;
    private Location location;
    private Integer userGrade;
    private Integer routeTime;
    private Integer pitchNumber;

    public Route(String n, Integer g, Location l, Integer p) {
        name = n;
        grade = g;
        location = l;
        pitchNumber = p;
    }

    public void setUserGrade(Integer userGrade) {
        this.userGrade = userGrade;
    }

    public void setRouteTime(Integer routeTime) {
        this.routeTime = routeTime;
    }

    public String getName() {
        return name;
    }

    public Integer getGrade() {
        return grade;
    }

    public Location getLocation() {
        return location;
    }

    public Integer getUserGrade() {
        return userGrade;
    }

    public Integer getRouteTime() {
        return routeTime;
    }

    public Integer getPitchNumber() {
        return pitchNumber;
    }

    public String pprint() {
        return("name: " + this.name + "; grade: " + this.grade.toString());
    }
}
