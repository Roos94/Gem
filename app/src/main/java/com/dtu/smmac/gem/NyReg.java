package com.dtu.smmac.gem;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NyReg extends Activity {

    private EditText title;
    private TextView regNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ny_reg);

        this.title = (EditText) findViewById(R.id.createTitle);
        this.regNo = (TextView) findViewById(R.id.regNr);

        this.regNo.setText("" + Splash.genstand.getNextID());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_topbar, menu);

        return super.onCreateOptionsMenu(menu);
    }

}
