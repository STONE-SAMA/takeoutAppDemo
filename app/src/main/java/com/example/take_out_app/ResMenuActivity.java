package com.example.take_out_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ResMenuActivity extends AppCompatActivity {
    TextView tv_user_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_menu);

        tv_user_ID = findViewById(R.id.tv_res_ID);

        Intent intent = getIntent();
        String login_Id = intent.getStringExtra("LoginID");
        tv_user_ID.setText(login_Id);

    }
}
