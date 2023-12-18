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

public class SiteaddedGuardAdapter extends RecyclerView.Adapter<Myview> {


    ArrayList<UserRecord> userRecordArrayList;
    Context context;
    GuardAddInterface guardAddInterface;

    public SiteaddedGuardAdapter(ArrayList<UserRecord> userRecordArrayList,Context context,GuardAddInterface guardAddInterface)
    {
        this.userRecordArrayList=userRecordArrayList;
        this.context=context;
        this.guardAddInterface=guardAddInterface;
    }
    @NonNull
    @Override
    public Myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.added_guard_on_site_view,parent,false);
        Myview myview=new Myview(view);
        return myview;
    }

    @Override
    public void onBindViewHolder(@NonNull Myview holder, int position) {

        holder.added_name.setText(userRecordArrayList.get(position).getFirstName()+" "+userRecordArrayList.get(position).getLastName());
        holder.added_lecienceNo.setText(userRecordArrayList.get(position).getLicenceNumber());
        holder.added_email.setText(userRecordArrayList.get(position).getMail());
        holder.removed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardAddInterface.remove_Guard_Click(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userRecordArrayList.size();
    }
}




class Myview extends RecyclerView.ViewHolder
{

    Button removed;
    TextView added_name,added_lecienceNo,added_email;
    public Myview(@NonNull View itemView) {
        super(itemView);
        added_name=itemView.findViewById(R.id.added_name);
        added_lecienceNo=itemView.findViewById(R.id.added_lecienceNo);
        added_email=itemView.findViewById(R.id.added_email);
        removed=itemView.findViewById(R.id.remove_to_site);
    }
}