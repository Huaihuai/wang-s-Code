package cn.itcast.adapter;

import java.util.List;

import cn.itcast.db.R;
import cn.itcast.domain.Person;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

//�Զ�����������ʵ����������ȡ���ݹ��ܣ�
public class PersonAdapter extends BaseAdapter {
	private List<Person> persons;//�ڰ󶨵�����
	private int resource;//�󶨵���Ŀ����
	private LayoutInflater inflater;
	/**
	 * context:Activity���󣨻�ȡ����������
	 * persons:����Դ
	 * resource:��Ŀ��Item������ID
	 */
	public PersonAdapter(Context context, List<Person> persons, int resource) {
		this.persons = persons;
		this.resource = resource;
		//LayoutInflater�������������android���÷���������ʹ��XML�ļ�ת����һ�����ֶ���
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
    //��ȡ��Ŀ��Item������
	@Override
	public int getCount() {
		return persons.size();//��������
	}
    //��ȡ��Ŀ��Item������
	@Override
	public Object getItem(int position) {
		return persons.get(position);//�õ���ӦId������
	}

	@Override
	public long getItemId(int position) {
		return position;//�õ���ĿId
	}
    //ȡ����Ŀ����
	/**
	 * position:��ǰ���ص���ĿId��
	 * convertView:�����Ŀ��ʵ���еĿؼ�
	 * parent:������
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {  
		TextView nameView = null;
		TextView phoneView = null;
		TextView amountView = null;
		if(convertView==null){
			//��ȡ��XMLת�������Ĳ��ֶ���
			convertView = inflater.inflate(resource, null);//������Ŀ�������
			//��ȡ��XMLת�������Ĳ��ֶ�����ڲ��ؼ�
			nameView = (TextView) convertView.findViewById(R.id.name);
			phoneView = (TextView) convertView.findViewById(R.id.phone);
			amountView = (TextView) convertView.findViewById(R.id.amount);
			
			//���Ѿ�new�õĵ�View����ڻ����У���Ҫ�Լ������ڲ��ࣩ
			ViewCache cache = new ViewCache();
			cache.nameView = nameView;
			cache.phoneView = phoneView;
			cache.amountView = amountView;			
			convertView.setTag(cache);
		}else{
			//�ӻ����еõ��ϴ��Ѿ�new����View����
			ViewCache cache = (ViewCache) convertView.getTag();
			nameView = cache.nameView;
			phoneView = cache.phoneView;
			amountView = cache.amountView;
		}
		Person person = persons.get(position);
		//�������ʵ�����ݰ�
		nameView.setText(person.getName());
		phoneView.setText(person.getPhone());
		amountView.setText(person.getAmount().toString());
		
		return convertView;
	}
	
	//�����ڲ���
	private final class ViewCache{
		//ʹ��publicֱ�Ӹ��丳ֵ��Ϊ�˼�С���ص���������ļ��Ĵ�С���ʲ�ʹ��get,set��������
		public TextView nameView;
		public TextView phoneView;
		public TextView amountView;
	}

}
