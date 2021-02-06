package com.example.take_out_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class UserActivity extends AppCompatActivity {
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //得到当前登录用户的信息
        Intent intent = getIntent();
        final String user_login_id = intent.getStringExtra("user_login_id");
        //Toast.makeText(UserActivity.this,user_login_id,Toast.LENGTH_SHORT).show();

        //检查当前登录用户的个人信息，如个人信息为空，则跳转至个人信息完善界面
        DBHelper dbHelper = new DBHelper(UserActivity.this,"TakeOutApp.db",null,3);
        int result = dbHelper.UserInfoCheck(user_login_id);
        if(result==1){
            Intent intent_info = new Intent(UserActivity.this,PerInfoActivity.class);
            intent_info.putExtra("user_login_id",user_login_id);
            startActivity(intent_info);
        }

        TakeoutFragment takeoutFragment = new TakeoutFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userID",user_login_id);
        takeoutFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fl_layout,takeoutFragment);
        transaction.commit();

//设定商家图片
//        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("resPic",R.drawable.zhihu);
//        sqLiteDatabase.update("restaurant",contentValues,"resID = ?",new String[]{"6589845623"});
//        sqLiteDatabase.close();
//
//        ContentValues contentValues1 = new ContentValues();
//        contentValues1.put("resPic",R.drawable.thermate);
//        sqLiteDatabase.update("restaurant",contentValues1,"resID = ?",new String[]{"3565131321"});
//
//
//        ContentValues contentValues2 = new ContentValues();
//        contentValues2.put("resPic",R.drawable.mira);
//        sqLiteDatabase.update("restaurant",contentValues2,"resID = ?",new String[]{"1654822562"});
//
//
//        ContentValues contentValues3 = new ContentValues();
//        contentValues3.put("resPic",R.drawable.mastero);
//        sqLiteDatabase.update("restaurant",contentValues3,"resID = ?",new String[]{"2031654987"});
//
//
//        ContentValues contentValues4 = new ContentValues();
//        contentValues4.put("resPic",R.drawable.lesion);
//        sqLiteDatabase.update("restaurant",contentValues4,"resID = ?",new String[]{"1230465789"});
//
//
//        ContentValues contentValues5 = new ContentValues();
//        contentValues5.put("resPic",R.drawable.ela);
//        sqLiteDatabase.update("restaurant",contentValues5,"resID = ?",new String[]{"1234567890"});
//
//
//        sqLiteDatabase.execSQL("delete from Product");
//
//        sqLiteDatabase.close();


        radioGroup = findViewById(R.id.radio_group_menu);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbtn_takeout:
                        TakeoutFragment takeoutFragment = new TakeoutFragment();

                        Bundle bundle1 = new Bundle();
                        bundle1.putString("userID",user_login_id);
                        takeoutFragment.setArguments(bundle1);

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.fl_layout,takeoutFragment);
                        transaction.commit();
                        break;
                    case R.id.rbtn_indent:
                        IndentFragment indentFragment = new IndentFragment();

                        Bundle bundle2 = new Bundle();
                        bundle2.putString("userID",user_login_id);
                        indentFragment.setArguments(bundle2);

                        FragmentManager fragmentManager1 = getSupportFragmentManager();
                        FragmentTransaction transaction1 = fragmentManager1.beginTransaction();
                        transaction1.replace(R.id.fl_layout,indentFragment);
                        transaction1.commit();
                        break;
                    case R.id.rbtn_user:
                        UserFragment userFragment = new UserFragment();

                        //传递当前用户信息到userFragment中
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("userID",user_login_id);
                        userFragment.setArguments(bundle3);

                        FragmentManager fragmentManager2 = getSupportFragmentManager();
                        FragmentTransaction transaction2 = fragmentManager2.beginTransaction();
                        transaction2.replace(R.id.fl_layout,userFragment);
                        transaction2.commit();
                        break;
                }
            }
        });




    }
}
