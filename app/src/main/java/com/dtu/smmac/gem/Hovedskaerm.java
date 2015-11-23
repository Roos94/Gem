package com.dtu.smmac.gem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Hovedskaerm extends Activity implements AdapterView.OnItemClickListener {

    ListView liste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hovedskaerm);

        liste = (ListView) findViewById(R.id.listView);

        ArrayList emne = new ArrayList();

        emne.add("Billede");
        emne.add("Emnegruppe");
        emne.add("Modtagelsesdato");
        emne.add("Betegnelse");
        emne.add("Datering");
        emne.add("Beskrivelse");
        emne.add("Referencer");
        emne.add("Tilføj Andet");

        liste.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, emne));


        liste.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


    }
}
