package com.cimu.snslib;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.weibo.net.AccessToken;
import com.weibo.net.DialogError;
import com.weibo.net.Oauth2AccessTokenHeader;
import com.weibo.net.Utility;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboDialogListener;
import com.weibo.net.WeiboException;
import com.weibo.net.WeiboParameters;


public class SinaWBManager {
	private final static int MSG_LOGIN = 1;
	private final static int MSG_SENTWEIBO = 2;
	private final static String SINAWB_PRE = "SINAWB_PRE";
	private final static String SINAWB_ACCESSTOKEN = "SINAWB_ACCESSTOKEN";
	private final static String SINAWB_EXPIREIN = "SINAWB_EXPIREIN";
	private final static String SINAWB_USERUID = "SINAWB_USERUID";
	
    private static SinaWBManager m_instance = null;
    private static Weibo weibo;
    
    private String m_messageFilter = "";
    private String m_appid , m_appsecurit;
    private Activity m_activity;
    private Handler m_handler;
    private Handler m_CHandler;
    private String  m_userid;
    
    private JSONObject m_currentUser = null;
    private Map<String, JSONObject> m_users;
    private Map<String, JSONObject> m_weibos;
    String m_OfficeWeiboID = "";
    
    private SinaWBManager() {
    	weibo = Weibo.getInstance();
    	m_users = new HashMap<String, JSONObject>();
    	m_weibos = new HashMap<String, JSONObject>();
    }
    
    public synchronized static SinaWBManager getInstance() {
        if (m_instance == null) {
            m_instance = new SinaWBManager();
        }
        return m_instance;
    }
    
    public static Object getInstanceC()
    {
    	Log.d("SinaWBManager", "getInstanceC");
    	return getInstance();
    }
    
    //called in java layer main activity
    public boolean initJava(Activity tActivity)
    {
    	setCurrentActivity(tActivity);
    	createMainThreadHandler();
    	return true;
    }
    
    //called in lua
    public boolean init(String tAppid , String tAppSecurit)
    {
    	//Log.d("SinaWBManager", "init");
    	m_appid = tAppid;
    	m_appsecurit = tAppSecurit;
    	
    	weibo.setupConsumerConfig(tAppid, tAppSecurit);
    	weibo.setRedirectUrl("http://www.sina.com");
    	createCThreadHandler();
    	return true;
    }
    public void setCurrentActivity(Activity tActivity)
    {
    	//Log.d("SinaWBManager", "setCurrentActivity");
    	m_activity = tActivity;
    }
    
    public void createCThreadHandler()
    {
        //    	//Looper.prepare();
        //    	m_CHandler = new Handler(){
        //
        //            public void handleMessage(Message msg) {
        //            	Log.e("m_CHandler handleMessage", "222 ");
        //                switch (msg.what) {
        //                case 1:
        //                {
        //                	Log.e("m_CHandler handleMessage", "111111 ");
        //                	SNSCompleteCallBack("adsfa");
        //                }
        //                	break;
        //                }
        //            };
        //        };
    }
    public void createMainThreadHandler()
    {
    	m_handler = new Handler(){
            
            @Override
			public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_LOGIN:
                    {
                        //Log.e("handleMessage", "111111 ");
                        SinaWBManager.getInstance().login();
                    }
                        break;
                    case MSG_SENTWEIBO:
                    {
                        SinaWBManager.getInstance().sentWeibo((String)msg.obj);
                    }
                        break;
                }
            };
        };
        Log.d("SinaWBManager","handler threadid :"+Thread.currentThread().getId());
    }
    
    public void setMessageFilter(String tFilter)
    {
    	m_messageFilter = tFilter;
    }
    
    public void login()
    {
    	
    	Log.d("SinaWBManager","threadid :"+Thread.currentThread().getId());
    	Log.d("SinaWBManager", "login");
    	
    	weibo.authorize(m_activity,	new AuthDialogListener());
    }
    public void loginC()
    {
    	SharedPreferences preferences =
        m_activity.getApplicationContext().getSharedPreferences(SINAWB_PRE, 0);
    	if(preferences.getString(SINAWB_ACCESSTOKEN, null) != null)
    	{
    		String token = preferences.getString(SINAWB_ACCESSTOKEN, null);
    	    String expires_in = preferences.getString(SINAWB_EXPIREIN, null);
    	    m_userid = preferences.getString(SINAWB_USERUID, null);
    	    AccessToken accessToken = new AccessToken(token, m_appsecurit);
    	    accessToken.setExpiresIn(expires_in);
    	    Utility.setAuthorization(new Oauth2AccessTokenHeader());
    	    SinaWBManager.weibo.setAccessToken(accessToken);
    	    if(weibo.isSessionValid())
    	    {
    	    	SNSCompleteCallBack(null);
    	    	return;
    	    }
    	}
        Message msg = new Message();
        msg.what = MSG_LOGIN;
        m_handler.sendMessage(msg);
    }
    
    public void logout()
    {
    	SharedPreferences preferences =
        m_activity.getApplicationContext().getSharedPreferences(SINAWB_PRE, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
    public boolean isLogin()
    {
    	return weibo.isSessionValid();
    }
    public void downloadMyInfo()
    {
    	//m_userid
    	Log.e("SinaWBManager", "m_userid::"+m_userid);
    	try{
	    	String url = Weibo.SERVER + "users/show.json";
	        WeiboParameters bundle = new WeiboParameters();
	        //bundle.add("source", weibo.getAppKey());
	        bundle.add("uid", m_userid);
	        String rlt = weibo.request(m_activity, url, bundle, "GET", weibo.getAccessToken());
	        
	        String errorInfo = analysisWeiboResultError(rlt);
	        if(errorInfo != null)
	        {
	        	SNSCompleteCallBack(errorInfo);
	        	return;
	        }
	        
	        Log.d("SinaWBManager", "downloadMyInfo::"+rlt);
	        m_currentUser = new JSONObject(rlt);
	        
	   		String nikeName = m_currentUser.getString("screen_name");
    		String avatarUrl = m_currentUser.getString("profile_image_url");
    		String userId = m_currentUser.getString("id");
    		
    		SNSCreateCurrentUser(nikeName,userId,avatarUrl);
    		SNSCompleteCallBack(null);
    	}
    	catch (WeiboException e)
    	{
    		Log.e("SinaWBManager", "WeiboException:"+e.getMessage());
    		SNSCompleteCallBack(e.getMessage());
    	}
    	catch(JSONException je)
    	{
    		SNSCompleteCallBack(je.getMessage());
    	}
    }
    
    public void sentWeibo(String tText)
    {
    	try {
            //    		weibo.share2weibo(m_activity, weibo.getAccessToken().getToken(), weibo.getAccessToken()
            //                    .getSecret(), tText, "");
    		
    		String url = Weibo.SERVER + "statuses/update.json";
	        WeiboParameters bundle = new WeiboParameters();
	        bundle.add("status", tText);
	        String rlt = weibo.request(m_activity, url, bundle, "POST", weibo.getAccessToken());
	        Log.e("SinaWBManager", "weibo sentweibo::"+rlt);
	        
	        String errorInfo = analysisWeiboResultError(rlt);
	        if(errorInfo != null)
	        {
	        	SNSCompleteCallBack(errorInfo);
	        	return;
	        }
	        SNSCompleteCallBack(null);
	        
    	} catch (WeiboException e) {
    		e.printStackTrace();
    		SNSCompleteCallBack(e.getLocalizedMessage());
    	}
    	catch(Exception e)
    	{
    		SNSCompleteCallBack(e.getLocalizedMessage());
    	}
    }
    
    public void sentWeiboWithImg(String tText, String tPicPath)
    {
    	try {
            //    		weibo.share2weibo(m_activity, weibo.getAccessToken().getToken(), weibo.getAccessToken()
            //                    .getSecret(), tText, "");
    		
    		String url = Weibo.SERVER + "statuses/upload.json";
	        WeiboParameters bundle = new WeiboParameters();
	        bundle.add("status", tText);
	        bundle.add("pic", tPicPath);
	        String rlt = weibo.request(m_activity, url, bundle, "POST", weibo.getAccessToken());
	        Log.e("SinaWBManager", "weibo sentweibo::"+rlt);
	        
	        String errorInfo = analysisWeiboResultError(rlt);
	        if(errorInfo != null)
	        {
	        	SNSCompleteCallBack(errorInfo);
	        	return;
	        }
	        SNSCompleteCallBack(null);
	        
    	} catch (WeiboException e) {
    		e.printStackTrace();
    		SNSCompleteCallBack(e.getLocalizedMessage());
    	}
    	catch(Exception e)
    	{
    		SNSCompleteCallBack(e.getLocalizedMessage());
    	}
    }
    
    public void sentWeiboC(String tText)
    {
    	Message msg = new Message();
        msg.what = MSG_SENTWEIBO;
        msg.obj = tText;
        m_handler.sendMessage(msg);
    }
    
    public void downLoadWeiBos()
    {
    	Log.d("SinaWBManager", "downLoadWeiBos");
    	try{
	    	String url = Weibo.SERVER + "statuses/friends_timeline.json";
	        WeiboParameters bundle = new WeiboParameters();
	        //bundle.add("source", weibo.getAppKey());
	        bundle.add("base_app", "1");
	        bundle.add("feature","1");
	        String rlt = weibo.request(m_activity, url, bundle, "GET", weibo.getAccessToken());
	        Log.d("SinaWBManager", "downLoadWeiBos::"+rlt);
	        
	        String errorInfo = analysisWeiboResultError(rlt);
	        if(errorInfo != null)
	        {
	        	SNSCompleteCallBack(errorInfo);
	        	return;
	        }
	        
	        analysisWeiboResult(rlt);
	        SNSCompleteCallBack(null);
    	}
    	catch (WeiboException e)
    	{
    		Log.d("SinaWBManager",e.getLocalizedMessage());
    		SNSCompleteCallBack(e.getLocalizedMessage());
    	}
    	catch (Exception e)
    	{
    		Log.d("SinaWBManager",e.getLocalizedMessage());
    		SNSCompleteCallBack(e.getLocalizedMessage());
    	}
        //return rlt;
    }
    
    public void commentAWeibo(String tMsg, String tWeiboId)
    {
    	try
    	{
    		String url = Weibo.SERVER + "comments/create.json";
	        WeiboParameters bundle = new WeiboParameters();
	        //bundle.add("source", weibo.getAppKey());
	        bundle.add("comment", tMsg);
	        bundle.add("id",tWeiboId);
	        String rlt = weibo.request(m_activity, url, bundle, "POST", weibo.getAccessToken());
	        Log.e("SinaWBManager", "weibo comment::"+rlt);
	        
	        String errorInfo = analysisWeiboResultError(rlt);
	        if(errorInfo != null)
	        {
	        	SNSCompleteCallBack(errorInfo);
	        	return;
	        }
	        SNSCompleteCallBack(null);
    	}
    	catch(WeiboException e)
    	{
    		SNSCompleteCallBack(e.getLocalizedMessage());
    	}
    	catch(Exception e)
    	{
    		SNSCompleteCallBack(e.getLocalizedMessage());
    	}
    }
    
    
    protected boolean analysisWeiboResult(String tResult) throws JSONException
    {
        JSONObject jsonResult = new JSONObject(tResult);
        JSONArray arrayStatuses =jsonResult.getJSONArray("statuses");
        Log.d("weibo result", "weibo result num: "+arrayStatuses.length());
        for(int i=0;i<arrayStatuses.length();i++)
        {
            JSONObject jsonWeibo = arrayStatuses.getJSONObject(i);
            //todo m_messageFilter
            //filter the message
            String textmsg = jsonWeibo.getString("text");
            boolean isMsgOk = textmsg.startsWith(m_messageFilter);
            if(!isMsgOk)
            {
                continue;
            }
            JSONObject jsonUser = jsonWeibo.getJSONObject("user");
            
            String nikeName = jsonUser.getString("screen_name");
            String avatarUrl = jsonUser.getString("profile_image_url");
            String userId = jsonUser.getString("id");
            
            String weiboId = jsonWeibo.getString("id");
            
            Log.d("weibo result", "name: "+nikeName);
            Log.d("weibo result", "id: "+jsonUser.getString("id"));
            Log.d("weibo result", "avater url: "+avatarUrl);
            Log.d("weibo result", "  "+textmsg);
            
            if( m_users.get(userId) == null )
            {
                m_users.put(userId, jsonUser);
                //todo add user in c
                SNSCreateAUser(nikeName,userId,avatarUrl);
            }
            if( m_weibos.get(weiboId) == null )
            {
                m_weibos.put(userId, jsonWeibo);
                
                int iisOffice = m_OfficeWeiboID.compareTo(userId);
                boolean bisOffice = false;
                if( iisOffice == 0 )
                {
                    bisOffice = true;
                }
                
                Date ttDate =  new Date(jsonWeibo.getString("created_at"));
                Log.d("weibo result", "date time: "+ttDate.getTime());
                
                SNSCreateAGame(userId,
                               String.valueOf(ttDate.getTime()), jsonWeibo.getString("id"),
                               jsonWeibo.getString("text"),
                               jsonWeibo.getString("thumbnail_pic"),
                               jsonWeibo.getString("bmiddle_pic"),
                               jsonWeibo.getString("original_pic"),
                               jsonWeibo.getInt("comments_count"), bisOffice );
                
                //todo add user in c
            }
        }
    	return true;
    }
    
    protected String analysisWeiboResultError(String tResult) throws JSONException
    {
    	JSONObject tre = new JSONObject(tResult);
    	boolean haveError = tre.has("error");
        if( haveError )
        {
        	Log.e("WeiboResultError",tre.getString("error"));
        	return tre.getString("error");
        }
    	return null;
    }
    
    private static native boolean SNSCreateCurrentUser(String tUserName, String tUserId, String tAvaterUrl);
    private static native boolean SNSCreateAUser(String tUserName, String tUserId, String tAvaterUrl);
    private static native boolean SNSCreateAGame(String tCreateUserId, String tDate, String tPostId, String tMsg, String tThumbPUrl, String tMiddlePUrl, String tOriginalPUrl, int tCommentNum, boolean IsOffice );
    private static native void	  SNSCompleteCallBack(String tError);
    
    public void setOfficeWeiboID(String tWeiboId)
    {
    	m_OfficeWeiboID = tWeiboId;
    }
    
	//login dialog callback
    class AuthDialogListener implements WeiboDialogListener {
        
		@Override
		public void onComplete(Bundle values) {
			Log.d("SinaWBManager", "AuthDialogListener onComplete");
			
			String token = values.getString("access_token");
			String expires_in = values.getString("expires_in");
			m_userid = values.getString("uid");
			//mToken.setText("access_token : " + token + "  expires_in: "+ expires_in);
			
			AccessToken accessToken = new AccessToken(token, m_appsecurit);
			accessToken.setExpiresIn(expires_in);
			weibo.setAccessToken(accessToken);
			
			SharedPreferences preferences =
            m_activity.getApplicationContext().getSharedPreferences(SINAWB_PRE, 0);
		    SharedPreferences.Editor editor = preferences.edit();
		    editor.putString(SINAWB_ACCESSTOKEN, token);
		    editor.putString(SINAWB_EXPIREIN, expires_in);
		    editor.putString(SINAWB_USERUID, m_userid);
		    editor.commit();
		    SNSCompleteCallBack(null);
		    //Intent intent = new Intent();
			//intent.setClass(AuthorizeActivity.this, TestActivity.class);
			//startActivity(intent);
		}
        
		@Override
		public void onError(DialogError e) {
			Log.d("SinaWBManager", "AuthDialogListener onError");
			Toast.makeText(m_activity.getApplicationContext(),"Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();
			SNSCompleteCallBack("Auth error : " + e.getMessage());
		}
        
		@Override
		public void onCancel() {
			Log.d("SinaWBManager", "AuthDialogListener onCancel");
			Toast.makeText(m_activity.getApplicationContext(), "Auth cancel",
                           Toast.LENGTH_LONG).show();
			SNSCompleteCallBack(null);
		}
        
		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(m_activity.getApplicationContext(),
                           "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG)
            .show();
		}
        
	}
}
