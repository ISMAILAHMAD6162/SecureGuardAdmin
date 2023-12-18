package com.secure.secureguardadmin.site_managment_activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.secure.secureguardadmin.Models.UserRecord;
import com.secure.secureguardadmin.R;

import java.util.ArrayList;

public class SiteGuardsAvaliableAdapter extends RecyclerView.Adapter<MyViewe> {


    public ArrayList<UserRecord> userRecordArrayList;
    Context context;

    GuardAddInterface guardAddInterface;
    public  SiteGuardsAvaliableAdapter(ArrayList<UserRecord> userRecordArrayList,Context context,GuardAddInterface guardAddInterface)
    {

        this.userRecordArrayList=userRecordArrayList;
        this.context=context;
        this.guardAddInterface=guardAddInterface;
    }

    @NonNull
    @Override
    public MyViewe onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.add_guard_on_site_view,parent,false);
        MyViewe myViewe=new MyViewe(view);
        return myViewe;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewe holder, int position) {


        holder.available_name.setText(userRecordArrayList.get(position).getFirstName()+" "+userRecordArrayList.get(position).getLastName());
       holder.avaliable_lecienceNo.setText(userRecordArrayList.get(position).getLicenceNumber());
       holder.avaible_address.setText(userRecordArrayList.get(position).getMail());

        holder.add_to_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardAddInterface.add_Guard_Click(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userRecordArrayList.size();
    }
}

class MyViewe extends  RecyclerView.ViewHolder
{
     Button add_to_site;
     TextView available_name,avaliable_lecienceNo,avaible_address;
    public MyViewe(@NonNull View itemView) {
        super(itemView);
        add_to_site=itemView.findViewById(R.id.add_to_site);
        available_name=itemView.findViewById(R.id.available_name);
        avaliable_lecienceNo=itemView.findViewById(R.id.avaliable_lecienceNo);
        avaible_address=itemView.findViewById(R.id.avaible_address);

    }
}
