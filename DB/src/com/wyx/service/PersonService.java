package com.wyx.service;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wyx.domain.Person;

public class PersonService {
	private DBOpenHelper dbOpenHelper;
	private SQLiteDatabase sqLiteDatabase;
	
	public PersonService(Context context) {
		// TODO Auto-generated constructor stub
		this.dbOpenHelper = new DBOpenHelper(context);
		
		//�õ����ݿ����ʵ��(��ͬһDBOpenHelper���󣬻�õ���ͬ�����ݿ����ʵ��)
		sqLiteDatabase = dbOpenHelper.getWritableDatabase();
	}
	/**
	 * ��Ӽ�¼
	 * @param person
	 */
	public void insert(Person person) {
		/**
		 * ���������﷨��insert into [table] ([column1],[column2],...) values('[data]','[data]'));
		 */
		sqLiteDatabase.execSQL("insert into person(name,phone) values('"+ person.getName() +"','"+ person.getPhone() +"')");
		
		//�Ƽ�ʹ��ռλ����SQL��䴫��
        //sqLiteDatabase.execSQL("insert into person(name,phone) values(?,?)", new Object[]{person.getName(), person.getPhone()});
	}
	/**
	 * ɾ����¼
	 * @param person
	 */
	public void delete(int id) {
		/**
		 * ɾ�������﷨��delete from [table] where [column]=? ;
		 */
		sqLiteDatabase.execSQL("delete from person where personid = ?", new Object[]{id});
	}
	/**
	 * ���¼�¼
	 * @param person
	 */
	public void update(Person person) {
		/**
		 * ���������﷨��update [table] set [column1]=data1,[column1]=data1,... where [column]=? ;
		 */ 
		sqLiteDatabase.execSQL("update person set name = ?,phone = ? where personid=?", new Object[]{
				person.getName(),person.getPhone(),person.getId()});
	}
	/**
	 * ���Ҽ�¼
	 * @param person
	 */
	public Person search(int id) {
		/**
		 * select [column1],[column2],... from [table] where [column]= ?
		 */ 
		
		SQLiteDatabase sqLiteDatabase1 = dbOpenHelper.getReadableDatabase();   //�����ݿ���̿ռ���ʱ���޷�getWritableDatabase()
		                                                                       //�޷��õ�����ʵ��,����ʱ���ǿ���ͨ��getReadableDatabase()�õ�ʵ����ֻ�ܶ�����д����
		                                                                       //���ڴ��̿ռ�δ��������£��������ַ����õ������ݲ���ʵ����һ���ģ����ɶ���д��
		Cursor cursor = sqLiteDatabase1.rawQuery("select * from person where personid=?", new String[]{String.valueOf(id)});
		if(cursor.moveToFirst()){
			int personid = cursor.getInt(cursor.getColumnIndex("personid"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String phone = cursor.getString(cursor.getColumnIndex("phone"));
			return new Person(personid, name, phone);
		}
		return null;
	}
	/**
	 * ��ҳ���Ҽ�¼
	 * @param person
	 */
	public List<Person> getScrollData(int offset, int maxResult) {
		List<Person> personList = new ArrayList<Person>();
		SQLiteDatabase sqLiteDatabase1 = dbOpenHelper.getReadableDatabase();
		Cursor cursor = sqLiteDatabase1.rawQuery("select * from person order by personid asc limit ?,?", new String[]{String.valueOf(offset), String.valueOf(maxResult)});
		while(cursor.moveToNext()){
			int personid = cursor.getInt(cursor.getColumnIndex("personid"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String phone = cursor.getString(cursor.getColumnIndex("phone"));
			personList.add(new Person(personid, name, phone));
		}
		cursor.close();   //һ��Ҫ��ס�ر��αꣻ
		return personList;
	}
	/**
	 * ��ȡ��¼����
	 * @param person
	 */
	public long getCount() {
		SQLiteDatabase sqLiteDatabase1 = dbOpenHelper.getReadableDatabase();
		Cursor cursor = sqLiteDatabase1.rawQuery("select count(*) from person", null);
		cursor.moveToFirst();
		//ȡ������
		long result = cursor.getLong(0);
		return 1;
	}
}
