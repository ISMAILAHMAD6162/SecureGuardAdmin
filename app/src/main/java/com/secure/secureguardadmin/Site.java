package com.secure.secureguardadmin;

import androidx.recyclerview.widget.RecyclerView;

import java.util.stream.Stream;

public class Site {

    public String title;
    public String superviour;
    public String locationLatitudel;
    public String locationLongitude;
    public int state;

    Site()
    {

    }

    Site(String title,String superviour,String locationLatitudel,String locationLongitude,int state)
    {
      this.title=title;
      this.superviour=superviour;
      this.locationLatitudel=locationLatitudel;
      this.locationLongitude=locationLongitude;
      this.state=state;
    }



}
