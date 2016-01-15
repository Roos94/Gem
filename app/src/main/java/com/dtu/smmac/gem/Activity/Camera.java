package com.dtu.smmac.gem.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.dtu.smmac.gem.R;

import java.io.File;

public class Camera extends Activity implements View.OnClickListener {

    private ImageView iv;
    private Button b;
    private int REQUEST_CODE = 1;
    private Bitmap bit;
    private ProgressBar progress;

    private Intent h;
    private int ID;
    private Intent lastUsed;

    private boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        this.getActionBar().setTitle("    " + "Tag billede");

        this.done = true;

        this.progress = (ProgressBar) findViewById(R.id.proCA);
        this.progress.setVisibility(View.INVISIBLE);

        b = (Button) findViewById(R.id.bbillede);
        iv = (ImageView) findViewById(R.id.iv_billede);

        b.setOnClickListener(this);

        //Sætter HS
        this.h = new Intent(this, ItemView.class);

        //Trækker fra HS
        this.lastUsed = getIntent();
        this.ID = this.lastUsed.getIntExtra("ID", 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void done(MenuItem item)
    {
        if(this.done == true) {
            this.progress.setVisibility(View.VISIBLE);

            this.done = false;

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    try {
                        //Splash.DB.postFile(Camera.this, ID, Uri.fromFile(iv), "jpg");
                        Splash.DB.setGenstandList();
                        Splash.DB.setGenstand(ID);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Object resultat)
                {
                    Main.adap.notifyDataSetChanged();
                    startHS();
                }
            }.execute();
        }
    }

    @Override
    public void onClick(View v) {
        //Uri u = ;
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //i.putExtra(MediaStore.EXTRA_OUTPUT, u);
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

                bit = (Bitmap) bundle.get("data");

                iv.setImageBitmap(bit);

            }
        }
    }

    public void startHS()
    {
        //Item skal køres over på h
        h.putExtra("ID", this.ID);

        startActivity(h);

        finish();
    }

    @Override
    public void onBackPressed()
    {
        if (this.done == true) {
            startHS();
        }
    }
}