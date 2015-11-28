package com.dtu.smmac.gem;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NyReg extends Activity {

    private EditText title;
    private TextView regNo;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ny_reg);

        this.title = (EditText) findViewById(R.id.createTitle);
        this.regNo = (TextView) findViewById(R.id.regNr);

        this.regNo.setText("" + Splash.genstand.getNextID());

        this.i = new Intent(this, Billede.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_topbar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void done(MenuItem item)
    {
        if (title.getText().toString() == null || title.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Der er ikke angivet nogen titel!", Toast.LENGTH_LONG).show();
            return;
        }

        Splash.genstand.setGenTitle(title.getText().toString());

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    Splash.genstand.addGenstand();
                    Splash.genstand.setGenstand();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

        Startskaerm.adap.notifyDataSetChanged();

        startActivity(i);

        finish();
    }

}
