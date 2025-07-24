package com.example.smartshoppinglist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddItemMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_additemmenu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonSaveItem = findViewById(R.id.buttonSaveItem);
        buttonSaveItem.setOnClickListener(v -> {
                EditText nameField = findViewById(R.id.editTextItemName);
                EditText qtyField = findViewById(R.id.editTextQuantity);
                Spinner spinner = findViewById(R.id.spinnerCategory);

                String name = nameField.getText().toString().trim();
                String qtyStr = qtyField.getText().toString().trim();
                String category = spinner.getSelectedItem().toString();


                if (name.isEmpty() || qtyStr.isEmpty()) {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (category.equals("Category")) {
                    Toast.makeText(this, "Please select a valid category", Toast.LENGTH_SHORT).show();
                    return;
                }

                int quantity;
                try {
                    quantity = Integer.parseInt(qtyStr);
                    if (quantity <= 0) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Enter a valid quantity", Toast.LENGTH_SHORT).show();
                    return;
                }
                Item newItem = new Item(name, quantity, category);

                saveItemToPrefs(newItem);
                Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();
                finish();
        });

        Button buttonCancel = findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(v -> {
            finish();
        });
    }

    private void saveItemToPrefs(Item item) {
        SharedPreferences prefs = getSharedPreferences("ShoppingData", MODE_PRIVATE);
        String itemListJson = prefs.getString("items", "[]");

        try {
            JSONArray arr = new JSONArray(itemListJson);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                if (obj.getString("name").equalsIgnoreCase(item.getName()) &&
                        obj.getString("category").equalsIgnoreCase(item.getCategory())) {
                    Toast.makeText(this, "Item already exists", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            arr.put(item.toJson());
            prefs.edit().putString("items", arr.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}