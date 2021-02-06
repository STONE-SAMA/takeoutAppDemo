package com.example.take_out_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    ListView lv_search;
    List<Restaurant> lists;
    DataAdapter myDataAdapter;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        lv_search = findViewById(R.id.lv_search);

        Intent intent = getIntent();

        type = intent.getIntExtra("type",-1);
        DBHelper dbHelper = new DBHelper(SearchResultActivity.this,"TakeOutApp.db",null,3);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        if(type==1){
            String key = intent.getStringExtra("key");
            Cursor cursor = sqLiteDatabase.rawQuery("select * from restaurant where resName like?",new String[]{"%"+key+"%"});
            search_result(cursor);
        }else{
            //根据type值来检索，2为美食（中餐、西餐、料理），3为小吃，4为饮品，5为水果，6为热门
            //Cursor cursor = sqLiteDatabase.rawQuery("select * from restaurant where resType =?",new String[]{""});
            switch (type){
                case 2: Cursor cursor2 = sqLiteDatabase.rawQuery("select * from restaurant where resType =? or resType =? or resType =?",new String[]{"中餐","西餐","料理"});
                    search_result(cursor2);
                    break;
                case 3: Cursor cursor3 = sqLiteDatabase.rawQuery("select * from restaurant where resType =?",new String[]{"小吃"});
                    search_result(cursor3);
                    break;
                case 4: Cursor cursor4 = sqLiteDatabase.rawQuery("select * from restaurant where resType =?",new String[]{"饮品"});
                    search_result(cursor4);
                    break;
                case 5: Cursor cursor5 = sqLiteDatabase.rawQuery("select * from restaurant where resType =?",new String[]{"水果"});
                    search_result(cursor5);
                    break;
                case 6:
                    Toast.makeText(SearchResultActivity.this,"正在开发中！",Toast.LENGTH_SHORT).show();
                    break;
            }


        }




       // DBHelper dbHelper = new DBHelper(SearchResultActivity.this,"TakeOutApp.db",null,1);
       // SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        //Cursor cursor = sqLiteDatabase.rawQuery("select * from restaurant where resName like?",new String[]{"%"+key+"%"});
//        lists = new ArrayList<>();
//        while(cursor.moveToNext()){
//            String resName = cursor.getString(cursor.getColumnIndex("resName"));
//            String resLevel = cursor.getString(cursor.getColumnIndex("resCredit")).toString();
//            String resSell = cursor.getString(cursor.getColumnIndex("resSell"));
//            String resType = cursor.getString(cursor.getColumnIndex("resType"));
//            String resAddress = cursor.getString(cursor.getColumnIndex("resAddress"));
//            String resID = cursor.getString(cursor.getColumnIndex("resID"));
//            Restaurant restaurant = new Restaurant(resName,null,resLevel,resType,resAddress,resSell,resID);
//            lists.add(restaurant);
//        }
//        myDataAdapter = new DataAdapter(lists,SearchResultActivity.this);
//        lv_search.setAdapter(myDataAdapter);
    }

    public void search_result(Cursor cursor){
        lists = new ArrayList<>();
        while(cursor.moveToNext()){
            String resName = cursor.getString(cursor.getColumnIndex("resName"));
            String resLevel = cursor.getString(cursor.getColumnIndex("resCredit")).toString();
            int resPic = cursor.getInt(cursor.getColumnIndex("resPic"));
            String resSell = cursor.getString(cursor.getColumnIndex("resSell"));
            String resType = cursor.getString(cursor.getColumnIndex("resType"));
            String resAddress = cursor.getString(cursor.getColumnIndex("resAddress"));
            String resID = cursor.getString(cursor.getColumnIndex("resID"));
            Restaurant restaurant = new Restaurant(resName,resPic,resLevel,resType,resAddress,resSell,resID);
            lists.add(restaurant);
        }
        myDataAdapter = new DataAdapter(lists,SearchResultActivity.this);
        lv_search.setAdapter(myDataAdapter);
    }


}
