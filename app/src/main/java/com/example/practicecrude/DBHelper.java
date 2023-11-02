package com.example.practicecrude;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String PRODUCT_INVENTORY = "product_inventory";
    public static final String PRODUCT_ID = "product_ID";
    public static final String PRODUCT_PRICE = "product_price";
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_QUANTITY = "product_quantity";
    private final String DB_NAME = "inventory.db";
    private final int DB_VERSION = 1;
    public DBHelper(Context context) {
        super(context, "inventory.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + PRODUCT_INVENTORY + " (" + PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PRODUCT_NAME + " TEXT, " +
                PRODUCT_PRICE + " REAL, " + PRODUCT_QUANTITY + " INTEGER )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PRODUCT_INVENTORY);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + PRODUCT_INVENTORY;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
    public void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PRODUCT_INVENTORY, PRODUCT_ID + "=?", new String[]{String.valueOf(id)});
    }
}
