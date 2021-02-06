package com.example.take_out_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class RecordAdapter extends BaseAdapter {
    List<Record> lists;
    Context mcontext;

    public RecordAdapter(List<Record> lists, Context mcontext) {
        this.lists = lists;
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView==null){
            view = LayoutInflater.from(mcontext).inflate(R.layout.myindent,null);
        }else{
            view = convertView;
        }

        Record record = lists.get(position);

        TextView tv_indentID = view.findViewById(R.id.tv_indent_id_num);
        TextView tv_userID = view.findViewById(R.id.tv_indent_user_id_num);
        TextView tv_resName = view.findViewById(R.id.tv_indent_res_id_name);
        TextView tv_resPhone = view.findViewById(R.id.tv_indent_res_id_phone_num);
        TextView tv_indent_address = view.findViewById(R.id.tv_indent_user_address_num);
        TextView tv_indent_phone = view.findViewById(R.id.tv_indent_user_phone_num);
        TextView tv_type = view.findViewById(R.id.tv_indent_type_num);
        TextView tv_total = view.findViewById(R.id.tv_indent_total_num);

        tv_indentID.setText(Integer.toString(record.getOrderID()));
        tv_userID.setText(record.getUserId());
        tv_resName.setText(record.getResName());
        tv_resPhone.setText(record.getResPhone());
        tv_indent_address.setText(record.getIndentAddress());
        tv_indent_phone.setText(record.getIndentPhone());
        tv_type.setText(record.getType());
        tv_total.setText(record.getTotal().toString());

        return view;
    }
}
