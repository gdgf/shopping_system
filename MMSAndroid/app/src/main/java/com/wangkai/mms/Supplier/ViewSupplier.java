package com.wangkai.mms.Supplier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.wangkai.mms.MainActivity;
import com.wangkai.mms.R;

import org.json.JSONArray;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wangkai.mms.base.HttpUtil.BASE_URL;
import static com.wangkai.mms.base.HttpUtil.getRequest;

public class ViewSupplier extends Activity {
    String []list={"numb","name","goods","phone","account","address"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supplier);
        goBack();
        try {
            String url= BASE_URL+ "MMS/looksupplier";
            String json=getRequest(url);
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
                listItem.put("numb",temp.get(0));
                listItem.put("name",temp.get(1));
                listItem.put("goods",temp.get(2));
                listItem.put("phone",temp.get(3));
                listItem.put("account",temp.get(4));
                listItem.put("address",temp.get(5));
                listItems.add(listItem);
            }
            // 创建一个SimpleAdapter
            SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
                    R.layout.supplierlist,list,
                    new int[] { R.id.numb, R.id.name ,R.id.goods,R.id.phone,R.id.account, R.id.address });
            ListView list = (ListView) findViewById(R.id.buslist);
            // 为ListView设置Adapter
            list.setAdapter(simpleAdapter);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    private void goBack(){
        findViewById(R.id.iv_Back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ViewSupplier.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
