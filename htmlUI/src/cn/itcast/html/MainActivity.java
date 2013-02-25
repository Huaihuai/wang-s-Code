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
        //���ñ�js���õ��࣬������ʱʹ�õı���
        webView.addJavascriptInterface(new JSObject(), "contact");
        
        contactService = new ContactService();
        
        //��ȡwebsettings����
        WebSettings webSettings = webView.getSettings();
        //����֧��JavaScript�ű�
        webSettings.setJavaScriptEnabled(true);
        //���ÿ��Է����ļ�
        webSettings.setAllowFileAccess(true);
        
        //����WebChromeClient,����ҳ��Alert(),Comfirm(),Prompt()�¼����м���
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
				//java����js�еķ���
				webView.loadUrl("javascript:show('"+ json+ "')");
			} catch (JSONException e) {
				e.printStackTrace();
			}
    	}
    }

    /**
     * WebChromeClient��ʵ�ð���
     */
    class MyWebChromeClient extends WebChromeClient{
		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				final JsResult result) {
			// TODO Auto-generated method stub
			//����һ��builder����ʾ��ҳ�еĶԻ���
			new AlertDialog.Builder(MainActivity.this).setTitle("Alert�Ի���")
			.setMessage(message)
			.setPositiveButton(android.R.string.ok,    //ϵͳ�Դ����ı�
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
			new AlertDialog.Builder(MainActivity.this).setTitle("Confirm�Ի���")
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

		//String defaultValue:onJsPrompt�������У���ʾ����û�û��������Ϣ��Ĭ��ֵ
		@Override
		public boolean onJsPrompt(WebView view, String url, String message,
				String defaultValue, JsPromptResult result) {
			// TODO Auto-generated method stub
			/**
			 * ���һ��LayoutInflater���󣬶Ըö������
			 * ��ָ����xml�����ļ���Ӧ�Ķ�����ӵ�View��
			 * ����
			 */
			final LayoutInflater factory = LayoutInflater.from(MainActivity.this);
			//��ȡxml�����ļ�
			final View dialogView = factory.inflate(R.id.webView, null);
			//���������...
			return super.onJsPrompt(view, url, message, defaultValue, result);
		}
    	
    }
}