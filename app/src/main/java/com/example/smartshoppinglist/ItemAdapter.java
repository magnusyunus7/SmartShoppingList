package com.example.smartshoppinglist;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.*;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private final Context context;
    private final List<Item> itemList;
    private final OnItemChangeListener listener;

    public ItemAdapter(Context context, List<Item> itemList) {
        this(context, itemList, null);
    }

    public ItemAdapter(Context context, List<Item> itemList, OnItemChangeListener listener) {
        this.context = context;
        this.itemList = itemList;
        this.listener = listener;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView nameView, detailView;
        ImageView buttonDeleteItemRow, buttonConfirm;

        public ItemViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.textViewItemName);
            detailView = itemView.findViewById(R.id.textViewDetails);
            buttonDeleteItemRow = itemView.findViewById(R.id.buttonDeleteItemRow);
            buttonConfirm = itemView.findViewById(R.id.buttonConfirm);
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item current = itemList.get(position);
        holder.nameView.setText(current.getName());
        holder.detailView.setText(current.getQuantity() + " × " + current.getCategory());

        holder.buttonDeleteItemRow.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Item Deletion")
                    .setMessage("Delete \"" + current.getName() + "\"?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        int pos = holder.getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            Item toRemove = itemList.get(pos);
                            itemList.remove(pos);
                            notifyItemRemoved(pos);
                            deleteFromPrefs(toRemove);

                            if (listener != null) {
                                listener.onItemListChanged(itemList.size());
                            }
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        holder.buttonConfirm.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Confirm Purchase")
                    .setMessage("Mark \"" + current.getName() + "\" as purchased?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        int pos = holder.getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            Item item = itemList.get(pos);
                            itemList.remove(pos);
                            notifyItemRemoved(pos);
                            deleteFromPrefs(item);

                            String time = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(new Date());
                            PurchaseHistoryItem purchased = new PurchaseHistoryItem(
                                    item.getName(),
                                    item.getQuantity() + " × " + item.getCategory(),
                                    time
                            );

                            PurchaseHistoryManager manager = PurchaseHistoryManager.getInstance();
                            manager.addToHistory(purchased, context);

                            if (listener != null) {
                                listener.onItemListChanged(itemList.size());
                            }
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    private void deleteFromPrefs(Item itemToDelete) {
        SharedPreferences prefs = context.getSharedPreferences("ShoppingData", Context.MODE_PRIVATE);
        String itemListJson = prefs.getString("items", "[]");

        try {
            JSONArray original = new JSONArray(itemListJson);
            JSONArray updated = new JSONArray();

            for (int i = 0; i < original.length(); i++) {
                JSONObject obj = original.getJSONObject(i);
                Item curr = Item.fromJson(obj);
                if (!curr.equals(itemToDelete)) {
                    updated.put(obj);
                }
            }

            prefs.edit().putString("items", updated.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}