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

        this.lastUsed = getIntent();
        this.ID = this.lastUsed.getIntExtra("ID", 0);
        this.genstandID = Splash.DB.getGenstandID(this.ID);

        this.getActionBar().setTitle("    " + Splash.DB.getItemList().get(this.genstandID).getTitle());

        this.liste = (ListView) findViewById(R.id.listView);

        setList();
    }

    public void setList() {
        this.adap = new ItemView_adapter(this, Splash.DB.getItemList().get(this.genstandID));
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
        switch(position){
            case 0:
                this.intent = new Intent(this, Camera.class);
                break;
            case 1:
                this.intent = new Intent(this, Subject.class);
                break;
            case 2:
                this.intent = new Intent(this, ReceivedDate.class);
                break;
            case 3:
                this.intent = new Intent(this,Term.class);
                break;
            case 4:
                this.intent = new Intent(this, Dating.class);
                break;
            case 5:
                this.intent = new Intent(this, Description.class);
                break;
            case 6:
                this.intent = new Intent(this, References.class);
                break;
            case 7:
             //   this.intent = new Intent(this, Andet);
                break;

        }
        if(this.intent != null) {
            this.intent.putExtra("ID", this.ID);
            startActivity(this.intent);
            finish();
        }

        this.intent = null;
    }

    public void done(MenuItem item)
    {
        finish();
    }

}
