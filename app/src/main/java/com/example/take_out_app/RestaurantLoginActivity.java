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
import android.widget.Toast;

public class RestaurantLoginActivity extends AppCompatActivity {
    EditText et_res_ID;
    EditText et_res_Password;
    Button btn_login;
    Button btn_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_login);

        et_res_ID = findViewById(R.id.et_res_login_ID);
        et_res_Password = findViewById(R.id.et_res_login_Password);
        btn_login = findViewById(R.id.btn_res_login);
        btn_return = findViewById(R.id.btn_res_login_return);

        //商家登陆事件
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_res_ID.getText().toString())||TextUtils.isEmpty(et_res_Password.getText().toString())){
                    Toast.makeText(RestaurantLoginActivity.this,"请输入账号和密码！",Toast.LENGTH_SHORT).show();
                }else{
                    DBHelper dbHelper = new DBHelper(RestaurantLoginActivity.this,"TakeOutApp.db",null,3);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("resID",et_res_ID.getText().toString());
                    contentValues.put("resPassword",et_res_Password.getText().toString());
                    if(dbHelper.ResLogin(contentValues)){
                        AlertDialog.Builder builder = new AlertDialog.Builder(RestaurantLoginActivity.this);
                        builder.setTitle("提示信息");
                        builder.setMessage("登录成功！");
                        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String res_LoginID = et_res_ID.getText().toString();
                                Intent intent = new Intent(RestaurantLoginActivity.this,ResMenuActivity.class);
                                intent.putExtra("LoginID",res_LoginID);
                                startActivity(intent);
                                RestaurantLoginActivity.this.finish();
                            }
                        });
                        builder.show();
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(RestaurantLoginActivity.this);
                        builder.setTitle("提示信息");
                        builder.setMessage("登录失败！\n账号或密码错误！");
                        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                et_res_ID.setText("");
                                et_res_Password.setText("");
                            }
                        });
                        builder.show();
                    }
                }
            }
        });
        //返回事件
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestaurantLoginActivity.this.finish();
            }
        });





    }
}
