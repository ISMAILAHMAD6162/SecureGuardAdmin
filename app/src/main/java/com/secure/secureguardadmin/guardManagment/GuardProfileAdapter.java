package com.secure.secureguardadmin.guardManagment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.transition.Hold;
import com.secure.secureguardadmin.Models.UserRecord;
import com.secure.secureguardadmin.R;

import java.util.ArrayList;

public class GuardProfileAdapter extends RecyclerView.Adapter<GuardProfileViewHolder> {

    ArrayList<UserRecord> userRecordArrayList;

    Context context;

    public GuardProfileAdapter(ArrayList<UserRecord> userRecordArrayList,Context context)
    {
        this.userRecordArrayList=userRecordArrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public GuardProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.guard_profile,parent,false);
        GuardProfileViewHolder guardProfileViewHolder=new GuardProfileViewHolder(view);
        return guardProfileViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GuardProfileViewHolder holder, int position) {

        holder.licenceExpireDate.setText(userRecordArrayList.get(position).getLicenceExpireDate());
        holder.phoneNumber.setText(userRecordArrayList.get(position).getPhoneNumber());
         holder.name.setText(userRecordArrayList.get(position).getFirstName()+" "+userRecordArrayList.get(position).getLastName());
         holder.email.setText(userRecordArrayList.get(position).getMail());
         holder.lecience.setText(userRecordArrayList.get(position).getLicenceNumber());
         holder.address.setText(userRecordArrayList.get(position).getAddress());

         holder.call_guard.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

             }
         });
       holder.name.setOnClickListener(new View.OnClickListener() {




            @Override
            public void onClick(View v) {
             //   Toast.makeText(context,"name ckike"+userRecordArrayList.get(position).getLastName(),Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return userRecordArrayList.size();
    }
}
class GuardProfileViewHolder extends RecyclerView.ViewHolder
{

    TextView name,lecience,email,address,phoneNumber,licenceNumber, licenceExpireDate,profilePicture, backSideBadge,frontSideBadge, city,password,state;;

    Button call_guard;
    public GuardProfileViewHolder(@NonNull View itemView) {

        super(itemView);
        name=itemView.findViewById(R.id.name);
        lecience=itemView.findViewById(R.id.leicneNo);
        email=itemView.findViewById(R.id.email);
        address=itemView.findViewById(R.id.address);
        phoneNumber=itemView.findViewById(R.id.phone);
        call_guard=itemView.findViewById(R.id.call);
        licenceExpireDate=itemView.findViewById(R.id.experidate);





    }
}
