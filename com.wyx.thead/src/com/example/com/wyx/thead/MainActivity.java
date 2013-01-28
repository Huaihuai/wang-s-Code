package com.example.com.wyx.thead;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button staButton;
	private Button enButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        staButton = (Button)findViewById(R.id.start);
        enButton = (Button)findViewById(R.id.end);
        
        OnClickListener clickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(staButton.equals(v)){
					handler.post(updateThread);
				}else if(enButton.equals(v)) {
					 handler.removeCallbacks(updateThread);
					 
				}
			}
		};
		staButton.setOnClickListener(clickListener);
        enButton.setOnClickListener(clickListener);
    }

    Handler handler = new Handler();
    
    Runnable updateThread = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
//			System.out.println("I am updateThread!");
//			System.out.println("updateThread"); 
			Log.v("flag", "hear me?");
			handler.postDelayed(updateThread, 3000);
		}
	};
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
