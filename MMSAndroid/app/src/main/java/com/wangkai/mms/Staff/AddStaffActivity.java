package com.wangkai.mms.Staff;
/*
增加员工
 */

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

public class AddStaffActivity extends Activity {

    private EditText Name;
    private EditText StaffID;
    private EditText Sex;
    private EditText Age;
    private EditText Kind;
    private EditText Phone;
    private EditText Account;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstaff);
        Name=findViewById(R.id.name);
        StaffID=findViewById(R.id.staffID);
        Sex=findViewById(R.id.sex);
        Age=findViewById(R.id.age);
        Kind=findViewById(R.id.kind);
        Phone=findViewById(R.id.phone);
        Account=findViewById(R.id.accountID);
        Button Upload=findViewById(R.id.upload);
        Upload.setOnClickListener(new Milliliter());
        goBack();

    }
    private class Milliliter implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            try {
                if (upload()){

                    DialogUtil.showDialog(AddStaffActivity.this
                            , "上传成功！", false);
                }
            } catch (Exception e) {
                DialogUtil.showDialog(AddStaffActivity.this
                        , "服务器响应异常，没有上传成功，请稍后再试！", false);
                e.printStackTrace();
            }
        }
    }
    private boolean upload() throws Exception {

        String  name=Name.getText().toString();
        String staffID=StaffID.getText().toString();
        String sex=Sex.getText().toString();
        String age=Age.getText().toString();
        String kind=Kind.getText().toString();
        String phone=Phone.getText().toString();
        String account=Account.getText().toString();

        JSONObject jsonObject;
        try{
            jsonObject=query(staffID,name,sex,age,kind,phone,account);
            if(jsonObject.getInt("upload")==666){
                return true;
            }

        }catch (Exception e){
            DialogUtil.showDialog(this,"服务器响应异常，请稍候再试！",false);
            e.printStackTrace();

        }
        return  false;
    }
    // 定义发送请求的方法
    private JSONObject query(String staffID, String name , String sex, String age,String kind, String phone, String account)
            throws Exception {
        // 使用Map封装请求参数
        Map<String,String> map = new HashMap<>();
        map.put("staffID",staffID);
        map.put("name",name);
        map.put("sex",sex);
        map.put("age", age);
        map.put("kind", kind);
        map.put("phone",phone);
        map.put("account",account);
        // 定义发送请求的URL
        String url = BASE_URL+ "MMS/addstaff";
        // 发送请求
        return new JSONObject(postRequest(url, map));
    }

    private void goBack(){
        findViewById(R.id.iv_Back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddStaffActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
