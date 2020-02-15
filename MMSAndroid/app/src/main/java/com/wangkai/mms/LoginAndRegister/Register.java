package com.wangkai.mms.LoginAndRegister;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.wangkai.mms.R;
import com.wangkai.mms.base.DialogUtil;
import com.wangkai.mms.base.HttpUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.wangkai.mms.base.HttpUtil.BASE_URL;
import static com.wangkai.mms.base.HttpUtil.postRequest;


@SuppressLint("Registered")
public class Register extends Activity{
	
	private EditText etxtUserName;
	private EditText etxtPassword;

	//private static final String ipAdress="10.30.27.226";
	
	//////////////////
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etxtUserName= findViewById(R.id.name);
        etxtPassword= findViewById(R.id.passwd);
		Button btnRegister = findViewById(R.id.rRegister);
        btnRegister.setOnClickListener(new MyButtonOnClickListener());
	}
    
/////////////////////////////////////////////////////////////////////////////////////

	class MyButtonOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// 执行输入校验
			if (validate())
			{
				// 如果注册成功
				if (registerPro())
				{
					// 启动Main Activity
					Intent intent = new Intent(Register.this
							, LogIn.class);
					startActivity(intent);
					// 结束该Activity
					finish();
				} else {
					DialogUtil.showDialog(Register.this
							, "注册失败", false);
				}
			}
		}
	}

	// 对用户输入的用户名、密码进行校验，防止为空
	private boolean validate() {

		//trim方法：返回此字符串移除了前导和尾部空白的副本；如果没有前导和尾部空白，则返回此字符串
		String username = etxtUserName.getText().toString().trim();
		if (username.equals("")) {
			DialogUtil.showDialog(this, "账户是必填项！", false);
			return false;
		}
		String pwd = etxtPassword.getText().toString().trim();
		if (pwd.equals("")) {
			DialogUtil.showDialog(this, "用户密码是必填项！", false);
			return false;
		}
		return true;
	}
	private boolean registerPro() {
		// 获取用户输入的用户名、密码
		String username = etxtUserName.getText().toString();
		String pwd = etxtPassword.getText().toString();
		JSONObject jsonObject;
		//String jsonObj;
		try {
			jsonObject = query(username, pwd);
			if(jsonObject.getInt("register")==222){
				DialogUtil.showDialog(this
						, "注册成功，祝您愉快！", false);
				return true;
			}
		} catch (Exception e) {
			DialogUtil.showDialog(this
					, "服务器响应异常，请稍后再试！", false);
			e.printStackTrace();
		}

		return false;
	}
	// 定义发送请求的方法
	private JSONObject query(String username, String password)
	//private String query(String username, String password)
			throws Exception {
		// 使用Map封装请求参数
		Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
		// 定义发送请求的URL
		String url = BASE_URL+"MMS/register";
		// 发送请求
		return new JSONObject(postRequest(url, map));
	}
}
