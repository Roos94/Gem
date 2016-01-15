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


public class Term extends Activity {

    private EditText et;

    private Intent h;
    private int ID;
    private Intent lastUsed;
    private int genstandID;
    private ProgressBar progress;

    private String bet;

    private boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);

        this.getActionBar().setTitle("    " + "Betegnelse");

        this.done = true;

        this.progress = (ProgressBar) findViewById(R.id.proET);
        this.progress.setVisibility(View.INVISIBLE);

        this.et = (EditText) findViewById(R.id.betegnelseET);

        //Sætter HS
        this.h = new Intent(this, ItemView.class);

        //Trækker fra HS
        this.lastUsed = getIntent();
        this.ID = this.lastUsed.getIntExtra("ID", 0);

        this.genstandID = Splash.DB.getGenstandID(this.ID);

        this.bet = Splash.DB.getGenstandList().get(this.genstandID).getBetegnelse();

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
            this.progress.setVisibility(View.VISIBLE);

            this.done = false;

            this.bet = this.et.getText().toString();

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    try {
                        Splash.DB.setBetegnelse(ID, bet);
                        Splash.DB.setGenstandList();
                        Splash.DB.setGenstand(ID);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Object resultat)
                {
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
