package com.dtu.smmac.gem;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Beskrivelse extends Activity {

    Button lydfil;
    EditText beskrivelse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beskrivelse);

        lydfil = (Button) findViewById(R.id.lydfil);

        beskrivelse = (EditText) findViewById(R.id.beskrivelse);
    }
}
