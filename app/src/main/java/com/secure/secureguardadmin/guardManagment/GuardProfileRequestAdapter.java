package com.secure.secureguardadmin.guardManagment;

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

public class GuardProfileRequestAdapter extends RecyclerView.Adapter<MyGuardProfileAdapterView> {

public ArrayList<UserRecord> userRecordArrayList;
public GuardProfileAcceptInterface guardProfileAcceptInterface;

public  GuardProfileRequestAdapter(ArrayList<UserRecord> userRecordArrayList,GuardProfileAcceptInterface guardProfileAcceptInterface)
{
    this.userRecordArrayList=userRecordArrayList;
    this.guardProfileAcceptInterface=guardProfileAcceptInterface;
}

    @NonNull
    @Override
    public MyGuardProfileAdapterView onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {



        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.guard_profile_request_view,parent,false);
        MyGuardProfileAdapterView myRecycleViewHolder=new MyGuardProfileAdapterView(view);
        return myRecycleViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyGuardProfileAdapterView holder, int position) {


    holder.name.setText(userRecordArrayList.get(position).getFirstName()+" "+userRecordArrayList.get(position).getLastName());
    holder.leicen.setText(userRecordArrayList.get(position).getLicenceNumber());
    holder.address.setText(userRecordArrayList.get(position).getAddress());

    holder.reject.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
guardProfileAcceptInterface.rejectClik(position);
        }
    });
    holder.accept.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            guardProfileAcceptInterface.acceptClick(position);

        }
    });



    }

    @Override
    public int getItemCount() {
        return userRecordArrayList.size();
    }
}

class MyGuardProfileAdapterView extends RecyclerView.ViewHolder
{

    TextView name,leicen,address;
    Button accept,reject;
    public MyGuardProfileAdapterView(@NonNull View itemView) {
        super(itemView);
        accept=itemView.findViewById(R.id.accept);
        name=itemView.findViewById(R.id.new_name);
        address=itemView.findViewById(R.id.new_address);
        leicen=itemView.findViewById(R.id.new_lecienceNo);
        reject=itemView.findViewById(R.id.new_reject);


    }
}
