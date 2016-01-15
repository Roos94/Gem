package com.dtu.smmac.gem.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.dtu.smmac.gem.R;

public class References extends Activity {

    private EditText don;
    private EditText pro;
    private ProgressBar progress;

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
        setContentView(R.layout.activity_references);

        this.getActionBar().setTitle("    " + "Referencer");

        this.done = true;

        this.progress = (ProgressBar) findViewById(R.id.proRef);
        this.progress.setVisibility(View.INVISIBLE);

        //Trækker fra HS
        this.lastUsed = getIntent();
        this.ID = this.lastUsed.getIntExtra("ID", 0);

        this.genstandID = Splash.DB.getGenstandID(this.ID);

        this.donator = Splash.DB.getGenstandList().get(this.genstandID).getDonator();
        this.producent = Splash.DB.getGenstandList().get(this.genstandID).getProducer();

        //Sætter HS
        this.h = new Intent(this, ItemView.class);

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
            this.progress.setVisibility(View.VISIBLE);

            this.done = false;

            this.donator = this.don.getText().toString();
            this.producent = this.pro.getText().toString();

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    try {
                        Splash.DB.setRef(ID, donator, producent);
                        Splash.DB.setGenstandList();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Object resultat) {
                    Main.adap.notifyDataSetChanged();
                    startHS();
                }
            }.execute();
        }
    }

    public void startHS()
    {
        //Item skal køres over på h
        h.putExtra("ID", this.ID);

        startActivity(h);

        finish();
    }

    @Override
    public void onBackPressed()
    {
        if (this.done == true) {
            startHS();
        }
    }
}
