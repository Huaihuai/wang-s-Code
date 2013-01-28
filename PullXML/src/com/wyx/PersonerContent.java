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
    //对URI匹配，使用UriMarcher类（常量一般都大写）；UriMatcher(在uri不匹配时，将传入的匹配码)
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    //对所有数据操作
    private static final int PERSONS = 1;
    //对特定数据操作
    private static final int PERSON = 2;
    static{
    	/**
    	 * authority:主机名
    	 * path:路径
    	 * code:返回码
    	 */
    	MATCHER.addURI("content://com.wyx.provides", "person", PERSONS);
    	MATCHER.addURI("content://com.wyx.provides", "person/#", PERSON);
    }
	//第一次访问后才被创建（只调用一次）
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
			 *  表名,           //Uri
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
			//抛出非法参数异常
			throw new IllegalArgumentException("this unknow Uri:"+uri);
			break;
		}
		return null;
	}

	/**
	 * 返回目前操作数据的内容类型
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
			long rowid = db.insert(table, nullColumnHack, values);  //主键
			Uri insertUri = Uri.parse("content://com.wyx.provides/person/"+rowid);
			//拼接Uri；
			//Uri uri = ContentUris.withAppendedId(contentUri, id);
			
			/**
			 * uri:需要操作的数据；
			 * observer：监听者（收到通知的对象）[null:表示所有的监听者]；
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
			//由ContentUris工具类获取Uri的id号
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
			//得到Uri的id号
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
