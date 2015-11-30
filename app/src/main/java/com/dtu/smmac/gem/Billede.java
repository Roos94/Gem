package com.dtu.smmac.gem;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;


public class Billede extends Activity {

    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabHost = (TabHost) findViewById(R.id.tabHost);


        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("Billede");

        tabSpec.setContent(R.id.billede);
        tabSpec.setIndicator("Billede");
        tabHost.addTab(tabSpec);


        tabHost.newTabSpec("Galleri");

        tabSpec.setContent(R.id.galleri);
        tabSpec.setIndicator("Galleri");
        tabHost.addTab(tabSpec);

    }

}