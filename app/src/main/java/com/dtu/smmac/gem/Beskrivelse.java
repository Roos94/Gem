package com.dtu.smmac.gem;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Beskrivelse extends Activity implements View.OnClickListener {

    Button lydfil;
    EditText beskrivelse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beskrivelse);

        this.getActionBar().setTitle("Beskrivelse");

        lydfil = (Button) findViewById(R.id.lydfil);

        lydfil.setOnClickListener(this);

        beskrivelse = (EditText) findViewById(R.id.beskrivelse);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_topbar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void done(MenuItem item)
    {

    }

    @Override
    public void onClick(View v) {

    }
}
