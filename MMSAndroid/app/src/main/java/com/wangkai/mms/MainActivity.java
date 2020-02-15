package com.wangkai.mms;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;;
import android.widget.Button;

import android.widget.TextView;;import com.wangkai.mms.Purchase.PurchaseActivity;
import com.wangkai.mms.Sales.SalesActivity;
import com.wangkai.mms.Staff.AddStaffActivity;
import com.wangkai.mms.Staff.ViewStaffActivity;
import com.wangkai.mms.Supplier.AddSupplierActivity;
import com.wangkai.mms.Supplier.ViewSupplier;

/*
交易大厅
*/
public class MainActivity extends Activity {
    String[]  item1= new String[] {"查看员工信息", "增加员工"};
    String[]  item2= new String[] {"查看供应商信息", "增加供应商"};

    TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show=findViewById(R.id.show);
        Button staff=findViewById(R.id.staff);
        Button supeir=findViewById(R.id.supper);

        staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleList(v);
            }
        });
        supeir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleList2(v);
            }
        });
        //售货
        Button sales=(Button)findViewById(R.id.sales);
        sales.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SalesActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //进货
        Button puchase=(Button)findViewById(R.id.purchase);
        puchase.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,PurchaseActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //后台
        Button home=(Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void simpleList(View source)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                // 设置对话框标题
                .setTitle("员工基本信息")
                // 设置图标
                .setIcon(R.drawable.ic_launcher)
                // 设置简单的列表项内容
                .setItems(item1, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(item1[which].equals("增加员工")){
                            Intent intent=new Intent(MainActivity.this,AddStaffActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        if(item1[which].equals("查看员工信息")) {
                            Intent intent=new Intent(MainActivity.this,ViewStaffActivity.class);
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
    public void simpleList2(View source)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                // 设置对话框标题
                .setTitle("供应商基本信息")
                // 设置图标
                .setIcon(R.drawable.ic_launcher)
                // 设置简单的列表项内容
                .setItems(item2, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(item2[which].equals("增加供应商")){
                            Intent intent=new Intent(MainActivity.this,AddSupplierActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        if(item2[which].equals("查看供应商信息")){
                            Intent intent=new Intent(MainActivity.this,ViewSupplier.class);
                            startActivity(intent);
                            finish();
                        }
                        show.setText("你选中了《" + item2[which] + "》");
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
                show.setText("单击了【确定】按钮！");
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
                show.setText("单击了【取消】按钮！");
            }
        });
    }



}
