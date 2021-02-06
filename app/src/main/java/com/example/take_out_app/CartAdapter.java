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
public class CartAdapter extends BaseAdapter {

    private com.example.take_out_app.ResInfoActivity context;
    private List<GoodsDetails> list;
    private int num;
    public CartAdapter(com.example.take_out_app.ResInfoActivity context, List<GoodsDetails> list){
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
            convertView = View.inflate(context, R.layout.listitem_goods_cart,null);
            holder = new ViewHolder();
            holder.mName = (TextView) convertView.findViewById(R.id.tv_name_cart);
            holder.mNum = (TextView) convertView.findViewById(R.id.tv_num_cart);
            holder.mPrice = (TextView)convertView.findViewById(R.id.price_cart);
            holder.mAdd = (ImageView)convertView.findViewById(R.id.add_cart);
            holder.mReduce = (ImageView) convertView.findViewById(R.id.reduce_cart);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        //初始化
        holder.mName.setText(list.get(position).getProdName());
        holder.mPrice.setText("￥" + list.get(position).getProdPrice());
        holder.mNum.setText(list.get(position).getProdCount() + "");


        double totalPrice = num * list.get(position).getProdPrice();
        list.get(position).setProdTotal(totalPrice);

        holder.mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = list.get(position).getProdCount();
                count ++;
                list.get(position).setProdCount(count);
                context.modifyGoodsNum(list.get(position).getProdId(),count);
                context.calculateTotalPrice();
                notifyDataSetChanged();
                context.updateList(true,true,false);
            }
        });
        holder.mReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = list.get(position).getProdCount();
                count--;
                if (count <= 0)
                    count = 0;
                list.get(position).setProdCount(count);
                context.modifyGoodsNum(list.get(position).getProdId(),count);
                context.calculateTotalPrice();
                notifyDataSetChanged();
                context.updateList(true,true,false);
            }
        });
        return convertView;
    }

    class ViewHolder{
        private TextView mName;
        private TextView mPrice;
        private TextView mNum;
        private ImageView mAdd;
        private ImageView mReduce;
    }
}
