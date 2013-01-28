package com.wyx.contacts;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

public class ContactsTest extends AndroidTestCase{
	private static final String TAG = "ContactsTest";
	 public void contactTestCase() throws Exception{
		Uri uri = Uri.parse("content://com.android.contacts/contacts");
		ContentResolver resolver = this.getContext().getContentResolver();
		Cursor cursor = resolver.query(uri, new String[]{"_id"}, null, null, null);
		while (cursor.moveToNext()) {
			int contactId = cursor.getInt(0);
			StringBuffer sb = new StringBuffer("contractid=");
			sb.append(contactId);
			//某个联系人下的数据
			Uri uri2 = Uri.parse("content://com.android.contacts/contacts"+contactId+ "/data");
			Cursor cursor1 = resolver.query(uri2, new String[]{"mimetype","data1","data2"}, null, null, null);
			while (cursor1.moveToNext()) {
				String data = cursor1.getString(cursor1.getColumnIndex("data1"));
				String type = cursor1.getString(cursor1.getColumnIndex("mimetype"));
				if("vnd.android.cursor.item/name".equals(type)){
					sb.append(", ="+data);
				}else if ("vnd.android.cursor.item/email_v2".equals(type)) {
					sb.append(",email="+data);
				}else if ("vnd.android.cursor.item/phone_v2".equals(type)) {
					sb.append(",phone="+data);
				}
			}
			Log.i(TAG, sb.toString());
			cursor1.close();
		}
		cursor.close();
	}
}
