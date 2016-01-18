package com.dtu.smmac.gem.Activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dtu.smmac.gem.Adapter.Main_adapter;
import com.dtu.smmac.gem.R;

public class Main extends Activity implements AdapterView.OnItemClickListener, TextWatcher {

    private EditText search;
    private ListView list;
    private TextView t;
    private Intent i, h;
    public static Main_adapter adap;
    public static TextView txt;

    private ProgressBar progress;
    private boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.done = true;

        //Sætter titlen på item på actionbaren
        this.getActionBar().setTitle("");

        //Sætter loadingbaren
        this.progress = (ProgressBar) findViewById(R.id.proMain);
        this.progress.setVisibility(View.INVISIBLE);

        //Sætter "Intet resultat"-teksten
        this.txt = ( TextView ) findViewById(R.id.txt_Main);
        this.txt.setVisibility(View.INVISIBLE);

        //Sætter intent
        this.i = new Intent(this, NewItem.class);
        this.h = new Intent(this, ItemView.class);

        //Sætter listen
        this.list = (ListView) findViewById(R.id.list);
        setList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        View v = (View) menu.findItem(R.id.search_item).getActionView();

        //Sætter søgbaren i actionbaren
        this.search = ( EditText ) v.findViewById(R.id.txt_search);
        this.search.addTextChangedListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    //Når der klikeks på plusikonet - Åbner aktiviteten, der opretter et nyt item
    public void openNew(MenuItem item) {
        if(done == true)
        {
            this.done = false;
            startActivity(i);
            this.done = true;
        }
    }

    //Når man klikker på søgikonet
    public void search(MenuItem item)
    {
        //Sætter fokus på søgbaren
        this.search.requestFocus();
        //Viser tastaturet
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public void setList() {
        //Sætter adapteren til listen
        this.adap = new Main_adapter(this, Splash.DB.getItemList());
        this.list.setAdapter(adap);

        //Gør at man kan klikke på item på listen
        this.list.setOnItemClickListener(this);
    }

    //Når man klikker på et item
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(this.done == true)
        {
            this.done = false;

            //Viser loadingbaren
            this.progress.setVisibility(View.VISIBLE);

            //Finder id på det klikkede item
            this.t = (TextView) view.findViewById(R.id.id);
            final int ID = Integer.parseInt(this.t.getText().toString());

            //Item id køres videre på intent h - ItemView
            this.h.putExtra("ID", ID);

            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    try
                    {
                        //Henter de resterende oplysninger for et item
                        Splash.DB.setItem(ID);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Object resultat)
                {
                    //Starter ItemView
                    startActivity(h);
                    done = true;

                    //Fjerner loadingbaren
                    progress.setVisibility(View.INVISIBLE);
                }
            }.execute();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    //Når der ændres tegn i søgbaren
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        //Hvis tegnene i søgbaren er forskellig fra dem, der var der før
        if (count < before) {
            this.adap.resetData();
        }

        //Sætter filter på adapteren
        this.adap.getFilter().filter(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
