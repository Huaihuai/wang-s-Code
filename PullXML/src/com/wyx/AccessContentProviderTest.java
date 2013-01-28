package com.wyx;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.test.AndroidTestCase;
import android.util.Log;

public class AccessContentProviderTest extends AndroidTestCase{
	private static final String TAG="AccessContentProviderTest";
	public void testInsert() throws Exception{
		Uri uri = Uri.parse("content://com.wyx.provides/person");
		//�õ�ContentResoler����
		ContentResolver resolver = this.getContext().getContentResolver();
		ContentValues values = new ContentValues();
		values.put("name", "laoli");
		values.put("tel", "5556");
		values.put("amount", "500000");
		/**
		 * ��������uri��
		 * ������ݣ�values��
		 * ����ֵ��Uri��
		 */
		resolver.insert(uri, values);
	}
	
	public void testDelete() throws Exception{
		Uri uri = Uri.parse("content://com.wyx.provides/person/1");
		ContentResolver resolver = this.getContext().getContentResolver();
		/**
		 * ��������uri��
		 * ɾ����������null��
		 * ɾ���Ķ���ƥ�������null��
		 */
		resolver.delete(uri, null, null);
	}
	
	public void testUpdate() throws Exception{
		Uri uri = Uri.parse("content://com.wyx.provides/person/1");
		ContentResolver resolver = this.getContext().getContentResolver();
		ContentValues values = new ContentValues();
		values.put("name", "xiaoxiao");
		resolver.update(uri, values, null, null);
	}
	
	public void testQuery() throws Exception{
		Uri uri = Uri.parse("content://com.wyx.provides/person/1");
		ContentResolver resolver = this.getContext().getContentResolver();
		Cursor cursor = resolver.query(uri, null, null, null, "id asc");
		while (cursor.moveToNext()) {
			//�������õ��������������������õ����ֶ��µ��ı���
			String nameString = cursor.getString(cursor.getColumnIndex("name"));
		    Log.i(TAG, nameString);
		}
		cursor.close();
	}



////��������������������������������������������������������������������������������������������������������������������������������������������������������		
//		/**
//		 * ���ݼ����߼������ݸı�
//		 */
//		Uri uri2 = Uri.parse("content://com.wyx.provides/person/1");
//		/**
//		 * uri:������������
//		 * true������
//		 * new PersonContentObserver(new Handler())�����������ݸı��ص��û�����
//		 */
//		getContext().getContentResolver().registerContentObserver(uri, true, new PersonContentObserver(new Handler()));
//
//private class PersonContentObserver extends ContentObserver{
//
//	public PersonContentObserver(Handler handler) {
//		super(handler);
//		// TODO Auto-generated constructor stub
//	}
//
//	//�������󴥷��÷���
//	@Override
//	public void onChange(boolean selfChange) {
//		// TODO Auto-generated method stub
//		super.onChange(selfChange);
//		
//	}
//}
	
}
