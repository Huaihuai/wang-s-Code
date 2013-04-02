package cn.itcast.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//创建数据库及需要用到的表，用来存放断点记录
public class DBOpenHelper extends SQLiteOpenHelper {
	private static final String DBNAME = "itcast.db";
	private static final int VERSION = 1;
	
	public DBOpenHelper(Context context) {
		super(context, DBNAME, null, VERSION);
	}
	//表的字段：下载数据路径；记录线程ID；记录已经下载的数据量
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog (id integer primary key autoincrement, downpath varchar(100), threadid INTEGER, downlength INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS filedownlog");
		onCreate(db);
	}

}
