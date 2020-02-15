package com.wangkai.mms.Purchase;

/*
进货模块
获取能选择那些东西
查询供应商的供应物品获得：能购买的物品

职工：进货员，通过查询员工表列出可以选择的员工。

     date         # 日期      //系统当前时间
    importID      # 进货单号   //自己填写
    goodName      # 商品名称   //可选择，查询供应数据库，确定有哪些商品
    importprice   # 进价      //自己写
    quantity      # 数量      //自己输入
    totalmoney    # 总价      //自动计算
    staffID       # 进货员编号 //可选择
    name          # 负责人名字 //绑定商品名称和供应商
进货时服务器也要更新库存。
*/

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wangkai.mms.MainActivity;
import com.wangkai.mms.R;
import com.wangkai.mms.base.DialogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import static com.wangkai.mms.base.BaseFuncation.deduplication;
import static com.wangkai.mms.base.HttpUtil.BASE_URL;
import static com.wangkai.mms.base.HttpUtil.getRequest;
import static com.wangkai.mms.base.HttpUtil.postRequest;

public class PurchaseActivity extends Activity
{
//定义三个指针，因为他们代表的集合后面可能会发生变化
    String []goods;         //货物集合
    String []sup;           //商家
    String [] worker;       //工人
    String []list=new String[6];
    //List<String> list=new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        Button go=findViewById(R.id.shopping);  //商品
        Button su=findViewById(R.id.supplier);  //供应商
        Button wo=findViewById(R.id.worker);     //员工
        final TextView price=findViewById(R.id.price);//价格
        final TextView num=findViewById(R.id.num);    //数量
        final TextView date=findViewById(R.id.date);  //时间
        Button ok=findViewById(R.id.OK);      //确定进货

//1、首先获得所有的商品，供应商，员工，
        //得到供应商、商品、进货员
        //查询供应商库，得到共供应商，商品列表
        //查询员工库，得到员工职务是进货员的员工
        //查询所有商品，供应商

        try {
            String url= BASE_URL+ "MMS/look_profferinfor";
            String json=getRequest(url);
            if (json != null && json.startsWith("\ufeff")) {
                json = json.substring(1);
            }
            Log.v("json",json);
            JSONArray jsonArray=new JSONArray(json);
            goods=new String[jsonArray.length()];         //货物集合
            sup=new String[jsonArray.length()];    //商家
            for(int i=0;i<jsonArray.length();i++){
                JSONArray temp;
                temp = (JSONArray) jsonArray.get(i);
                goods[i] =(String) temp.get(0);
                sup[i]=(String)temp.get(1);
            }

            //去重
            goods=deduplication(goods);
            sup=deduplication(sup);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //查询员工
        try {
            String url= BASE_URL+ "MMS/get_workerjin";
            String json=getRequest(url);
            if (json != null && json.startsWith("\ufeff")) {
                json = json.substring(1);
            }
            Log.v("json",json);
            JSONArray jsonArray=new JSONArray(json);
            worker=new String[jsonArray.length()];   //集合
            for(int i=0;i<jsonArray.length();i++){
                JSONArray temp;
                temp = (JSONArray) jsonArray.get(i);
                worker[i] =(String) temp.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //查询商品
                simpleList(v);
            }
        });
        su.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleList2(v);
            }
        });
        wo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleList3(v);
            }
        });


        //向数据库添加数据
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查询商家
                try {
                    list[3]=price.getText().toString();
                    list[4]=num.getText().toString();
                    list[5]=date.getText().toString();
                    String url= BASE_URL+ "MMS/importgoods";
                    Map<String ,String> map=new HashMap<>();
                    map.put("goods", list[0]);
                    map.put("supplier",list[1]);
                    map.put("worker",list[2]);
                    map.put("price",list[3]);
                    map.put("num",list[4]);
                    map.put("date",list[5]);
                    String json=postRequest(url,map);
                    JSONObject jsonObject=new JSONObject(json);
                    try{

                        if(jsonObject.getInt("upload")==000){
                            DialogUtil.showDialog(PurchaseActivity.this
                                    , "进货成功！", false);
                        }
                    }catch (Exception e){
                        DialogUtil.showDialog(PurchaseActivity.this,"服务器响应异常，请稍候再试！",false);
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        goBack();
    }

    public void simpleList(View source)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                // 设置对话框标题
                .setTitle("商品")
                // 设置图标
                .setIcon(R.drawable.ic_launcher)
                // 设置简单的列表项内容
                .setSingleChoiceItems(goods, 0, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        list[0]=goods[which];
                        //查询此商品对应的供应商
                        try {
                            String url= BASE_URL+ "MMS/return_whichproffer";
                            Map<String ,String> map=new HashMap<>();
                            map.put("goods",goods[which]);//请求获得goods[which]的供应商s
                            String json=postRequest(url,map);
                            if (json != null && json.startsWith("\ufeff")) {
                                json = json.substring(1);
                            }
                            Log.v("json",json);
                            JSONArray jsonArray=new JSONArray(json);
                            sup=new String[jsonArray.length()];          //商家
                            for(int i=0;i<jsonArray.length();i++){
                                JSONArray temp;
                                temp = (JSONArray) jsonArray.get(i);
                                sup[i] =(String) temp.get(0);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        // 为AlertDialog.Builder添加“确定”按钮
        setPositiveButton(builder);
        // 为AlertDialog.Builder添加“取消”按钮
        setNegativeButton(builder)
                .create()
                .show();
    }
    public void simpleList2(View source)
    {
         AlertDialog.Builder builder = new AlertDialog.Builder(this)
                // 设置对话框标题
                .setTitle("商家")
                // 设置图标
                .setIcon(R.drawable.ic_launcher)
                // 设置简单的列表项内容
                .setSingleChoiceItems(sup, 1, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                         list[1]=sup[which];
                    }
                });
        // 为AlertDialog.Builder添加“确定”按钮
        setPositiveButton(builder);
        // 为AlertDialog.Builder添加“取消”按钮
        setNegativeButton(builder)
                .create()
                .show();
    }
    public void simpleList3(View source)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                // 设置对话框标题
                .setTitle("进货员")
                // 设置图标
                .setIcon(R.drawable.ic_launcher)
                // 设置简单的列表项内容
                .setSingleChoiceItems(worker, 1, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        list[2]=worker[which];
                    }
                });
        // 为AlertDialog.Builder添加“确定”按钮
        setPositiveButton(builder);
        // 为AlertDialog.Builder添加“取消”按钮
        setNegativeButton(builder)
                .create()
                .show();
    }
    private AlertDialog.Builder setPositiveButton(
            AlertDialog.Builder builder)
    {
        // 调用setPositiveButton方法添加“确定”按钮
        return builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //show.setText("单击了【确定】按钮！");
            }
        });
    }
    private AlertDialog.Builder setNegativeButton(
            AlertDialog.Builder builder)
    {
        // 调用setNegativeButton方法添加“取消”按钮
        return builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //show.setText("单击了【取消】按钮！");
            }
        });
    }

    private void goBack(){
        findViewById(R.id.iv_Back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PurchaseActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
