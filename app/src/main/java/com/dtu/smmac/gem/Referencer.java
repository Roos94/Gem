package com.dtu.smmac.gem;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class Referencer extends Activity {

    private EditText don;
    private EditText pro;

    private Intent h;
    private int ID;
    private Intent lastUsed;
    private int genstandID;

    private String donator;
    private String producent;

    private boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referencer);

        this.getActionBar().setTitle("    " + "Referencer");

        this.done = true;

        //Trækker fra HS
        this.lastUsed = getIntent();
        this.ID = this.lastUsed.getIntExtra("ID", 0);

        setGenstand(this.ID);

        this.donator = Splash.genstand.getGenstandList().get(this.genstandID).getDonator();
        this.producent = Splash.genstand.getGenstandList().get(this.genstandID).getProducer();

        //Sætter HS
        this.h = new Intent(this, Hovedskaerm.class);

        // Sætter edit text
        this.don = (EditText) findViewById(R.id.et1);
        this.don.setText(this.donator);
        this.don.setSelection(this.don.getText().length());

        this.pro = (EditText) findViewById(R.id.et2);
        this.pro.setText(this.producent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void done(MenuItem item)
    {
        if(this.done == true) {
            this.done = false;

            this.donator = this.don.getText().toString();
            this.producent = this.pro.getText().toString();

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    try {
                        Splash.genstand.setRef(ID, donator, producent);
                        Splash.genstand.setGenstandList();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Object resultat) {
                    Startskaerm.adap.notifyDataSetChanged();
                    startHS();
                }
            }.execute();
        }
    }

    public void setGenstand(int ID)
    {
        for (this.genstandID = 0; this.genstandID < Splash.genstand.getGenstandList().size(); this.genstandID++) {
            if (Splash.genstand.getGenstandList().get(this.genstandID).getID() == ID)
            {
                return;
            }
        }
    }

    public void startHS()
    {
        //Genstand skal køres over på h
        h.putExtra("ID", this.ID);

        startActivity(h);

        finish();
    }

    @Override
    public void onBackPressed()
    {
        startHS();
    }
}
