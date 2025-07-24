package com.example.smartshoppinglist;

import org.json.JSONException;
import org.json.JSONObject;

public class Item {
    private String name;
    private int quantity;
    private String category;

    public Item(String name, int quantity, String category) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }

    public String getName() {
        return name;
    }
    public int getQuantity() {
        return quantity;
    }
    public String getCategory() {
        return category;
    }
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("name", name);
        obj.put("quantity", quantity);
        obj.put("category", category);
        return obj;
    }

    public static Item fromJson(JSONObject obj) throws JSONException {
        return new Item(
                obj.getString("name"),
                obj.getInt("quantity"),
                obj.getString("category")
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Item other = (Item) obj;
        return name.equals(other.name) &&
                category.equals(other.category) &&
                quantity == other.quantity;
    }
}
