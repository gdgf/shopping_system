package com.wangkai.mms.base;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
public class HttpUtil
{
	// 创建HttpClient对象
	public static HttpClient httpClient = new DefaultHttpClient();
	public static final String BASE_URL = "http://192.168.43.22:8000/";
	/**
	 *
	 * @param url 发送请求的URL
	 * @return 服务器响应字符串
	 * @throws Exception
	 */
	/*
	public static String getRequest(final String url)
			throws Exception
	{
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>()
				{
					@Override
					public String call() throws Exception
					{
						// 创建HttpGet对象。
						HttpGet get = new HttpGet(url);
						// 发送GET请求
						HttpResponse httpResponse = httpClient.execute(get);
						// 如果服务器成功地返回响应
						if (httpResponse.getStatusLine()
								.getStatusCode() == 200)
						{
							// 获取服务器响应字符串
							String result = EntityUtils
									.toString(httpResponse.getEntity());
		                    String result1=URLDecoder.decode(result,"utf-8");
							Log.v("result",result1);
							return result1;

						}
						return null;
					}
				});
		new Thread(task).start();
		return task.get();
	}
	*/
	public static String getRequest(final String url)
			throws Exception {
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						// 创建HttpGet对象。
						URL get = new URL(url);
						HttpURLConnection conn = (HttpURLConnection) get.openConnection();
						conn.setRequestMethod("GET");
						//请求超时时间
						conn.setConnectTimeout(5000);
						//请求超时读取时间
						conn.setReadTimeout(3000);
						conn.setRequestProperty("Accept-Language", "zh-CN");
						conn.setRequestProperty("Charset", "UTF-8");
						conn.setRequestProperty("Connection", "Keep-Alive");
						//得到服务区的返回的结果码
						int code = conn.getResponseCode();
						//利用结果判断
						if (code == 200) {
							BufferedReader bufReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
							String backData = "";
							String line = "";
							while ((line = bufReader.readLine()) != null)
								backData += line + "\r\n";

							Log.d("result",backData);
							return backData;

						}
						return null;
					}
				});
		new Thread(task).start();
		return task.get();

	}
	/**
	 * @return 服务器响应字符串
	 * @throws Exception
	 */

	public static String postRequest(final String Url
			, final Map<String ,String> rawParams)throws Exception
	{
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>()
				{
					@Override
					public String call() throws Exception
					{

						//URL url=new URL(Url);
						// 创建HttpPost对象。

						HttpPost post = new HttpPost(Url);
						// 如果传递参数个数比较多的话可以对传递的参数进行封装
						List<NameValuePair> params = new ArrayList<>();
						for(String key : rawParams.keySet())
						{
							//封装请求参数
							params.add(new BasicNameValuePair(key
									, rawParams.get(key)));
						}
						// 设置请求参数
						post.setEntity(new UrlEncodedFormEntity(
								params, "utf-8"));
						// 发送POST请求
						HttpResponse httpResponse = httpClient.execute(post);
						// 如果服务器成功地返回响应
						if (httpResponse.getStatusLine()
								.getStatusCode() == 200)
						{
							String result = EntityUtils
									.toString(httpResponse.getEntity());
							System.out.print(result);
							//String result="successful";
							return result;
						}

						return null;
					}
				});
		new Thread(task).start();
		return task.get();
	}
}
