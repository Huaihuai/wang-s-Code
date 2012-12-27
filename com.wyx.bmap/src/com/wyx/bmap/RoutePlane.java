package com.wyx.bmap;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class RoutePlane extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_plane);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_route_plane, menu);
        return true;
    }
}
