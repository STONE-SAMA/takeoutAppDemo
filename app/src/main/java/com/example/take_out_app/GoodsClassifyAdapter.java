package com.example.take_out_app;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:商品分类的适配器
 * author: henry
 * Date: 16-4-20 下午1:47.
 */
public class GoodsClassifyAdapter extends BaseAdapter {
    private Context context;
    private List<GoodsClassify> classifies = new ArrayList<>();
    private int selectedPostion = 0;    //选择的位置

    public GoodsClassifyAdapter(Context context,List<GoodsClassify> classifies){
        this.context = context;
        this.classifies = classifies;
    }
    @Override
    public int getCount() {
        return classifies.size();
    }

    @Override
    public Object getItem(int position) {
        return classifies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 自定义方法，改变position
     * @param position
     */
    public void clearSelection(int position) {
        selectedPostion = position;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder ;
        if (convertView == null){
            convertView = View.inflate(context, R.layout.listitem_parentgoods,null);
            holder = new ViewHolder();
            holder.mName = (TextView) convertView.findViewById(R.id.tv_name_classify);
            holder.mNum = (TextView) convertView.findViewById(R.id.tv_count_classify);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mName.setText(classifies.get(position).getTypeName());
        setNum(position,holder.mNum);

        //设置选中位置的字体和背景颜色
        if (selectedPostion == position){
            convertView.setBackgroundColor(Color.WHITE);
            holder.mName.setTextColor(Color.BLUE);
        }else{
            convertView.setBackgroundColor(Color.LTGRAY);
            holder.mName.setTextColor(Color.GRAY);
        }

        return convertView;
    }



    /**
     * 设置商品数量的显示或隐藏
     * @param position
     * @param textView
     */
    public void setNum(int position,TextView textView){
        int num = 0;
        for (int i = 0;i < classifies.get(position).getProdList().size();i++){
            num += classifies.get(position).getProdList().get(i).getProdCount();
        }
        classifies.get(position).setCount(num);

        if (num == 0){
            textView.setVisibility(View.GONE);
        }else{
            textView.setVisibility(View.VISIBLE);
            textView.getPaint().setAntiAlias(true);
            textView.setText(num + "");

        }
        //设置商品第一列，即热门推荐数量隐藏
        //if (position == 0){
        //    textView.setVisibility(View.GONE);
        //}
    }

    class ViewHolder{
        private TextView mName;
        private TextView mNum;
    }
}
