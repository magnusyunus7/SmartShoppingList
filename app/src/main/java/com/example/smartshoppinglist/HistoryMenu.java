package com.example.smartshoppinglist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.*;

public class HistoryMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_historymenu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        PurchaseHistoryManager.getInstance().loadFromPrefs(this);
        Button buttonGoBack2 = findViewById(R.id.buttonGoBack2);
        buttonGoBack2.setOnClickListener(v -> {
            finish();
        });

        RecyclerView historyRecyclerView = findViewById(R.id.recyclerViewHistory);
        TextView emptyView = findViewById(R.id.textNothingHere2);

        List<PurchaseHistoryItem> history = PurchaseHistoryManager.getInstance().getHistoryList();
        if (history.isEmpty()) {
            historyRecyclerView.setVisibility(View.INVISIBLE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            historyRecyclerView.setAdapter(new HistoryAdapter(history));
            historyRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.INVISIBLE);
        }
    }
}