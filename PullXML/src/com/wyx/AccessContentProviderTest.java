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
		//得到ContentResoler对象
		ContentResolver resolver = this.getContext().getContentResolver();
		ContentValues values = new ContentValues();
		values.put("name", "laoli");
		values.put("tel", "5556");
		values.put("amount", "500000");
		/**
		 * 操作对象：uri；
		 * 添加数据：values；
		 * 返回值：Uri；
		 */
		resolver.insert(uri, values);
	}
	
	public void testDelete() throws Exception{
		Uri uri = Uri.parse("content://com.wyx.provides/person/1");
		ContentResolver resolver = this.getContext().getContentResolver();
		/**
		 * 操作对象：uri；
		 * 删除的条件：null；
		 * 删除的对象匹配参数：null；
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
			//由列名得到列索引，再由列索引得到该字段下的文本；
			String nameString = cursor.getString(cursor.getColumnIndex("name"));
		    Log.i(TAG, nameString);
		}
		cursor.close();
	}



////————————————————————————————————————————————————————————————————————————————		
//		/**
//		 * 内容监听者监听数据改变
//		 */
//		Uri uri2 = Uri.parse("content://com.wyx.provides/person/1");
//		/**
//		 * uri:监听操作数据
//		 * true：？？
//		 * new PersonContentObserver(new Handler())：监听到数据改变后回调该还函数
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
//	//监听到后触发该方法
//	@Override
//	public void onChange(boolean selfChange) {
//		// TODO Auto-generated method stub
//		super.onChange(selfChange);
//		
//	}
//}
	
}
