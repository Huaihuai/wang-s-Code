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
/**ListViewʵ��ʵ��
 * ����SimpleAdapter��SimpleCursorAdapter���Զ���������
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
		//��Ŀ����¼�
        listView.setOnItemClickListener(new ItemClickListener());
        show2();
    }
    
    private final class ItemClickListener implements OnItemClickListener{
		/**����˵��
		 * ���ڵ�ListView����
		 * ���ڵ����View����
		 * �����View��Id
		 * �Ƚ����õ����Ȳ��˽�
         */
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			ListView lView = (ListView)parent;
			/* �Զ���������
			Person person = (Person) lView.getItemAtPosition(position);
			Toast.makeText(getApplicationContext(), person.getId().toString(), 1).show();*/
			
			//����SimpleCursorAdapter���ص���Cursor���ͣ�SimpleAdapter���ص���Map
			Cursor cursor = (Cursor) lView.getItemAtPosition(position);    //�õ�ĳ��λ�õ�Cursor��¼
			int personid = cursor.getInt(cursor.getColumnIndex("_id"));    //����Cursor��¼��"_id"�е������ó���
			Toast.makeText(getApplicationContext(), personid+ "", 1).show();
		}
    }
    
    
    //�Զ���������
	private void show3() {
		List<Person> persons = personService.getScrollData(0, 20);
		PersonAdapter adapter = new PersonAdapter(this, persons, R.layout.item);
		listView.setAdapter(adapter);
	}
    //SimpleCursorAdapter������
	private void show2() {
		Cursor cursor = personService.getCursorScrollData(0, 20);
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.item, cursor,
				new String[]{"name", "phone", "amount"}, new int[]{R.id.name, R.id.phone, R.id.amount});
		listView.setAdapter(adapter);
	}
    //SimpleAdapter������
	private void show() {
		List<Person> persons = personService.getScrollData(0, 20);
		//List�е����ͱ���ΪHashMap����
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
		for(Person person : persons){
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("name", person.getName());
			item.put("phone", person.getPhone());
			item.put("amount", person.getAmount());
			item.put("id", person.getId());
			data.add(item);
		}
		/**����˵��
		 * �����Ķ���
		 * ����Դ��Map�������ͣ�
		 * ����Դ�е��ֶ�
		 * ��Ӧ����Ŀ�е�id
         */
		SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item,
				new String[]{"name", "phone", "amount"}, new int[]{R.id.name, R.id.phone, R.id.amount});
		
		listView.setAdapter(adapter);		
	}

    //ArrayAdapter�����������÷�ʽ
	adapter = new ArrayAdapter<String>(this, R.layout.listview_item, R.id.textView, data);
}