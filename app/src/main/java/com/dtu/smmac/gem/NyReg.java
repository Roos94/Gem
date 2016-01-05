package com.dtu.smmac.gem;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NyReg extends Activity {

    //Skal oprette en ny reg når activiteten starter, skal skrive titel til den oprettede reg
    //Så der kommer flow i regNo - Hvis to personer opretter på samme tid
    //Hvis der bliver klikket på back, så skal den slette den igen

    private EditText title;
    private TextView regNo;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ny_reg);

        this.getActionBar().setTitle("    " + "Ny registrering");

        this.title = (EditText) findViewById(R.id.createTitle);
        this.regNo = (TextView) findViewById(R.id.regNr);

        this.regNo.setText("" + Splash.genstand.getNextID());

        this.i = new Intent(this, Billede.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_bar, menu);

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
                    Splash.genstand.setGenstandList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object resultat)
            {
                Startskaerm.adap.notifyDataSetChanged();
            }
        }.execute();

        startActivity(i);
        finish();
    }

}
