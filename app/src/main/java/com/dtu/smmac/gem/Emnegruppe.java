package com.dtu.smmac.gem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Emnegruppe extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    ListView lv;
    Button b1, b2;
    TextView tv;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emnegruppe);

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

        b1 = (Button) findViewById(R.id.dateringb1);

        b2 = (Button) findViewById(R.id.dateringb2);

        tv = (TextView) findViewById(R.id.textView);


        b1.setOnClickListener(this);

        b2.setOnClickListener(this);

        lv.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == b1)
        {
            finish();
        }
        else if(v == b2)
        {
            // Her skal der gemmes til databasen!
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "Valgt emnegruppe: " + lv.getItemAtPosition(position), Toast.LENGTH_LONG).show();
    }
}
