package com.secure.secureguardadmin.Models;

public class Guard {

    public  String title;
    public  String leicenNo;
    public  String profilePic;
    public int State;
    public String experi_date;

    public Guard()
    {

    }

    public  Guard(String title,String leicenNo,String profilePic,int state,String experi_date)
    {

        this.title=title;
        this.leicenNo=leicenNo;
        this.profilePic=profilePic;
        this.State=state;
        this.experi_date=experi_date;

    }




}
