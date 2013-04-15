package com.cimu.NUITools;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class NUIToolsCore {
	private final static int MSG_SHOWPROGRASS = 1;
	private final static int MSG_SHOWALERTDIALOG = 2;
	private final static int MSG_SHOWINPUTDIALOG = 3;
	private final static int MSG_SHOWTOAST = 4;
	private final static int MSG_SHOWLISTDIELOG = 5;
	
	private static NUIToolsCore m_instance = null;
	private Handler m_handler;
	private Handler m_handlerC;
	private Activity m_activity;
	
	private ProgressDialog m_currentProgress = null;
	private int 	m_alertDialogResult = -1;
	private int 	m_listDialogResult = -1;
	private String 	m_inputDialogResult = "";
	
	private NUIToolsCore() {
	}
	
	public synchronized static NUIToolsCore getInstance() {    
        if (m_instance == null) {    
            m_instance = new NUIToolsCore(); 
        }    
        return m_instance;
    }
	
	public static Object getInstanceC()
    {
    	return getInstance();
    }
	
	public boolean initJava(Activity tActivity)
    {
    	setCurrentActivity(tActivity);
    	createMainThreadHandler();
    	return true;
    }
	private void createMainThreadHandler()
    {
    	m_handler = new Handler(){  
            
            public void handleMessage(Message msg) {  
                switch (msg.what) {  
                case MSG_SHOWPROGRASS:
                {
                	showProgress((String)msg.obj);
                }
                	break;
                case MSG_SHOWALERTDIALOG:
                {
                	String[] tarray = (String[])msg.obj;
                	showAlertDialog(tarray[0],tarray[1],tarray[2]);
                	
                }
                	break;
                case MSG_SHOWINPUTDIALOG:
                {
                	String[] tarray = (String[])msg.obj;
                	showInputDialog(tarray[0],tarray[1],tarray[2],tarray[3],tarray[4]);
                	
                }
                	break;
                case MSG_SHOWTOAST:
                {
                	Bundle parm = msg.getData();
                	boolean tAnim = parm.getBoolean("animed");
                	double tDelay = parm.getDouble("delay");
                	String tInfo  = parm.getString("info");
                	showToast(tDelay, tInfo);
                }
                	break;
                case MSG_SHOWLISTDIELOG:
                {
                	Bundle parm = msg.getData();
                	String tTitle = parm.getString("Title");
                	String tCelBtn = parm.getString("CancelBtn");
                	String tDestructiveBtn = parm.getString("DestructiveBtn");
                	String tButtons = parm.getString("Buttons");
                	
                	showListDialog(tTitle, tCelBtn, tDestructiveBtn, tButtons);
                }
                	break;
                }  
            };  
        };
        Log.d("SinaWBManager","handler threadid :"+Thread.currentThread().getId());
    }
	public void setCurrentActivity(Activity tActivity)
    {
    	m_activity = tActivity;
    }
	
//	public void showAlert(Context context, String title, String text)
//	{
////		Builder alertBuilder = new Builder(context);
////        alertBuilder.setTitle(title);
////        alertBuilder.setMessage(text);
////        alertBuilder.create().show();
//		Toast.makeText(context, "Auth cancel",
//				Toast.LENGTH_LONG).show();
//	}
	
//	public void showAlert2(Activity tActivity, String title, String text)
//	{
//		ProgressDialog dialog = ProgressDialog.show(tActivity, "lalal title", "Loading. Please wait...", true);
//	}
	
	public void showProgress(String tText)
	{
		String[] texts = tText.split("\\|",2);
		int tlen = texts.length;
		if( tlen == 2 )
		{
			m_currentProgress = ProgressDialog.show(m_activity, texts[0], texts[1]);
		}
		else
		{
			m_currentProgress = ProgressDialog.show(m_activity, "", tText);
		}
	}
	public void showProgressC(String tText)
	{
		Message msg = new Message();     
		msg.what = MSG_SHOWPROGRASS;
		msg.obj = tText;
		m_handler.sendMessage(msg);
	}
	
	public void diemessProgress()
	{
		if( m_currentProgress != null )
		{
			m_currentProgress.dismiss();
			m_currentProgress = null;
		}
	}
	
	public void showAlertDialog(String tTitle, String tMsg, String tBtns)
	{
		String[] btns =  tBtns.split("\\|",3);
		
		//Log.d("tests btn",tests[0]+"  "+tests[1]+"  ");
		//Log.d("btns btn",btns[0]+"  "+btns[1]+"  "+btns[2]);
		
//		int tlen = tests.length;
		int blen = btns.length;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(m_activity);
		builder.setCancelable(false);
//		if( tlen == 2 )
//		{
			builder.setTitle(tTitle);
			builder.setMessage(tMsg);
//		}
//		else
//		{
//			builder.setMessage(tests[0]);
//		}
		
		if( blen>=1 )
		{
			builder.setPositiveButton(btns[0], new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   m_alertDialogResult = 0;
		        	   NUIToolsCompleteCallCFunc();
		           }
		       });
		}
		if( blen>=2 )
		{
			builder.setNeutralButton(btns[1], new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   m_alertDialogResult = 1;
		        	   NUIToolsCompleteCallCFunc();
		           }
			  });
		}
		if( blen==3 )
		{
			builder.setNegativeButton(btns[2], new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   m_alertDialogResult = 2;
			        	   NUIToolsCompleteCallCFunc();
			           }
			  });
		}
	     
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void showAlertDialogC(String tTitle, String tMsg, String tBtns)
	{
		String[] tarray = {tTitle, tMsg, tBtns};
		Message msg = new Message();     
		msg.what = MSG_SHOWALERTDIALOG;
		msg.obj = tarray;
		m_handler.sendMessage(msg);
	}
	
	public int GetAlertDialogResult()
	{
		return m_alertDialogResult;
	}
	
	public String GetInputDialogResult()
	{
		return m_inputDialogResult;
	}
	public void showInputDialog(String tTitle, String tMsg,String tContent, String tPlaceHolder, String tBtns)
	{
		Log.d("java c", "input dialog");
		String[] btns =  tBtns.split("\\|",3);
		int blen = btns.length;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(m_activity);
		builder.setTitle(tTitle);
		builder.setMessage(tMsg);
		
		final EditText input = new EditText(m_activity);
		builder.setView(input);
		
		if( blen>=1 )
		{
			builder.setPositiveButton(btns[0], new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   m_alertDialogResult = 0;
		        	   m_inputDialogResult = input.getText().toString();
		        	   NUIToolsCompleteCallCFunc();
		           }
		       });
		}
		if( blen>=2 )
		{
			builder.setNeutralButton(btns[1], new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   m_alertDialogResult = 1;
		        	   m_inputDialogResult = input.getText().toString();
		        	   NUIToolsCompleteCallCFunc();
		           }
			  });
		}
		if( blen==3 )
		{
			builder.setNegativeButton(btns[2], new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   m_alertDialogResult = 2;
			        	   m_inputDialogResult = input.getText().toString();
			        	   NUIToolsCompleteCallCFunc();
			           }
			  });
		}
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void showInputDialogC(String tTitle, String tMsg,String tContent, String tPlaceHolder, String tBtns)
	{
		String[] tarray = {tTitle, tMsg, tContent ,tPlaceHolder, tBtns};
		Message msg = new Message();     
		msg.what = MSG_SHOWINPUTDIALOG;
		msg.obj = tarray;
		m_handler.sendMessage(msg);
	}
	
	public void showToast(double delay, String tInfo)
	{
		int dur = 1;
		if( delay>=1 )
		{
			dur = Toast.LENGTH_LONG;
		}
		else
		{
			dur = Toast.LENGTH_SHORT;
		}
		Toast.makeText(m_activity, tInfo,
				dur).show();
	}
	
	public void showToastC(boolean tAnimed, double delay, String tInfo)
	{
		Bundle parm = new Bundle();
		parm.putBoolean("animed", tAnimed);
		parm.putDouble("delay", delay);
		parm.putString("info", tInfo);
		Message msg = new Message();     
		msg.what = MSG_SHOWTOAST;
		msg.setData(parm);
		m_handler.sendMessage(msg);
	}
	
	
	public void showListDialog(String tTitle, String tCancelBtn, String tDestructiveBtn, String tButtons)
	{
		ArrayList<String> tItemList = new ArrayList<String>();
		if(tDestructiveBtn != null && tDestructiveBtn.length() > 0)
		{
			tItemList.add(tDestructiveBtn);
		}
		String[] btns =  tButtons.split("\\|");
		for(int i =0; i<btns.length; i++)
		{
			tItemList.add(btns[i]);
		}
		if(tCancelBtn != null && tCancelBtn.length() > 0)
		{
			tItemList.add(tCancelBtn);
		}
		
		String[] tShowItems = tItemList.toArray(new String[0]);
		Log.d("list dialog", "len :"+tShowItems.length);
		Log.d("list dialog", "len 0:"+tShowItems[0]);
		AlertDialog.Builder builder = new AlertDialog.Builder(m_activity);
		builder .setTitle(tTitle);
		builder.setItems(tShowItems, new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int which) {
            	 Log.d("show list dialog","geted which "+which);
            	 m_listDialogResult = which;
            	 NUIToolsCompleteCallCFunc();
             }
         });
         builder.create().show();
         Log.d("list dialog", "len:create");
	}
	public void showListDialogC(String tTitle, String tCancelBtn, String tDestructiveBtn, String tButtons)
	{
		Bundle parm = new Bundle();
		parm.putString("Title", tTitle);
		parm.putString("CancelBtn", tCancelBtn);
		parm.putString("DestructiveBtn", tDestructiveBtn);
		parm.putString("Buttons", tButtons);
		
		Message msg = new Message();     
		msg.what = MSG_SHOWLISTDIELOG;
		msg.setData(parm);
		m_handler.sendMessage(msg);
	}
	
	public int GetListDialogResult()
	{
		return m_listDialogResult;
	}
	
	public static native void NUIToolsCompleteCallback();
	public void NUIToolsCompleteCallCFunc()
	{
		NUIToolsCompleteCallback();
	}
	
}
