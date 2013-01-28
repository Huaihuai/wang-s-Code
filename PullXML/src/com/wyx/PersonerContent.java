package com.wyx;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class PersonerContent extends ContentProvider{
    private DBOpenHelper dbOpenHelper;
    //��URIƥ�䣬ʹ��UriMarcher�ࣨ����һ�㶼��д����UriMatcher(��uri��ƥ��ʱ���������ƥ����)
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    //���������ݲ���
    private static final int PERSONS = 1;
    //���ض����ݲ���
    private static final int PERSON = 2;
    static{
    	/**
    	 * authority:������
    	 * path:·��
    	 * code:������
    	 */
    	MATCHER.addURI("content://com.wyx.provides", "person", PERSONS);
    	MATCHER.addURI("content://com.wyx.provides", "person/#", PERSON);
    }
	//��һ�η��ʺ�ű�������ֻ����һ�Σ�
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * ContentUris.withAppendedId(Contacts.People.CONTENT_URI, 2),  //Uri
     *           projection,    // Which columns to return.
     *           null,          // WHERE clause.
     *           null,          // WHERE clause value substitution
     *           People.NAME + " ASC");   // Sort order.
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		switch (MATCHER.match(uri)) {
		case 1:
			/**
			 *  ����,           //Uri
		     *  projection,    // Which columns to return.
		     *  selection,     // WHERE clause.
		     *  selectionArgs, // WHERE clause value substitution
		     *  null,          // groupBy
		     *  null,          // having
		     *  People.NAME + " ASC");   // Sort order.
			 */
			return db.query("person", projection, selection, selectionArgs, null, null, sortOrder);
			break;
			
		case 2:
			long rowId = ContentUris.parseId(uri);
			String where = "personid="+rowId;
			if(selection != null && !"".equals(selection.trim())){
				where += "and"+selection;
			}
			return db.query("person", projection, where, selectionArgs, null, null, sortOrder);
			break;
		default:
			//�׳��Ƿ������쳣
			throw new IllegalArgumentException("this unknow Uri:"+uri);
			break;
		}
		return null;
	}

	/**
	 * ����Ŀǰ�������ݵ���������
	 */
	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		// TODO Auto-generated method stub
		switch (MATCHER.match(uri)) {
		case 1:
			long rowid = db.insert(table, nullColumnHack, values);  //����
			Uri insertUri = Uri.parse("content://com.wyx.provides/person/"+rowid);
			//ƴ��Uri��
			//Uri uri = ContentUris.withAppendedId(contentUri, id);
			
			/**
			 * uri:��Ҫ���������ݣ�
			 * observer�������ߣ��յ�֪ͨ�Ķ���[null:��ʾ���еļ�����]��
			 */
			this.getContext().getContentResolver().notifyChange(uri, null);
			return insertUri;
			break;

		default:
			throw new IllegalArgumentException("this unknow Uri:"+insertUri);
			break;
		}
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		switch (MATCHER.match(uri)) {
		case 1:
			long rowId = db.delete("person", selection, selectionArgs);
			break;
		case 2:
			//��ContentUris�������ȡUri��id��
			int rowId = ContentUris.parseId(uri);
			String where = "personid="+rowId;
			if(selection != null && !"".equals(selection.trim())){
				where += "and" + selection;
			}
			db.delete("person", where, selectionArgs);
		default:
			break;
		}
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		switch (MATCHER.match(uri)) {
		case 1:
			long rowId = db.update("person", values, selection, selectionArgs);
			break;
		case 2:
			//�õ�Uri��id��
			int rowId = ContentUris.parseId(uri);
			String where = "personid="+rowId;
			if(selection != null && !"".equals(selection.trim())){
				where += "and" + selection;
			}
			db.delete("person", where, selectionArgs);
		default:
			break;
		}
		return 0;
	}

}
