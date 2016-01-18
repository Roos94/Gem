package com.dtu.smmac.gem.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.dtu.smmac.gem.Activity.Main;
import com.dtu.smmac.gem.Items.Item;
import com.dtu.smmac.gem.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roos on 11/01/16.
 */
public class Main_adapter extends ArrayAdapter<Item> implements Filterable {

    private Context context;
    private List<Item> item;
    private Filter itemFilter;
    private List<Item> orgItem;

    //Konstruktøren
    public Main_adapter(Context c, List<Item> item) {
        super(c, R.layout.row_main, item);
        this.context = c;
        this.item = item;
        this.orgItem = item;
    }

    //Returnerer antallet af rækker i listen
    public int getCount() {
        return this.item.size();
    }

    //Sætter view'et for hver række i listen
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        ItemHolder holder = new ItemHolder();

        //Hvis der ikke eksisterer minimum en række
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(R.layout.row_main, null);

            ImageView img = (ImageView) row.findViewById(R.id.image);
            TextView title = (TextView) row.findViewById(R.id.title);
            TextView id = (TextView) row.findViewById(R.id.id);

            holder.titleView = title;
            holder.idView = id;
            holder.imgView = img;

            row.setTag(holder);
        }
        else
        {
            holder = (ItemHolder) row.getTag();
        }

        Item n = this.item.get(position);

        //Sætter billede på item
        holder.imgView.setImageResource(n.getImage());

        //Sætter title på item
        holder.titleView.setText(n.getTitle());

        //Sætter id på item
        holder.idView.setText(n.getIDtoString());

        return row;
    }

    //Klassen der håndterer hver række
    private static class ItemHolder {
        public TextView titleView;
        public TextView idView;
        public ImageView imgView;
    }

    //Resetter filteret til den oprindelige itemliste
    public void resetData() {
        this.item = this.orgItem;
    }

    //Sætter filteret
    @Override
    public Filter getFilter() {
        if (this.itemFilter == null)
        {
            this.itemFilter = new ItemFilter();
        }

        return itemFilter;
    }

    //Filter klassen
    private class ItemFilter extends Filter {

        //Sætter filteret i forhold til, hvad der skrives som filter
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {

                results.values = orgItem;
                results.count = orgItem.size();
            }
            else {
                List<Item> nItem = new ArrayList<Item>();

                for (Item n : item)
                {
                    if (n.getTitle().toUpperCase().contains(constraint.toString().toUpperCase()) || n.getIDtoString().toUpperCase().contains(constraint.toString().toUpperCase()))
                        nItem.add(n);
                }

                results.values = nItem;
                results.count = nItem.size();
            }
            return results;
        }

        //Notificerer listen på Main
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            item = (List<Item>) results.values;

            if(item.size() == 0)
            {
                //Viser "Intet resultat"-teksten
                Main.txt.setVisibility(View.VISIBLE);
            }
            else
            {
                //Skjuler "Intet resultat"-teksten
                Main.txt.setVisibility(View.INVISIBLE);
            }

            //Notificerer ændringer på adapteren
            notifyDataSetChanged();
        }

    }
}
