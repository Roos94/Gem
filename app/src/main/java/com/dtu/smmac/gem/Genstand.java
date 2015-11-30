package com.dtu.smmac.gem;

/**
 * Created by Roooooooos on 09/11/15.
 */
public class Genstand {
// test test test
    private String title;
    private int ID;
    private int image;
    private String beskrivelse;

    public Genstand(String title, int ID, int image, String beskrivelse)
    {
        this.title = title;
        this.ID = ID;
        this.image = image;
        this.beskrivelse = beskrivelse;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getIDtoString()
    {
        return Integer.toString(this.ID);
    }

    public int getID()
    {
        return this.ID;
    }

    public int getImage()
    {
        return this.image;
    }

    public String getBeskrivelse()
    {
        return this.beskrivelse;
    }

}
