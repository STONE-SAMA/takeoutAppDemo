package com.example.take_out_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class PerInfoActivity extends AppCompatActivity {

    Button btn_info;
    EditText et_user_name;
    EditText et_user_address;
    RadioGroup radioGroup_sex;

    String ID ;//此时登录用户的账号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_info);

        Intent intent = getIntent();
        ID = intent.getStringExtra("user_login_id");

        btn_info = findViewById(R.id.btn_user_info);
        et_user_name = findViewById(R.id.et_user_name);
        et_user_address = findViewById(R.id.et_user_address);

        final String[] sex = new String[]{"无"};
        radioGroup_sex = findViewById(R.id.radio_group_sex);
        radioGroup_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_btn_male:
                        sex[0] = "男";
                        //Toast.makeText(PerInfoActivity.this,"男",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio_btn_female:
                        //Toast.makeText(PerInfoActivity.this,"女",Toast.LENGTH_SHORT).show();
                        sex[0] = "女";
                        break;

                }
            }
        });

        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = et_user_name.getText().toString();
                final String userAddress = et_user_address.getText().toString();
                if(TextUtils.isEmpty(userName)||TextUtils.isEmpty(userAddress)||sex[0].equals("无")){
                    Toast.makeText(PerInfoActivity.this,"请填写完整个人信息！",Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(PerInfoActivity.this);
                    builder.setTitle("提示信息");
                    builder.setMessage("确认提交？");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DBHelper dbHelper = new DBHelper(PerInfoActivity.this,"TakeOutApp.db",null,3);
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("userName",userName);
                            contentValues.put("userSex",sex[0]);
                            contentValues.put("userAddress",userAddress);
                            dbHelper.update(contentValues,ID);
                            //个人信息增加
                            AlertDialog.Builder builder = new AlertDialog.Builder(PerInfoActivity.this);
                            builder.setTitle("提示信息");
                            builder.setMessage("个人信息已完善！");
                            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    PerInfoActivity.this.finish();
                                }
                            });
                            builder.show();

                        }
                    });
                    builder.setNegativeButton("再等等", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();

                }

            }
        });

    }
}
