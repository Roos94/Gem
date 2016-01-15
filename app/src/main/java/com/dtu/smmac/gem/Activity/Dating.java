package com.dtu.smmac.gem.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import com.dtu.smmac.gem.R;

/*
 *   *** Created by Anders Thostrup Thomsen (S140996) and Christoffer John Svendsen (S145089) ***
 */

public class Dating extends Activity {

    private NumberPicker num1 = null;
    private NumberPicker num2 = null;
    private NumberPicker num3 = null;
    private NumberPicker num4 = null;
    private NumberPicker num5 = null;
    private NumberPicker num6 = null;
    private ProgressBar progress;

    private int fromDay;
    private int fromMonth;
    private int fromYear;

    private int toDay;
    private int toMonth;
    private int toYear;

    private Intent h;
    private int ID;
    private Intent lastUsed;
    private int itemID;

    private String fra;
    private String til;

    private boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dating);

        this.getActionBar().setTitle("    " + "Datering");

        this.done = true;

        this.progress = (ProgressBar) findViewById(R.id.proD);
        this.progress.setVisibility(View.INVISIBLE);

        // *** Sets default "from"-date ***
        this.fromYear = 1957;
        this.fromMonth = 1;
        this.fromDay = 1;

        // *** Sets default "to"-date ***
        this.toYear = 1990;
        this.toMonth = 1;
        this.toDay = 1;

        // *** Makes correct mainscreen ***
        this.h = new Intent(this, ItemView.class);

        // *** Pulls from mainscreen ***
        this.lastUsed = getIntent();
        this.ID = this.lastUsed.getIntExtra("ID", 0);
        this.itemID = Splash.DB.getGenstandID(this.ID);

        this.fra = Splash.DB.getGenstandList().get(this.itemID).getDateringFra();
        this.til = Splash.DB.getGenstandList().get(this.itemID).getDateringTil();

        if (this.fra.length() == 10 && !this.fra.equals("0000-00-00"))
        {
            String f[] = this.fra.split("-");

            this.fromYear = Integer.parseInt(f[0]);
            this.fromMonth = Integer.parseInt(f[1]);
            this.fromDay = Integer.parseInt(f[2]);
        }

        if (this.til.length() == 10  && !this.til.equals("0000-00-00"))
        {
            String t[] = this.til.split("-");

            this.toYear = Integer.parseInt(t[0]);
            this.toMonth = Integer.parseInt(t[1]);
            this.toDay = Integer.parseInt(t[2]);
        }

        // *** NumberPicker "from"-day ***
        num1 = (NumberPicker) findViewById(R.id.numpDagD1);
        num1.setMaxValue(31);
        num1.setMinValue(1);
        num1.setWrapSelectorWheel(false);
        num1.setValue(this.fromDay);

        // *** NumberPicker "from"-month ***
        num2 = (NumberPicker) findViewById(R.id.numpMDD1);
        num2.setMaxValue(12);
        num2.setMinValue(1);
        num2.setWrapSelectorWheel(false);
        num2.setValue(this.fromMonth);

        // *** Number Picker "from"-year ***
        num3 = (NumberPicker) findViewById(R.id.numpAarD1);
        num3.setMaxValue(2100);
        num3.setMinValue(1940);
        num3.setWrapSelectorWheel(false);
        num3.setValue(this.fromYear);

        // *** NumberPicker "to"-day ***
        num4 = (NumberPicker) findViewById(R.id.numpDagD2);
        num4.setMaxValue(31);
        num4.setMinValue(1);
        num4.setWrapSelectorWheel(false);
        num4.setValue(this.toDay);

        // *** NumberPicker "to"-month ***
        num5 = (NumberPicker) findViewById(R.id.numpMDD2);
        num5.setMaxValue(12);
        num5.setMinValue(1);
        num5.setWrapSelectorWheel(false);
        num5.setValue(this.toMonth);

        // *** Number Picker "to"-month ***
        num6 = (NumberPicker) findViewById(R.id.numpAarD2);
        num6.setMaxValue(2100);
        num6.setMinValue(1940);
        num6.setWrapSelectorWheel(false);
        num6.setValue(this.toYear);
    }

    public String getDateTo()
    {
        return num6.getValue() + "-" + num5.getValue() + "-" + num4.getValue();
    }

    public String getDateFrom()
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
            this.progress.setVisibility(View.VISIBLE);

            this.done = false;

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    try {
                        Splash.DB.setDatering(ID, getDateFrom(), getDateTo());
                        Splash.DB.setGenstandList();
                        Splash.DB.setGenstand(ID);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Object resultat) {
                    Main.adap.notifyDataSetChanged();
                    startHS();
                }
            }.execute();
        }
    }

    public void startHS()
    {
        // *** show Item correct on mainscreen ***
        h.putExtra("ID", this.ID);

        startActivity(h);

        finish();
    }

    @Override
    public void onBackPressed()
    {
        if (this.done == true) {
            startHS();
        }
    }

}
