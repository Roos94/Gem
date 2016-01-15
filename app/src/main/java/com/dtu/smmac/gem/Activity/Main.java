package com.dtu.smmac.gem.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dtu.smmac.gem.Adapter.Main_adapter;
import com.dtu.smmac.gem.Items.Item;
import com.dtu.smmac.gem.R;

public class Main extends Activity implements AdapterView.OnItemClickListener, TextWatcher {

    private EditText search;
    private ListView list;
    private TextView t;
    private Intent i, h;
    public static Main_adapter adap;

    private ProgressBar progress;
    private boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getActionBar().setTitle("");

        this.progress = (ProgressBar) findViewById(R.id.proMain);
        this.progress.setVisibility(View.INVISIBLE);

        this.done = true;

        this.list = (ListView) findViewById(R.id.list);
        this.i = new Intent(this, NewItem.class);
        this.h = new Intent(this, ItemView.class);

        setList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_startskaerm, menu);

        View v = (View) menu.findItem(R.id.search_item).getActionView();

        this.search = ( EditText ) v.findViewById(R.id.txt_search);
        this.search.addTextChangedListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    public void openNew(MenuItem item) {
        if(done == true)
        {
            this.done = false;
            startActivity(i);
            this.done = true;
        }
    }

    public void setList() {
        this.adap = new Main_adapter(this, Splash.DB.getGenstandList());
        this.list.setAdapter(adap);
        this.list.setTextFilterEnabled(true);

        this.list.setOnItemClickListener(this);
        this.progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(this.done == true)
        {
            this.done = false;

            this.progress.setVisibility(View.VISIBLE);
            this.list.setOnItemClickListener(null);

            this.t = (TextView) view.findViewById(R.id.id);

            final int ID = Integer.parseInt(this.t.getText().toString());

            //Item skal køres over på h
            h.putExtra("ID", ID);

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    try
                    {
                        Splash.DB.setGenstand(ID);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Object resultat)
                {
                    startActivity(h);
                    done = true;
                    setList();
                }
            }.execute();
        }
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
