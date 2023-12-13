package com.example.practicecrude;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button login;
    EditText username, password;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        db = new DBHelper(this);
        login = findViewById(R.id.login1);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredUsername = username.getText().toString();
                String enteredPassword = password.getText().toString();

                if (isValidUser(enteredUsername, enteredPassword)) {
                    // Save the username in SharedPreferences
                    saveUsernameToSharedPreferences(enteredUsername);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Incorrect credentials, show a toast or handle the error
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveUsernameToSharedPreferences(String username) {
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", username);
        editor.apply();
    }


    private boolean isValidUser(String enteredUsername, String enteredPassword) {
        SQLiteDatabase dbRead = db.getReadableDatabase();
        Cursor cursor = dbRead.rawQuery("SELECT * FROM " + DBHelper.ACCOUNT_TABLE +
                        " WHERE " + DBHelper.ACCOUNT_USERNAME + "=? AND " + DBHelper.ACCOUNT_PASSWORD + "=?",
                new String[]{enteredUsername, enteredPassword});

        boolean isValid = cursor.getCount() > 0;

        cursor.close();
        dbRead.close();

        return isValid;
    }
}