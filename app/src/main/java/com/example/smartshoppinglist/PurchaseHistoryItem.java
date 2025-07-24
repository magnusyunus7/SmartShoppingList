package com.example.smartshoppinglist;

import org.json.JSONException;
import org.json.JSONObject;

public class PurchaseHistoryItem {
    private final String name;
    private final String details;
    private final String time;

    public PurchaseHistoryItem(String name, String details, String time) {
        this.name = name;
        this.details = details;
        this.time = time;
    }

    public String getName() {
        return name;
    }
    public String getDetails() {
        return details;
    }
    public String getPurchaseTime() {
        return time;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("name", name);
            obj.put("details", details);
            obj.put("time", time);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static PurchaseHistoryItem fromJson(JSONObject obj) {
        String name = obj.optString("name");
        String details = obj.optString("details");
        String time = obj.optString("time");
        return new PurchaseHistoryItem(name, details, time);
    }
}

