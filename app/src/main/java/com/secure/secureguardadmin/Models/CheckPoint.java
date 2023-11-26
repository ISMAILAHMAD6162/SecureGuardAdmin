package com.secure.secureguardadmin.Models;

public class CheckPoint {

    public String title;
    private String radious;
    private String latitude;
    private String longitude;
    private int state;

    public  CheckPoint()
    {

    }

    public CheckPoint(String title,String radious,String latitude,String longitude,int state) {

        this.title = title;
        this.radious = radious;
        this.latitude = latitude;
        this.longitude = longitude;
        this.state = state;

    }


}
