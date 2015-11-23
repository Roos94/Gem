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
    private List<Genstand> genstand;
    private Adapter adap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startskaerm);

        this.add = (Button) findViewById(R.id.add);
        this.search = (EditText) findViewById(R.id.search);
        this.list = (ListView) findViewById(R.id.list);
        this.i = new Intent(this, NyReg.class);
        this.genstand = new ArrayList<Genstand>();

        this.add.setOnClickListener(this);
        this.search.addTextChangedListener(this);

        setList();

        new AsyncTask()
        {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    setGenstand_bg();
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
        this.adap = new Adapter(this, this.genstand);
        this.list.setAdapter(adap);
        this.list.setTextFilterEnabled(true);

        this.list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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

    public static String hentUrl(String url) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        StringBuilder sb = new StringBuilder();
        String linje = br.readLine();
        while (linje != null) {
            sb.append(linje + "\n");
            linje = br.readLine();
        }
        return sb.toString();
    }

    public void setGenstand_bg() throws Exception
    {
        String data = hentUrl("http://78.46.187.172:4019/items");

        JSONArray json = new JSONArray(data);

        for (int i = 0; i < json.length(); i++)
        {
            JSONObject obj = json.getJSONObject(i);

            System.out.println("ihead = " + obj.optString("itemheadline", "Titel"));

            this.genstand.add(i, new Genstand(obj.optString("itemheadline", "Titel"), obj.optInt("itemid", 0), R.drawable.ddf));
        }
    }

}
