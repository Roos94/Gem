package com.dtu.smmac.gem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.TextView;


public class Datering extends Activity {

    private NumberPicker num1 = null;
    private NumberPicker num2 = null;
    private NumberPicker num3 = null;
    private NumberPicker num4 = null;
    private NumberPicker num5 = null;
    private NumberPicker num6 = null;

    private int fdag;
    private int fmd;
    private int faar;

    private int tdag;
    private int tmd;
    private int taar;

    private Intent h;
    private int ID;
    private Intent lastUsed;
    private int genstandID;

    private String fra;
    private String til;

    private boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datering);

        this.getActionBar().setTitle("    " + "Datering");

        this.done = true;

        //Sætter default dag, måned, år
        this.faar = 1957;
        this.fmd = 1;
        this.fdag = 1;

        this.taar = 1990;
        this.tmd = 1;
        this.tdag = 1;

        //Sætter HS
        this.h = new Intent(this, Hovedskaerm.class);

        //Trækker fra HS
        this.lastUsed = getIntent();
        this.ID = this.lastUsed.getIntExtra("ID", 0);

        setGenstand(this.ID);

        this.fra = Splash.genstand.getGenstandList().get(this.genstandID).getDateringFra();
        this.til = Splash.genstand.getGenstandList().get(this.genstandID).getDateringTil();

        if (this.fra.length() == 10 && !this.fra.equals("0000-00-00"))
        {
            String f[] = this.fra.split("-");

            this.faar = Integer.parseInt(f[0]);
            this.fmd = Integer.parseInt(f[1]);
            this.fdag = Integer.parseInt(f[2]);
        }

        if (this.til.length() == 10  && !this.til.equals("0000-00-00"))
        {
            String t[] = this.til.split("-");

            this.taar = Integer.parseInt(t[0]);
            this.tmd = Integer.parseInt(t[1]);
            this.tdag = Integer.parseInt(t[2]);
        }

        // NumberPicker (fra) Dag:

        num1 = (NumberPicker) findViewById(R.id.numpDagD1);
        num1.setMaxValue(31);
        num1.setMinValue(1);
        num1.setWrapSelectorWheel(false);
        num1.setValue(this.fdag);

        // NumberPicker (fra) Måned:

        num2 = (NumberPicker) findViewById(R.id.numpMDD1);
        num2.setMaxValue(12);
        num2.setMinValue(1);
        num2.setWrapSelectorWheel(false);
        num2.setValue(this.fmd);

        // Number Picker (fra) Årstal:

        num3 = (NumberPicker) findViewById(R.id.numpAarD1);
        num3.setMaxValue(2100);
        num3.setMinValue(1940);
        num3.setWrapSelectorWheel(false);
        num3.setValue(this.faar);

        // NumberPicker (til) Dag:

        num4 = (NumberPicker) findViewById(R.id.numpDagD2);
        num4.setMaxValue(31);
        num4.setMinValue(1);
        num4.setWrapSelectorWheel(false);
        num4.setValue(this.tdag);

        // NumberPicker (til) Måned:

        num5 = (NumberPicker) findViewById(R.id.numpMDD2);
        num5.setMaxValue(12);
        num5.setMinValue(1);
        num5.setWrapSelectorWheel(false);
        num5.setValue(this.tmd);

        // Number Picker (til) Årstal:

        num6 = (NumberPicker) findViewById(R.id.numpAarD2);
        num6.setMaxValue(2100);
        num6.setMinValue(1940);
        num6.setWrapSelectorWheel(false);
        num6.setValue(this.taar);
    }

    public String getDatoTil()
    {
        return num6.getValue() + "-" + num5.getValue() + "-" + num4.getValue();
    }

    public String getDatoFra()
    {
        return num3.getValue() + "-" + num2.getValue() + "-" + num1.getValue();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void done(MenuItem item)
    {
        if (this.done == true) {
            this.done = false;

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    try {
                        Splash.genstand.setDatering(ID, getDatoFra(), getDatoTil());
                        Splash.genstand.setGenstandList();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Object resultat) {
                    Startskaerm.adap.notifyDataSetChanged();
                    startHS();
                }
            }.execute();
        }
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

    public void startHS()
    {
        //Genstand skal køres over på h
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
