package com.dtu.smmac.gem;

import java.util.Date;

/**
 * Created by Roooooooos on 09/11/15.
 */
public class Genstand {
// test test test
    private String title;
    private int ID;
    private int image;
    private String beskrivelse;
    private String modtaget;
    private String dateringFra;
    private String dateringTil;
    private String donator;
    private String producer;
    private String postCode;
    private String emnegruppe;
    private String betegnelse;

    public Genstand(int itemId,
                    String itemHeadline,
                    String itemDescription,
                    String itemRecieved,
                    String itemDatingFrom,
                    String itemDatingTo,
                    String donator,
                    String producer,
                    String postalCode,
                    String emnegruppe,
                    String betegnelse,
                    int image)
    {
        this.ID = itemId;
        this.title = itemHeadline;
        this.beskrivelse = itemDescription;
        this.modtaget = itemRecieved;
        this.dateringFra = itemDatingFrom;
        this.dateringTil = itemDatingTo;
        this.donator = donator;
        this.producer = producer;
        this.postCode = postalCode;
        this.emnegruppe = emnegruppe;
        this.betegnelse = betegnelse;
        this.image = image;
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

    public String getModtaget() { return this.modtaget; }

    public String getDateringFra() {return this.dateringFra;}

    public String getDateringTil() {return this.dateringTil;}

    public String getDonator() {return this.donator;}

    public String getProducer() {return this.producer;}

    public String getPostCode() {return this.postCode;}

    public String getEmnegruppe() {return this.emnegruppe;}

    public String getBetegnelse() {return this.betegnelse;}
}
