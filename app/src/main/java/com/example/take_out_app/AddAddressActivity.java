package com.example.take_out_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddAddressActivity extends AppCompatActivity {
    EditText et_phone;
    EditText et_address;
    Button btn_save;
    Button btn_cancel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        et_phone = findViewById(R.id.et_address_phone);
        et_address = findViewById(R.id.et_address_address);
        btn_save = findViewById(R.id.btn_address_save);
        btn_cancel = findViewById(R.id.btn_address_cancel);

        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");

        //添加
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String phone = et_phone.getText().toString();
               String address = et_address.getText().toString();
                Pattern p = Pattern.compile("^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$");
                Matcher m = p.matcher(phone);
                if(m.matches()){
                    if(!TextUtils.isEmpty(address)){
                        //将新增地址添加到数据库
                        DBHelper dbHelper = new DBHelper(AddAddressActivity.this,"TakeOutApp.db",null,3);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("userID",userID);
                        contentValues.put("userPhone",phone);
                        contentValues.put("userAddress",address);
                        dbHelper.AddAddress(contentValues);
                        Toast.makeText(AddAddressActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                        setResult(222);
                        AddAddressActivity.this.finish();
                    }else{
                        Toast.makeText(AddAddressActivity.this,"请输入地址!",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(AddAddressActivity.this,"请输入正确的电话号码!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //取消添加
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddAddressActivity.this);
                builder.setTitle("提示信息");
                builder.setMessage("确认取消添加地址？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddAddressActivity.this.finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

    }
}
