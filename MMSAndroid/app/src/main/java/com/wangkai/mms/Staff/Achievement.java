package com.wangkai.mms.Staff;

/*
计算员工的业绩
按照月来计算
查询销售列表，按照一月得到员工的销售的件数
选择不同的月份，选择不同的售货员
___月份____售货员___|查询|

月份1~12
售货员：
查询：
   先查询销售表，得到
   |时间|商品|数量|

| 商品| 数量|

*/

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.wangkai.mms.HomeActivity;
import com.wangkai.mms.R;

import org.json.JSONArray;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wangkai.mms.base.BaseFuncation.deduplication;
import static com.wangkai.mms.base.HttpUtil.BASE_URL;
import static com.wangkai.mms.base.HttpUtil.getRequest;
import static com.wangkai.mms.base.HttpUtil.postRequest;

public class Achievement extends Activity {
    String[] worker;
    NumberPicker np1, np2;
    String []list={"goods","num"};
    String []jlist=new String[3];
    Button wo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievement);
        wo=findViewById(R.id.worker);     //员工
        Button search=findViewById(R.id.search);  //查找
        final TextView year=findViewById(R.id.year);
        final TextView month=findViewById(R.id.month);

//2. 从库存表查询售货员
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
        wo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleList2(v);
            }
        });
//3. 提交数据
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    jlist[0]=year.getText().toString();
                    jlist[1]=month.getText().toString();
                    String url= BASE_URL+ "MMS/monthach";
                    Map<String ,String> map=new HashMap<>();
                    Log.v("year",jlist[0]);
                    Log.v("month",jlist[1]);
                    Log.v("worker",jlist[2]);
                    map.put("year",jlist[0]);
                    map.put("month",jlist[1]);
                    map.put("worker",jlist[2]);
                    String json=postRequest(url,map);

                    if (json != null && json.startsWith("\ufeff")) {
                        json = json.substring(1);
                    }
                    List<Map<String, Object>> listItems =
                            new ArrayList<Map<String, Object >>();
                    JSONArray jsonArray=new JSONArray(json);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONArray temp;
                        temp = (JSONArray) jsonArray.get(i);
                        Map<String, Object> listItem = new HashMap<String, Object>();
                        listItem.put("goods",temp.get(0));
                        listItem.put("num",temp.get(1));
                        listItems.add(listItem);
                    }
                    /* 创建一个SimpleAdapter */
                    SimpleAdapter simpleAdapter = new SimpleAdapter(Achievement.this,listItems,R.layout.achlist,list,
                            new int[] {R.id.goods,R.id.num});
                    ListView list = (ListView) findViewById(R.id.achlist);
                    // 为ListView设置Adapterevement
                    list.setAdapter(simpleAdapter);

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        goBack();
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
                        jlist[2]=worker[which];
                        wo.setText(worker[which]);
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
                Intent intent=new Intent(Achievement.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
