package com.example.take_out_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private Bitmap bitmap;
    private String code;

    EditText et_register_id;
    EditText et_register_password;
    EditText et_register_password_again;
    EditText et_register_verify;
    TextView tv_agreement;
    Button btn_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_register_id = findViewById(R.id.et_register_id);
        et_register_password = findViewById(R.id.et_register_password);
        et_register_password_again = findViewById(R.id.et_register_password_again);
        et_register_verify = findViewById(R.id.et_verify);
        tv_agreement = findViewById(R.id.tv_agreement);

        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = et_register_id.getText().toString();
                Pattern p = Pattern.compile("^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$");
                Matcher m = p.matcher(phone);
                if(!m.matches()){
                    Toast.makeText(RegisterActivity.this,"请输入正确的电话号码!",Toast.LENGTH_SHORT).show();
                }else{
                    String pass1 = et_register_password.getText().toString();
                    String pass2 = et_register_password_again.getText().toString();
                    if(pass1.equals(pass2)){
                        String str = et_register_verify.getText().toString();
                        String code_lower = code.toLowerCase();
                        String code_upper = code.toUpperCase();

                        if(str.equals(code)||str.equals(code_lower)||str.equals(code_upper)){
                            DBHelper dbHelper = new DBHelper(RegisterActivity.this,"TakeOutApp.db",null,3);

                            if(dbHelper.UserPhoneCheck(phone)){
                                Toast.makeText(RegisterActivity.this,"该手机号已被注册！",Toast.LENGTH_SHORT).show();
                            }else{
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("userPhone",phone);
                                contentValues.put("userPassword",pass1);
                                dbHelper.insert(contentValues);
                                //注册提示信息
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setTitle("提示信息");
                                builder.setMessage("注册成功！");
                                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        RegisterActivity.this.finish();
                                    }
                                });
                                builder.show();
                            }
                        }else{
                            Toast.makeText(RegisterActivity.this,"验证码输入错误！",Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(RegisterActivity.this,"前后密码输入不一致!",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        //获取需要展示图片验证码的ImageView
        final ImageView image = (ImageView) findViewById(R.id.iv_verify_image);
        //获取工具类生成的图片验证码对象
        bitmap = CodeUtils.getInstance().createBitmap();
        //获取当前图片验证码的对应内容用于校验
        code = CodeUtils.getInstance().getCode();

        image.setImageBitmap(bitmap);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap = CodeUtils.getInstance().createBitmap();
                code = CodeUtils.getInstance().getCode();
                image.setImageBitmap(bitmap);
                //Toast.makeText(RegisterActivity.this, code, Toast.LENGTH_SHORT).show();
            }
        });

        tv_agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.bilibili.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }

}
