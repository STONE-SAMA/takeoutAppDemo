package com.example.take_out_app;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class TakeoutFragment extends Fragment  {

    ViewFlipper flipper;
    Button btn_search;
    EditText et_search;

    RadioGroup radioGroup_type;

    private Context mContext;
    private final static int MIN_MOVE = 100;
    private GestureDetector detector;
    private MyGestureListener MyGesture;

    List<Restaurant> lists;
    DataAdapter myDataAdapter;
    ListView listView;
    SearchView searchView;


    int []picID = {R.drawable.ela,R.drawable.lesion,R.drawable.mastero,R.drawable.mira};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.take_out_fragment,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        flipper = getActivity().findViewById(R.id.viewFlipper);
        listView = getActivity().findViewById(R.id.lv_takeout);

       //detector = new GestureDetector(this);

        for(int i = 0;i < picID.length;i++){
            flipper.addView(getImageView(picID[i]));
        }

        //btn_search = getActivity().findViewById(R.id.btn_search);
        //et_search = getActivity().findViewById(R.id.et_search);
        searchView = getActivity().findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                DBHelper dbHelper = new DBHelper(getActivity(),"TakeOutApp.db",null,3);
                Cursor cursor = dbHelper.search(newText);
                lists = new ArrayList<>();
                while(cursor.moveToNext()){
                    //resName text,resPhone text,resAddress text,resType text,resCredit integer,resSell integer,resPic text
                    String resName = cursor.getString(cursor.getColumnIndex("resName"));
                    int resPic = cursor.getInt(cursor.getColumnIndex("resPic"));
                    String resLevel = cursor.getString(cursor.getColumnIndex("resCredit")).toString();
                    String resSell = cursor.getString(cursor.getColumnIndex("resSell"));
                    String resType = cursor.getString(cursor.getColumnIndex("resType"));
                    String resAddress = cursor.getString(cursor.getColumnIndex("resAddress"));
                    String resID = cursor.getString(cursor.getColumnIndex("resID"));
                    Restaurant restaurant = new Restaurant(resName,resPic,resLevel,resType,resAddress,resSell,resID);
                    lists.add(restaurant);
                }
                myDataAdapter = new DataAdapter(lists,getActivity());
                listView.setAdapter(myDataAdapter);
                return false;
            }
        });

        radioGroup_type = getActivity().findViewById(R.id.radio_group_type);

        //得到当前登陆用户的手机号（账号）
        Bundle bundle = TakeoutFragment.this.getArguments();
        final String userID = bundle.getString("userID");

        radioGroup_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Intent intent = new Intent(getActivity(),SearchResultActivity.class);
                switch (checkedId){
                    case R.id.rbtn_food:
                        intent.putExtra("type",2);
                        break;
                    case R.id.rbtn_fastfood:
                        intent.putExtra("type",3);
                        break;
                    case R.id.rbtn_drink:
                        intent.putExtra("type",4);
                        break;
                    case R.id.rbtn_fruit:
                        intent.putExtra("type",5);
                        break;
                    case R.id.rbtn_hot:
                        intent.putExtra("type",6);
                        break;
                }
                startActivity(intent);
            }
        });

        //搜索按钮点击事件
//        btn_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String key = et_search.getText().toString();
////                DBHelper dbHelper = new DBHelper(getActivity(),"TakeOutApp.db",null,1);
//////                SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
//////                Cursor cursor = sqLiteDatabase.rawQuery("select * from restaurant where resName like?",new String[]{"%"+key+"%"});
//////                while(cursor.moveToNext()){
//////
//////                }
//                Intent intent = new Intent(getActivity(),SearchResultActivity.class);
//                intent.putExtra("key",key);
//                intent.putExtra("type",1);
//                startActivity(intent);
//
//                //getActivity().finish();
//
//            }
//        });



        //加载数据库中商家信息到listview中
        DBHelper dbHelper = new DBHelper(getActivity(),"TakeOutApp.db",null,3);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "select * from restaurant";
        lists = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        while(cursor.moveToNext()){
            //resName text,resPhone text,resAddress text,resType text,resCredit integer,resSell integer,resPic text
            String resName = cursor.getString(cursor.getColumnIndex("resName"));
            int resPic = cursor.getInt(cursor.getColumnIndex("resPic"));
            String resLevel = cursor.getString(cursor.getColumnIndex("resCredit")).toString();
            String resSell = cursor.getString(cursor.getColumnIndex("resSell"));
            String resType = cursor.getString(cursor.getColumnIndex("resType"));
            String resAddress = cursor.getString(cursor.getColumnIndex("resAddress"));
            String resID = cursor.getString(cursor.getColumnIndex("resID"));
            Restaurant restaurant = new Restaurant(resName,resPic,resLevel,resType,resAddress,resSell,resID);
            lists.add(restaurant);
        }
        myDataAdapter = new DataAdapter(lists,getActivity());
        listView.setAdapter(myDataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView click_resID = (TextView)view.findViewById(R.id.tv_res_info_ID);
                String resID = click_resID.getText().toString();
                Intent intent = new Intent(getActivity(),ResInfoActivity.class);
                intent.putExtra("resID",resID);//将点击的商家的ID传递过去，在商家详情界面通过数据库加载数据
                intent.putExtra("userID",userID);
                startActivity(intent);
                //Toast.makeText(getActivity(),resID,Toast.LENGTH_SHORT).show();
            }
        });


//        flipper.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                detector.onTouchEvent(event);
//                return false;
//            }
//        });

    }


//
//
//    public boolean onTouchEvent(MotionEvent event) {
//        TODO Auto-generated method stub
//        return this.detector.onTouchEvent(event);
//    }

    //实现手势操作
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e1.getX() - e2.getX() > MIN_MOVE){
            //Toast.makeText(getActivity(), (int) e1.getX(),Toast.LENGTH_SHORT).show();
            flipper.setInAnimation(mContext,R.anim.right_in);
            flipper.setOutAnimation(mContext, R.anim.right_out);
            flipper.showNext();
        }else if(e2.getX() - e1.getX() > MIN_MOVE){
            flipper.setInAnimation(mContext,R.anim.left_in);
            flipper.setOutAnimation(mContext, R.anim.left_out);
            flipper.showPrevious();
        }
        return true;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    private ImageView getImageView(int resId){
        ImageView img = new ImageView(getActivity());
        img.setBackgroundResource(resId);
        return img;
    }

//    @Override
//    public boolean onDown(MotionEvent e) {
//        return false;
//    }
//
//    @Override
//    public void onShowPress(MotionEvent e) {
//
//    }
//
//    @Override
//    public boolean onSingleTapUp(MotionEvent e) {
//        return false;
//    }
//
//    @Override
//    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//        return false;
//    }
//
//    @Override
//    public void onLongPress(MotionEvent e) {
//
//    }
//
//    @Override
//    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//        if(e1.getX() - e2.getX() > MIN_MOVE){
//            //Toast.makeText(getActivity(), (int) e1.getX(),Toast.LENGTH_SHORT).show();
//            flipper.setInAnimation(mContext,R.anim.right_in);
//            flipper.setOutAnimation(mContext, R.anim.right_out);
//            flipper.showNext();
//        }else if(e2.getX() - e1.getX() > MIN_MOVE){
//            flipper.setInAnimation(mContext,R.anim.left_in);
//            flipper.setOutAnimation(mContext, R.anim.left_out);
//            flipper.showPrevious();
//        }
//        return true;
//    }
//



}
