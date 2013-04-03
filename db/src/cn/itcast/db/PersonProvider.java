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
//�����ṩ��ʵ����
//****************
public class PersonProvider extends ContentProvider {
	private DBOpenHelper dbOpenHelper;
	//UriMatcher.NO_MATCH������ƥ��ʱ���ص����ݣ�����UriMatcher.NO_MATCH��ֵΪ-1����
	private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
	private static final int PERSONS = 1;
	private static final int PERSON = 2;
	//���ó�ʼ��ƥ����
	static{
		MATCHER.addURI("cn.itcast.providers.personprovider", "person", PERSONS);
		MATCHER.addURI("cn.itcast.providers.personprovider", "person/#", PERSON);
	}
	

	//���г�ʼ�������������ݿ⽨����
	@Override
	public boolean onCreate() {
		dbOpenHelper = new DBOpenHelper(this.getContext());
		return true;
	}

    //���ز�ѯ�������ݼ���
	/**
	 *uri:��������·��
	 *projection:��Ҫ��ȡ�ĵ��ֶ�
	 *selection:����
	 *selectionArgs:�����е�ռλ��
	 *sortOrder:��������
     */ 
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		//��Ԥ���趨��Uri����ƥ����
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

    //�����������ļ�����������
	@Override
	public String getType(Uri uri) {
		switch (MATCHER.match(uri)) {
		case PERSONS:
			return "vnd.android.cursor.dir/person";   //���ѶԶ����������Ͳ�����Uri��ʹ�á�vnd.android.cursor.dir/��
		case PERSON:
			return "vnd.android.cursor.item/person";   //���ѶԵ����������Ͳ�����Uri��ʹ�á�vnd.android.cursor.item/��
		default:
			throw new IllegalArgumentException("this is Unknown Uri:"+ uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		switch (MATCHER.match(uri)) {
		case PERSONS:
			long rowid = db.insert("person", "name", values);//����ֵ
			//  content://cn.itcast.provides.personprovider/person/10
			Uri insertUri = ContentUris.withAppendedId(uri, rowid);   //��ƴUri���������������Ҫ�������ݵ�IDֵ��
			/**����˵��
             * @�����仯������id
             * @�����ṩ�߼������󣨵����ݸı�ʱ��Ҫ�����Ķ���
             */
			//�������ݷ����仯��Ĵ���ʽ��
			this.getContext().getContentResolver().notifyChange(uri, null);//�������ݱ仯֪ͨ
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
			long rowid = ContentUris.parseId(uri);   //��ȡUri����Idֵ
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
