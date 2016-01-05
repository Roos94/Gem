package com.dtu.smmac.gem;

import android.content.Context;
import android.os.SystemClock;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private final String API = "http://msondrup.dk/api/v1/items"; //http://78.46.187.172:4019";
    private final String userID = "?userID=56837dedd2d76438906140";
    private String genTitle;
    private DateFormat formatter;

    public GenstandList()
    {
        this.genstand = new ArrayList<Genstand>();

        this.formatter = new SimpleDateFormat("yyyy-MM-dd");
    }

    public List<Genstand> getGenstandList()
    {
        return genstand;
    }

    public void setGenstand() throws Exception
    {
        this.genstand.clear();

        this.data = getUrl(this.API + this.userID);
        this.json = new JSONArray(data);

        //Mangler stadig 100%

        //for (int i = 0; i < this.json.length(); i++) {

        //    this.data = getUrl(this.API + "/" + i + this.userID);

        //    this.data = this.data.substring(1, this.data.length() - 1);

        //}

        for (int i = 0; i < this.json.length(); i++)
        {
            this.obj = this.json.getJSONObject(i);

            this.genstand.add(i, new Genstand(this.obj.optString("itemheadline", "Titel"), this.obj.optInt("itemid", 0), R.drawable.ddf, this.obj.optString("itemdescription", "")));
        }
    }

    public String getUrl(String url) throws IOException {
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

    public void addGenstand() throws JSONException {
        String requestUrl = this.API + "/items?itemheadline=" + this.genTitle;

        InputStream is = null;
        String response = "";

        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);

            conn.connect();
            int responseCode = conn.getResponseCode();
            is = conn.getInputStream();

            response = readIt(is, 2000);
        }
        catch (Exception e){
            response = e.getMessage();
        }
        finally {
            if(is != null){
                try {
                    is.close();
                    setGenTitle(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    public void setGenTitle(String title)
    {
        this.genTitle = title;
    }

}
