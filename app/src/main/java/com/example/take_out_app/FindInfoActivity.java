package com.example.take_out_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FindInfoActivity extends AppCompatActivity {
    Button btn_match;
    EditText et_find_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_info);

        btn_match = findViewById(R.id.btn_match);
        et_find_id = findViewById(R.id.et_find_id);

        //根据输入的手机号获取账号信息，直接跳转至密码修改界面
        btn_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
