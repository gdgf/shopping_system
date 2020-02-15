package com.wangkai.mms.Supplier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wangkai.mms.MainActivity;
import com.wangkai.mms.R;
import com.wangkai.mms.base.DialogUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import static com.wangkai.mms.base.HttpUtil.BASE_URL;
import static com.wangkai.mms.base.HttpUtil.postRequest;

/*
1.发送请求
2. json数据解析
3. 数据显示
 */
public class AddSupplierActivity  extends  Activity{

    private EditText Name;
    private EditText SupplierID;
    private EditText GoodName;
    private EditText Address;
    private EditText Phone;
    private EditText Account;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsupplier);
        Name=findViewById(R.id.sname);
        SupplierID=findViewById(R.id.supplierID);
        GoodName=findViewById(R.id.sgoodname);
        Phone=findViewById(R.id.sphone);
        Account=findViewById(R.id.saccountID);
        Address=findViewById(R.id.saddress);
        Button sUpload=findViewById(R.id.supload);
        sUpload.setOnClickListener(new addsup());
        goBack();

    }
    private class addsup implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            try {
                if (upload()){

                    DialogUtil.showDialog(AddSupplierActivity.this
                            , "添加成功！", false);
                }
            } catch (Exception e) {
                DialogUtil.showDialog(AddSupplierActivity.this
                        , "服务器响应异常，没有上传成功，请稍后再试！", false);
                e.printStackTrace();
            }
        }
    }
    private boolean upload() throws Exception {

        String  name=Name.getText().toString();
        String supolierID=SupplierID.getText().toString();
        String goodName=GoodName.getText().toString();
        String address=Address.getText().toString();
        String phone=Phone.getText().toString();
        String account=Account.getText().toString();

        JSONObject jsonObject;
        try{
            jsonObject=query(name,supolierID,goodName,address,phone,account);
            if(jsonObject.getInt("upload")==777){
                return true;
            }

        }catch (Exception e){
            DialogUtil.showDialog(this,"服务器响应异常，请稍候再试！",false);
            e.printStackTrace();

        }
        return  false;
    }
    // 定义发送请求的方法
    private JSONObject query(String name , String supplierID,String goodname , String address, String phone, String account)
            throws Exception {
        // 使用Map封装请求参数
        Map<String,String> map = new HashMap<>();
        map.put("name",name);
        map.put("sp",supplierID);
        map.put("gd",goodname);
        map.put("ad", address);
        map.put("ph", phone);
        map.put("ac",account);
        // 定义发送请求的URL
        String url = BASE_URL+ "MMS/addsupplier";
        // 发送请求
        return new JSONObject(postRequest(url, map));
    }

    private void goBack(){
        findViewById(R.id.iv_Back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddSupplierActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
