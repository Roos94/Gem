package com.dtu.smmac.gem;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NyReg extends Activity implements View.OnClickListener {

    private EditText title;
    private TextView regNo;
    private Button end;
    private Button on;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ny_reg);

        this.title = (EditText) findViewById(R.id.createTitle);
        this.regNo = (TextView) findViewById(R.id.regNr);
        this.end = (Button) findViewById(R.id.b1);
        this.on = (Button) findViewById(R.id.b2);

        this.regNo.setText("" + Startskaerm.genstand.getNextID());

        this.end.setOnClickListener(this);
        this.on.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b1:
                finish();
                break;
            case R.id.b2:

                break;
        }
    }

}
