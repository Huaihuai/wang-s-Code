package cn.itcast.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//数据库创建类
public class DBOpenHelper extends SQLiteOpenHelper {

	public DBOpenHelper(Context context) {
		/**参数说明
		 * @父类构造器
		 * @需要构造的数据库名称
		 * @游标工厂（游标：迭代查询的结果集，进行访问）：传入Null代表使用系统默认的游标工厂
		 * @数据库名称的版本号（要求不能为0）,建议从1开始
		 */
		super(context, "itcast.db", null, 2);//此处当库不存在时创建数据库，否则将打开数据库，并存放于：<包>/databases/
	}

    //是在数据库每一次被创建的时候调用的
	@Override
	public void onCreate(SQLiteDatabase db) {
		//在该方法中生成数据库表
		db.execSQL("CREATE TABLE person(personid integer primary key autoincrement, name varchar(20), phone VARCHAR(12) NULL)");
	}

    //数据库文件版本号被更新的时候调用
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//在该方法中修改数据库表或结构
		db.execSQL("ALTER TABLE person ADD amount integer");
	}

}
