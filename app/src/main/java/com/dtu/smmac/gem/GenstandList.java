package com.dtu.smmac.gem;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Roos on 23/11/15.
 */
public class GenstandList {

    private List<Genstand> genstand;

    public GenstandList()
    {
        this.genstand = new ArrayList<Genstand>();
    }

    public List<Genstand> getGenstandList()
    {
        return genstand;
    }

    public void setGenstand() throws Exception
    {
        String data = hentUrl("http://78.46.187.172:4019/items");

        JSONArray json = new JSONArray(data);

        for (int i = 0; i < json.length(); i++)
        {
            JSONObject obj = json.getJSONObject(i);

            this.genstand.add(i, new Genstand(obj.optString("itemheadline", "Titel"), obj.optInt("itemid", 0), R.drawable.ddf));
        }
    }

    public String hentUrl(String url) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        StringBuilder sb = new StringBuilder();
        String linje = br.readLine();
        while (linje != null) {
            sb.append(linje + "\n");
            linje = br.readLine();
        }
        return sb.toString();
    }

}
