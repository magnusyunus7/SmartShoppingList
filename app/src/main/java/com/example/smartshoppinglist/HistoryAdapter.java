package com.example.smartshoppinglist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.*;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<PurchaseHistoryItem> historyList;

    public HistoryAdapter(List<PurchaseHistoryItem> historyList) {
        this.historyList = historyList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, details, time;

        public ViewHolder(View view) {
            super(view);
            itemName = view.findViewById(R.id.textViewItemName2);
            details = view.findViewById(R.id.textViewDetails2);
            time = view.findViewById(R.id.textViewPurchaseTime);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rowhistory, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PurchaseHistoryItem item = historyList.get(position);
        holder.itemName.setText(item.getName());
        holder.details.setText(item.getDetails());
        holder.time.setText(item.getPurchaseTime());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
}

