package com.example.take_out_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AddressAdapter extends BaseAdapter {
    List<AddressAdd> lists;
    Context mcontext;

    public AddressAdapter(List<AddressAdd> lists, Context mcontext) {
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
            view = LayoutInflater.from(mcontext).inflate(R.layout.addressinfo,null);
        }else{
            view = convertView;
        }

        AddressAdd addressAdd = lists.get(position);
        TextView tv_phone = view.findViewById(R.id.tv_address_phone);
        TextView tv_address = view.findViewById(R.id.tv_address_address);
        tv_phone.setText(addressAdd.getPhone());
        tv_address.setText(addressAdd.getNewAddress());

        return  view;
    }
}
