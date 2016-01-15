package com.dtu.smmac.gem.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dtu.smmac.gem.R;

import java.util.ArrayList;

public class Subject extends Activity implements AdapterView.OnItemClickListener {

    private ListView lv;
    private String emne;
    private TextView emnet;
    private ProgressBar progress;

    private Intent h;
    private int ID;
    private Intent lastUsed;
    private int genstandID;

    private boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        this.getActionBar().setTitle("    " + "Emnegruppe");

        this.done = true;

        this.progress = (ProgressBar) findViewById(R.id.prosub);
        this.progress.setVisibility(View.INVISIBLE);

        this.emnet = (TextView) findViewById(R.id.editText2);

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
        this.h = new Intent(this, ItemView.class);

        //Trækker fra HS
        this.lastUsed = getIntent();
        this.ID = this.lastUsed.getIntExtra("ID", 0);

        this.genstandID = Splash.DB.getItemID(this.ID);

        this.emne = Splash.DB.getItemList().get(this.genstandID).getEmnegruppe();

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
            this.progress.setVisibility(View.VISIBLE);

            this.done = false;

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    try {
                        Splash.DB.setSubject(ID, emne);
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
                    startHS();
                }
            }.execute();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        emne = lv.getItemAtPosition(position).toString();
        emnet.setText("Valgt emne: " + emne);
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
