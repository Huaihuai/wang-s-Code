package com.wyx.intent;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PopActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_pop, menu);
        return true;
    }
}
