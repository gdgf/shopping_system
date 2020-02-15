package com.wangkai.mms;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wangkai.mms.Purchase.PRecording;
import com.wangkai.mms.Sales.Inventoryday;
import com.wangkai.mms.Sales.Inventorymonth;
import com.wangkai.mms.Sales.SRecording;
import com.wangkai.mms.Staff.Achievement;

/**
 * Created by wangaki on 2018/3/23.
 * 账户信息与退出系统
 * 只实现了账户信息的展示，并没有实现存储
 */
public class HomeActivity extends Activity {
    String[]  item1= new String[] {"日盘存", "月盘存"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //进货记录
       Button im= findViewById(R.id.im);
       im.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(HomeActivity.this,PRecording.class);
               startActivity(intent);
               finish();
           }
       });

       //收货记录
        Button sa=findViewById(R.id.salse);
        sa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,SRecording.class);
                startActivity(intent);
                finish();
            }
        });

        //库存
        Button store=findViewById(R.id.store);
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,StoreActivity.class);
                startActivity(intent);
                finish();
            }
        });


        //盘存
        Button inventory=findViewById(R.id.inventory);
        inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleList(v);
            }
        });


        Button ac=findViewById(R.id.achievement);
        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,Achievement.class);
                startActivity(intent);
                finish();
            }
        });
        Button hall= findViewById(R.id.hall);
        hall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void simpleList(View source)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                // 设置对话框标题
                .setTitle("盘存")
                // 设置图标
                .setIcon(R.drawable.ic_launcher)
                // 设置简单的列表项内容
                .setItems(item1, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(item1[which].equals("日盘存")){
                            Intent intent=new Intent(HomeActivity.this,Inventoryday.class);
                            startActivity(intent);
                            finish();
                        }

                        if(item1[which].equals("月盘存")) {
                            Intent intent=new Intent(HomeActivity.this,Inventorymonth.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                });
        // 为AlertDialog.Builder添加“确定”按钮
        setPositiveButton(builder);
        //为AlertDialog.Builder添加“取消”按钮
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
             //   show.setText("单击了【确定】按钮！");
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
               // show.setText("单击了【取消】按钮！");
            }
        });
    }
}

