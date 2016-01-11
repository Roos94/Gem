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
    private Filter genstandFilter;
    private List<Item> orggenstand;

    public Main_adapter(Context c, List<Item> item) {
        super(c, R.layout.row_single, item);
        this.context = c;
        this.item = item;
        this.orggenstand = item;
    }

    public int getCount() {
        return this.item.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        GenstandHolder holder = new GenstandHolder();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(R.layout.row_single, null);

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
            holder = (GenstandHolder) row.getTag();
        }

        Item n = this.item.get(position);

        holder.imgView.setImageResource(n.getImage());
        holder.titleView.setText(n.getTitle());
        holder.idView.setText(n.getIDtoString());

        return row;
    }

    private static class GenstandHolder {
        public TextView titleView;
        public TextView idView;
        public ImageView imgView;
    }

    public void resetData() {
        this.item = this.orggenstand;
    }

    @Override
    public Filter getFilter() {
        if (this.genstandFilter == null)
        {
            this.genstandFilter = new genstandFilter();
        }

        return genstandFilter;
    }

    private class genstandFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {

                results.values = orggenstand;
                results.count = orggenstand.size();
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

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            item = (List<Item>) results.values;
            notifyDataSetChanged();
        }

    }
}
