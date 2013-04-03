package cn.itcast.db;

import android.content.ContentProvider;
import cn.itcast.service.DBOpenHelper;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

//****************
//内容提供者实现类
//****************
public class PersonProvider extends ContentProvider {
	private DBOpenHelper dbOpenHelper;
	//UriMatcher.NO_MATCH：当不匹配时返回的数据（这里UriMatcher.NO_MATCH的值为-1）；
	private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
	private static final int PERSONS = 1;
	private static final int PERSON = 2;
	//设置初始化匹配码
	static{
		MATCHER.addURI("cn.itcast.providers.personprovider", "person", PERSONS);
		MATCHER.addURI("cn.itcast.providers.personprovider", "person/#", PERSON);
	}
	

	//进行初始化操作（如数据库建立）
	@Override
	public boolean onCreate() {
		dbOpenHelper = new DBOpenHelper(this.getContext());
		return true;
	}

    //返回查询到的数据集合
	/**
	 *uri:操作数据路径
	 *projection:需要获取的的字段
	 *selection:条件
	 *selectionArgs:条件中的占位符
	 *sortOrder:排序类型
     */ 
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		//与预先设定的Uri进行匹配检测
		switch (MATCHER.match(uri)) {
		case PERSONS:
			return db.query("person", projection, selection, selectionArgs, null, null, sortOrder);

		case PERSON:
			long rowid = ContentUris.parseId(uri);
			String where = "personid="+ rowid;
			if(selection!=null && !"".equals(selection.trim())){
				where += " and "+ selection;
			}
			return db.query("person", projection, where, selectionArgs, null, null, sortOrder);
		default:
			throw new IllegalArgumentException("this is Unknown Uri:"+ uri);
		}
	}

    //返回所操作文件的内容类型
	@Override
	public String getType(Uri uri) {
		switch (MATCHER.match(uri)) {
		case PERSONS:
			return "vnd.android.cursor.dir/person";   //对已对多条数据类型操作的Uri，使用“vnd.android.cursor.dir/”
		case PERSON:
			return "vnd.android.cursor.item/person";   //对已对单条数据类型操作的Uri，使用“vnd.android.cursor.item/”
		default:
			throw new IllegalArgumentException("this is Unknown Uri:"+ uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		switch (MATCHER.match(uri)) {
		case PERSONS:
			long rowid = db.insert("person", "name", values);//主键值
			//  content://cn.itcast.provides.personprovider/person/10
			Uri insertUri = ContentUris.withAppendedId(uri, rowid);   //组拼Uri（在主机名后加上要操作数据的ID值）
			/**参数说明
             * @发生变化的数据id
             * @内容提供者监听对象（当数据改变时需要触发的对象）
             */
			//设置数据发生变化后的处理方式；
			this.getContext().getContentResolver().notifyChange(uri, null);//发出数据变化通知
			return insertUri;

		default:
			throw new IllegalArgumentException("this is Unknown Uri:"+ uri);
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		int num = 0;
		switch (MATCHER.match(uri)) {
		case PERSONS:
			num = db.delete("person", selection, selectionArgs);
			break;
		case PERSON:
			long rowid = ContentUris.parseId(uri);   //截取Uri最后的Id值
			String where = "personid="+ rowid;
			if(selection!=null && !"".equals(selection.trim())){
				where += " and "+ selection;
			}
			num = db.delete("person", where, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("this is Unknown Uri:"+ uri);
		}
		return num;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		int num = 0;
		switch (MATCHER.match(uri)) {
		case PERSONS:
			num = db.update("person", values, selection, selectionArgs);
			break;
		case PERSON:
			long rowid = ContentUris.parseId(uri);
			String where = "personid="+ rowid;
			if(selection!=null && !"".equals(selection.trim())){
				where += " and "+ selection;
			}
			num = db.update("person", values, where, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("this is Unknown Uri:"+ uri);
		}
		return num;
	}

}
