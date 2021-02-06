package com.example.take_out_app;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static DBHelper dbHelper;


    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    static synchronized DBHelper getInstance(Context context){
        if(dbHelper == null){
            dbHelper = new DBHelper(context,"TakeOutApp.db",null,3);
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_user = "create table if not exists user(userPhone char(11) primary key,userPassword text,userName text,userSex text,userAddress text)";
        db.execSQL(sql_user);

        String sql_res = "create table if not exists restaurant(resID char(10) primary key,resPassword text,resName text,resPhone text,resAddress text,resType text,resCredit integer,resSell integer,resPic text)";
        db.execSQL(sql_res);

        String sql_address = "create table if not exists userAddress(ID integer primary key autoincrement,userID text,userPhone text,userAddress text)";
        db.execSQL(sql_address);

        String sql_indent = "create table if not exists userIndent(orderID integer primary key,userID char(11),resID char(10),userAddress text,userPhone text,type text,total double)";
        db.execSQL(sql_indent);

        String sql_indent_info = "create table if not exists IndentInfo(ID integer primary key autoincrement,orderID intger,ProdId char(6),ProdName text,ProdPrice double,ProdTotal double)";
        db.execSQL(sql_indent_info);

        String sql_Prod = "create table if not exists Product(ProdID integer primary key autoincrement,resID char(10),ProdName text,ProdPrice double,type text,ProdInfo text,ProdPic intger)";
        db.execSQL(sql_Prod);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if(oldVersion==1){
//            String sql = "create table if not exists userAddress(ID integer primary key autoincrement,userID text,userPhone text,userAddress text)";
//            db.execSQL(sql);
//        }else if(oldVersion==2){
//            String sql_indent = "create table if not exists userIndent(orderID integer primary key,userPhone char(11),resID char(10),userAddress text,userPhone text,type text,total double)";
//            db.execSQL(sql_indent);
//            String sql_indent_info = "create table if not exists IndentInfo(ID integer primary key autoincrement,orderID intger,ProdId char(6),ProdName text,ProdPrice double,ProdTotal double)";
//            db.execSQL(sql_indent_info);
//            String sql_Prod = "create table if not exists Product(ProdID integer primary key autoincrement,resID char(10),ProdName text,ProdPrice double,type text,ProdInfo text,ProdPic intger)";
//            db.execSQL(sql_Prod);
//        }

    }
    //检测用户注册时使用的电话是否已被使用
    public boolean UserPhoneCheck(String userID){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "select * from user where userPhone = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{userID});
        while (cursor.moveToNext()){
            sqLiteDatabase.close();
            return true;
        }
        return false;
    }


    //用户注册
    public void insert(ContentValues contentValues){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert("user",null,contentValues);
        sqLiteDatabase.close();
    }
    //登陆时检测账号密码是否正确，正确则返回1
    public int Login(ContentValues contentValues){
        String id = (String) contentValues.get("ID");
        String password = (String) contentValues.get("Password");
        int flag = 0;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "select userPassword from user where userPhone = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{id});

        while (cursor.moveToNext()){
            String pass = cursor.getString(cursor.getColumnIndex("userPassword"));
            if(pass.equals(password)){
                flag = 1;
            }
        }
        return flag;

    }

    //用户个人信息更新
    public void update(ContentValues contentValues,String userID){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.update("user",contentValues,"userPhone=?",new String[]{userID});
        sqLiteDatabase.close();
    }

    //检查个人信息是否完整
    public int UserInfoCheck(String userID){
        int result=0;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "select userName from user where userPhone = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{userID});
        while (cursor.moveToNext()){
            String info = cursor.getString(cursor.getColumnIndex("userName"));
            if(TextUtils.isEmpty(info)){
                result = 1;
            }
        }
        return result;
    }

    //店家注册时检查账号是否存在
    public boolean ResIDCheck(String resID){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "select * from restaurant where resID = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{resID});
        while (cursor.moveToNext()){
            sqLiteDatabase.close();
            return true;
        }
        return false;
    }

    //店家注册时检查电话是否已使用
    public boolean ResPhoneCheck(String resPhone){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "select * from restaurant where resPhone = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{resPhone});
        while (cursor.moveToNext()){
            sqLiteDatabase.close();
            return true;
        }
        return false;
    }

    //店家注册
    public void ResRegister(ContentValues contentValues){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert("restaurant",null,contentValues);
        sqLiteDatabase.close();
    }

    //店家登陆
    public boolean ResLogin(ContentValues contentValues){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String resID = (String)contentValues.get("resID");
        String resPassword = (String)contentValues.get("resPassword");
        String sql = "select resPassword from restaurant where resID = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{resID});
        while (cursor.moveToNext()){
            String pass = cursor.getString(cursor.getColumnIndex("resPassword"));
            if(pass.equals(resPassword)){
                return true;
            }
        }
        return false;
    }

    //密码修改
    public void ChangePassword(ContentValues contentValues){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        int type = (int)contentValues.get("type");
        String ID = (String)contentValues.get("ID");
        String pass = (String)contentValues.get("pass");
        ContentValues values = new ContentValues();
        if(type==1){
            values.put("userPassword",pass);
            sqLiteDatabase.update("user",values,"userPhone=?",new String[]{ID});
        }else{
            values.put("resPassword",pass);
            sqLiteDatabase.update("restaurant",values,"resID=?",new String[]{ID});
        }
        sqLiteDatabase.close();
    }
    //得到当前登陆用户的用户名
    public String UserName(String userId){
        String result = null;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "select userName from user where userPhone = ?";
        Cursor cursor =sqLiteDatabase.rawQuery(sql,new String[]{userId});
        while (cursor.moveToNext()){
            result = cursor.getString(cursor.getColumnIndex("userName"));
        }
        sqLiteDatabase.close();
        return result;
    }
    //添加新地址到数据库
    public void AddAddress(ContentValues contentValues){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert("userAddress",null,contentValues);
        sqLiteDatabase.close();
    }
    //删除新地址
    public void DelAddress(ContentValues contentValues){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String id = contentValues.get("ID").toString();
        String phone = contentValues.get("phone").toString();
        String address = contentValues.get("address").toString();
        sqLiteDatabase.delete("userAddress","userID = ? and userPhone = ? and userAddress = ?",new String[]{id,phone,address});
        sqLiteDatabase.close();
    }
    //添加订单
    public void userIndent(ContentValues contentValues){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert("userIndent",null,contentValues);
        sqLiteDatabase.close();
    }


    //主界面搜索
    public Cursor search(String text){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "select * from restaurant where resName like ? or resType like ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{"%"+text+"%","%"+text+"%"});
        return cursor;
    }


}
