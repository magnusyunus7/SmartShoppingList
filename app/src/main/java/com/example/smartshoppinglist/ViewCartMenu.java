package com.example.smartshoppinglist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.*;

public class ViewCartMenu extends AppCompatActivity implements OnItemChangeListener {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Item> itemList;
    private TextView textNothingHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_viewcartmenu);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            );
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerViewItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = loadItemsFromPrefs();
        adapter = new ItemAdapter(this, itemList, this);
        recyclerView.setAdapter(adapter);

        textNothingHere = findViewById(R.id.textNothingHere);
        updateVisibility(itemList.size());

        Button buttonGoBack = findViewById(R.id.buttonGoBack);
        buttonGoBack.setOnClickListener(v -> finish());
    }

    private List<Item> loadItemsFromPrefs() {
        SharedPreferences prefs = getSharedPreferences("ShoppingData", MODE_PRIVATE);
        String itemsJson = prefs.getString("items", "[]");
        List<Item> list = new ArrayList<>();

        try {
            JSONArray array = new JSONArray(itemsJson);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                list.add(Item.fromJson(obj));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void onItemListChanged(int size) {
        updateVisibility(size);
    }

    private void updateVisibility(int listSize) {
        if (listSize == 0) {
            recyclerView.setVisibility(View.INVISIBLE);
            textNothingHere.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            textNothingHere.setVisibility(View.INVISIBLE);
        }
    }
}