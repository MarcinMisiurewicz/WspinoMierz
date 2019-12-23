package com.example.wspinomierz.ui;

import android.location.Location;

public class Route {
    private String name;
    private Integer grade;
    private Location location;
    private Integer userGrade;
    private Integer routeTime;
    private Integer pitchNumber;

    public Route(String n, Integer g, Location l, Integer u, Integer r, Integer p) {
        name = n;
        grade = g;
        location = l;
        userGrade = u;
        routeTime = r;
        pitchNumber = p;
    }

    public String pprint() {
        return("name: " + this.name + "; grade: " + this.grade.toString());
    }
}
