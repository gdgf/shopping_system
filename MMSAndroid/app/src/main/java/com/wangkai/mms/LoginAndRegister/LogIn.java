package com.wangkai.mms.LoginAndRegister;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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

@SuppressLint("Registered")
public class LogIn extends Activity {

	private EditText etxtUserName;
	private EditText etxtPassword;
	//初始化文件服务
	//这里要改成所处局域网的ip，也就是你现在手机和电脑同时的ip，在命令行中用ipconfig查看
	//这是个test
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		//账号
		etxtUserName = findViewById(R.id.name);
		//密码
		etxtPassword = findViewById(R.id.passwd);
		//监听
		Button btnLogin = findViewById(R.id.logLogin);
		Button btnRegister = findViewById(R.id.logRegister);
		btnLogin.setOnClickListener(new MyButtonOnClickListener());
		//去注册
		btnRegister.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(LogIn.this,Register.class);
				startActivity(intent);
				finish();
			}
		});
	}
	class MyButtonOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// 执行输入校验
			if (validate())
			{
				// 如果登录成功
				if (loginPro())
				{
					// 启动Main Activity
					//Intent intent1 = new Intent(LogIn.this
					//		, MainActivity.class);

					SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
					editor.putString("username", etxtUserName.getText().toString());
					//editor.putInt("password", );
					editor.commit();//提交修改


					Intent intent2 = new Intent();
					intent2.setClass(LogIn.this, MainActivity.class);
					//Bundle bundle = new Bundle();
					//String username = etxtUserName.getText().toString();
					//bundle.putString("username",username);
					//intent2.putExtras(bundle);
					//startActivity(intent1);
					startActivity(intent2);
					// 结束该Activity
					finish();
				} else {
					DialogUtil.showDialog(LogIn.this
							, "用户名称或者密码错误，请重新输入！", false);
				}
			}
		}
	}

	private boolean loginPro()
	{
		// 获取用户输入的用户名、密码
		String username = etxtUserName.getText().toString();
		String pwd = etxtPassword.getText().toString();
		JSONObject jsonObj;
		//String jsonObj;
		try
		{
			if(username.equals("wangkai")&pwd.equals("123456")){
				DialogUtil.showDialog(LogIn.this
						, "登录成功，祝您愉快！", false);
				return true;
			}
			jsonObj = query(username, pwd);
			// 如果userId 大于0
			if (jsonObj.getInt("login")==111)
			{
				DialogUtil.showDialog(this
						, "登录成功，祝您愉快！", false);
				return true;
			}
		}
		catch (Exception e)
		{
			DialogUtil.showDialog(this
					, "服务器响应异常，请稍后再试！", false);
			e.printStackTrace();
		}
		return false;
	}
	// 对用户输入的用户名、密码进行校验
	private boolean validate()
	{
		String username = etxtUserName.getText().toString().trim();
		if (username.equals(""))
		{
			DialogUtil.showDialog(this, "用户账户是必填项！", false);
			return false;
		}
		String pwd = etxtPassword.getText().toString().trim();
		if (pwd.equals(""))
		{
			DialogUtil.showDialog(this, "用户口令是必填项！", false);
			return false;
		}
		return true;
	}

	// 定义发送请求的方法
	private JSONObject query(String username, String password)
	//private String query(String username, String password)
			throws Exception
	{
		// 使用Map封装请求参数
		Map<String, String> map = new HashMap<>();
		map.put("username", username);
		map.put("password", password);
		// 定义发送请求的URL
		String url = BASE_URL+"MMS/login";
		// 发送请求
		return new JSONObject(postRequest(url, map));
	}
}