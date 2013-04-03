package cn.itcast.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//���ݿⴴ����
public class DBOpenHelper extends SQLiteOpenHelper {

	public DBOpenHelper(Context context) {
		/**����˵��
		 * @���๹����
		 * @��Ҫ��������ݿ�����
		 * @�α깤�����α꣺������ѯ�Ľ���������з��ʣ�������Null����ʹ��ϵͳĬ�ϵ��α깤��
		 * @���ݿ����Ƶİ汾�ţ�Ҫ����Ϊ0��,�����1��ʼ
		 */
		super(context, "itcast.db", null, 2);//�˴����ⲻ����ʱ�������ݿ⣬���򽫴����ݿ⣬������ڣ�<��>/databases/
	}

    //�������ݿ�ÿһ�α�������ʱ����õ�
	@Override
	public void onCreate(SQLiteDatabase db) {
		//�ڸ÷������������ݿ��
		db.execSQL("CREATE TABLE person(personid integer primary key autoincrement, name varchar(20), phone VARCHAR(12) NULL)");
	}

    //���ݿ��ļ��汾�ű����µ�ʱ�����
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//�ڸ÷������޸����ݿ���ṹ
		db.execSQL("ALTER TABLE person ADD amount integer");
	}

}
