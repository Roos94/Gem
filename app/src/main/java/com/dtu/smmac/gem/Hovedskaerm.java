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

import java.lang.ref.Reference;
import java.util.ArrayList;

public class Hovedskaerm extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {

    ListView liste;
    Button b1, b2;
    TextView tv;
    Intent intent = null;

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

        b1 = (Button) findViewById(R.id.b1);

        b2 = (Button) findViewById(R.id.b2);

        tv = (TextView) findViewById(R.id.textView);

        b1.setOnClickListener(this);

        b2.setOnClickListener(this);

        liste.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch(position){
            case 0:
              //  intent = new Intent(this,Billede.class);
                break;
            case 1:
              //  intent = new Intent(this,Emnegruppe.class);
                break;
            case 2:
              //  intent = new Intent(this,Modtagelsesdato.class);
                break;
            case 3:
              //  intent = new Intent(this,Betegnelse.class);
                break;
            case 4:
                intent = new Intent(this,Datering.class);
                break;
            case 5:
                intent = new Intent(this,Beskrivelse.class);
                break;
            case 6:
             //   intent = new Intent(this,Referencer.class);
                break;
            case 7:
             //   intent = new Intent(this,Andet);
                break;

        }
        startActivity(intent);


    }

    @Override
    public void onClick(View v) {
        if(v == b1)
        {
            finish();
        }
        else if(v == b2)
        {
            finish();
        }
    }
}
