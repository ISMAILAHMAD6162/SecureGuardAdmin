package com.secure.secureguardadmin.shift_managment;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.collection.LLRBNode;
import com.secure.secureguardadmin.Models.Shift;
import com.secure.secureguardadmin.R;

import java.util.ArrayList;

public class ShiftReyceviewAdapter extends RecyclerView.Adapter<MyShiftRecycelViewHolder>
{
    ArrayList<Shift> shiftArrayList;
 ShiftItemClickInterface shiftItemClickInterface;
    public ShiftReyceviewAdapter(ArrayList<Shift> arrayList,ShiftItemClickInterface shiftItemClickInterface)
    {
        this.shiftItemClickInterface=shiftItemClickInterface;
        this.shiftArrayList=arrayList;

    }
    @NonNull
    @Override
    public MyShiftRecycelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()) .inflate(R.layout.shift_item_view_upcoming,parent,false);
        MyShiftRecycelViewHolder myShiftRecycelViewHolder =new MyShiftRecycelViewHolder(view,shiftItemClickInterface);
        return myShiftRecycelViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyShiftRecycelViewHolder holder, int position) {

       // holder.shiftTitle.setText(shiftArrayList.get(position).siteId);
       // holder.shiftId.setText(shiftArrayList.get(position).shiftId);

      //  holder.shiftTime.setText("Start Time"+shiftArrayList.get(position).startTime+"   End Time"+shiftArrayList.get(position).endTime);
    // holder.shiftDate.setText(shiftArrayList.get(position).year+"-"+shiftArrayList.get(position).month+"-"+shiftArrayList.get(position).day);
   // holder.shiftstarttime.setText(shiftArrayList.get(position).startTime);
   // holder.shiftendtime.setText(shiftArrayList.get(position).endTime);

        if(shiftArrayList.get(position).state==13)
        {
            holder.shift_alert.setBackgroundColor(Color.parseColor("#FF0000"));
            holder.shift_alert.setText("ALERT FOUND");
        }


    }

    @Override
    public int getItemCount() {

        return shiftArrayList.size();
    }
}

class MyShiftRecycelViewHolder extends RecyclerView.ViewHolder
{

    TextView shiftId,shiftTime,shiftTitle,shiftstarttime,shiftendtime,shift_alert;
    Button shiftDate;
    public MyShiftRecycelViewHolder(@NonNull View itemView,ShiftItemClickInterface shiftItemClickInterface) {
        super(itemView);

        shift_alert=itemView.findViewById(R.id.shift_alert);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shiftItemClickInterface.shiftItemClicklistne(getAdapterPosition());

            }
        });
     //   shiftTitle=itemView.findViewById(R.id.shift_title_comming);
     // shiftId=itemView.findViewById(R.id.shift_id_upcoming);
       // shiftTime=itemView.findViewById(R.id.shiftime);
     //   shiftstarttime=itemView.findViewById(R.id.shift_start_time);
      //  shiftDate=itemView.findViewById(R.id.up_coming_date);
       /// shiftendtime=itemView.findViewById(R.id.shift_endtime);
    }
}
