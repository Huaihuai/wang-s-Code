package com.wyx.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
	/**
	 * @param context:上下文
	 * @param name:指定数据库生成名称
	 * @param factory:游标工厂（用来产生游标对象），若设置成null：使用系统默认的游标【游标：迭代查询后的结果集】
	 * @param version:数据库版本号
	 */
	public DBOpenHelper(Context context) {
		//第一调用getReadableDatabase()或是getWritableDatabase()
		//就会通过调用父类，创建数据库
		super(context, "wyx.db", null, 1);    //创建的文件位于:<包>/databases
		// TODO Auto-generated constructor stub
	}
     
	//数据库第一次被创建的时候被调用的（在创建数据库后，可以在这里创建表）
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table person(personid integer primary key autoincrement, name varchar(20), phone varchar(12))");
	}

	//当数据库存在且版本号改变后，调用此函数
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("alter table person add phone varchar(12) null");
	}

}
