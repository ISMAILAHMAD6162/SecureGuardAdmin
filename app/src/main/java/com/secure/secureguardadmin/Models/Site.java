package com.secure.secureguardadmin.Models;

import java.util.stream.Stream;

public class Site {

    public String title;
    public String siteClientId;
    public String supervisorName;
    public String locationLatitude;
    public String locationLongitude;
    public int state;
    public int alertState;
    public int alramState;
    public String email;
    public String phoneNo;
    public String address;
    public String startDate;
    public  String endDate;

public Site()
{

}
    public Site(String title,String supervisorName,String locationLatitude,String locationLongitude,int state,String siteClientId, String email, String phoneNo, String address, String startDate, String endDate,int alertState,int alramState)
    {
      this.title=title;
      this.supervisorName=supervisorName;
      this.locationLatitude=locationLatitude;
      this.locationLongitude=locationLongitude;
      this.state=state;
      this.siteClientId=siteClientId;
      this.email=email;
      this.phoneNo=phoneNo;
      this.address=address;
      this.startDate=startDate;
      this.endDate=endDate;
      this.alertState=alertState;
      this.alramState=alramState;
    }



}
