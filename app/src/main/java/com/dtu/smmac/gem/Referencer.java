package com.dtu.smmac.gem;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Referencer extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referencer);

        this.getActionBar().setTitle("    " + "Referencer");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_topbar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void done(MenuItem item)
    {

    }
}
