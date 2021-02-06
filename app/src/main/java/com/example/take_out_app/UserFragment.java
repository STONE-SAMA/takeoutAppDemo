package com.example.take_out_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserFragment extends Fragment {

    TextView tv_userPhone;
    TextView tv_userName;
    Button btn_changePass;
    Button btn_address;
    Button btn_collect;
    Button btn_about;
    Button btn_lock;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //得到当前登陆用户的手机号（账号）
        Bundle bundle = this.getArguments();
        final String userID = bundle.getString("userID");

        tv_userPhone = getActivity().findViewById(R.id.tv_user_Phone);
        tv_userPhone.setText(userID);

        //得到当前用户的用户名
        tv_userName = getActivity().findViewById(R.id.tv_user_name);
        DBHelper dbHelper = new DBHelper(getActivity(),"TakeOutApp.db",null,3);
        tv_userName.setText(dbHelper.UserName(userID));


        btn_changePass = getActivity().findViewById(R.id.btn_change_user_password);
        btn_about = getActivity().findViewById(R.id.btn_about);
        btn_address = getActivity().findViewById(R.id.btn_manage_address);
        btn_collect = getActivity().findViewById(R.id.btn_collect);
        btn_lock = getActivity().findViewById(R.id.btn_lock);

        btn_changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ChangePasswordActivity.class);
                intent.putExtra("type",1);
                intent.putExtra("ID",userID);
                startActivity(intent);
            }
        });

        btn_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("版权信息");
                builder.setMessage("开发时间：2019/12\n人员：石函睿、单玉华、曹浩");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

        btn_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"正在开发中！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),AddressActivity.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });

        btn_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"正在开发中！",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
