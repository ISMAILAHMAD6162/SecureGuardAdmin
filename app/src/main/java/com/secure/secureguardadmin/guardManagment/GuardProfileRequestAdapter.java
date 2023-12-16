package com.secure.secureguardadmin.guardManagment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.secure.secureguardadmin.Models.UserRecord;
import com.secure.secureguardadmin.R;

import java.util.ArrayList;

public class GuardProfileRequestAdapter extends RecyclerView.Adapter<MyGuardProfileAdapterView> {

public ArrayList<UserRecord> userRecordArrayList;

public  GuardProfileRequestAdapter(ArrayList<UserRecord> userRecordArrayList)
{
    this.userRecordArrayList=userRecordArrayList;
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




    }

    @Override
    public int getItemCount() {
        return 4;
    }
}

class MyGuardProfileAdapterView extends RecyclerView.ViewHolder
{

    public MyGuardProfileAdapterView(@NonNull View itemView) {
        super(itemView);
    }
}
