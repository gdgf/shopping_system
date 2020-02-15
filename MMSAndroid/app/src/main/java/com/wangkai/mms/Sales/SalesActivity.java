package com.wangkai.mms.Sales;
/*
售货模块
     date # 日期       ：自己填写（系统时间间隔太小，不好统计）
    salesID # 进货单号  ：自己填写
    goodName  # 商品名称 ：自己填写，可以选择，需要查询库存
    salesPrice # 售价   ： 自己填写
    quantity # 数量     ： 自己填写
    totalmoney  # 总价  ：自动计算
    staffID  # 售货员编号 ：可以选择，需要查询员工表
  售货后服务器端需要更新库存。

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

public class SalesActivity extends Activity {
    //定义两个指针
    String []goods;         //货物集合
    String [] worker;       //员工
    String []list=new String[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        Button go=findViewById(R.id.goods);     //商品
        Button wo=findViewById(R.id.worker);     //员工
        final TextView price=findViewById(R.id.price); //价格
        final TextView num=findViewById(R.id.num);    //数量
        final TextView date=findViewById(R.id.date);  //日期
        Button ok=findViewById(R.id.OK);        //确定进货

        //从库存表查询商品
        try {
            String url= BASE_URL+ "MMS/get_goods";
            String json=getRequest(url);
            if (json != null && json.startsWith("\ufeff")) {
                json = json.substring(1);
            }
            Log.v("json",json);
            JSONArray jsonArray=new JSONArray(json);
            goods=new String[jsonArray.length()];         //货物集合
            for(int i=0;i<jsonArray.length();i++){
                JSONArray temp;
                temp = (JSONArray) jsonArray.get(i);
                goods[i] =(String) temp.get(0);
            }

            //去重
            goods=deduplication(goods);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //从库存表查询售货员
        try {
            String url= BASE_URL+ "MMS/get_workershou";
            String json=getRequest(url);
            if (json != null && json.startsWith("\ufeff")) {
                json = json.substring(1);
            }
            Log.v("json",json);
            JSONArray jsonArray=new JSONArray(json);
            worker=new String[jsonArray.length()];         //货物集合
            for(int i=0;i<jsonArray.length();i++){
                JSONArray temp;
                temp = (JSONArray) jsonArray.get(i);
                worker[i] =(String) temp.get(0);
            }
            //去重
            worker=deduplication(worker);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //向数据库添加数据
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查询商家
                try {
                    list[2]=price.getText().toString();
                    list[3]=num.getText().toString();
                    list[4]=date.getText().toString();
                    String url= BASE_URL+ "MMS/salse";
                    Map<String ,String> map=new HashMap<>();
                    map.put("goods", list[0]);
                    map.put("worker",list[1]);
                    map.put("price",list[2]);
                    map.put("num",list[3]);
                    map.put("date",list[4]);
                    String json=postRequest(url,map);
                    JSONObject jsonObject=new JSONObject(json);
                    try{

                        int p=Integer.valueOf(price.getText().toString())*Integer.valueOf(num.getText().toString());
                        if(jsonObject.getInt("upload")==222){
                            DialogUtil.showDialog(SalesActivity.this
                                    , "售货成功！共计消费"+p+"元", false);
                        }
                    }catch (Exception e){
                        DialogUtil.showDialog(SalesActivity.this,"服务器响应异常，请稍候再试！",false);
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        goBack();
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleList(v);
            }
        });
        wo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleList2(v);
            }
        });
    }
    public void simpleList(View source)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                // 设置对话框标题
                .setTitle("商品")
                // 设置图标
                .setIcon(R.drawable.ic_launcher)
                // 设置简单的列表项内容
                .setSingleChoiceItems(goods, 1, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        list[0]=goods[which];  //确定商品
                        
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
                .setTitle("售货员")
                // 设置图标
                .setIcon(R.drawable.ic_launcher)
                // 设置简单的列表项内容
                .setSingleChoiceItems(worker, 1, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                       list[1]=worker[which];
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
                Intent intent=new Intent(SalesActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}


