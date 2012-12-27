package com.wyx.bmap;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PoiSearch extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_search);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_poi_search, menu);
        return true;
    }
}
