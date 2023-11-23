package com.secure.secureguardadmin.site_managment_activities;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.secure.secureguardadmin.Models.Site;
import com.secure.secureguardadmin.R;
import java.util.ArrayList;


public class SiteRecycleViewAdapter extends RecyclerView.Adapter<MySiteRecycleViewHolder>
{

    SiteItemClick siteItemClick;
    ArrayList<Site> siteArrayList;
    SiteRecycleViewAdapter(ArrayList<Site> siteArrayList,SiteItemClick siteItemClick)
    {

        this.siteItemClick=siteItemClick;
        this.siteArrayList=siteArrayList;
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

        if (siteArrayList.size()>0)
        {

            mySiteRecycleViewHolder.siteTitle.setText(siteArrayList.get(position).title);

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

     TextView siteTitle;

    public MySiteRecycleViewHolder(@NonNull View itemView,SiteItemClick siteItemClick) {
        super(itemView);
        siteTitle=itemView.findViewById(R.id.site_item_title);
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
