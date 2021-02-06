package com.example.take_out_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.widget.AdapterView.*;

public class RestaurantRegisterActivity extends AppCompatActivity {

    EditText et_resID;
    EditText et_resName;
    EditText et_resAddress;
    EditText et_resPhone;
    EditText et_resPassword;
    EditText et_resPassword_again;
    Spinner res_type_spinner;
    Button btn_register;
    Button btn_cancel;
    String spinner_data;

    //final String[] restype = {"中餐", "西餐", "料理", "小吃", "饮品", "水果", "其他"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_register);

        et_resID = findViewById(R.id.et_resID);//商家注册账号
        et_resName = findViewById(R.id.et_resName);
        et_resAddress = findViewById(R.id.et_resAddress);
        et_resPhone = findViewById(R.id.et_resPhone);
        et_resPassword = findViewById(R.id.et_resPassword);
        et_resPassword_again = findViewById(R.id.et_resPassword_again);
        res_type_spinner = findViewById(R.id.res_type_spinner);
        btn_register = findViewById(R.id.btn_res_register);
        btn_cancel = findViewById(R.id.btn_res_register_cancel);

        res_type_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_data = (String)res_type_spinner.getItemAtPosition(position);
                //Toast.makeText(RestaurantRegisterActivity.this,data,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        btn_register.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DBHelper dbHelper = new DBHelper(RestaurantRegisterActivity.this,"TakeOutApp.db",null,1);
//                ContentValues contentValues = new ContentValues();
//                contentValues.put("resID",et_resID.getText().toString());
//                contentValues.put("resPassword",et_resPassword.getText().toString());
//                contentValues.put("resName",et_resName.getText().toString());
//                contentValues.put("resPhone",et_resPhone.getText().toString());
//                contentValues.put("resAddress",et_resAddress.getText().toString());
//                contentValues.put("resType",spinner_data);
//                contentValues.put("resCredit",80);
//                contentValues.put("resSell",0);
//                dbHelper.ResRegister(contentValues);
//                Toast.makeText(RestaurantRegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
//            }
//        });

        //取消注册事件
        btn_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                RestaurantRegisterActivity.this.finish();
            }
        });

        btn_register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断注册信息是否输入正确
                String resID = et_resID.getText().toString();
                if(TextUtils.isEmpty(resID)||resID.length()!=10){
                    Toast.makeText(RestaurantRegisterActivity.this,"账号输入不合法！",Toast.LENGTH_SHORT).show();
                }else{
                    DBHelper dbHelper = new DBHelper(RestaurantRegisterActivity.this,"TakeOutApp.db",null,3);
                    if(dbHelper.ResIDCheck(resID)){
                        Toast.makeText(RestaurantRegisterActivity.this,"该账号已存在！",Toast.LENGTH_SHORT).show();
                    }else{
                        String pass1 = et_resPassword.getText().toString();
                        String pass2 = et_resPassword_again.getText().toString();
                        if(TextUtils.isEmpty(pass1)||TextUtils.isEmpty(pass2)||pass1.length()<6){
                            Toast.makeText(RestaurantRegisterActivity.this,"请输入至少6位密码",Toast.LENGTH_SHORT).show();
                        }else{
                            if(pass1.equals(pass2)){
                                if(TextUtils.isEmpty(et_resAddress.getText().toString())){
                                    Toast.makeText(RestaurantRegisterActivity.this,"地址不能为空！",Toast.LENGTH_SHORT).show();
                                }else{
                                    if(TextUtils.isEmpty(et_resPhone.getText().toString())){
                                        Toast.makeText(RestaurantRegisterActivity.this,"电话不能为空！",Toast.LENGTH_SHORT).show();
                                    }else{
                                        String phone = et_resPhone.getText().toString();
                                        Pattern p = Pattern.compile("^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$");
                                        Matcher m = p.matcher(phone);
                                        if(m.matches()){
                                            if(dbHelper.ResPhoneCheck(phone)){
                                                Toast.makeText(RestaurantRegisterActivity.this,"该电话已使用！",Toast.LENGTH_SHORT).show();
                                            }else{
                                                if(TextUtils.isEmpty(et_resName.getText().toString())){
                                                    Toast.makeText(RestaurantRegisterActivity.this,"店名不能为空！",Toast.LENGTH_SHORT).show();
                                                }else{
                                                    //if(TextUtils.isEmpty(spinner_data)){
                                                    if(spinner_data.equals("请选择类型")){
                                                        Toast.makeText(RestaurantRegisterActivity.this,"请选择主营类型！",Toast.LENGTH_SHORT).show();
                                                    }else{

                                                        //在此继续判断验证码是否输入正确

                                                        //至此注册验证判断通过，进行注册操作
                                                        ContentValues contentValues = new ContentValues();
                                                        //resID char(10) primary key,resPassword text,resName text,resPhone text,resAddress text,resType text,resCredit integer,resSell integer,resPic text
                                                        contentValues.put("resID",resID);
                                                        contentValues.put("resPassword",et_resPassword.getText().toString());
                                                        contentValues.put("resName",et_resName.getText().toString());
                                                        contentValues.put("resPhone",et_resPhone.getText().toString());
                                                        contentValues.put("resAddress",et_resAddress.getText().toString());
                                                        contentValues.put("resType",spinner_data);
                                                        contentValues.put("resCredit",80);
                                                        contentValues.put("resSell",0);
                                                        dbHelper.ResRegister(contentValues);

                                                        Toast.makeText(RestaurantRegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(RestaurantRegisterActivity.this,RestaurantLoginActivity.class);
                                                        startActivity(intent);
                                                        RestaurantRegisterActivity.this.finish();


                                                    }
                                                }
                                            }
                                        }else{
                                            Toast.makeText(RestaurantRegisterActivity.this,"请输入正确的电话号码!",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }else{
                                Toast.makeText(RestaurantRegisterActivity.this,"密码输入不一致！",Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }

            }
        });


    }


}
