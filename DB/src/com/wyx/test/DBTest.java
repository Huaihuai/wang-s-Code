package com.wyx.test;

import java.util.List;

import com.wyx.domain.Person;
import com.wyx.service.DBOpenHelper;
import com.wyx.service.PersonService;

import android.test.AndroidTestCase;
import android.util.Log;

public class DBTest extends AndroidTestCase {
	private final static String TAG = "DBTestCase";
	public void testCreateDB() throws Exception{
		DBOpenHelper dbOpenHelper = new DBOpenHelper(getContext());
		dbOpenHelper.getReadableDatabase();
		dbOpenHelper.getWritableDatabase();
	}

	public void testInsert() throws Exception {
		PersonService personService = new PersonService(this.getContext());
		Person person = new Person("wyx", "123");
		personService.insert(person);
	}

	public void testDelete() throws Exception {
		PersonService service = new PersonService(this.getContext());
		service.delete(21);
	}

	public void testUpdate() throws Exception {
		PersonService service = new PersonService(this.getContext());
		Person person = service.search(1);
		person.setName("zhangxiaoxiao");
		service.update(person);
	}

	public void testSearch() throws Exception {
		PersonService service = new PersonService(this.getContext());
		Person person = service.search(1);
		Log.i(TAG, person.toString());
	}

	public void testGetScrollData() throws Exception {
		PersonService service = new PersonService(this.getContext());
		List<Person> persons = service.getScrollData(0, 5);
		for(Person person : persons){
			Log.i(TAG, person.toString());
		}
	}

	public void testGetCount() throws Exception {
		PersonService service = new PersonService(this.getContext());
		long result = service.getCount();
		Log.i(TAG, result+"");
	}
}
