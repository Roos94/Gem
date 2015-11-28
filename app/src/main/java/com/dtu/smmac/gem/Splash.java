package com.dtu.smmac.gem;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends Activity {

    private ImageView img;
    private TextView titel, lille;
    private Intent i;
    private Thread timer;
    public static GenstandList genstand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.i = new Intent(this, Startskaerm.class);

        this.img = (ImageView) findViewById(R.id.imageSplash);
        this.img.setImageResource(R.drawable.logo);

        this.titel = (TextView) findViewById(R.id.textSplah);
        this.titel.setText("GEM");

        this.lille = (TextView) findViewById(R.id.lilleSplash);
        this.lille.setText("Made by: SMMAC");

        this.genstand = new GenstandList();

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    genstand.setGenstand();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

        this.timer = new Thread() {
            public void run() {
                try {
                    sleep(2500);

                    startActivity(i);

                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        this.timer.start();
    }


}
