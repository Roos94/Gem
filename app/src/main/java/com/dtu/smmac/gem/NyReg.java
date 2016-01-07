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
    private int ID;
    private String titel;

    private boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ny_reg);

        this.getActionBar().setTitle("    " + "Ny registrering");

        this.done = true;

        this.title = (EditText) findViewById(R.id.createTitle);
        this.regNo = (TextView) findViewById(R.id.regNr);

        this.i = new Intent(this, Hovedskaerm.class);

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    Splash.genstand.addGenstand();
                    ID = Splash.genstand.getNextID();
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
            this.done = false;
            this.titel = this.title.getText().toString();

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    try {
                        Splash.genstand.setTitel(ID, titel);
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
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    Splash.genstand.deleteGenstand(ID);
                    }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

        finish();

        return;
    }

    public void setRegNo(int no)
    {
        this.regNo.setText("" + no);
    }

}
