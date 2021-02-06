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
import android.widget.TextView;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {

    TextView tv_userID;
    EditText et_password;
    EditText et_password_again;
    Button btn_OK;
    Button btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        tv_userID = findViewById(R.id.tv_change_password_userID);
        et_password = findViewById(R.id.et_change_password_1);
        et_password_again = findViewById(R.id.et_change_password_2);
        btn_OK = findViewById(R.id.btn_change_password_ok);
        btn_cancel = findViewById(R.id.btn_change_password_return);

        //得到修改用户的账号信息并显示
        final Intent intent = getIntent();
        final String ID = intent.getStringExtra("ID");
        tv_userID.setText(ID);

        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
                builder.setTitle("提示信息");
                builder.setMessage("确认修改？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String pass1 = et_password.getText().toString();
                        String pass2 = et_password_again.getText().toString();
                        if(TextUtils.isEmpty(pass1)||TextUtils.isEmpty(pass2)||pass1.length()<6){
                            Toast.makeText(ChangePasswordActivity.this,"请至少输入6位密码！",Toast.LENGTH_SHORT).show();
                        }else{
                            if(!pass1.equals(pass2)){
                                Toast.makeText(ChangePasswordActivity.this,"输入密码不一致",Toast.LENGTH_SHORT).show();
                            }else{
                                //执行数据库操作
                                int type = intent.getIntExtra("type",-1);
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("type",type);
                                contentValues.put("ID",ID);
                                contentValues.put("pass",pass1);
                                DBHelper dbHelper = new DBHelper(ChangePasswordActivity.this,"TakeOutApp.db",null,3);
                                dbHelper.ChangePassword(contentValues);

                                Toast.makeText(ChangePasswordActivity.this,"密码修改完成！",Toast.LENGTH_SHORT).show();
                                ChangePasswordActivity.this.finish();
                            }
                        }
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

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
                builder.setTitle("提示信息");
                builder.setMessage("放弃修改？");
                builder.setPositiveButton("放弃", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ChangePasswordActivity.this.finish();
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
