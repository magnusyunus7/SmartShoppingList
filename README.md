# SmartShoppingList

SmartShoppingList is a lightweight Android app that helps users manage their shopping items efficiently, mark purchases, and view a persistent purchase history, all while keeping the interface clean and responsive.

## Features

- Add items with quantity and category
- Auto-suggestions for category input
- Confirm purchases with timestamp logging
- View most recent 10 purchases (newest first)
- Auto-save using SharedPreferences (data persists after app restarts)
- Clean, user-friendly interface

## Download

👉 **[Click here to download the APK](https://github.com/magnusyunus7/SmartShoppingList/releases/latest/download/SmartShoppingList.apk)**

> Note: You may need to enable *Install from unknown sources* in your phone settings.

## Built With

- Java
- Android SDK
- RecyclerView
- SharedPreferences
- ConstraintLayout

## Project Structure

SmartShoppingList/
├── app/
│ ├── java/com.example.smartshoppinglist/
│ │ ├── MainActivity.java
│ │ ├── PurchaseHistoryActivity.java
│ │ ├── ItemAdapter.java
│ │ ├── HistoryAdapter.java
│ │ ├── PurchaseHistoryItem.java
│ │ └── PurchaseHistoryManager.java
│ ├── res/
│ │ ├── layout/
│ │ └── drawable/
├── APK/
│ └── SmartShoppingList.apk

## Notes

- App limits history to 10 items (oldest entries get removed).
- Tapping the ✅ tick icon confirms purchase and moves the item to history.
