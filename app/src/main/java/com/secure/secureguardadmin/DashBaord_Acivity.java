package com.secure.secureguardadmin;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.secure.secureguardadmin.guardManagment.GuardActivity;
import com.secure.secureguardadmin.site_managment_activities.SiteActivity;

public class DashBaord_Acivity extends AppCompatActivity implements Dash_Board_ItemInterface{



    private  RecyclerView dashBoard_recyclerView;
    String []arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        dashBoard_recyclerView=findViewById(R.id.dash_baord_recyclerview);
        arr=new String[]{"SITE MANAGMENT","GUARDS","REPORTS","ABC","ABC"};
        DashBoard_RecyclerView dashBoardRecyclerView=new DashBoard_RecyclerView(arr,this::ItemClick);



        GridLayoutManager layoutManager=new GridLayoutManager(this,2);


        dashBoard_recyclerView.setLayoutManager(layoutManager);
        dashBoard_recyclerView.setAdapter(dashBoardRecyclerView);



    }

    @Override
    public void ItemClick(int pstion) {

        if(pstion==0) {
            Intent intent = new Intent(getApplicationContext(), SiteActivity.class);
            startActivity(intent);
        }
        if (pstion==1)
        {
            Toast.makeText(getApplicationContext(),"postion is"+pstion,Toast.LENGTH_LONG).show();

            Intent guard=new Intent(getApplicationContext(), GuardActivity.class);
            startActivity(guard);
        }

    }
}