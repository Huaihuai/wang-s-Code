package cn.itcast.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.itcast.adapter.PersonAdapter;
import cn.itcast.domain.Person;
import cn.itcast.service.PersonService;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
/**ListView实现实例
 * 包括SimpleAdapter，SimpleCursorAdapter，自定义适配器
 */
public class MainActivity extends Activity {
    private ListView listView;
    private PersonService personService;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        personService = new PersonService(this);
        
        listView = (ListView) this.findViewById(R.id.listView);
		//条目点击事件
        listView.setOnItemClickListener(new ItemClickListener());
        show2();
    }
    
    private final class ItemClickListener implements OnItemClickListener{
		/**参数说明
		 * 所在的ListView对象
		 * 所在点击的View对象
		 * 所点击View的Id
		 * 比较少用到，先不了解
         */
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			ListView lView = (ListView)parent;
			/* 自定义适配器
			Person person = (Person) lView.getItemAtPosition(position);
			Toast.makeText(getApplicationContext(), person.getId().toString(), 1).show();*/
			
			//对于SimpleCursorAdapter返回的是Cursor类型，SimpleAdapter返回的是Map
			Cursor cursor = (Cursor) lView.getItemAtPosition(position);
			int personid = cursor.getInt(cursor.getColumnIndex("_id"));
			Toast.makeText(getApplicationContext(), personid+ "", 1).show();
		}
    }
    
    
    //自定义适配器
	private void show3() {
		List<Person> persons = personService.getScrollData(0, 20);
		PersonAdapter adapter = new PersonAdapter(this, persons, R.layout.item);
		listView.setAdapter(adapter);
	}
    //SimpleCursorAdapter适配器
	private void show2() {
		Cursor cursor = personService.getCursorScrollData(0, 20);
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.item, cursor,
				new String[]{"name", "phone", "amount"}, new int[]{R.id.name, R.id.phone, R.id.amount});
		listView.setAdapter(adapter);
	}
    //SimpleAdapter适配器
	private void show() {
		List<Person> persons = personService.getScrollData(0, 20);
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
		for(Person person : persons){
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("name", person.getName());
			item.put("phone", person.getPhone());
			item.put("amount", person.getAmount());
			item.put("id", person.getId());
			data.add(item);
		}
		/**参数说明
		 * 上下文对象
		 * 数据源（Map数据类型）
		 * 数据源中的字段
		 * 对应到条目中的id
         */
		SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item,
				new String[]{"name", "phone", "amount"}, new int[]{R.id.name, R.id.phone, R.id.amount});
		
		listView.setAdapter(adapter);		
	}
}