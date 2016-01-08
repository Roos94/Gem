package com.dtu.smmac.gem;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class Emnegruppe extends Activity implements AdapterView.OnItemClickListener {

    private ListView lv;
    private String emne;
    private EditText emnet;

    private Intent h;
    private int ID;
    private Intent lastUsed;
    private int genstandID;

    private boolean done;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emnegruppe);

        this.getActionBar().setTitle("    " + "Emnegruppe");

        this.done = true;

        this.emnet = (EditText) findViewById(R.id.editText2);

        this.lv = (ListView) findViewById(R.id.lv);

        ArrayList emnegruppe = new ArrayList();

        emnegruppe.add("Harddisk");
        emnegruppe.add("Printer");
        emnegruppe.add("Spillemaskin");
        emnegruppe.add("Kasetter");
        emnegruppe.add("Gamle ting");
        emnegruppe.add("Fifa");
        emnegruppe.add("Playstation");
        emnegruppe.add("Xbox");
        emnegruppe.add("Computer");
        emnegruppe.add("Batteri");
        emnegruppe.add("Disk");

        this.lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, emnegruppe));

        this.lv.setOnItemClickListener(this);

        //Sætter HS
        this.h = new Intent(this, Hovedskaerm.class);

        //Trækker fra HS
        this.lastUsed = getIntent();
        this.ID = this.lastUsed.getIntExtra("ID", 0);

        setGenstand(this.ID);

        this.emne = Splash.genstand.getGenstandList().get(this.genstandID).getEmnegruppe();

        this.emnet.setText("Valgt emne: " + this.emne);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void done(MenuItem item) {
        if(this.done == true)
        {
            this.done = false;

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    try {
                        Splash.genstand.setEmnegruppe(ID, emne);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.emne = lv.getItemAtPosition(position).toString();
        this.emnet.setText("Valgt emne: " + this.emne);
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
