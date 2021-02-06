package com.example.take_out_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv_forget;
    TextView tv_register;
    Button btn_Login;
    EditText et_user_id;
    EditText et_user_password;
    Button btn_res_Login;
    Button btn_res_Register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_forget = findViewById(R.id.tv_forget_password);
        tv_register = findViewById(R.id.tv_register);
        btn_Login = findViewById(R.id.btn_Login);
        et_user_id = findViewById(R.id.et_login_id);
        et_user_password = findViewById(R.id.et_login_password);

        btn_res_Login = findViewById(R.id.btn_restaurant_login);
        btn_res_Register = findViewById(R.id.btn_restaurant_register);

        //DBHelper dbHelper = new DBHelper(this,"TakeOutApp.db",null,1);
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("userPhone","18851730833");
//        contentValues.put("userPassword","123456");
//        contentValues.put("userName","内部账号");
//        contentValues.put("userSex","男");
//        contentValues.put("userAddress","科技楼2-410");
//        dbHelper.insert(contentValues);

        //注册事件
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        //忘记密码
        tv_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FindInfoActivity.class);
                startActivity(intent);
            }
        });
        //用户登陆事件
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    DBHelper dbHelper = new DBHelper(MainActivity.this,"TakeOutApp.db",null,3);
                    final String userID = et_user_id.getText().toString();
                    String userPassword = et_user_password.getText().toString();
                    //验证账号密码
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("ID",userID);
                    contentValues.put("Password",userPassword);
                    int result = dbHelper.Login(contentValues);
                    if(result == 1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("提示信息");
                        builder.setMessage("登录成功！");
                        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String user_login_id = userID;
                                Intent intent = new Intent(MainActivity.this,UserActivity.class);
                                intent.putExtra("user_login_id",user_login_id);
                                startActivity(intent);
                                MainActivity.this.finish();
                            }
                        });
                        builder.show();
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("提示信息");
                        builder.setMessage("登录失败！\n账号或密码错误！");
                        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                et_user_id.setText("");
                                et_user_password.setText("");
                            }
                        });
                        builder.show();
                    }

            }
        });

        //跳转商家注册
        btn_res_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RestaurantRegisterActivity.class);
                startActivity(intent);
            }
        });
        //跳转商家登录
        btn_res_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RestaurantLoginActivity.class);
                startActivity(intent);
            }
        });



    }
}
