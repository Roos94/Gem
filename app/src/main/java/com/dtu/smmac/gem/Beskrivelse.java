package com.dtu.smmac.gem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Beskrivelse extends Activity implements View.OnClickListener {

    Button lydfil, b1, b2;
    EditText beskrivelse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beskrivelse);

        lydfil = (Button) findViewById(R.id.lydfil);

        b1 = (Button) findViewById(R.id.dateringb1);

        b2 = (Button) findViewById(R.id.dateringb2);

        lydfil.setOnClickListener(this);

        b1.setOnClickListener(this);

        b2.setOnClickListener(this);

        beskrivelse = (EditText) findViewById(R.id.beskrivelse);

    }

    @Override
    public void onClick(View v) {

    }
}
