package com.dtu.smmac.gem;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends Activity {

    private ImageView img;
    private TextView titel, lille;
    private Intent i;
    private Thread timer;
    private Runnable r, l1, l2, l3, l4, l5;
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

        setRunable();

        this.timer = new Thread(this.r);

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

        this.timer.start();
    }

    public void setTimer() {
        SystemClock.sleep(1000);

        runOnUiThread(this.l1);
        SystemClock.sleep(400);

        runOnUiThread(this.l2);
        SystemClock.sleep(400);

        runOnUiThread(this.l3);
        SystemClock.sleep(400);

        runOnUiThread(this.l4);
        SystemClock.sleep(400);

        runOnUiThread(this.l5);
        SystemClock.sleep(400);

        startActivity(i);

        finish();
    }

    public void setRunable()
    {
        this.r = new Runnable() {
            public void run() {
                setTimer();
            }
        };

        this.l1 = new Runnable() {
            public void run() {
                img.setImageResource(R.drawable.logo1);
            }
        };

        this.l2 = new Runnable() {
            public void run() {
                img.setImageResource(R.drawable.logo2);
            }
        };

        this.l3 = new Runnable() {
            public void run() {
                img.setImageResource(R.drawable.logo3);
            }
        };

        this.l4 = new Runnable() {
            public void run() {
                img.setImageResource(R.drawable.logo4);
            }
        };

        this.l5 = new Runnable() {
            public void run() {
                img.setImageResource(R.drawable.logo5);
            }
        };
    }

}
