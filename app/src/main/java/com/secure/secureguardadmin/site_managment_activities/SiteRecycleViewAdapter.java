package com.secure.secureguardadmin.site_managment_activities;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.secure.secureguardadmin.Models.Site;
import com.secure.secureguardadmin.R;
import java.util.ArrayList;


public class SiteRecycleViewAdapter extends RecyclerView.Adapter<MySiteRecycleViewHolder>
{

    SiteItemClick siteItemClick;
    ArrayList<Site> siteArrayList;
    Context context;
    SiteRecycleViewAdapter(ArrayList<Site> siteArrayList,SiteItemClick siteItemClick,Context context)
    {

        this.siteItemClick=siteItemClick;
        this.siteArrayList=siteArrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public MySiteRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.site_item_view,parent,false);
        MySiteRecycleViewHolder myRecycleViewHolder=new MySiteRecycleViewHolder(view,siteItemClick);
        return myRecycleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MySiteRecycleViewHolder mySiteRecycleViewHolder, int position) {



        mySiteRecycleViewHolder.site_alert_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             siteItemClick.sos_Alert_Click(position);
            }
        });


        if (siteArrayList.size()>0)
        {

          mySiteRecycleViewHolder.siteTitle.setText(siteArrayList.get(position).title);

            if(siteArrayList.get(position).state==13) {

                try {

                    mySiteRecycleViewHolder.alertTitle.setText("Alert Found");
                    mySiteRecycleViewHolder.alertTitle.setBackgroundColor(Color.parseColor("#FF0000"));
                     mySiteRecycleViewHolder.alertLayoout.setBackgroundColor(Color.parseColor("#FF0000")); // Example color: Pink

                } catch (IllegalArgumentException e) {

                }
            }

        }

    }


    @Override
    public int getItemCount() {

         if(siteArrayList.size()>0)
        return siteArrayList.size();
         else  return 0;
    }
}

class MySiteRecycleViewHolder extends RecyclerView.ViewHolder
{

     TextView siteTitle,alertTitle,site_alert_state;
     LinearLayout alertLayoout;

    public MySiteRecycleViewHolder(@NonNull View itemView,SiteItemClick siteItemClick) {
        super(itemView);
        siteTitle=itemView.findViewById(R.id.site_item_title);
        alertLayoout=itemView.findViewById(R.id.site_alert_idicate);
        alertTitle=itemView.findViewById(R.id.alert_state);
        site_alert_state=itemView.findViewById(R.id.site_alert_state);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (siteItemClick!=null)
                {

                    int p=getAdapterPosition();


                    siteItemClick.onClick(p);

                }

            }
        });

    }
}
