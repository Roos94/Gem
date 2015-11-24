package com.dtu.smmac.gem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roos on 23/11/15.
 */
public class GenstandList {

    private List<Genstand> genstand;
    private int nextID;
    private String data;
    private JSONArray json;
    private JSONObject obj;

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
        this.data = hentUrl("http://78.46.187.172:4019/items");

        this.json = new JSONArray(data);

        for (int i = 0; i < this.json.length(); i++)
        {
            this.obj = this.json.getJSONObject(i);

            this.genstand.add(i, new Genstand(this.obj.optString("itemheadline", "Titel"), this.obj.optInt("itemid", 0), R.drawable.ddf));
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

    public int getNextID()
    {
        this.nextID = 0;

        for (int j = 0; j < this.genstand.size(); j++) {
            if (this.genstand.get(j).getID() >= this.nextID) {
                this.nextID = this.genstand.get(j).getID() + 1;
            }
        }

        return this.nextID;
    }

    public void addGenstand(String titel) throws JSONException {
        this.obj = new JSONObject();

        this.obj.put("itemid", getNextID());
        this.obj.put("itemheadline", titel);

        this.json.put(this.obj);
    }

}
