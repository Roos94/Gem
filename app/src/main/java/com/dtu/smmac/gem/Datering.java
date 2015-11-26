package com.dtu.smmac.gem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;



public class Datering extends Activity implements View.OnClickListener {

    private Button b1, b2;

    private NumberPicker num1 = null;
    private NumberPicker num2 = null;
    private NumberPicker num3 = null;
    private NumberPicker num4 = null;
    private NumberPicker num5 = null;
    private NumberPicker num6 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datering);

        b2 = (Button) findViewById(R.id.dateringb2);
        b2.setOnClickListener(this);
        b2.setText(">");

        // NumberPicker (fra) Dag:

        num1 = (NumberPicker) findViewById(R.id.numpDagD1);
        num1.setMaxValue(31);
        num1.setMinValue(1);
        num1.setWrapSelectorWheel(false);
        num1.setValue(1);

        // NumberPicker (fra) Måned:

        num2 = (NumberPicker) findViewById(R.id.numpMDD1);
        num2.setMaxValue(12);
        num2.setMinValue(1);
        num2.setWrapSelectorWheel(false);
        num2.setValue(1);

        // Number Picker (fra) Årstal:

        num3 = (NumberPicker) findViewById(R.id.numpAarD1);
        num3.setMaxValue(2100);
        num3.setMinValue(1900);
        num3.setWrapSelectorWheel(false);
        num3.setValue(1957);

        // NumberPicker (til) Dag:

        num4 = (NumberPicker) findViewById(R.id.numpDagD2);
        num4.setMaxValue(31);
        num4.setMinValue(1);
        num4.setWrapSelectorWheel(false);
        num4.setValue(1);

        // NumberPicker (til) Måned:

        num5 = (NumberPicker) findViewById(R.id.numpMDD2);
        num5.setMaxValue(12);
        num5.setMinValue(1);
        num5.setWrapSelectorWheel(false);
        num5.setValue(1);

        // Number Picker (til) Årstal:

        num6 = (NumberPicker) findViewById(R.id.numpAarD2);
        num6.setMaxValue(2100);
        num6.setMinValue(1900);
        num6.setWrapSelectorWheel(false);
        num6.setValue(1957);
    }

    public String getDatoTil()
    {
        return num4.getValue() + "-" + num5.getValue() + "-" + num6.getValue();
    }

    public String getDatoFra()
    {
        return num1.getValue() + "-" + num2.getValue() + "-" + num3.getValue();
    }

    @Override
    public void onClick(View v)
    {


    }



}
