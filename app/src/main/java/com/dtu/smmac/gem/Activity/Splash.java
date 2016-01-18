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

        //Sætter intent - Åbner Main klassen
        this.i = new Intent(this, Main.class);

        //Sætter logoet
        this.img = (ImageView) findViewById(R.id.imageSplash);
        this.img.setImageResource(R.drawable.logo);

        //Sætter app navnet
        this.titel = (TextView) findViewById(R.id.textSplah);
        this.titel.setText(R.string.app_name);

        //Sætter "Made by"-teksten
        this.lille = (TextView) findViewById(R.id.lilleSplash);
        this.lille.setText("Made by: SMMAC");

        //Chekker om telefonen er på nettet
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
        //Opretter DAO'en
        this.DB = new DAO();

        //Sætter runable
        setRunable();

        //Sætter en ny tråd
        this.timer = new Thread(this.r);

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    //Henter item i DAO
                    DB.setItemList();

                    //Starter timeren
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

    //Bruges til at se om der er netværk
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
