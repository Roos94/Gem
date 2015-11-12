package com.dtu.smmac.gem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Startskaerm extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener, TextWatcher {

    private Button add;
    private EditText search;
    private ListView list;
    private Intent i;
    private List<Genstand> emne;
    private Adapter adap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startskaerm);

        this.add = (Button) findViewById(R.id.add);
        this.search = (EditText) findViewById(R.id.search);
        this.list = (ListView) findViewById(R.id.list);
        this.i = new Intent(this, NyReg.class);
        this.emne = new ArrayList<Genstand>();

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

    public void setList() {

        // Mangler et hent fra en DB eller lign
        for (int i = 0; i <= 10; i++)
            {
            this.emne.add(i, new Genstand("Titel", i, R.drawable.ddf));
        }
        // ^

        this.adap = new Adapter(this, this.emne);
        this.list.setAdapter(adap);
        this.list.setTextFilterEnabled(true);

        this.list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "Valgt ID er " + this.emne.get(position).getID(), Toast.LENGTH_SHORT).show();
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
