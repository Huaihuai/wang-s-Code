package com.wyx.sharePreferences;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wyx.intent.R;

public class SharepreferencesActivity extends Activity {
	private Button submit;
	private EditText ageEditText;
	private EditText nameEditText;
	private String name;
	private int age;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sharepreferences);
		
		ageEditText = (EditText)findViewById(R.id.age);
		nameEditText = (EditText)findViewById(R.id.name);
		name = nameEditText.getText().toString();
		age = Integer.valueOf(ageEditText.getText().toString());
		
		SharedPreferences sharedPreferences = this.getSharedPreferences("Test", Context.MODE_PRIVATE);
		name = sharedPreferences.getString("Age", "Wangyx");
	    age = sharedPreferences.getInt("Age", 25);
	    
	    nameEditText.setText(name);
	    ageEditText.setText(age);
	}
	
	void save(View v){
		SharedPreferences sharedPreferences = this.getSharedPreferences("Test", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putInt("Age", age);
		editor.putString("Name", name);
		editor.commit();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_sharepreferences, menu);
		return true;
	}

}
