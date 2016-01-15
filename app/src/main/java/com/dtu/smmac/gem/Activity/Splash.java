package com.dtu.smmac.gem.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.SystemClock;
import android.widget.ImageView;
import android.widget.TextView;

import com.dtu.smmac.gem.Items.DAO;
import com.dtu.smmac.gem.R;

public class Splash extends Activity {

    private ImageView img;
    private TextView titel, lille;
    private Intent i;
    private Thread timer;
    private Runnable r, l1, l2, l3, l4, l5;
    public static DAO DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.i = new Intent(this, Main.class);

        this.img = (ImageView) findViewById(R.id.imageSplash);
        this.img.setImageResource(R.drawable.logo);

        this.titel = (TextView) findViewById(R.id.textSplah);
        this.titel.setText(R.string.app_name);

        this.lille = (TextView) findViewById(R.id.lilleSplash);
        this.lille.setText("Made by: SMMAC");

        if(isNetworkAvailable() == true) {
            setInfo();
        }
        else
        {
            this.titel.setText("Der er ingen internetforbindelse, og du kan derfor ikke benytte denne applikation lige nu.");
        }


    }

    public void setInfo()
    {
        this.DB = new DAO();

        setRunable();

        this.timer = new Thread(this.r);

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    DB.setGenstandList();
                    timer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
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
        SystemClock.sleep(300);

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

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
