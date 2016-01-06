package com.dtu.smmac.gem;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class Beskrivelse extends Activity implements View.OnClickListener {

    // Warning hvis der allerede er lavet en recording

    private ImageButton record, play;
    private EditText beskrivelse;
    private int rec, pl; // Bruges til at holde styr på hvilket billede der vises
    private MediaRecorder recorder = new MediaRecorder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beskrivelse);

        this.getActionBar().setTitle("    " + "Beskrivelse");

        record = (ImageButton) findViewById(R.id.Record);
        record.setOnClickListener(this);
        record.setImageResource(R.drawable.mic);
        rec = 1;

        play = (ImageButton) findViewById(R.id.Play);
        play.setOnClickListener(this);
        play.setImageResource(R.drawable.play);
        pl = 1;

        beskrivelse = (EditText) findViewById(R.id.beskrivelse);

        beskrivelse.setText("Hej"); // Skal hentes den gemte tekst fra databasen
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

        if(v == record)  // hvis der enten skal optages eller stoppe en optagelse
        {
            // Skifter mellem de to billeder (mic og stop)
            if (rec == 1) {
                record.setImageResource(R.drawable.rcircle);
                this.rec = 2;
                // Starter lydoptagelse
            }

            else
            {
                record.setImageResource(R.drawable.mic);
                this.rec = 1;
                // Stopper lydoptagelsen
            }
        }

        else if(v == play) // hvis lydfilen skal afspilles eller stoppes
        {
            // Skifter mellem de to billeder (mic og stop)
            if (this.pl == 1)
            {
                play.setImageResource(R.drawable.rcircle);
                this.pl = 2;
                // Starter afspilningen
            }

            else
            {
                play.setImageResource(R.drawable.play);
                this.pl = 1;
                // Stopper afspilningen
            }
        }

    }

}
