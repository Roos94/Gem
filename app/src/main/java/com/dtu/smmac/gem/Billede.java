package com.dtu.smmac.gem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Billede extends Activity implements View.OnClickListener {

    private ImageView iv;
    private Button b;
    private int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billede);

        this.getActionBar().setTitle("    " + "Tag billede");

        b = (Button) findViewById(R.id.bbillede);
        iv = (ImageView) findViewById(R.id.iv_billede);

        b.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_topbar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void done(MenuItem item)
    {
        finish();
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(i.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(i, REQUEST_CODE);
        }
    }

    public void onActivityResult (int requestcode, int resultcode, Intent data)
    {
        if(requestcode == REQUEST_CODE)
        {
            if(resultcode == RESULT_OK)
            {
                Bundle bundle = new Bundle();

                bundle = data.getExtras();

                Bitmap bit;

                bit = (Bitmap) bundle.get("data");

                iv.setImageBitmap(bit);

            }
        }
    }
}