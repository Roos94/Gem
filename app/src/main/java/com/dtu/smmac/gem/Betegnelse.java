package com.dtu.smmac.gem;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class Betegnelse extends Activity {

    private EditText et;

    private Intent h;
    private int ID;
    private Intent lastUsed;
    private int genstandID;

    private String bet;

    private boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betegnelse);

        this.getActionBar().setTitle("    " + "Betegnelse");

        this.done = true;

        this.et = (EditText) findViewById(R.id.betegnelseET);

        //Sætter HS
        this.h = new Intent(this, Hovedskaerm.class);

        //Trækker fra HS
        this.lastUsed = getIntent();
        this.ID = this.lastUsed.getIntExtra("ID", 0);

        setGenstand(this.ID);

        this.bet = Splash.genstand.getGenstandList().get(this.genstandID).getBetegnelse();

        this.et.setText(this.bet);

        //Sætter cursor ved slutningen af teksten
        this.et.setSelection(this.et.getText().length());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_bar, menu);

        return super.onCreateOptionsMenu(menu);

    }

    public void done(MenuItem item)
    {
        if(this.done == true)
        {
            this.done = false;

            this.bet = this.et.getText().toString();

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    try {
                        Splash.genstand.setBetegnelse(ID, bet);
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
