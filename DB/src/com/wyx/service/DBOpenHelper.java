package com.wyx.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
	/**
	 * @param context:������
	 * @param name:ָ�����ݿ���������
	 * @param factory:�α깤�������������α���󣩣������ó�null��ʹ��ϵͳĬ�ϵ��α꡾�α꣺������ѯ��Ľ������
	 * @param version:���ݿ�汾��
	 */
	public DBOpenHelper(Context context) {
		//��һ����getReadableDatabase()����getWritableDatabase()
		//�ͻ�ͨ�����ø��࣬�������ݿ�
		super(context, "wyx.db", null, 1);    //�������ļ�λ��:<��>/databases
		// TODO Auto-generated constructor stub
	}
     
	//���ݿ��һ�α�������ʱ�򱻵��õģ��ڴ������ݿ�󣬿��������ﴴ����
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table person(personid integer primary key autoincrement, name varchar(20), phone varchar(12))");
	}

	//�����ݿ�����Ұ汾�Ÿı�󣬵��ô˺���
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("alter table person add phone varchar(12) null");
	}

}
