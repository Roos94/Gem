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

import java.util.ArrayList;
import java.util.List;

public class Hovedskaerm extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ListView liste;
    private Button b1, b2;
    private TextView tv;
    private Intent intent;
    private int ID;
    private Intent lastUsed;
    private Genstand genstand;
    private List emne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hovedskaerm);

        this.lastUsed = getIntent();
        this.ID = this.lastUsed.getIntExtra("ID", 0);

        setGenstand(this.ID);

        this.liste = (ListView) findViewById(R.id.listView);

        emne = new ArrayList();

        emne.add("Billede");
        emne.add("Emnegruppe");
        emne.add("Modtagelsesdato");
        emne.add("Betegnelse");
        emne.add("Datering");
        emne.add("Beskrivelse");
        emne.add("Referencer");
        emne.add("Tilføj Andet");

        this.liste.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, this.emne));

        this.b1 = (Button) findViewById(R.id.beskrivelseb1);

        //this.b2 = (Button) findViewById(R.id.beskrivelseb2);

        //this.tv = (TextView) findViewById(R.id.overskriftBeskrivelse);
        this.tv.setText(genstand.getTitle());

        this.b1.setOnClickListener(this);

        this.b2.setOnClickListener(this);

        this.liste.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        System.out.println(position);

        switch(position){
            case 0:
              //  intent = new Intent(this,Billede.class);
                break;
            case 1:
                this.intent = new Intent(this, Emnegruppe.class);
                break;
            case 2:
              //  this.intent = new Intent(this,Modtagelsesdato.class);
                break;
            case 3:
                this.intent = new Intent(this,Betegnelse.class);
                break;
            case 4:
                this.intent = new Intent(this, Datering.class);
                break;
            case 5:
                this.intent = new Intent(this, Beskrivelse.class);
                break;
            case 6:
                this.intent = new Intent(this, Referencer.class);
                break;
            case 7:
             //   this.intent = new Intent(this, Andet);
                break;

        }
        if(this.intent != null) {
            startActivity(this.intent);
        }

        this.intent = null;
    }

    @Override
    public void onClick(View v) {
        if(v == this.b1)
        {
            finish();
        }
        else if(v == this.b2)
        {
            finish();
        }
    }

    public void setGenstand(int ID)
    {
        for (int j = 0; j < Startskaerm.genstand.getGenstandList().size(); j++) {
            if (Startskaerm.genstand.getGenstandList().get(j).getID() == ID)
            {
                this.genstand = Startskaerm.genstand.getGenstandList().get(j);
            }
        }
    }

}
