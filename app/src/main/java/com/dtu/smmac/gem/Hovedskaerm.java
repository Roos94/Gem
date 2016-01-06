package com.dtu.smmac.gem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class Hovedskaerm extends Activity implements AdapterView.OnItemClickListener {

    private ListView liste;
    private Intent intent;
    private int ID;
    private Intent lastUsed;
    private int genstandID;
    public static HS_adapter adap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hovedskaerm);

        this.lastUsed = getIntent();
        this.ID = this.lastUsed.getIntExtra("ID", 0);

        setGenstand(this.ID);

        this.getActionBar().setTitle("    " + Splash.genstand.getGenstandList().get(this.genstandID).getTitle());

        this.liste = (ListView) findViewById(R.id.listView);

        setList();
    }

    public void setList() {
        this.adap = new HS_adapter(this, Splash.genstand.getGenstandList().get(this.genstandID));
        this.liste.setAdapter(adap);

        this.liste.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        System.out.println(position);

        switch(position){
            case 0:
                this.intent = new Intent(this, Billede.class);
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
            this.intent.putExtra("ID", this.ID);
            startActivity(this.intent);
        }

        this.intent = null;
    }

    public void setGenstand(int ID)
    {
        for (this.genstandID = 0; this.genstandID < Splash.genstand.getGenstandList().size(); this.genstandID++) {
            if (Splash.genstand.getGenstandList().get(this.genstandID).getID() == ID)
            {
                return;
            }
        }
    }

    public void done(MenuItem item)
    {
        finish();
    }

}
