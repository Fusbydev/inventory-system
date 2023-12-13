package com.example.practicecrude;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {
    public static final String PRODUCT_INVENTORY = "product_inventory";
    public static final String PRODUCT_ID = "product_ID";
    public static final String PRODUCT_PRICE = "product_price";
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_INVESTMENT = "investment";
    public static final String PRODUCT_QUANTITY = "product_quantity";
    public static final String INITIAL_QUANTITY = "product_initial";
    public static final String CREDIT_DATA = "id";
    public static final String ACCOUNT_ID = CREDIT_DATA;
    public static final String ACCOUNT_USERNAME = "username";
    public static final String ACCOUNT_PASSWORD = "password";
    public static final String ACCOUNT_TABLE = "accounts";
    public static final String CREDIT = "credit";
    public static final String CUSTOMER_NAME = "customer_name";
    public static final String CUSTOMER_LOAN = "customer_loan";

    private static final String COLUMN_TRANSACTION_ID = "id";
    private static final String COLUMN_PRODUCT_ID = "product_id";
    private static final String COLUMN_PRODUCT_NAME = "product_name";
    private static final String COLUMN_PRODUCT_QUANTITY = "product_quantity";
    private static final String COLUMN_TRANSACTION_DATE = "transaction_date";
    private static final String COLUMN_PRODUCT_PRICE = "product_price";


    private static final String TABLE_TRANSACTION = "transactions";
    private final String DB_NAME = "inventory.db";
    public DBHelper(Context context) {
        super(context, "inventory.db", null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + PRODUCT_INVENTORY + " (" + PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PRODUCT_NAME + " TEXT, " +
                PRODUCT_PRICE + " REAL, " + PRODUCT_QUANTITY + " INTEGER, " + PRODUCT_INVESTMENT + " REAL," + INITIAL_QUANTITY + " REAL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + ACCOUNT_TABLE + " (" + ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ACCOUNT_USERNAME + " TEXT, " + ACCOUNT_PASSWORD + " TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE " + CREDIT + " (" + CREDIT_DATA + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CUSTOMER_NAME + " TEXT, " + CUSTOMER_LOAN + " REAL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_TRANSACTION + " ("
                + COLUMN_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PRODUCT_ID + " INTEGER, "
                + COLUMN_PRODUCT_NAME + " TEXT, "
                + COLUMN_PRODUCT_QUANTITY + " INTEGER,"
                + COLUMN_PRODUCT_PRICE + " INTEGER,"
                + COLUMN_TRANSACTION_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PRODUCT_INVENTORY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TABLE);
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

    public Boolean updateData(int id, String productName, int ProductQuantity, float productPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME, productName);
        values.put(PRODUCT_QUANTITY, ProductQuantity);
        values.put(PRODUCT_PRICE, productPrice);

        int rowsAffected = db.update(PRODUCT_INVENTORY, values, PRODUCT_ID+"=?", new String[]{String.valueOf(id)});
        return rowsAffected > 0;
    }

    public Cursor getDataById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PRODUCT_INVENTORY + " WHERE " + PRODUCT_ID + "=?", new String[]{String.valueOf(id)});
        return cursor;
    }

    public boolean updateQuantity(int newQuantity, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int currentQuantity = 0;

        // Retrieve the current quantity from the database
        Cursor cursor = db.query(PRODUCT_INVENTORY, new String[]{PRODUCT_QUANTITY}, PRODUCT_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToFirst()) {
            currentQuantity = cursor.getInt(0);
        }
        cursor.close();

        // Calculate the updated quantity
        int updatedQuantity = currentQuantity - newQuantity;

        ContentValues values = new ContentValues();
        values.put(PRODUCT_QUANTITY, updatedQuantity);

        // Update the database with the new quantity value
        int rowsAffected = db.update(PRODUCT_INVENTORY, values, PRODUCT_ID + "=?", new String[]{String.valueOf(id)});

        return rowsAffected > 0;
    }

    public boolean updateCredit( int id, String name, int credit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CUSTOMER_NAME, name);
        values.put(CUSTOMER_LOAN, credit);

        int rowsAffected = db.update(CREDIT, values, CREDIT_DATA+"=?", new String[]{String.valueOf(id)});
        return rowsAffected > 0;
    }

    public Cursor getLoanData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + CREDIT;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
    public void deleteLoan(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CREDIT, CREDIT_DATA + "=?", new String[]{String.valueOf(id)});
    }

    public Cursor getCustomer(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CREDIT + " WHERE " + CREDIT_DATA + "=?", new String[]{String.valueOf(id)});
        return cursor;
    }
    public boolean insertTransaction(int productId, String productName, int productPrice, int productQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_PRODUCT_ID, productId);
        values.put(COLUMN_PRODUCT_NAME, productName);
        values.put(COLUMN_PRODUCT_PRICE, productPrice);
        values.put(COLUMN_PRODUCT_QUANTITY, productQuantity);
        values.put(COLUMN_TRANSACTION_DATE, getDateTime());

        long newRowId = db.insert(TABLE_TRANSACTION, null, values);
        db.close();

        return newRowId != -1;
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


    public ArrayList<Transaction> getAllTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_TRANSACTION_ID,
                COLUMN_PRODUCT_ID,
                COLUMN_PRODUCT_NAME,
                COLUMN_PRODUCT_QUANTITY,
        };

        Cursor cursor = db.query(
                TABLE_TRANSACTION,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            int transactionId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TRANSACTION_ID));
            int productId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_ID));
            String productName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_QUANTITY));

            Transaction transaction = new Transaction(transactionId, productId, productName, quantity);
            transactions.add(transaction);
        }

        cursor.close();
        db.close();

        return transactions;
    }

    public ArrayList<Transaction> getAllTransactionsTop(int limit) {
        ArrayList<Transaction> transactions = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_TRANSACTION_ID,
                COLUMN_PRODUCT_ID,
                COLUMN_PRODUCT_NAME,
                COLUMN_PRODUCT_QUANTITY,
        };

        Cursor cursor = db.query(
                TABLE_TRANSACTION,
                projection,
                null,
                null,
                null,
                null,
                COLUMN_PRODUCT_QUANTITY + " DESC", // Order by quantity in descending order
                String.valueOf(limit) // Limit the result to the top N items
        );

        while (cursor.moveToNext()) {
            int transactionId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TRANSACTION_ID));
            int productId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_ID));
            String productName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_QUANTITY));

            Transaction transaction = new Transaction(transactionId, productId, productName, quantity);
            transactions.add(transaction);
        }

        cursor.close();
        db.close();

        return transactions;
    }

    public float getDailyProfit() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Get current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String today = dateFormat.format(new Date());

        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_PRODUCT_QUANTITY + " * " + COLUMN_PRODUCT_PRICE + ") FROM " + TABLE_TRANSACTION +
                " WHERE date(" + COLUMN_TRANSACTION_DATE + ") = date(?)", new String[]{today});

        float dailyIncome = 0;
        if (cursor.moveToFirst()) {
            dailyIncome = cursor.getFloat(0);
        }

        cursor.close();
        db.close();

        float totalInvestments = getTotalInvestments();

        return dailyIncome - totalInvestments;
    }

    public float getWeeklyProfit() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Get the start and end dates for the current week
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date weekStartDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndDate = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String startDate = dateFormat.format(weekStartDate);
        String endDate = dateFormat.format(weekEndDate);

        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_PRODUCT_QUANTITY + " * " + COLUMN_PRODUCT_PRICE + ") FROM " + TABLE_TRANSACTION +
                " WHERE date(" + COLUMN_TRANSACTION_DATE + ") BETWEEN date(?) AND date(?)", new String[]{startDate, endDate});

        float weeklyIncome = 0;
        if (cursor.moveToFirst()) {
            weeklyIncome = cursor.getFloat(0);
        }

        cursor.close();
        db.close();

        float totalInvestments = getTotalInvestments();

        return weeklyIncome - totalInvestments;
    }

    public float getMonthlyProfit() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Get the start and end dates for the current month
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date monthStartDate = calendar.getTime();
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date monthEndDate = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String startDate = dateFormat.format(monthStartDate);
        String endDate = dateFormat.format(monthEndDate);

        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_PRODUCT_QUANTITY + " * " + COLUMN_PRODUCT_PRICE + ") FROM " + TABLE_TRANSACTION +
                " WHERE date(" + COLUMN_TRANSACTION_DATE + ") BETWEEN date(?) AND date(?)", new String[]{startDate, endDate});

        float monthlyIncome = 0;
        if (cursor.moveToFirst()) {
            monthlyIncome = cursor.getFloat(0);
        }

        cursor.close();
        db.close();

        float totalInvestments = getTotalInvestments();

        return monthlyIncome - totalInvestments;
    }
    public float getTotalInvestments() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + PRODUCT_INVESTMENT + " * " + INITIAL_QUANTITY +") FROM " + PRODUCT_INVENTORY, null);

        float sum = 0;
        if (cursor.moveToFirst()) {
            sum = cursor.getFloat(0);
        }

        cursor.close();
        db.close();

        return sum;
    }
}
