package com.dtu.smmac.gem;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class Emnegruppe extends Activity implements AdapterView.OnItemClickListener {

    ListView lv;
    Intent intent;
    private String emne;


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

        //lv.performItemClick(
        //        lv.getAdapter().getView(1, null, null),
        //        1,
        //        lv.getAdapter().getItemId(1));

        // lv.setSelection(4);

        // System.out.println(lv.getSelectedItemPosition());

        // lv.performClick();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void done(MenuItem item) {
        System.out.println(emne);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        emne = lv.getItemAtPosition(position).toString();
    }


}
