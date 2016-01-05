package com.dtu.smmac.gem;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class Betegnelse extends Activity {

    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betegnelse);

        this.getActionBar().setTitle("    " + "Betegnelse");

        et = (EditText) findViewById(R.id.betegnelseET);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void done(MenuItem item)
    {
        finish();
    }
}
