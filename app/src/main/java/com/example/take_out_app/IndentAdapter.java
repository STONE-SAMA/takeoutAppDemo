package com.example.take_out_app;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Description:购物车的适配器
 * author: henry
 * Date: 16-4-21 下午5:36.
 */
public class IndentAdapter extends BaseAdapter {

    private IndentActivity context;
    private List<GoodsDetails> list;
    private int num;
    public IndentAdapter(IndentActivity context, List<GoodsDetails> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder ;
        if (convertView == null){
            convertView = View.inflate(context, R.layout.listitem_indent_cart,null);
            holder = new ViewHolder();
            holder.mName = (TextView) convertView.findViewById(R.id.tv_name_cart);
            holder.mNum = (TextView) convertView.findViewById(R.id.tv_num_cart);
            holder.mPrice = (TextView)convertView.findViewById(R.id.price_cart);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        //初始化
        holder.mName.setText(list.get(position).getProdName());
        holder.mPrice.setText("￥" + list.get(position).getProdPrice());
        holder.mNum.setText(list.get(position).getProdCount() + "");

        return convertView;
    }

    class ViewHolder{
        private TextView mName;
        private TextView mPrice;
        private TextView mNum;
    }
}
