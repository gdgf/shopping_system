package com.wangkai.mms.LoginAndRegister;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.wangkai.mms.R;

/**
 * Created by wangaki on 2018/3/20.
 * 登录/注册
 * 登录->登录界面；
 * 注册->注册界面
 */
@SuppressLint("Registered")
public class GuidActivity extends Activity {
   @Override

    public void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_guide);
       ImageView guidLogin= findViewById(R.id.guid_login);
       ImageView guidRegister= findViewById(R.id.guid_register);

       //去登录
       guidLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(GuidActivity.this,LogIn.class);
               startActivity(intent);
               finish();
           }
       });
       //去注册
       guidRegister.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(GuidActivity.this,Register.class);
               startActivity(intent);
               finish();
           }
       });
   }
}
