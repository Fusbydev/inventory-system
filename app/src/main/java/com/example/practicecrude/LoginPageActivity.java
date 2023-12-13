package com.example.practicecrude;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPageActivity extends AppCompatActivity {

    EditText password1, password2, username;
    Button login, signup;
    DBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        helper = new DBHelper(this);
        password1 = findViewById(R.id.password);
        password2 = findViewById(R.id.password1);
        username = findViewById(R.id.username);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.sign);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = password1.getText().toString().trim();
                String repass = password2.getText().toString().trim();
                String usern = username.getText().toString().trim();

                if (!TextUtils.isEmpty(pass) && !TextUtils.isEmpty(repass) && !TextUtils.isEmpty(usern)) {
                    if (repass.equals(pass)) {
                        signUp(usern, pass);
                    } else {
                        Toast.makeText(LoginPageActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginPageActivity.this, "Input all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPageActivity.this, LoginActivity.class));
            }
        });

    }

    public void signUp(String user, String pass) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.ACCOUNT_USERNAME, user);
        values.put(DBHelper.ACCOUNT_PASSWORD, pass);
        long newRowId = db.insert(DBHelper.ACCOUNT_TABLE, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "sign-up successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginPageActivity.this, LoginActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Failed to sign-up", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}