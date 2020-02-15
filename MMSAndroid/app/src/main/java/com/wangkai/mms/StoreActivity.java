package com.wangkai.mms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.wangkai.mms.base.DialogUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wangkai.mms.base.HttpUtil.BASE_URL;
import static com.wangkai.mms.base.HttpUtil.getRequest;

public class StoreActivity extends Activity {

    String[] list = {"date", "goods", "num", "warnnum"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stareviews);
        try {
            String url = BASE_URL + "MMS/lookstore";
            String json = getRequest(url);
            if (json != null && json.startsWith("\ufeff")) {
                json = json.substring(1);
            }
            List<Map<String, Object>> listItems =
                    new ArrayList<Map<String, Object>>();
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray temp;
                temp = (JSONArray) jsonArray.get(i);
                Map<String, Object> listItem = new HashMap<String, Object>();
                listItem.put("date", temp.get(1));
                listItem.put("goods", temp.get(0));
                listItem.put("num", temp.get(2));
                listItem.put("warnnum", temp.get(3));
                if((int)temp.get(2)<10){
                    DialogUtil.showDialog(this
                            , temp.get(0)+"库存已经不足10，请赶快进货", false);
                }
                listItems.add(listItem);
            }
            
            /*
             *  
             * 
             * * 
             *  
             */
            /* 创建一个SimpleAdapter */
            SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.starelist, list,
                    new int[]{R.id.date, R.id.goods, R.id.num});
            ListView list = (ListView) findViewById(R.id.starelist);
            // 为ListView设置Adapter
            list.setAdapter(simpleAdapter);

            goBack();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void goBack() {
        findViewById(R.id.iv_Back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

