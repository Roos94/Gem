package com.dtu.smmac.gem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Roos on 02/12/15.
 */
public class HS_adapter extends ArrayAdapter<Genstand> {

    private Genstand genstand;
    private Context context;

    public HS_adapter(Context c, Genstand genstand) {
        super(c, R.layout.hs_row);
        this.genstand = genstand;
        this.context = c;
    }

    public int getCount() {
        return 7;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        Holder holder = new Holder();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(R.layout.hs_row, null);

            ImageView img = (ImageView) row.findViewById(R.id.image);
            TextView title = (TextView) row.findViewById(R.id.title);
            TextView bes = (TextView) row.findViewById(R.id.bes);

            holder.titleView = title;
            holder.besView = bes;
            holder.imgView = img;

            row.setTag(holder);
        }
        else
        {
            holder = (Holder) row.getTag();
        }

        if (position == 0) {
            holder.imgView.setImageResource(genstand.getImage());
            holder.titleView.setText("Billede");
            holder.besView.setText("Antal billeder: ");
        }
        else if (position == 1) {
            //holder.imgView.setImageResource();
            holder.titleView.setText("Emnegruppe");
            holder.besView.setText("");
        }
        else if (position == 2) {
            //holder.imgView.setImageResource();
            holder.titleView.setText("Modtagelsesdato");
            holder.besView.setText("");
        }
        else if (position == 3) {
            //holder.imgView.setImageResource();
            holder.titleView.setText("Betegnelse");
            holder.besView.setText("");
        }
        else if (position == 4) {
            //holder.imgView.setImageResource();
            holder.titleView.setText("Datering");
            holder.besView.setText("Fra: " + "-" + " Til: " + "-");
        }
        else if (position == 5) {
            //holder.imgView.setImageResource();
            holder.titleView.setText("Beskrivelse");
            holder.besView.setText("");
        }
        else if (position == 6) {
            //holder.imgView.setImageResource();
            holder.titleView.setText("Referencer");
            holder.besView.setText("Donator: " + "-" + " Producent: " + "-");
        }

        return row;
    }

    private static class Holder {
        public TextView titleView;
        public TextView besView;
        public ImageView imgView;
    }
}