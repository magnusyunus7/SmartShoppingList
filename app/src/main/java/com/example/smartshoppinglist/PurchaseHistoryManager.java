package com.example.smartshoppinglist;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class PurchaseHistoryManager {
    private static final String PREF_NAME = "PurchaseHistoryPrefs";
    private static final String KEY_HISTORY = "history";

    private static PurchaseHistoryManager instance;
    private List<PurchaseHistoryItem> historyList = new ArrayList<>();

    private PurchaseHistoryManager() {}

    public static PurchaseHistoryManager getInstance() {
        if (instance == null) {
            instance = new PurchaseHistoryManager();
        }
        return instance;
    }

    public List<PurchaseHistoryItem> getHistoryList() {
        return historyList;
    }

    public void addToHistory(PurchaseHistoryItem item, Context context) {
        historyList.add(0, item);

        if (historyList.size() > 10) {
            historyList.remove(historyList.size() - 1);
        }

        saveToPrefs(context);
    }

    public void saveToPrefs(Context context) {
        JSONArray jsonArray = new JSONArray();
        for (PurchaseHistoryItem item : historyList) {
            jsonArray.put(item.toJson());
        }
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_HISTORY, jsonArray.toString()).apply();
    }

    public void loadFromPrefs(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String jsonString = prefs.getString(KEY_HISTORY, "[]");
        historyList.clear();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                historyList.add(PurchaseHistoryItem.fromJson(obj));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
