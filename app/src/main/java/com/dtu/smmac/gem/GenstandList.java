package com.dtu.smmac.gem;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Roos on 23/11/15.
 */
public class GenstandList {

    private List<Genstand> genstand;
    private int nextID;
    private String data;
    private JSONArray json;
    private JSONObject obj, fobj;
    private final String API = "http://msondrup.dk/api/v1/items"; //"http://78.46.187.172:4019";
    private final String userID = "?userID=56837dedd2d76438906140"; //""
    private URL url;
    private int dag;
    private int md;
    private int aar;
    private final Calendar cal = Calendar.getInstance();

    public GenstandList()
    {
        this.genstand = new ArrayList<Genstand>();
    }

    public List<Genstand> getGenstandList()
    {
        return genstand;
    }

    public void setGenstandList() throws Exception
    {
        this.genstand.clear();

        this.data = getUrl(this.API + this.userID);
        this.json = new JSONArray(data);

        int j = 0;
        for (int i = 0; i < this.json.length(); i++) {
            this.fobj = this.json.getJSONObject(i);
            j = j + setGenstand(j, this.fobj.optInt("itemid"));
        }
    }

    public int getNextID() throws Exception
    {
        this.nextID = 0;

        this.data = getUrl(this.API + this.userID);
        this.json = new JSONArray(data);

        for (int i = 0; i < this.json.length(); i++) {
            this.fobj = this.json.getJSONObject(i);

            System.out.println(this.fobj.optInt("itemid"));

            if (this.fobj.optInt("itemid") >= this.nextID) {
                this.nextID = this.fobj.optInt("itemid");
            }
        }

        return this.nextID;
    }

    public int setGenstand(int listID, int ID) throws JSONException
    {
        try
        {
            this.data = getUrl(this.API + "/" + ID + this.userID);
            this.obj = new JSONObject(this.data);

            System.out.println(this.data);

            //Gør at den ikke viser de items, der ikke har nogen titel?
            if (this.obj.optString("itemheadline").isEmpty())
            {
                System.out.println("Sletter item med ID: " + this.obj.optInt("itemid"));

                //Det er et spørgsmål, om den skal slette de items, der ikke har nogen titel?
                //deleteGenstand(this.obj.optInt("itemid"));
                return 0;
            }
            else
            {
                this.genstand.add(listID, new Genstand(
                        this.obj.optInt("itemid"),
                        this.obj.optString("itemheadline"),
                        this.obj.optString("itemdescription"),
                        this.obj.optString("itemreceived"),
                        this.obj.optString("itemdatingfrom"),
                        this.obj.optString("itemdatingto"),
                        this.obj.optString("donator"),
                        this.obj.optString("producer"),
                        this.obj.optString("postnummer"),
                        this.obj.optString("emnegruppe"),
                        this.obj.optString("betegnelse"),
                        R.drawable.ddf
                ));
                return 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 1;
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

    public void addGenstand() throws IOException {
        this.obj = new JSONObject();

        dag = cal.get(Calendar.DAY_OF_MONTH);
        md = cal.get(Calendar.MONTH) + 1;
        aar = cal.get(Calendar.YEAR);

        try {
            //this.obj.put("itemid", "" + Splash.genstand.getNextID());
            this.obj.put("itemheadline", "");
            this.obj.put("itemdescription", "");
            this.obj.put("itemreceived", aar + "-" + md + "-" + dag);
            this.obj.put("itemdatingfrom", "");
            this.obj.put("itemdatingto", "");
            this.obj.put("donator", "");
            this.obj.put("producer", "");
            this.obj.put("postalCode", "");
            this.obj.put("emnegruppe", "");
            this.obj.put("betegnelse", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.url = new URL(this.API + this.userID);

        postGenstand(this.obj, this.url);
    }

    public void setTitel(int ID, String title) throws IOException
    {
        this.obj = new JSONObject();

        try {
            this.obj.put("itemheadline", title);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.url = new URL(this.API + "/" + ID + this.userID);

        postGenstand(this.obj, this.url);
    }

    public void setModtaget(int ID, String modtaget) throws IOException
    {
        this.obj = new JSONObject();

        try {
            this.obj.put("itemreceived", modtaget);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.url = new URL(this.API + "/" + ID + this.userID);

        postGenstand(this.obj, this.url);
    }

    public void setDatering(int ID, String fra, String til) throws IOException
    {
        this.obj = new JSONObject();

        try {
            this.obj.put("itemdatingfrom", fra);
            this.obj.put("itemdatingto", til);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.url = new URL(this.API + "/" + ID + this.userID);

        postGenstand(this.obj, this.url);
    }

    public void setBeskrivelse(int ID, String beskrivelse) throws IOException
    {
        this.obj = new JSONObject();

        try {
            this.obj.put("itemdescription", beskrivelse);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.url = new URL(this.API + "/" + ID + this.userID);

        postGenstand(this.obj, this.url);
    }

    public void setBetegnelse(int ID, String betegnelse) throws IOException
    {
        this.obj = new JSONObject();

        try {
            this.obj.put("betegnelse", betegnelse);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.url = new URL(this.API + "/" + ID + this.userID);

        postGenstand(this.obj, this.url);
    }

    public void setEmnegruppe(int ID, String emne) throws IOException
    {
        this.obj = new JSONObject();

        try {
            this.obj.put("emnegruppe", emne);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.url = new URL(this.API + "/" + ID + this.userID);

        postGenstand(this.obj, this.url);
    }

    public void setRef(int ID, String donator, String producent) throws IOException
    {
        this.obj = new JSONObject();

        try {
            this.obj.put("donator", donator);
            this.obj.put("producer", producent);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.url = new URL(this.API + "/" + ID + this.userID);

        postGenstand(this.obj, this.url);
    }

    public void postGenstand(JSONObject ob, URL url) throws IOException {
        InputStream is = null;

        try
        {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoInput(true);

            String requestBody = ob.toString();
            byte[] outputBytes = requestBody.getBytes();
            OutputStream os = conn.getOutputStream();
            os.write(outputBytes);
            os.close();

            is = conn.getInputStream();

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void deleteGenstand(int ID) throws IOException
    {
        this.url = new URL(this.API + "/" + ID + this.userID);

        InputStream is = null;

        try
        {
        HttpURLConnection conn = (HttpURLConnection) this.url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("DELETE");
        conn.setDoInput(true);

        is = conn.getInputStream();

        } catch (Exception e) {
        e.printStackTrace();
        }
    }

}
