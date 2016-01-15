package com.dtu.smmac.gem.Items;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.dtu.smmac.gem.Activity.Splash;
import com.dtu.smmac.gem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Denne klasse
 *
 * Created by Roos on 23/11/15.
 */
public class DAO {

    private List<Item> item;
    private String data;
    private JSONArray json;
    private JSONObject obj;
    private final String API = "http://msondrup.dk/api/v1/items"; //"http://78.46.187.172:4019";
    private final String userID = "?userID=56837dedd2d76438906140"; //""
    private URL url;

    //Konstruktøren
    public DAO()
    {
        this.item = new ArrayList<Item>();
    }

    //Returnere listen med items
    public List<Item> getGenstandList()
    {
        return item;
    }

    //Sætter listen med items fra API'en
    public void setGenstandList() throws Exception
    {
        //Ryder item listen
        this.item.clear();

        //Sætter datastrengen fra API'en
        this.data = getUrl(this.API + this.userID);

        //Opsætter JSON Array med data fra API'en
        this.json = new JSONArray(data);

        //Looper alle items igennem i JSON Array
        for (int i = 0; i < this.json.length(); i++) {

            //Sætter JSON objekt fra JSON Array
            this.obj = this.json.getJSONObject(i);

            //Gør at den ikke viser de items, der ikke har nogen titel
            if (this.obj.optString("itemheadline").isEmpty())
            {
                //Hvis app'en kun bruges af 1 person er det muligt at slette items uden titel
                //deleteGenstand(this.obj.optInt("itemid"));
            }
            else {
                //Opretter et item inde i itemlisten
                this.item.add(new Item(
                        this.obj.optInt("itemid"),
                        this.obj.optString("itemheadline"),
                        R.drawable.ddf
                ));
            }
        }
    }


    public void setGenstand(int ID) throws JSONException
    {
        try
        {
            this.data = getUrl(this.API + "/" + ID + this.userID);
            this.obj = new JSONObject(this.data);

            int genstandID = getGenstandID(ID);

            this.item.get(genstandID).setBeskrivelse(this.obj.optString("itemdescription"));
            this.item.get(genstandID).setModtaget(this.obj.optString("itemreceived"));
            this.item.get(genstandID).setBetegnelse(this.obj.optString("betegnelse"));
            this.item.get(genstandID).setDateringFra(this.obj.optString("itemdatingfrom"));
            this.item.get(genstandID).setDateringTil(this.obj.optString("itemdatingto"));
            this.item.get(genstandID).setDonator(this.obj.optString("donator"));
            this.item.get(genstandID).setProducer(this.obj.optString("producer"));
            this.item.get(genstandID).setPostCode(this.obj.optString("postnummer"));
            this.item.get(genstandID).setEmnegruppe(this.obj.optString("emnegruppe"));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getNextID() throws Exception
    {
        int nextID = 0;

        this.data = getUrl(this.API + this.userID);
        this.json = new JSONArray(data);

        for (int i = 0; i < this.json.length(); i++) {
            this.obj = this.json.getJSONObject(i);

            System.out.println(this.obj.optInt("itemid"));

            if (this.obj.optInt("itemid") >= nextID) {
                nextID = this.obj.optInt("itemid");
            }
        }

        return nextID;
    }

    public int getGenstandID(int ID)
    {
        for (int genstandID = 0; genstandID < this.item.size(); genstandID++) {
            if (this.item.get(genstandID).getID() == ID)
            {
                return genstandID;
            }
        }

        return 0;
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

        Calendar cal = Calendar.getInstance();

        int dag = cal.get(Calendar.DAY_OF_MONTH);
        int md = cal.get(Calendar.MONTH) + 1;
        int aar = cal.get(Calendar.YEAR);

        try {
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

    public void postFile(Context c, int ID, Uri filePath, String ext)
    {
        InputStream is;
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        ByteArrayOutputStream byteBuffer = null;

        try{
            is = c.getContentResolver().openInputStream(filePath);

            byteBuffer = new ByteArrayOutputStream();

            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        try{
            URL url = new URL(this.API + "/" + ID + this.userID);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            if(ext.equals("jpg")){
                conn.setRequestProperty("Content-Type", "image/jpg");
            } else if(ext.equals("mp4")){
                conn.setRequestProperty("Content-Type", "audio/mp4");
            }
            conn.setDoInput(true);

            OutputStream os = conn.getOutputStream();
            os.write(byteBuffer.toByteArray());
            os.close();
            System.out.println(conn.getResponseCode());

        } catch(MalformedURLException | ProtocolException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }

    }

}
