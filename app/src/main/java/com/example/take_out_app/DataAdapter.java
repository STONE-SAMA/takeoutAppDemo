package com.example.take_out_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DataAdapter extends BaseAdapter {
    List<Restaurant> lists;
    Context mcontext;

    public DataAdapter(List<Restaurant> lists,Context mcontext){
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
            view = LayoutInflater.from(mcontext).inflate(R.layout.resinfo,null);
        }else{
            view = convertView;
        }

        Restaurant restaurant = lists.get(position);
        ImageView iv_shop = view.findViewById(R.id.iv_shop);
        ImageView iv_star = view.findViewById(R.id.iv_star);
        TextView tv_resName = view.findViewById(R.id.tv_shopname);
        TextView tv_resLevel = view.findViewById(R.id.tv_level);
        TextView tv_resInfo = view.findViewById(R.id.tv_info);
        TextView tv_resMessage = view.findViewById(R.id.tv_message);
        TextView tv_resSell = view.findViewById(R.id.tv_sellnum);
        TextView tv_resID = view.findViewById(R.id.tv_res_info_ID);

        iv_shop.setImageResource(restaurant.getResPic());
        tv_resName.setText(restaurant.getResName());
        tv_resLevel.setText(restaurant.getResLevel());
        tv_resInfo.setText(restaurant.getResInfo());
        tv_resMessage.setText(restaurant.getResOff());
        tv_resSell.setText(restaurant.getResSell());
        tv_resID.setText(restaurant.getResID());

        return view;
    }
}
