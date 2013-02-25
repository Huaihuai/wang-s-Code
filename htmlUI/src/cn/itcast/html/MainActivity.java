package cn.itcast.html;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.itcast.domain.Contact;
import cn.itcast.service.ContactService;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends Activity {
    private WebView webView;
    private ContactService contactService;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        webView = (WebView) this.findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/index.html");
        webView.getSettings().setJavaScriptEnabled(true);
        //设置被js调用的类，及调用时使用的别名
        webView.addJavascriptInterface(new JSObject(), "contact");
        
        contactService = new ContactService();
        
        //获取websettings对象
        WebSettings webSettings = webView.getSettings();
        //设置支持JavaScript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        
        //设置WebChromeClient,对网页中Alert(),Comfirm(),Prompt()事件进行监听
        webView.setWebChromeClient(new MyWebChromeClient());
    }
    
    private final class JSObject{
    	
    	public void call(String phone){
    		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ phone));
    		startActivity(intent);
    	}
    	
    	public void showcontacts(){
    		//  [{name:"xxx",amount:600,phone:"13988888"},{name:"bb",amount:200,phone:"1398788"}]
    		try {
				List<Contact> contacts = contactService.getContacts();
				JSONArray jsonArray = new JSONArray();
				for(Contact c : contacts){
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("name", c.getName());
					jsonObject.put("amount", c.getAmount());
					jsonObject.put("phone", c.getPhone());
					jsonArray.put(jsonObject);
				}
				String json = jsonArray.toString();
				//java调用js中的方法
				webView.loadUrl("javascript:show('"+ json+ "')");
			} catch (JSONException e) {
				e.printStackTrace();
			}
    	}
    }

    /**
     * WebChromeClient简单实用案例
     */
    class MyWebChromeClient extends WebChromeClient{
		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				final JsResult result) {
			// TODO Auto-generated method stub
			//构建一个builder来显示网页中的对话框
			new AlertDialog.Builder(MainActivity.this).setTitle("Alert对话框")
			.setMessage(message)
			.setPositiveButton(android.R.string.ok,    //系统自带的文本
					new AlertDialog.OnClickListener(){

						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							result.confirm();
						}
				
			}).setCancelable(false).show();
			return super.onJsAlert(view, url, message, result);
		}

		@Override
		public boolean onJsConfirm(WebView view, String url, String message,
				final JsResult result) {
			// TODO Auto-generated method stub
			new AlertDialog.Builder(MainActivity.this).setTitle("Confirm对话框")
			.setMessage(message)
			.setPositiveButton(android.R.string.ok, 
					new AlertDialog.OnClickListener(){

						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							result.confirm();
						}
				
			} )
			.setNegativeButton(android.R.string.cancel, 
					new AlertDialog.OnClickListener(){

						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							result.cancel();
						}
				
			})
			.setCancelable(false).show();	
			return super.onJsConfirm(view, url, message, result);
		}

		//String defaultValue:onJsPrompt方法独有，表示如果用户没有输入信息的默认值
		@Override
		public boolean onJsPrompt(WebView view, String url, String message,
				String defaultValue, JsPromptResult result) {
			// TODO Auto-generated method stub
			/**
			 * 获得一个LayoutInflater对象，对该对象可以
			 * 将指定的xml布局文件相应的对象添加到View对
			 * 象中
			 */
			final LayoutInflater factory = LayoutInflater.from(MainActivity.this);
			//获取xml布局文件
			final View dialogView = factory.inflate(R.id.webView, null);
			//后面代码略...
			return super.onJsPrompt(view, url, message, defaultValue, result);
		}
    	
    }
}