package com.dtu.smmac.gem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roos on 05/11/15.
 */
class Adapter extends ArrayAdapter<Genstand> implements Filterable {
    private Context context;
    private List<Genstand> genstand;
    private Filter genstandFilter;
    private List<Genstand> orggenstand;

    public Adapter(Context c, List<Genstand> genstand) {
        super(c, R.layout.row_single, genstand);
        this.context = c;
        this.genstand = genstand;
        this.orggenstand = genstand;
    }

    public int getCount() {
        return this.genstand.size();
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

        Genstand n = this.genstand.get(position);

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
        this.genstand = this.orggenstand;
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
                List<Genstand> nGenstand = new ArrayList<Genstand>();

                for (Genstand n : genstand)
                {
                    if (n.getTitle().toUpperCase().contains(constraint.toString().toUpperCase()) || n.getIDtoString().toUpperCase().contains(constraint.toString().toUpperCase()))
                        nGenstand.add(n);
                }

                results.values = nGenstand;
                results.count = nGenstand.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            genstand = (List<Genstand>) results.values;
            notifyDataSetChanged();
        }

    }

}

