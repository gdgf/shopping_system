package com.wangkai.mms.Purchase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.wangkai.mms.HomeActivity;
import com.wangkai.mms.MainActivity;
import com.wangkai.mms.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wangkai.mms.base.HttpUtil.BASE_URL;
import static com.wangkai.mms.base.HttpUtil.getRequest;

/*
查询商品的进货记录，
并能按月统计
*/
public class PRecording extends Activity {

    String[] list = {"date", "goods", "boss", "worker", "price", "num"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.precording);
        try {
            String url = BASE_URL + "MMS/lookprecording";
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
                listItem.put("date", temp.get(0));
                listItem.put("goods", temp.get(1));
                listItem.put("boss", temp.get(5));
                listItem.put("worker", temp.get(2));
                listItem.put("price", temp.get(3));
                listItem.put("num", temp.get(4));
                listItems.add(listItem);
            }
            /* 创建一个SimpleAdapter */
            SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.precordinglist, list,
                    new int[]{R.id.date, R.id.goods, R.id.boss, R.id.worker, R.id.price, R.id.num});
            ListView list = (ListView) findViewById(R.id.precordlist);
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
                Intent intent = new Intent(PRecording.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}