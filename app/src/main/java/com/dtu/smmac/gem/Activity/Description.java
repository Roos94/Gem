package com.dtu.smmac.gem.Activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.dtu.smmac.gem.R;

import java.io.File;
import java.io.IOException;

public class Description extends Activity implements View.OnClickListener {

    // Warning hvis der allerede er lavet en recording

    private ImageButton record, play;
    private EditText beskrivelse;
    private int rec, pl; // Bruges til at holde styr på hvilket billede der vises
    private MediaRecorder recorder;
    private MediaPlayer player;
    private String OUTPUT_FILE;

    private Intent h;
    private int ID;
    private Intent lastUsed;
    private int genstandID;

    private String bes;

    private boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beskrivelse);

        this.getActionBar().setTitle("    " + "Beskrivelse");

        this.done = true;

        record = (ImageButton) findViewById(R.id.Record);
        record.setOnClickListener(this);
        record.setImageResource(R.drawable.mic);
        rec = 1;
/*
*/
        beskrivelse = (EditText) findViewById(R.id.beskrivelse);

        OUTPUT_FILE = Environment.getExternalStorageDirectory() + "/audiorecorder.3gpp";

        //Sætter HS
        this.h = new Intent(this, ItemView.class);

        //Trækker fra HS
        this.lastUsed = getIntent();
        this.ID = this.lastUsed.getIntExtra("ID", 0);

        setGenstand(this.ID);

        this.bes = Splash.DB.getGenstandList().get(this.genstandID).getBeskrivelse();

        this.beskrivelse.setText(this.bes);

        //Sætter cursor ved slutningen af teksten
        this.beskrivelse.setSelection(this.beskrivelse.getText().length());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void done(MenuItem item)
    {
        if(this.done == true)
        {
            this.done = false;

            this.bes = beskrivelse.getText().toString();

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    try {
                        Splash.DB.setBeskrivelse(ID, bes);
                        Splash.DB.setGenstandList();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Object resultat)
                {
                    Main.adap.notifyDataSetChanged();
                    startHS();
                }
            }.execute();
        }
    }

    @Override
    public void onClick(View v) {

        if(v == record)  // hvis der enten skal optages eller stoppe en optagelse
        {
            // Skifter mellem de to billeder (mic og stop)
            if (rec == 1) {
                record.setImageResource(R.drawable.rcircle);

                try {
                    beginRecording();
                }catch (Exception e){
                    e.printStackTrace();
                }

                this.rec = 2;
                // Starter lydoptagelse
            }

            else
            {
                record.setImageResource(R.drawable.mic);
                play = (ImageButton) findViewById(R.id.Play);
                play.setOnClickListener(this);
                play.setImageResource(R.drawable.play);
                pl = 1;

                try {
                    stopRecording();
                }catch (Exception e){
                    e.printStackTrace();
                }

                this.rec = 1;
                // Stopper lydoptagelsen
            }
        }

        else if(v == play) // hvis lydfilen skal afspilles eller stoppes
        {
            // Skifter mellem de to billeder (mic og stop)
            if (this.pl == 1) {
                play.setImageResource(R.drawable.stop);
                this.pl = 2;

                try {
                    playRecording();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            else {
                play.setImageResource(R.drawable.play);

                try {
                    stopPlayback();
                }catch (Exception e){
                    e.printStackTrace();
                }

                this.pl = 1;
                // Stopper afspilningen
            }
        }

    }

    private void beginRecording() throws IOException {
        ditchRecord();
        File outFile = new File(OUTPUT_FILE);

        if(outFile.exists())
            outFile.delete();

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
        recorder.setOutputFile(OUTPUT_FILE);
        recorder.prepare();
        recorder.start();

    }

    private void stopRecording() {
        if(recorder != null)
            recorder.stop();
    }

    private void playRecording() throws IOException {
        ditchPlayer();

        player = new MediaPlayer();
        player.setDataSource(OUTPUT_FILE);
        player.prepare();
        player.start();

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play.setImageResource(R.drawable.play);
                pl = 1;
            }
        });

    }

    private void stopPlayback() {
        if(player != null)
            player.stop();
    }

    private void ditchRecord() {
        if(recorder != null)
            recorder.release();
    }

    private void ditchPlayer() {
        if(player != null)
        {
            try {
                player.release();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void setGenstand(int ID)
    {
        for (this.genstandID = 0; this.genstandID < Splash.DB.getGenstandList().size(); this.genstandID++) {
            if (Splash.DB.getGenstandList().get(this.genstandID).getID() == ID)
            {
                return;
            }
        }
    }

    public void startHS()
    {
        //Item skal køres over på h
        h.putExtra("ID", this.ID);

        startActivity(h);

        finish();
    }

    @Override
    public void onBackPressed()
    {
        startHS();
    }

}
