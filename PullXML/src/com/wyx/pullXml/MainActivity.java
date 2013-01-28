package com.wyx.pullXml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.wyx.domain.Student;


/**
 * @author wangyx
 * 解析及生成XML文件实例
 */
public class MainActivity extends Activity {
	private TextView stuTextView;
	private String contentString;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		stuTextView = (TextView) findViewById(R.id.tip);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		//读取XML文件
		List<Student> list=new PullMXL().getPullXML();
		for(Student student:list){
			contentString += "id:"+student.getId()+"\tgroup:"+student.getGroup()+"\tname:"+student.getName()+
					"\tsex:"+student.getSex()+"\tage:"+student.getAge()+"\temail:"+student.getEmail()+"\tbirthday:"+
					student.getBirthday()+"\tmemo:"+student.getMemo()+'\n';
		}
		stuTextView.setText(contentString);
		
		//写入XML文件中
		File xmlFile = new File(getFilesDir(), "saveXml.xml");
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(xmlFile);
			new PullMXL().setPullXML(list, fileOutputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
