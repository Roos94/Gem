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
    private ProgressBar progress;

    private boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newitem);

        this.done = true;

        //Sætter actionbar-teksten
        this.getActionBar().setTitle("    " + "Ny registrering");

        //Sætter loadingbaren
        this.progress = (ProgressBar) findViewById(R.id.pronew);
        this.progress.setVisibility(View.INVISIBLE);

        //Sætter steder, hvor man skal skrive titel
        this.title = (EditText) findViewById(R.id.createTitle);

        //Sætter intent - Camera klassen
        this.i = new Intent(this, Camera.class);

        //Sætter id
        this.regNo = (TextView) findViewById(R.id.regNr);
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    //Opretter en tom genstand
                    Splash.DB.addItem();

                    //Finder id på den tomme genstand
                    ID = Splash.DB.getNextID();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object resultat) {
                //Sætter id på aktiviteten
                setRegNo(ID);
            }
        }.execute();
    }

    //Sætter actionbaren
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_topbar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //Når man klikker på done
    public void done(MenuItem item)
    {
        //Hvis titel feltet er tomt
        if (this.title.getText().toString() == null || this.title.getText().toString().isEmpty())
        {
            //Alert
            Toast.makeText(this, "Der er ikke angivet nogen titel!", Toast.LENGTH_LONG).show();

            //Afslutter metoden
            return;
        }

        if(this.done == true)
        {
            this.done = false;

            //Viser loadingbaren
            this.progress.setVisibility(View.VISIBLE);

            //Finder titlen, man har indtastet
            final String titel = this.title.getText().toString();

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    try {
                        //Sætter titlen på det tomme item
                        Splash.DB.setTitle(ID, titel);

                        //Opdaterer item listen
                        Splash.DB.setItemList();

                        //Henter de resterende oplysninger om item (modtagelsesdato bliver sat i det tomme item)
                        Splash.DB.setItem(ID);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Object resultat)
                {
                    //Notificerer adapteren på Main
                    Main.adap.notifyDataSetChanged();

                    //Item id køres videre på intent h - Camera
                    i.putExtra("ID", ID);
                    startActivity(i);
                    finish();
                }
            }.execute();
        }
    }

    //Det der sker, når man klikker på tilbage knappen på telefonen
    @Override
    public void onBackPressed()
    {
        if (this.done == true) {

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    try {
                        //Sletter den tomme genstand
                        Splash.DB.deleteItem(ID);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();

            finish();

        }
    }

    //Sætter id på aktiviteten
    public void setRegNo(int no)
    {
        this.regNo.setText("" + no);
    }

}
