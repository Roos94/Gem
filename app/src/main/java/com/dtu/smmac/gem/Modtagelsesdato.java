package com.dtu.smmac.gem;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.NumberPicker;

import java.util.Calendar;

public class Modtagelsesdato extends Activity {

    Button b2;

    private NumberPicker num1 = null;
    private NumberPicker num2 = null;
    private NumberPicker num3 = null;

    final Calendar cal = Calendar.getInstance();

    int dag;
    int md;
    int aar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modtagelsesdato);


        // b2 = findViewById(R.id.)

        dag = cal.get(Calendar.DAY_OF_MONTH);
        md = cal.get(Calendar.MONTH) + 1;
        aar = cal.get(Calendar.YEAR);

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
        return num1.getValue() + "-" + num2.getValue() + "-" + num3.getValue();
    }

}
