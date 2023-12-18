package com.secure.secureguardadmin.RecycleViewAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.secure.secureguardadmin.R;

public class GuardAssignmentAdapter2 extends RecyclerView.Adapter<Myview2> {

    @NonNull
    @Override
    public Myview2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.shift_guard_assign_item_view,parent,false);
        Myview2 myview=new Myview2(view);
        return myview;
    }

    @Override
    public void onBindViewHolder(@NonNull Myview2 holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
class Myview2 extends RecyclerView.ViewHolder
{

    public Myview2(@NonNull View itemView) {
        super(itemView);
    }
}

