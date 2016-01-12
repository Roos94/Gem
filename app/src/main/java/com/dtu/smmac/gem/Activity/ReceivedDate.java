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

public class ReceivedDate extends Activity {

    private NumberPicker num1 = null;
    private NumberPicker num2 = null;
    private NumberPicker num3 = null;
    private ProgressBar progress;

    final Calendar cal = Calendar.getInstance();

    private int dag;
    private int md;
    private int aar;

    private Intent h;
    private int ID;
    private Intent lastUsed;
    private int genstandID;

    private String modtaget;

    private boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiveddate);

        this.getActionBar().setTitle("    " + "Modtagelsesdato");

        this.done = true;

        this.progress = (ProgressBar) findViewById(R.id.proRD);
        this.progress.setVisibility(View.INVISIBLE);

        dag = cal.get(Calendar.DAY_OF_MONTH);
        md = cal.get(Calendar.MONTH) + 1;
        aar = cal.get(Calendar.YEAR);

        //Trækker fra HS
        this.lastUsed = getIntent();
        this.ID = this.lastUsed.getIntExtra("ID", 0);

        setGenstand(this.ID);

        this.modtaget = Splash.DB.getGenstandList().get(this.genstandID).getModtaget();

        if (this.modtaget.length() == 10 && !this.modtaget.equals("0000-00-00"))
        {
            String mod[] = this.modtaget.split("-");

            this.aar = Integer.parseInt(mod[0]);
            this.md = Integer.parseInt(mod[1]);
            this.dag = Integer.parseInt(mod[2]);
        }

        //Sætter HS
        this.h = new Intent(this, ItemView.class);

        // NumberPicker Dag:

        num1 = (NumberPicker) findViewById(R.id.numpDagMD1);
        num1.setMaxValue(31);
        num1.setMinValue(1);
        num1.setWrapSelectorWheel(false);
        num1.setValue(dag);

        // NumberPicker Måned:

        num2 = (NumberPicker) findViewById(R.id.numpMDMD1);
        num2.setMaxValue(12);
        num2.setMinValue(1);
        num2.setWrapSelectorWheel(false);
        num2.setValue(md);

        // Number Picker Årstal:

        num3 = (NumberPicker) findViewById(R.id.numpAarMD1);
        num3.setMaxValue(2100);
        num3.setMinValue(1990);
        num3.setWrapSelectorWheel(false);
        num3.setValue(aar);
    }

    public String getDato()
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
                        Splash.DB.setModtaget(ID, getDato());
                        Splash.DB.setGenstandList();
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
        if (this.done == true) {
            startHS();
        }
    }

}
