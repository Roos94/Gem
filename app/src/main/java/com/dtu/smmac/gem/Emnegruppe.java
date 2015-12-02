package com.dtu.smmac.gem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Emnegruppe extends Activity implements AdapterView.OnItemClickListener {

    ListView lv;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emnegruppe);

        this.getActionBar().setTitle("    " + "Emnegruppe");

        lv = (ListView) findViewById(R.id.lv);

        ArrayList emnegruppe = new ArrayList();

        emnegruppe.add("Harddisk");
        emnegruppe.add("Printer");
        emnegruppe.add("Spillemaskin");
        emnegruppe.add("Kasetter");
        emnegruppe.add("Gamle ting");
        emnegruppe.add("Fifa");
        emnegruppe.add("Playstation");
        emnegruppe.add("Tilføj Andet");

        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, emnegruppe));

        lv.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_topbar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void done(MenuItem item)
    {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "Valgt emnegruppe: " + lv.getItemAtPosition(position), Toast.LENGTH_LONG).show();
    }
}
