package com.dtu.smmac.gem;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class Beskrivelse extends Activity implements View.OnClickListener {

    private ImageButton record;
    private EditText beskrivelse;
    private int nr; // Bruges til at holde styr på hvilket billede der vises

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beskrivelse);

        this.getActionBar().setTitle("    " + "Beskrivelse");

        record = (ImageButton) findViewById(R.id.Record);
        record.setOnClickListener(this);

        beskrivelse = (EditText) findViewById(R.id.beskrivelse);

        beskrivelse.setText("Hej"); // Skal hentes den gemte tekst fra databasen

        record.setImageResource(R.drawable.mic);
        nr = 1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void done(MenuItem item)
    {
        System.out.print(beskrivelse.getText()); // skal gemmes i databasen
        finish();
    }

    @Override
    public void onClick(View v) {

        // Skifter mellem de to billeder (mic og stop)
        if(nr == 1)
        {
            record.setImageResource(R.drawable.rcircle);
            this.nr = 2;
            // Starter lydoptagelse
        }
        else
        {
            record.setImageResource(R.drawable.mic);
            this.nr = 1;
            // Stopper lydoptagelsen
        }

    }

}
