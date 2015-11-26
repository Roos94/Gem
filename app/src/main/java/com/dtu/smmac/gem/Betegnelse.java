package com.dtu.smmac.gem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Betegnelse extends Activity implements View.OnClickListener {

    private Button b1, b2;
    private TextView tv;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betegnelse);

        b1 = (Button) findViewById(R.id.beskrivelseb1);

        b2 = (Button) findViewById(R.id.dateringb2);

        et = (EditText) findViewById(R.id.betegnelseET);

        tv = (TextView) findViewById(R.id.betegnelseTV);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        et.setOnClickListener(this);
        tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v == b1)
        {
            finish();
        }
        else if(v == b2)
        {
            // Send til database!
            finish();
        }
    }
}
