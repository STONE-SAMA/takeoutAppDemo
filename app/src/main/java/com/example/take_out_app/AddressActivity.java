package com.example.take_out_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity {
    ListView listView;
    ListView listView_main;
    Button btn_add_address;

    List<AddressAdd> lists;
    AddressAdapter myadapter;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");

        listView = findViewById(R.id.lv_address);
        listView_main = findViewById(R.id.lv_main_address);
        btn_add_address = findViewById(R.id.btn_address_add);

        load_main_address(userID);
        load_other_address(userID);

        //添加新地址
        btn_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(AddressActivity.this,AddAddressActivity.class);
                intent.putExtra("userID",userID);
                startActivityForResult(intent,111);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                TextView tv_phone = view.findViewById(R.id.tv_address_phone);
                final String phone = tv_phone.getText().toString();
                TextView tv_address = view.findViewById(R.id.tv_address_address);
                final String address = tv_address.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(AddressActivity.this);
                builder.setTitle("提示信息");
                builder.setMessage("确认删除？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbHelper = new DBHelper(AddressActivity.this,"TakeOutApp.db",null,3);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("ID",userID);
                        contentValues.put("phone",phone);
                        contentValues.put("address",address);
                        dbHelper.DelAddress(contentValues);
                        Toast.makeText(AddressActivity.this,"删除成功!",Toast.LENGTH_SHORT).show();
                        load_other_address(userID);
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
    //更改后更新
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        load_other_address(userID);
        load_main_address(userID);
        //myadapter.notifyDataSetChanged();
    }

    //加载常用地址
    public void load_main_address(String userID){
        DBHelper dbHelper = new DBHelper(AddressActivity.this,"TakeOutApp.db",null,3);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "select * from user where userPhone = ?";
        lists = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{userID});
        while (cursor.moveToNext()){
            String addPhone = cursor.getString(cursor.getColumnIndex("userPhone"));
            String addAddress = cursor.getString(cursor.getColumnIndex("userAddress"));
            AddressAdd addressAdd = new AddressAdd(addPhone,addAddress);
            lists.add(addressAdd);
        }
        myadapter = new AddressAdapter(lists,this);
        listView_main.setAdapter(myadapter);
    }



    //加载其他地址
    public void load_other_address(String userID){
        DBHelper dbHelper = new DBHelper(AddressActivity.this,"TakeOutApp.db",null,3);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "select * from userAddress where userID = ?";
        lists = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{userID});
        while (cursor.moveToNext()){
            String addPhone = cursor.getString(cursor.getColumnIndex("userPhone"));
            String addAddress = cursor.getString(cursor.getColumnIndex("userAddress"));
            AddressAdd addressAdd = new AddressAdd(addPhone,addAddress);
            lists.add(addressAdd);
        }
        myadapter = new AddressAdapter(lists,this);
        listView.setAdapter(myadapter);
    }
}
