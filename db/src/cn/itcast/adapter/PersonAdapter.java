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

//自定义适配器（实现适配器读取数据功能）
public class PersonAdapter extends BaseAdapter {
	private List<Person> persons;//在绑定的数据
	private int resource;//绑定的条目界面
	private LayoutInflater inflater;
	/**
	 * context:Activity对象（获取布局填充服务）
	 * persons:数据源
	 * resource:条目（Item）布局ID
	 */
	public PersonAdapter(Context context, List<Person> persons, int resource) {
		this.persons = persons;
		this.resource = resource;
		//LayoutInflater：布局填充器，android内置服务，作用是使用XML文件转换成一个布局对象
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
    //获取条目（Item）数量
	@Override
	public int getCount() {
		return persons.size();//数据总数
	}
    //获取条目（Item）对象
	@Override
	public Object getItem(int position) {
		return persons.get(position);//得到对应Id的数据
	}

	@Override
	public long getItemId(int position) {
		return position;//得到条目Id
	}
    //取得条目界面
	/**
	 * position:当前加载的条目Id，
	 * convertView:存放条目中实例中的控件
	 * parent:父容器
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {  
		TextView nameView = null;
		TextView phoneView = null;
		TextView amountView = null;
		if(convertView==null){
			//获取由XML转换过来的布局对象
			convertView = inflater.inflate(resource, null);//生成条目界面对象
			//获取由XML转换过来的布局对象的内部控件
			nameView = (TextView) convertView.findViewById(R.id.name);
			phoneView = (TextView) convertView.findViewById(R.id.phone);
			amountView = (TextView) convertView.findViewById(R.id.amount);
			
			//将已经new好的的View存放在缓存中（需要自己定义内部类）
			ViewCache cache = new ViewCache();
			cache.nameView = nameView;
			cache.phoneView = phoneView;
			cache.amountView = amountView;			
			convertView.setTag(cache);
		}else{
			//从缓存中得到上次已经new过的View数据
			ViewCache cache = (ViewCache) convertView.getTag();
			nameView = cache.nameView;
			phoneView = cache.phoneView;
			amountView = cache.amountView;
		}
		Person person = persons.get(position);
		//下面代码实现数据绑定
		nameView.setText(person.getName());
		phoneView.setText(person.getPhone());
		amountView.setText(person.getAmount().toString());
		
		return convertView;
	}
	
	//定义内部类
	private final class ViewCache{
		//使用public直接给其赋值（为了减小加载到虚拟机中文件的大小，故不使用get,set方法。）
		public TextView nameView;
		public TextView phoneView;
		public TextView amountView;
	}

}
