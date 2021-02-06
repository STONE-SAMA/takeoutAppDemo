package com.example.take_out_app;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResInfoActivity extends Activity implements View.OnClickListener{

    private ListView mParentList;
    private ListView mChildList;
    private TextView mBill;
    private TextView mPrice;
    private TextView mCount;
    private TextView mShopName;
    private ImageView mCart;
    private ImageView mShopImage;
    private LinearLayout mWindow;
    private ListView mCartList;
    private TextView mClear;
    private View mView;

    private Boolean isShow = false;
    private double TotalPrice;
    private List<GoodsClassify> classifys = new ArrayList<>();
    private List<GoodsDetails> detailses = new ArrayList<>();
    private List<GoodsDetails> details1 = new ArrayList<>();
    private List<GoodsDetails> details2 = new ArrayList<>();
    private List<GoodsDetails> details3 = new ArrayList<>();
    private List<GoodsDetails> details4 = new ArrayList<>();
    private List<GoodsDetails> list ;//  购物车中商品列表
    private GoodsClassify goodsClassify;
    private GoodsDetails goodsDitails;

    private CartAdapter cartAdapter;
    private GoodsClassifyAdapter classifyAdapter;
    private GoodsDetailsAdapter detailsAdapter;
    private static DecimalFormat df = new DecimalFormat("0.00");
    private int location = 0;
    String resID = null;
    String userID = null;

    public ResInfoActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ViewUtils.inject(this);
        setContentView(R.layout.activity_res_info);

        Intent intent = getIntent();
        resID = intent.getStringExtra("resID");
        userID = intent.getStringExtra("userID");

//        DBHelper dbHelper = new DBHelper(ResInfoActivity.this,"TakeOutApp.db",null,3);
//        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("resID",resID);
//        contentValues.put("ProdName","汉堡");
//        contentValues.put("ProdPrice",38);
//        contentValues.put("type","主食");
//        contentValues.put("ProdPic",R.drawable.hanbao);
//        sqLiteDatabase.insert("Product",null,contentValues);
//
//        ContentValues contentValues1 = new ContentValues();
//        contentValues1.put("resID",resID);
//        contentValues1.put("ProdName","咖啡");
//        contentValues1.put("ProdPrice",28);
//        contentValues1.put("type","咖啡");
//        contentValues1.put("ProdPic",R.drawable.kafei);
//        sqLiteDatabase.insert("Product",null,contentValues1);

//        ContentValues contentValues2 = new ContentValues();
//        contentValues2.put("resID",resID);
//        contentValues2.put("ProdName","烧花鸭");
//        contentValues2.put("ProdPrice",98);
//        contentValues2.put("type","烧");
//        contentValues2.put("ProdPic",R.drawable.pic_004);
//        sqLiteDatabase.insert("Product",null,contentValues2);
//
//        ContentValues contentValues3 = new ContentValues();
//        contentValues3.put("resID",resID);
//        contentValues3.put("ProdName","烧雏鸡");
//        contentValues3.put("ProdPrice",98);
//        contentValues3.put("type","烧");
//        contentValues3.put("ProdPic",R.drawable.pic_005);
//        sqLiteDatabase.insert("Product",null,contentValues3);

        //sqLiteDatabase.delete("Product","type = ?",new String[]{"水果捞"});


        init();
        getGoodsData(resID);
        initView();

    }

    /**
     * 初始化控件，并设置监听事件
     */
    public void init(){
        mParentList = (ListView)findViewById(R.id.parentlist);
        mChildList = (ListView) findViewById(R.id.childlist);
        mCount = (TextView) findViewById(R.id.tv_count_main);
        mBill = (TextView) findViewById(R.id.billing);
        mPrice = (TextView) findViewById(R.id.price_main);
        mCart = (ImageView) findViewById(R.id.cart);
        mWindow = (LinearLayout) findViewById(R.id.window);
        mCartList = (ListView) findViewById(R.id.list_cart);
        mClear = (TextView) findViewById(R.id.clear);
        mView = (View) findViewById(R.id.view);
        mShopName=(TextView) findViewById(R.id.tv_ShopName);
        mShopImage=(ImageView) findViewById(R.id.iv_ShopImage);
        mBill.setOnClickListener(this);
        mCart.setOnClickListener(this);
        mClear.setOnClickListener(this);
        mView.setOnClickListener(this);


        mBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取系统时间作为订单号
                Integer date = Integer.valueOf(String.valueOf(new Date().getTime()).substring(0, 10));
                if( list.size()==0){
                    Toast.makeText(ResInfoActivity.this,"购物车为空",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(ResInfoActivity.this,IndentActivity.class);
                    intent.putExtra("userID",userID);
                    intent.putExtra("list",(Serializable)list);
                    intent.putExtra("indentID",date);
                    //intent.putExtra("total","￥" + TotalPrice + "元");
                    intent.putExtra("total",TotalPrice);
                    intent.putExtra("resID",resID);
                    startActivity(intent);
                    ResInfoActivity.this.finish();
                }
            }
        });




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.billing:
                isShow = setWindowView(true);
                /*Intent intent = new Intent(this,BillActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("LIST", (Serializable) list);
                intent.putExtras(bundle);
                startActivity(intent);*/
                break;
            case R.id.cart:
                isShow = setWindowView(isShow);
                createCartList();
                cartAdapter = new CartAdapter(ResInfoActivity.this,list);
                mCartList.setAdapter(cartAdapter);
                break;
            case R.id.clear:
                clearList();
                break;
            case R.id.view:
                isShow = setWindowView(true);
                break;
            default:
                break;
        }
    }

    /*
     *清空列表
     */
    public void clearList(){
        for (int i=0;i<classifys.size();i++) {
            for (int j = 0; j < classifys.get(i).getProdList().size(); j++) {
                classifys.get(i).getProdList().get(j).setProdCount(0);
            }
        }
        createCartList();
        cartAdapter = new CartAdapter(ResInfoActivity.this,list);
        mCartList.setAdapter(cartAdapter);
        updateList(true,true,false);

        mPrice.setText("￥" + df.format(0.00) + "元" );
        mCount.setVisibility(View.GONE);
    }

    /**
     * 刷新list，
     * @param flag  表示adapter，0代表classifyAdapter；1代表detailsAdapter；2代表cartAdapter；
     */
    public void updateList(Boolean... flag){
        if (flag[0])
            classifyAdapter.notifyDataSetChanged();
        if (flag[1])
            detailsAdapter.notifyDataSetChanged();
        if (flag[2])
            cartAdapter.notifyDataSetChanged();
    }

    /**
     * 修改购物车显示状态，并返回修改后的状态
     * @param isShow 购物车详细信息是否显示
     * @return      购物车显示的当前状态  显示true，不显示为false
     */
    public Boolean setWindowView(Boolean isShow){
        if (isShow == false){
            mView.setVisibility(View.VISIBLE);
            mWindow.setVisibility(View.VISIBLE);

            isShow = true;
        }else {
            mView.setVisibility(View.GONE);
            mWindow.setVisibility(View.GONE);
            updateList(true,true,false);
            isShow = false;
        }
        return isShow;
    }

    /**
     * 初始化列表
     */
    public void initView(){
        if(classifys != null &&  classifys.size() != 0){
            //设置parentList 数据
            classifyAdapter = new GoodsClassifyAdapter(getApplicationContext(),classifys);
            mParentList.setAdapter(classifyAdapter);
            detailses.clear();
            //设置childList 数据
            detailses.addAll(classifys.get(0).getProdList());
            detailsAdapter = new GoodsDetailsAdapter(ResInfoActivity.this,detailses,classifyAdapter);
            mChildList.setAdapter(detailsAdapter);
        }
        mParentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                location = position;
                classifyAdapter.clearSelection(position);
                detailses.clear();
                detailses.addAll(classifys.get(position).getProdList());
                updateList(false,true,false);
            }
        });
    }


    /**
     * 计算总价与选中的个数,并显示在界面上
     */
    public void calculateTotalPrice(){
        createCartList();
        int num = 0;
        double price = 0.00;
        for (int i=0;i<list.size();i++){
            num += list.get(i).getProdCount();
            price += list.get(i).getProdPrice() * list.get(i).getProdCount();
        }

        mPrice.setText("￥" + df.format(price) + "元" );
        if (num == 0){
            this.mCount.setVisibility(View.GONE);
        }else{
            this.mCount.setVisibility(View.VISIBLE);
            this.mCount.setText(num + "");
        }
        TotalPrice=price;
    }

    /*
     * 创建购物车商品列表
     */
    public void createCartList(){
        list = new ArrayList<>();
        for (int i=0;i<classifys.size();i++){
            for (int j=0;j<classifys.get(i).getProdList().size();j++){
                if (classifys.get(i).getProdList().get(j).getProdCount() > 0){
                    list.add(classifys.get(i).getProdList().get(j));
                }
            }
        }
    }

    /**
     * 修改指定id的商品的数量
     * @param id
     * @param num
     */
    public void modifyGoodsNum(String id,int num){
        for (int i=0;i<classifys.size();i++){
            for (int j=0;j<classifys.get(i).getProdList().size();j++){
                if (id.equals(classifys.get(i).getProdList().get(j).getProdId())){
                    classifys.get(i).getProdList().get(j).setProdCount(num);
                }
            }
        }
    }

    private void getGoodsData(String resID) {
//        mShopName.setText("报菜名");
//        mShopImage.setImageResource(R.drawable.shop);

        DBHelper dbHelper = new DBHelper(ResInfoActivity.this,"TakeOutApp.db",null,3);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        String sql_res = "select resName,resPic from restaurant where resID = ?";
        Cursor cursor_res = sqLiteDatabase.rawQuery(sql_res,new String[]{resID});
        while (cursor_res.moveToNext()){
            String shopName = cursor_res.getString(cursor_res.getColumnIndex("resName"));
            int shopImage = cursor_res.getInt(cursor_res.getColumnIndex("resPic"));
            mShopName.setText(shopName);
            mShopImage.setImageResource(shopImage);
        }


        String sql = "select type from Product where resID = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{resID});
        String resType[] = new String[4];

        //List  xx= new ArrayList<>();

        int i = 0;
        while (cursor.moveToNext()){
            if(i>0){
                String temp = cursor.getString(cursor.getColumnIndex("type"));
                int flag = 0;
                for(int j = 0; j < i ;j++){
                    if(temp.equals(resType[j])){
                        flag = 1;
                        break;
                    }
                }
                if(flag == 0){
                    resType[i] = temp;
                    i++;
                }
            }else{
                resType[i] = cursor.getString(cursor.getColumnIndex("type"));
                //xx.add(cursor.getString(cursor.getColumnIndex("type")));
                i++;
            }
        }
//        String x1 = resType[0];
//        String x2 = resType[1];
//        int num = xx.size();

//        classifys.add(new GoodsClassify(resType[0],details1, 0));
//        classifys.add(new GoodsClassify(resType[1],details2, 0));
//        classifys.add(new GoodsClassify(resType[2],details3, 0));
//        classifys.add(new GoodsClassify(resType[3],details4, 0));

        if(i>0) {
            classifys.add(new GoodsClassify(resType[0],details1, 0));
            String sql_type1 = "select * from Product where resID = ? and type = ?";
            Cursor cursor_type1 = sqLiteDatabase.rawQuery(sql_type1, new String[]{resID, resType[0]});
            while (cursor_type1.moveToNext()) {
                String ProdID = cursor_type1.getString(cursor_type1.getColumnIndex("ProdID"));
                String ProdName = cursor_type1.getString(cursor_type1.getColumnIndex("ProdName"));
                int ProdPic = cursor_type1.getInt(cursor_type1.getColumnIndex("ProdPic"));
                double ProdPrice = cursor_type1.getDouble(cursor_type1.getColumnIndex("ProdPrice"));
                details1.add(new GoodsDetails(ProdID, ProdName, ProdPic, ProdPrice, 0, 0));
            }
            i--;
        }

        if(i>0) {
            classifys.add(new GoodsClassify(resType[1],details2, 0));
            String sql_type2 = "select * from Product where resID = ? and type = ?";
            Cursor cursor_type2 = sqLiteDatabase.rawQuery(sql_type2, new String[]{resID, resType[1]});
            while (cursor_type2.moveToNext()) {
                String ProdID = cursor_type2.getString(cursor_type2.getColumnIndex("ProdID"));
                String ProdName = cursor_type2.getString(cursor_type2.getColumnIndex("ProdName"));
                int ProdPic = cursor_type2.getInt(cursor_type2.getColumnIndex("ProdPic"));
                double ProdPrice = cursor_type2.getDouble(cursor_type2.getColumnIndex("ProdPrice"));
                details2.add(new GoodsDetails(ProdID, ProdName, ProdPic, ProdPrice, 0, 0));
            }
            i--;
        }
        if(i>0) {
            classifys.add(new GoodsClassify(resType[2],details3, 0));
            String sql_type3 = "select * from Product where resID = ? and type = ?";
            Cursor cursor_type3 = sqLiteDatabase.rawQuery(sql_type3, new String[]{resID, resType[2]});
            while (cursor_type3.moveToNext()) {
                String ProdID = cursor_type3.getString(cursor_type3.getColumnIndex("ProdID"));
                String ProdName = cursor_type3.getString(cursor_type3.getColumnIndex("ProdName"));
                int ProdPic = cursor_type3.getInt(cursor_type3.getColumnIndex("ProdPic"));
                double ProdPrice = cursor_type3.getDouble(cursor_type3.getColumnIndex("ProdPrice"));
                details3.add(new GoodsDetails(ProdID, ProdName, ProdPic, ProdPrice, 0, 0));
            }
            i--;
        }
        if(i>0) {
            classifys.add(new GoodsClassify(resType[3],details4, 0));
            String sql_type4 = "select * from Product where resID = ? and type = ?";
            Cursor cursor_type4 = sqLiteDatabase.rawQuery(sql_type4, new String[]{resID, resType[3]});
            while (cursor_type4.moveToNext()) {
                String ProdID = cursor_type4.getString(cursor_type4.getColumnIndex("ProdID"));
                String ProdName = cursor_type4.getString(cursor_type4.getColumnIndex("ProdName"));
                int ProdPic = cursor_type4.getInt(cursor_type4.getColumnIndex("ProdPic"));
                double ProdPrice = cursor_type4.getDouble(cursor_type4.getColumnIndex("ProdPrice"));
                details4.add(new GoodsDetails(ProdID, ProdName, ProdPic, ProdPrice, 0, 0));
            }
            i--;
        }

    }

//    private void getGoodsData() {
//        mShopName.setText("报菜名");
//        mShopImage.setImageResource(R.drawable.shop);
//        classifys.add(new GoodsClassify("蒸",details1, 0));
//        details1.add(new GoodsDetails("001","蒸羊羔",R.drawable.pic_001,998,0,0));
//        details1.add(new GoodsDetails("002","蒸熊掌",R.drawable.pic_002,1888,0,0));
//        details1.add(new GoodsDetails("003","蒸鹿尾儿",R.drawable.pic_003,688,0,0));
//        classifys.add(new GoodsClassify("烧",details2, 0));
//        details2.add(new GoodsDetails("004","烧花鸭",R.drawable.pic_004,98,0,0));
//        details2.add(new GoodsDetails("005","烧雏鸡",R.drawable.pic_005,88,0,0));
//        details2.add(new GoodsDetails("006","烧仔鹅",R.drawable.pic_006,118,0,0));
//        classifys.add(new GoodsClassify("卤",details3, 0));
//        details3.add(new GoodsDetails("007","卤鸡",R.drawable.pic_007,78,0,0));
//        details3.add(new GoodsDetails("008","卤鸭",R.drawable.pic_008,88,0,0));
//        //Log.i("sas",classifys.get(0).getProdList().get(1).toString());
//    }
}
