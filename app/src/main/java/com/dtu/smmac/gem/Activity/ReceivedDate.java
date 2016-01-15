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
import java.util.Calendar;

/*
 *   *** Created by Anders Thostrup Thomsen (S140996) and Christoffer John Svendsen (S145089) ***
 */

public class ReceivedDate extends Activity {

    // *** Creates number pickers ***
    private NumberPicker num1 = null;
    private NumberPicker num2 = null;
    private NumberPicker num3 = null;
    private ProgressBar progress;

    // *** Creates Calendar (used to get todays date) ***
    final Calendar cal = Calendar.getInstance();

    // *** Creating varibles ***
    private int day;
    private int month;
    private int year;

    private Intent h;
    private int ID;
    private Intent lastUsed;
    private int itemID;

    private String recieved;

    private boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiveddate);

        this.getActionBar().setTitle("    " + "Modtagelsesdato");

        this.done = true;

        this.progress = (ProgressBar) findViewById(R.id.proRD);
        this.progress.setVisibility(View.INVISIBLE);

        // *** Display todays date in number picker ***
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH) + 1;
        year = cal.get(Calendar.YEAR);

        // *** Pulls from mainscreen ***
        this.lastUsed = getIntent();
        this.ID = this.lastUsed.getIntExtra("ID", 0);

        this.itemID = Splash.DB.getItemID(this.ID);

        this.recieved = Splash.DB.getItemList().get(this.itemID).getModtaget();

        if (this.recieved.length() == 10 && !this.recieved.equals("0000-00-00"))
        {
            String mod[] = this.recieved.split("-");

            this.year = Integer.parseInt(mod[0]);
            this.month = Integer.parseInt(mod[1]);
            this.day = Integer.parseInt(mod[2]);
        }

        // *** Show content in mainscreen ***
        this.h = new Intent(this, ItemView.class);

        // *** NumberPicker Day: ***
        num1 = (NumberPicker) findViewById(R.id.numpDagMD1);
        num1.setMaxValue(31);
        num1.setMinValue(1);
        num1.setWrapSelectorWheel(false);
        num1.setValue(day);

        // *** NumberPicker Month: ***
        num2 = (NumberPicker) findViewById(R.id.numpMDMD1);
        num2.setMaxValue(12);
        num2.setMinValue(1);
        num2.setWrapSelectorWheel(false);
        num2.setValue(month);

        // *** Number Picker year: ***
        num3 = (NumberPicker) findViewById(R.id.numpAarMD1);
        num3.setMaxValue(2100);
        num3.setMinValue(1990);
        num3.setWrapSelectorWheel(false);
        num3.setValue(year);
    }

    public String getDate()
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
        if(this.done == true) {
            this.progress.setVisibility(View.VISIBLE);

            this.done = false;

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    try {
                        Splash.DB.setReceivedDate(ID, getDate());
                        Splash.DB.setItemList();
                        Splash.DB.setItem(ID);
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
        // *** transfer the item to mainscreen ***
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
