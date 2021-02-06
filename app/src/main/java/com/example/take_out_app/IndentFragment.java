package com.example.take_out_app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class IndentFragment extends Fragment {

    List<Record> lists;
    RecordAdapter myAdapter;
    ListView listView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.indent_fragment,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //得到当前登陆用户的手机号（账号）
        Bundle bundle = IndentFragment.this.getArguments();
        final String userID = bundle.getString("userID");

        listView = getActivity().findViewById(R.id.lv_my_indent);

        //加载订单信息
    load_indent_info(userID);
    }

    public void load_indent_info(String userID){
        DBHelper dbHelper = new DBHelper(getActivity(),"TakeOutApp.db",null,3);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "select resID from userIndent where userID = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{userID});
        String resID = null;
        String resName = null;
        String resPhone = null;
        while (cursor.moveToNext()){
            resID = cursor.getString(cursor.getColumnIndex("resID"));
        }
        String sql_res = "select * from restaurant where resID = ?";
        Cursor cursor1 = sqLiteDatabase.rawQuery(sql_res,new String[]{resID});
        while(cursor1.moveToNext()){
            resName = cursor1.getString(cursor1.getColumnIndex("resName"));
            resPhone = cursor1.getString(cursor1.getColumnIndex("resPhone"));
        }
        String sql_indent = "select * from userIndent where userID = ?";
        Cursor cursor2 = sqLiteDatabase.rawQuery(sql_indent,new String[]{userID});
        lists = new ArrayList<>();
        while (cursor2.moveToNext()){
            int orderID = cursor2.getInt(cursor2.getColumnIndex("orderID"));
            String userAddress = cursor2.getString(cursor2.getColumnIndex("userAddress"));
            String userPhone = cursor2.getString(cursor2.getColumnIndex("userPhone"));
            String type = cursor2.getString(cursor2.getColumnIndex("type"));
            Double total = cursor2.getDouble(cursor2.getColumnIndex("total"));
            Record record = new Record(orderID,userID,resName,resPhone,userAddress,userPhone,type,total);
            lists.add(record);
        }
        myAdapter = new RecordAdapter(lists,getActivity());
        listView.setAdapter(myAdapter);
    }


}
