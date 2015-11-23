package com.dtu.smmac.gem;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Startskaerm extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener, TextWatcher {

    private Button add;
    private EditText search;
    private ListView list;
    private Intent i;
    private Intent h;
    private Adapter adap;
    public static GenstandList genstand;
    private Genstand gen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startskaerm);

        genstand = new GenstandList();

        this.add = (Button) findViewById(R.id.add);
        this.search = (EditText) findViewById(R.id.search);
        this.list = (ListView) findViewById(R.id.list);
        this.i = new Intent(this, NyReg.class);
        this.h = new Intent(this, Hovedskaerm.class);

        this.add.setOnClickListener(this);
        this.search.addTextChangedListener(this);

        setList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                openNew();
                break;
        }
    }

    public void openNew(){
        startActivity(i);
    }

    public void setList()
    {
        this.adap = new Adapter(this, this.genstand.getGenstandList());
        this.list.setAdapter(adap);
        this.list.setTextFilterEnabled(true);

        this.list.setOnItemClickListener(this);

        new AsyncTask(){
            @Override
            protected Object doInBackground(Object[] params) {
                try
                {
                    genstand.setGenstand();
                }
                catch (Exception e)
                {
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

        gen = this.genstand.getGenstandList().get(position);

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
