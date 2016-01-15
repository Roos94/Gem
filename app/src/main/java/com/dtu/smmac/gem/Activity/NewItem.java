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
import android.widget.TextView;
import android.widget.Toast;

import com.dtu.smmac.gem.R;

public class NewItem extends Activity {

    /*
    Skal oprette en ny reg når activiteten starter, skal skrive titel til den oprettede reg
    Så der kommer flow i regNo - Hvis to personer opretter på samme tid
    Hvis der bliver klikket på back, så skal den slette den igen
                                                                                    ^ Roos
     */

    private EditText title;
    private TextView regNo;
    private Intent i;
    private int ID;
    private String titel;
    private ProgressBar progress;

    private boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newitem);

        this.getActionBar().setTitle("    " + "Ny registrering");

        this.done = true;

        this.progress = (ProgressBar) findViewById(R.id.pronew);
        this.progress.setVisibility(View.INVISIBLE);

        this.title = (EditText) findViewById(R.id.createTitle);
        this.regNo = (TextView) findViewById(R.id.regNr);

        this.i = new Intent(this, Camera.class);

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    Splash.DB.addGenstand();
                    ID = Splash.DB.getNextID();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object resultat) {
                setRegNo(ID);
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void done(MenuItem item)
    {
        if (this.title.getText().toString() == null || this.title.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Der er ikke angivet nogen titel!", Toast.LENGTH_LONG).show();
            return;
        }

        if(this.done == true)
        {
            this.progress.setVisibility(View.VISIBLE);

            this.done = false;
            this.titel = this.title.getText().toString();

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    try {
                        Splash.DB.setTitel(ID, titel);
                        Splash.DB.setItemList();
                        Splash.DB.setItem(ID);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Object resultat)
                {
                    Main.adap.notifyDataSetChanged();

                    i.putExtra("ID", ID);
                    startActivity(i);
                    finish();
                }
            }.execute();
        }
    }


    @Override
    public void onBackPressed()
    {
        if (this.done == true) {

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    try {
                        Splash.DB.deleteGenstand(ID);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();

            finish();

        }
    }

    public void setRegNo(int no)
    {
        this.regNo.setText("" + no);
    }

}
