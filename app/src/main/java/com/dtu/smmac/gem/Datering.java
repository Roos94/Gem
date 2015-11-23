package com.dtu.smmac.gem;

import android.app.Activity;
import android.os.Bundle;
import android.widget.NumberPicker;



public class Datering extends Activity {


    // declare var:

    NumberPicker num1 = null;
    NumberPicker num2 = null;
    NumberPicker num3 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datering);

        // NumberPicker Dag:

        num1 = (NumberPicker) findViewById(R.id.numDag);
        num1.setMaxValue(31);
        num1.setMinValue(1);
        num1.setWrapSelectorWheel(false);

        // NumberPicker Måned:

        num2 = (NumberPicker) findViewById(R.id.numMD);
        num2.setMaxValue(12);
        num2.setMinValue(1);
        num2.setWrapSelectorWheel(false);

        // Number Picker Årstal:

        num3 = (NumberPicker) findViewById(R.id.numAar);
        num1.setMaxValue(2100);
        num1.setMinValue(1900);
        num3.setWrapSelectorWheel(false);

    }
}
