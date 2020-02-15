package com.wangkai.mms;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * 客户端
 *
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.widget.TextView;

public class TestActivity extends Activity {
    private TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        show = findViewById(R.id.show);
        goBack();
        try {

            Socket socket = new Socket("112.5.175.8", 30000);
            //设置10秒之后即认为是超时
            socket.setSoTimeout(10000);
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            String line = br.readLine();
            show.setText("来自服务器的数据："+line);
            br.close();
            socket.close();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            Log.e("UnknownHost", "来自服务器的数据");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("IOException", "来自服务器的数据");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void goBack(){
        findViewById(R.id.iv_Back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TestActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}