package com.dtu.smmac.gem.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dtu.smmac.gem.Adapter.ItemView_adapter;
import com.dtu.smmac.gem.R;

public class ItemView extends Activity implements AdapterView.OnItemClickListener {

    private ListView liste;
    private Intent intent;
    private int ID;
    private Intent lastUsed;
    private int genstandID;
    public static ItemView_adapter adap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemview);

        //Henter den sidste brugte intent
        this.lastUsed = getIntent();

        //Sætter item id, fra den sidste brugte intent
        this.ID = this.lastUsed.getIntExtra("ID", 0);

        //Sætter item id, som den har i Array listen
        this.genstandID = Splash.DB.getItemID(this.ID);

        //Sætter titlen på actionbaren
        this.getActionBar().setTitle("    " + Splash.DB.getItemList().get(this.genstandID).getTitle());

        //Sætter listen
        this.liste = (ListView) findViewById(R.id.listView);
        setList();
    }

    public void setList() {
        //Sætter adapteren til listen
        this.adap = new ItemView_adapter(this, Splash.DB.getItemList().get(this.genstandID));
        this.liste.setAdapter(adap);

        //Gør at man kan klikke på item på listen
        this.liste.setOnItemClickListener(this);
    }

    //Sætter actionbaren
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_topbar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //Når man klikker på et element
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(position){
            //Kamera
            case 0:
                this.intent = new Intent(this, Camera.class);
                break;

            //Emnegruppe
            case 1:
                this.intent = new Intent(this, Subject.class);
                break;

            //Modtagelsesdato
            case 2:
                this.intent = new Intent(this, ReceivedDate.class);
                break;

            //Betegnelse
            case 3:
                this.intent = new Intent(this,Term.class);
                break;

            //Datering
            case 4:
                this.intent = new Intent(this, Dating.class);
                break;

            //Beskrivelse
            case 5:
                this.intent = new Intent(this, Description.class);
                break;

            //Referencer
            case 6:
                this.intent = new Intent(this, References.class);
                break;

            //Hvis man skal tilføje andet!?
            case 7:
             //   this.intent = new Intent(this, Andet);
                break;

        }

        //Tjekke om intentet er sat til noget
        if(this.intent != null) {

            //Item id køres videre på intent
            this.intent.putExtra("ID", this.ID);
            startActivity(this.intent);
            finish();
        }

        //Nulstiller intent
        this.intent = null;
    }

    //Det der sker når man klikker done
    public void done(MenuItem item)
    {
        finish();
    }

}
