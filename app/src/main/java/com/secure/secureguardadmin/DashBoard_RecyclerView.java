package com.secure.secureguardadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DashBoard_RecyclerView extends RecyclerView.Adapter<MyRecycleViewHolder>
{

final Dash_Board_ItemInterface dashBoardItemInterface;

    String []arr;


    DashBoard_RecyclerView(String[] arr, Dash_Board_ItemInterface dashBoardItemInterface)

    {
        this.dashBoardItemInterface=dashBoardItemInterface;
this.arr=arr;
    }

    @NonNull
    @Override
    public MyRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dash_board_item_view,parent,false);
       MyRecycleViewHolder myRecycleViewHolder=new MyRecycleViewHolder(view,dashBoardItemInterface);
        return myRecycleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecycleViewHolder holder, int position) {

        if (arr.length>0) {
            holder.ItemTitle.setText(arr[position].toString());
        }

    }

    @Override
    public int getItemCount() {
        return arr.length;
    }
}

class MyRecycleViewHolder extends RecyclerView.ViewHolder
{

    TextView ItemTitle;

    public MyRecycleViewHolder(@NonNull View itemView,Dash_Board_ItemInterface dashBoardItemInterface) {
        super(itemView);

        ItemTitle=itemView.findViewById(R.id.itemTitle);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dashBoardItemInterface!=null)
                {

              int p=getAdapterPosition();


                  dashBoardItemInterface.ItemClick(p);

                }

            }
        });
    }
}
