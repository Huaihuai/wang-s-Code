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
		
		//得到数据库操作实例(对同一DBOpenHelper对象，会得到相同的数据库操作实例)
		sqLiteDatabase = dbOpenHelper.getWritableDatabase();
	}
	/**
	 * 添加记录
	 * @param person
	 */
	public void insert(Person person) {
		/**
		 * 插入数据语法：insert into [table] ([column1],[column2],...) values('[data]','[data]'));
		 */
		sqLiteDatabase.execSQL("insert into person(name,phone) values('"+ person.getName() +"','"+ person.getPhone() +"')");
		
		//推荐使用占位符给SQL语句传参
        //sqLiteDatabase.execSQL("insert into person(name,phone) values(?,?)", new Object[]{person.getName(), person.getPhone()});
	}
	/**
	 * 删除记录
	 * @param person
	 */
	public void delete(int id) {
		/**
		 * 删除数据语法：delete from [table] where [column]=? ;
		 */
		sqLiteDatabase.execSQL("delete from person where personid = ?", new Object[]{id});
	}
	/**
	 * 更新记录
	 * @param person
	 */
	public void update(Person person) {
		/**
		 * 更新数据语法：update [table] set [column1]=data1,[column1]=data1,... where [column]=? ;
		 */ 
		sqLiteDatabase.execSQL("update person set name = ?,phone = ? where personid=?", new Object[]{
				person.getName(),person.getPhone(),person.getId()});
	}
	/**
	 * 查找记录
	 * @param person
	 */
	public Person search(int id) {
		/**
		 * select [column1],[column2],... from [table] where [column]= ?
		 */ 
		
		SQLiteDatabase sqLiteDatabase1 = dbOpenHelper.getReadableDatabase();   //当数据库磁盘空间满时，无法getWritableDatabase()
		                                                                       //无法得到操作实例,但此时还是可以通过getReadableDatabase()得到实例（只能读不能写）；
		                                                                       //但在磁盘空间未满的情况下，以上两种方法得到的数据操作实例是一样的，都可读可写。
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
	 * 分页查找记录
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
		cursor.close();   //一定要记住关闭游标；
		return personList;
	}
	/**
	 * 获取记录条数
	 * @param person
	 */
	public long getCount() {
		SQLiteDatabase sqLiteDatabase1 = dbOpenHelper.getReadableDatabase();
		Cursor cursor = sqLiteDatabase1.rawQuery("select count(*) from person", null);
		cursor.moveToFirst();
		//取得行数
		long result = cursor.getLong(0);
		return 1;
	}
}
