package com.dtu.smmac.gem.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dtu.smmac.gem.Items.Item;
import com.dtu.smmac.gem.R;

/**
 * Created by Roos on 02/12/15.
 */
public class ItemView_adapter extends ArrayAdapter<Item> {

    private Item item;
    private Context context;

    public ItemView_adapter(Context c, Item item) {
        super(c, R.layout.row_itemview);
        this.item = item;
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

            row = inflater.inflate(R.layout.row_itemview, null);

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
            holder.imgView.setImageResource(item.getImage());
            holder.titleView.setText("Billede");
            holder.besView.setText("Antal billeder: 1");
        }
        else if (position == 1) {
            //holder.imgView.setImageResource();
            holder.titleView.setText("Emnegruppe");
            holder.besView.setText(this.item.getEmnegruppe());
        }
        else if (position == 2) {
            //holder.imgView.setImageResource();
            holder.titleView.setText("Modtagelsesdato");
            holder.besView.setText(this.item.getModtaget());
        }
        else if (position == 3) {
            //holder.imgView.setImageResource();
            holder.titleView.setText("Betegnelse");

            String bet = this.item.getBetegnelse();

            if (bet.length() > 97)
            {
                bet = bet.substring(0,97) + "...";
            }

            holder.besView.setText(bet);
        }
        else if (position == 4) {
            //holder.imgView.setImageResource();
            holder.titleView.setText("Datering");
            holder.besView.setText("Fra: " + this.item.getDateringFra() + "\n" + "Til: " + this.item.getDateringTil());
        }
        else if (position == 5) {
            //holder.imgView.setImageResource();
            holder.titleView.setText("Beskrivelse");

            String bes = this.item.getBeskrivelse();

            if (bes.length() > 97)
            {
                bes = bes.substring(0,97) + "...";
            }

            holder.besView.setText(bes);
        }
        else if (position == 6) {
            //holder.imgView.setImageResource();
            holder.titleView.setText("Referencer");
            holder.besView.setText("Donator: " + this.item.getDonator() + "\n" + "Producent: " + this.item.getProducer());
        }

        return row;
    }

    private static class Holder {
        public TextView titleView;
        public TextView besView;
        public ImageView imgView;
    }
}
