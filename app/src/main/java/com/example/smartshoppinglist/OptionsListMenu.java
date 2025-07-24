package com.example.smartshoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OptionsListMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_optionslistmenu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonViewCart = findViewById(R.id.buttonViewCart);
        Button buttonAddItem = findViewById(R.id.buttonAddItem);
        Button buttonFavourites = findViewById(R.id.buttonFavourites);
        Button buttonBack = findViewById(R.id.buttonBack);

        buttonViewCart.setOnClickListener(v -> {
            Intent intent = new Intent(OptionsListMenu.this, ViewCartMenu.class);
            startActivity(intent);
        });

        buttonAddItem.setOnClickListener(v -> {
            Intent intent = new Intent(OptionsListMenu.this, AddItemMenu.class);
            startActivity(intent);
        });

        buttonFavourites.setOnClickListener(v -> {
            Intent intent = new Intent(OptionsListMenu.this, HistoryMenu.class);
            startActivity(intent);
        });

        buttonBack.setOnClickListener(v -> {
           finish();
        });
    }
}