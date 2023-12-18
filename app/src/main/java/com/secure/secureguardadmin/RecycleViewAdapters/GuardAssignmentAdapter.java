package com.secure.secureguardadmin.RecycleViewAdapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.secure.secureguardadmin.Models.UserRecord;
import com.secure.secureguardadmin.R;
import com.secure.secureguardadmin.shift_managment.AssignGuard;

import java.util.ArrayList;

public class GuardAssignmentAdapter extends RecyclerView.Adapter<Myview> {


    AssignGuard assignGuard;
    public ArrayList<UserRecord> arrayList;

    public GuardAssignmentAdapter(ArrayList<UserRecord> arrayList,AssignGuard assignGuard)
    {
        this.arrayList=arrayList;
        this.assignGuard=assignGuard;
    }
    @NonNull
    @Override
    public Myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.shift_guard_assignment_available_item_view,parent,false);
        Myview myview=new Myview(view);
        return myview;
    }

    @Override
    public void onBindViewHolder(@NonNull Myview holder, int position) {

        holder.name.setText(arrayList.get(position).getFirstName()+" "+arrayList.get(position).getLastName());
        holder.email.setText(arrayList.get(position).getMail());
        holder.assgin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                assignGuard.assign_guard_click(position);

            }
        });

    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }
}
class Myview extends RecyclerView.ViewHolder
{

    TextView name,email;
    Button assgin;
    public Myview(@NonNull View itemView) {
        super(itemView);

        name=itemView.findViewById(R.id.assign_name);
        email=itemView.findViewById(R.id.assign_email);
        assgin=itemView.findViewById(R.id.assign_shift);



    }
}
