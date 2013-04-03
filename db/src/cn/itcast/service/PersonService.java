package cn.itcast.service;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.itcast.domain.Person;

//***********************************************************************
//使用SQL编程语言实现数据库增、删、改、查、数据分页、获取数据总量操作实例
//***********************************************************************
public class PersonService {
	private DBOpenHelper dbOpenHelper;

    //构造器
	public PersonService(Context context) {
		//通过DBHelper类获取数据库操作实例
		this.dbOpenHelper = new DBOpenHelper(context);
	}
	
	//实现事物实例
	public void payment(){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.beginTransaction();//开启事务
		try{
			db.execSQL("update person set amount=amount-10 where personid=1");
			db.execSQL("update person set amount=amount+10 where personid=2");
			db.setTransactionSuccessful();//设置事务的标志为True
		}finally{
			db.endTransaction();//结束事务,有两种情况：commit,rollback,
		//事务的提交或回滚是由事务的标志决定的,如果事务的标志为True，事务就会提交，否侧回滚,默认情况下事务的标志为False
		}
	}
	/**
	 * 添加记录
	 * @param person
	 */
	public void save(Person person){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		SQLiteDatabase db1 = dbOpenHelper.getWritableDatabase();  //以上为同一数据库操作实例
		//通过占位符进行赋值操作
		db.execSQL("insert into person(name, phone, amount) values(?,?,?)",
				new Object[]{person.getName(), person.getPhone(), person.getAmount()});
	}
	/**
	 * 删除记录
	 * @param id 记录ID
	 */
	public void delete(Integer id){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("delete from person where personid=?", new Object[]{id});
	}
	/**
	 * 更新记录
	 * @param person
	 */
	public void update(Person person){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("update person set name=?,phone=?,amount=? where personid=?",
				new Object[]{person.getName(), person.getPhone(),  person.getAmount(), person.getId()});
	}
	/**
	 * 查询记录
	 * @param id 记录ID
	 * @return
	 */
	public Person find(Integer id){
		//对于只进行读取操作，那么就调用getReadableDatabase()获取数据库读取实例
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		//获取游标
		Cursor cursor = db.rawQuery("select * from person where personid=?", new String[]{id.toString()});
		if(cursor.moveToFirst()){
			//分别获取在某个记录中的各个字段的数据
			int personid = cursor.getInt(cursor.getColumnIndex("personid"));
			int amount = cursor.getInt(cursor.getColumnIndex("amount"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String phone = cursor.getString(cursor.getColumnIndex("phone"));
			return new Person(personid, name, phone, amount);
		}
		cursor.close();
		return null;
	}
	/**
	 * 分页获取记录
	 * @param offset 跳过前面多少条记录
	 * @param maxResult 每页获取多少条记录
	 * @return
	 */
	public List<Person> getScrollData(int offset, int maxResult){
		List<Person> persons = new ArrayList<Person>();
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		//注意"limit ?,?":两个“？”分别代表从哪个位置开始查询和每次查询多少条数据
		Cursor cursor = db.rawQuery("select * from person order by personid asc limit ?,?",
				new String[]{String.valueOf(offset), String.valueOf(maxResult)});
		while(cursor.moveToNext()){
			int personid = cursor.getInt(cursor.getColumnIndex("personid"));
			int amount = cursor.getInt(cursor.getColumnIndex("amount"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String phone = cursor.getString(cursor.getColumnIndex("phone"));
			persons.add(new Person(personid, name, phone, amount));
		}
		cursor.close();
		return persons;
	}
	/**
	 * 分页获取记录
	 * @param offset 跳过前面多少条记录
	 * @param maxResult 每页获取多少条记录
	 * @return
	 */
	public Cursor getCursorScrollData(int offset, int maxResult){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select personid as _id,name,phone,amount from person order by personid asc limit ?,?",
				new String[]{String.valueOf(offset), String.valueOf(maxResult)});
		return cursor;
	}
	
	/**
	 * 获取记录总数
	 * @return
	 */
	public long getCount(){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select count(*) from person", null);
		//必须要移动到第一条
		cursor.moveToFirst();
		long result = cursor.getLong(0);
		cursor.close();
		return result;
	}
}
