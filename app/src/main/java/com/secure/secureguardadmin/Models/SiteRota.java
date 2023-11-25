package com.secure.secureguardadmin.Models;

public class SiteRota extends Rota{

    public String ShiftId;



    public SiteRota()
    {

    }

    public SiteRota(String shiftId,String year,String month,String day)
    {
        this.ShiftId=shiftId;
        this.year=year;
        this.month=month;
        this.day=day;
    }

}
