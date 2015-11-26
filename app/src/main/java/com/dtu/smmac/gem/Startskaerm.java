package com.dtu.smmac.gem;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Startskaerm extends Activity implements AdapterView.OnItemClickListener, TextWatcher {

    private EditText search;
    private ListView list;
    private TextView t;
    private Intent i, h;
    private Adapter adap;
    public static GenstandList genstand;
    private Genstand gen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startskaerm);

        genstand = new GenstandList();

        this.list = (ListView) findViewById(R.id.list);
        this.i = new Intent(this, NyReg.class);
        this.h = new Intent(this, Hovedskaerm.class);

        setList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_startskaerm, menu);

        View v = (View) menu.findItem(R.id.search_item).getActionView();

        this.search = ( EditText ) v.findViewById(R.id.txt_search);
        this.search.addTextChangedListener(this);

        getActionBar().setDisplayShowTitleEnabled(false);

        return super.onCreateOptionsMenu(menu);
    }

    public void openNew(MenuItem item) {
        startActivity(i);
    }

    public void setList() {
        this.adap = new Adapter(this, this.genstand.getGenstandList());
        this.list.setAdapter(adap);
        this.list.setTextFilterEnabled(true);

        this.list.setOnItemClickListener(this);

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    genstand.setGenstand();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                adap.notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        this.t = (TextView) view.findViewById(R.id.id);

        for (int j = 0; j < this.genstand.getGenstandList().size(); j++) {
            if (this.genstand.getGenstandList().get(j).getIDtoString().equals(t.getText().toString()))
            {
                this.gen = this.genstand.getGenstandList().get(j);
            }
        }

        //Genstand skal køres over på h
        h.putExtra("ID", gen.getID());

        startActivity(h);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (count < before) {
            this.adap.resetData();
        }

        this.adap.getFilter().filter(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


}
