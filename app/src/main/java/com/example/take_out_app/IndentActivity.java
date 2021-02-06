package com.example.take_out_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IndentActivity extends AppCompatActivity{
    TextView tv_indentID;
    TextView tv_total;
    TextView tv_resName;
    TextView tv_resPhone;
    ListView lv_indent;
    ListView lv_address;
    Button btn_ok;
    List<AddressAdd> lists;
    AddressAdapter myadapter;
    String addPhone;
    String addAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indent);

        tv_indentID = findViewById(R.id.tv_indentID);
        tv_total = findViewById(R.id.tv_indent_total);
        lv_indent = findViewById(R.id.lv_indent);
        lv_address = findViewById(R.id.lv_indent_address);
        btn_ok = findViewById(R.id.btn_indent_ok);
        tv_resName = findViewById(R.id.tv_indent_res_name);
        tv_resPhone = findViewById(R.id.tv_indent_res_phone);

        Intent intent = getIntent();
        final int indentID = intent.getIntExtra("indentID",-1);
        ArrayList<GoodsDetails> lists = (ArrayList<GoodsDetails>)intent.getSerializableExtra("list");
        final Double total = intent.getDoubleExtra("total",-1);
        final String userID = intent.getStringExtra("userID");
        final String resID = intent.getStringExtra("resID");

        tv_indentID.setText(String.valueOf(indentID));
        tv_total.setText("￥" + total + "元");
        IndentAdapter indentAdapter = new IndentAdapter(IndentActivity.this,lists);
        lv_indent.setAdapter(indentAdapter);

        //加载用户信息
        load_user_info(userID);
        //加载商家信息
        load_res_info(resID);


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(IndentActivity.this);
                builder.setTitle("提示信息");
                builder.setMessage("订单已提交！\n由于技术限制，支付接口未实现");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //将信息添加到userIndent表中
                        DBHelper dbHelper = new DBHelper(IndentActivity.this,"TakeOutApp.db",null,3);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("orderID",indentID);
                        contentValues.put("userID",userID);
                        contentValues.put("resID",resID);
                        contentValues.put("userAddress",addAddress);
                        contentValues.put("userPhone",addPhone);
                        contentValues.put("type","已支付");
                        contentValues.put("total",total);
                        dbHelper.userIndent(contentValues);
                        IndentActivity.this.finish();
                    }
                });
                builder.show();
            }
        });


    }

    //加载用户信息（电话、地址）
    public void load_user_info(String userID){
        DBHelper dbHelper = new DBHelper(IndentActivity.this,"TakeOutApp.db",null,3);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "select * from user where userPhone = ?";
        lists = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{userID});
        while (cursor.moveToNext()){
            addPhone = cursor.getString(cursor.getColumnIndex("userPhone"));
            addAddress = cursor.getString(cursor.getColumnIndex("userAddress"));
            AddressAdd addressAdd = new AddressAdd(addPhone,addAddress);
            lists.add(addressAdd);
        }
        sqLiteDatabase.close();
        myadapter = new AddressAdapter(lists,this);
        lv_address.setAdapter(myadapter);
    }

    //加载商家信息
    public void load_res_info(String resID){
        DBHelper dbHelper = new DBHelper(IndentActivity.this,"TakeOutApp.db",null,3);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "select * from restaurant where resID = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{resID});
        while (cursor.moveToNext()){
            String resName = cursor.getString(cursor.getColumnIndex("resName"));
            String resPhone = cursor.getString(cursor.getColumnIndex("resPhone"));
            tv_resName.setText(resName);
            tv_resPhone.setText(resPhone);
        }
    }



}
