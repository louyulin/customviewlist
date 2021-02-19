package com.example.louyulin.viewday16statusbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.Holder> {
    List<String> listStr;

    public void setListStr(List<String> listStr) {
        this.listStr = listStr;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View commonView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview, parent, false);
        RVAdapter.Holder viewHolder = new RVAdapter.Holder(commonView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.item_tv.setText(listStr.get(position));
    }

    @Override
    public int getItemCount() {
        return listStr.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView item_tv;
        public Holder(@NonNull View itemView) {
            super(itemView);
            item_tv = itemView.findViewById(R.id.tv);
        }
    }
}
