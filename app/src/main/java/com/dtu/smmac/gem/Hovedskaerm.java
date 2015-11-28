package com.dtu.smmac.gem;

import android.app.ActionBar;
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

import java.util.ArrayList;
import java.util.List;

public class Hovedskaerm extends Activity implements AdapterView.OnItemClickListener {

    private ListView liste;
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

        this.getActionBar().setTitle(genstand.getTitle());

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

        this.liste.setOnItemClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_topbar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        System.out.println(position);

        switch(position){
            case 0:
                intent = new Intent(this,Billede.class);
                break;
            case 1:
                this.intent = new Intent(this, Emnegruppe.class);
                break;
            case 2:
                this.intent = new Intent(this, Modtagelsesdato.class);
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

    public void setGenstand(int ID)
    {
        for (int j = 0; j < Splash.genstand.getGenstandList().size(); j++) {
            if (Splash.genstand.getGenstandList().get(j).getID() == ID)
            {
                this.genstand = Splash.genstand.getGenstandList().get(j);
                return;
            }
        }
    }

    public void done(MenuItem item)
    {

    }

}
