package com.secure.secureguardadmin.shift_managment;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.secure.secureguardadmin.Models.Shift;
import com.secure.secureguardadmin.R;


import java.util.ArrayList;

public class ShiftRecycelviewAdapter extends RecyclerView.Adapter<MyshiftViewHlder> {

    ShiftItemClickInterface shiftItemClickInterface;
    ArrayList<Shift> shiftArrayList;


    ShiftRecycelviewAdapter(ArrayList<Shift> shiftArrayList,ShiftItemClickInterface shiftItemClickInterface)
    {
        this.shiftArrayList=shiftArrayList;
        this.shiftItemClickInterface=shiftItemClickInterface;

    }
    @NonNull
    @Override
    public MyshiftViewHlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.shift_item_view,parent,false);
        MyshiftViewHlder myshiftViewHlder =new MyshiftViewHlder(view,shiftItemClickInterface);
        return myshiftViewHlder;



    }

    @Override
    public void onBindViewHolder(@NonNull MyshiftViewHlder holder, int position) {
holder.shift_item_id.setText(shiftArrayList.get(position).shiftId);

    }


    @Override
    public int getItemCount() {
        if (shiftArrayList!=null)
        return shiftArrayList.size();
        else return 0;
    }
}

class MyshiftViewHlder extends RecyclerView.ViewHolder
{
    CardView shiftcardView;
    TextView shift_item_id;

    public MyshiftViewHlder(@NonNull View itemView,ShiftItemClickInterface shiftItemClickInterface)
    {
        super(itemView);

        shiftcardView=itemView.findViewById(R.id.shift_item);
        shift_item_id=itemView.findViewById(R.id.shift_id_item_view);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p=getAdapterPosition();
                shiftItemClickInterface.shiftItemClicklistne(p);
            }
        });


    }
}