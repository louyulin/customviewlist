package com.example.louyulin.viewday16statusbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter {

    List<String> listStr;

    public static int COMMONTYPE = 0;
    public static int FOOTERTYPE = 99;

    public void setListStr(List<String> listStr) {
        this.listStr = listStr;
        notifyDataSetChanged();
    }

    boolean isNoMore = false;
    //是否已经没有更多
    public void setIsNoMore(boolean isNoMore){
        this.isNoMore = isNoMore;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == COMMONTYPE){
        View commonView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview,parent,false);
        viewHolder = new CommonHolder(commonView);
        }else {
         View footerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemfooter,parent,false);
         viewHolder = new FooterHolder(footerView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CommonHolder){
            ((CommonHolder) holder).item_tv.setText(listStr.get(position));
        }else {
            if (isNoMore){
                ((FooterHolder) holder).item_tv.setText("没有更多了");
            }else {
                ((FooterHolder) holder).item_tv.setText("正在加载更多");
            }
        }
    }

    @Override
    public int getItemCount() {
        return listStr.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == listStr.size()){
            return FOOTERTYPE;
        }else {
            return COMMONTYPE;
        }
    }

    class CommonHolder extends RecyclerView.ViewHolder{
        TextView item_tv;
        public CommonHolder(@NonNull View itemView) {
            super(itemView);
            item_tv = itemView.findViewById(R.id.tv);
        }
    }

    class FooterHolder extends RecyclerView.ViewHolder{
        TextView item_tv;
        public FooterHolder(@NonNull View itemView) {
            super(itemView);
            item_tv = itemView.findViewById(R.id.tv);
        }
    }
}
