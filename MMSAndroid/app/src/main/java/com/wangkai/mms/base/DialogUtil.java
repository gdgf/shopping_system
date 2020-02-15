package com.wangkai.mms.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.View;

import com.wangkai.mms.MainActivity;


public class DialogUtil
{
	// 定义一个显示消息的对话框
	public static void showDialog(final Context ctx
			, String msg , boolean goHome)
	{
		// 创建一个AlertDialog.Builder对象
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx)
				.setMessage(msg).setCancelable(false);    //设置要显示的消息，设置设置对话框是否可以取消
		if(goHome)
		{
			builder.setPositiveButton("确定", new OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					Intent i = new Intent(ctx , MainActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					ctx.startActivity(i);
				}
			});
		}
		else
		{
			builder.setPositiveButton("确定", null);
		}
		builder.create().show();
	}
	// 定义一个显示指定组件的对话框
	public static void showDialog(Context ctx , View view)
	{
		new AlertDialog.Builder(ctx)
				.setView(view)          //将自定义视图资源设置为对话框的内容
				.setCancelable(false)   //设置对话框是否可取消
				.setPositiveButton("确定", null)   //当按下对话框的正面按钮时，设置一个侦听器被调用
				.create()  //AlertDialog使用提供给此构建器的参数创建一个
				.show();   //AlterDialog使用提供给此构建器的参数构建一个参数，并立即显示对话框
	}
}

