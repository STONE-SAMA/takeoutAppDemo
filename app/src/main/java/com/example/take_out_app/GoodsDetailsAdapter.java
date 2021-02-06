package com.example.take_out_app;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:商品详细信息的适配器
 * author: henry
 * Date: 16-4-20 上午9:58.
 */
public class GoodsDetailsAdapter extends BaseAdapter  {
    private com.example.take_out_app.ResInfoActivity context;
    private List<GoodsDetails> goodsDetailsList = new ArrayList<>();
    private int count;
    private GoodsClassifyAdapter classifyAdapter;
    private String id ;

    public GoodsDetailsAdapter(com.example.take_out_app.ResInfoActivity context, List<GoodsDetails> goodsDetailsList,
                               GoodsClassifyAdapter classifyAdapter){
        this.context = context;
        this.goodsDetailsList = goodsDetailsList;
        this.classifyAdapter = classifyAdapter;
    }
    @Override
    public int getCount() {
        return goodsDetailsList.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsDetailsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 返回修改会的列表
     * @return
     */
    public List<GoodsDetails> getList(){
        return goodsDetailsList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final GoodsDetails goodsDetails  = goodsDetailsList.get(position);
        final ViewHolder holder;
        if (convertView == null){
            convertView = View.inflate(context, R.layout.listitem_childgoods,null);
            holder = new ViewHolder();
            holder.mGoodsName = (TextView)convertView.findViewById(R.id.goods);
            holder.mGoodsPrice = (TextView)convertView.findViewById(R.id.price);
            holder.mGoodsNum = (TextView)convertView.findViewById(R.id.num);
            holder.mGoodsIcon = (ImageView)convertView.findViewById(R.id.icon_goods);
            holder.mGoodsAdd = (ImageView)convertView.findViewById(R.id.add);
            holder.mGoodsReduce = (ImageView)convertView.findViewById(R.id.reduce);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //设置商品图片
        int imgUrl = goodsDetails.getProdImg();
        if(imgUrl>0){
            holder.mGoodsIcon.setImageResource(imgUrl);
        }
        holder.mGoodsName.setText(goodsDetails.getProdName());
        holder.mGoodsPrice.setText("￥" + goodsDetails.getProdPrice());

        int num = goodsDetails.getProdCount();

        //当所点上商品数量为0时，隐藏“减”的图标
        if (num == 0){
            holder.mGoodsNum.setVisibility(View.GONE);
            holder.mGoodsReduce.setVisibility(View.GONE);
        }else {
            holder.mGoodsNum.setVisibility(View.VISIBLE);
            holder.mGoodsReduce.setVisibility(View.VISIBLE);
        }

        double totalPrice = num * goodsDetailsList.get(position).getProdPrice();
        goodsDetails.setProdTotal(totalPrice);
        holder.mGoodsNum.setText(num + "");

        //设置加减监听事件
        holder.mGoodsReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = goodsDetailsList.get(position).getProdId();

                count = goodsDetailsList.get(position).getProdCount();
                count --;
                goodsDetailsList.get(position).setProdCount(count);
                context.modifyGoodsNum(id,count);

                context.calculateTotalPrice();
                GoodsDetailsAdapter.this.notifyDataSetChanged();
                classifyAdapter.notifyDataSetChanged();

            }
        });
        holder.mGoodsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = goodsDetailsList.get(position).getProdId();

                count = goodsDetailsList.get(position).getProdCount();
                count ++;
                goodsDetailsList.get(position).setProdCount(count);
                context.modifyGoodsNum(id,count);
                context.calculateTotalPrice();
                GoodsDetailsAdapter.this.notifyDataSetChanged();

                classifyAdapter.notifyDataSetChanged();
            }
        });

        return convertView;
    }


    class ViewHolder{
        private TextView mGoodsName;    //商品名称
        private TextView mGoodsNum;     //商品数量
        private TextView mGoodsPrice;   //商品单价
        private ImageView mGoodsIcon;   //商品图片
        private ImageView mGoodsAdd;    //增加
        private ImageView mGoodsReduce; //减少
    }
}
