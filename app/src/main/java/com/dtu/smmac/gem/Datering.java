package com.dtu.smmac.gem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;



public class Datering extends Activity implements View.OnClickListener {

    Button b1, b2;

    // declare var:

    private NumberPicker num1 = null;
    private NumberPicker num2 = null;
    private NumberPicker num3 = null;
    private NumberPicker num4 = null;
    private NumberPicker num5 = null;
    private NumberPicker num6 = null;

    private String datoTil;
    private String datoFra;

    private int fraDag;
    private int fraMD;
    private int fraAar;
    private int tilDag;
    private int tilMD;
    private int tilAar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        // NumberPicker (fra) Måned:

        num2 = (NumberPicker) findViewById(R.id.numpMDD1);
        num2.setMaxValue(12);
        num2.setMinValue(1);
        num2.setWrapSelectorWheel(false);

        // Number Picker (fra) Årstal:

        num3 = (NumberPicker) findViewById(R.id.numpAarD1);
        num3.setMaxValue(2100);
        num3.setMinValue(1900);
        num3.setWrapSelectorWheel(false);

        // NumberPicker (til) Dag:

        num4 = (NumberPicker) findViewById(R.id.numpDagD2);
        num4.setMaxValue(31);
        num4.setMinValue(1);
        num4.setWrapSelectorWheel(false);

        // NumberPicker (til) Måned:

        num5 = (NumberPicker) findViewById(R.id.numpMDD2);
        num5.setMaxValue(12);
        num5.setMinValue(1);
        num5.setWrapSelectorWheel(false);

        // Number Picker (til) Årstal:

        num6 = (NumberPicker) findViewById(R.id.numpAarD2);
        num6.setMaxValue(2100);
        num6.setMinValue(1900);
        num6.setWrapSelectorWheel(false);
    }


    @Override
    public void onClick(View v)
    {
        fraDag = num1.getValue();
        fraMD = num2.getValue();
        fraAar = num3.getValue();
        tilDag = num4.getValue();
        tilMD = num5.getValue();
        tilAar = num6.getValue();

        datoFra = fraDag + "-" + fraMD + "-" + fraAar;
        datoTil = tilDag + "-" + tilMD + "-" + tilAar;

        System.out.println(datoTil);
        System.out.println(datoFra);
    }


}
