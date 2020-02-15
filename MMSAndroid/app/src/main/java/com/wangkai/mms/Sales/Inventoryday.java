package com.wangkai.mms.Sales;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.wangkai.mms.HomeActivity;
import com.wangkai.mms.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wangkai.mms.base.HttpUtil.BASE_URL;
import static com.wangkai.mms.base.HttpUtil.postRequest;

public class Inventoryday extends Activity {

    String []list={"goods","num"};
    String []jlist=new String[3];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daylook);
        Button search=findViewById(R.id.search1);  //查找
        final TextView year=findViewById(R.id.year1);
        final TextView month=findViewById(R.id.month1);
        final TextView day=findViewById(R.id.day1);

//3. 提交数据
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    jlist[0]=year.getText().toString();
                    jlist[1]=month.getText().toString();
                    jlist[2]=day.getText().toString();
                    String url= BASE_URL+ "MMS/get_days";
                    Map<String ,String> map=new HashMap<>();
                    Log.v("year",jlist[0]);
                    Log.v("month",jlist[1]);
                    Log.v("day",jlist[2]);

                    map.put("year",jlist[0]);
                    map.put("month",jlist[1]);
                    map.put("day",jlist[2]);
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
                    SimpleAdapter simpleAdapter = new SimpleAdapter(Inventoryday.this,listItems,R.layout.achlist,list,
                            new int[] {R.id.goods,R.id.num});
                    ListView list = (ListView) findViewById(R.id.daylist);
                    // 为ListView设置Adapterevement
                    list.setAdapter(simpleAdapter);

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        goBack();
    }
    private void goBack(){
        findViewById(R.id.iv_Back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Inventoryday.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

