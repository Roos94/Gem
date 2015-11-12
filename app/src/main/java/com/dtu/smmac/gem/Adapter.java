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
    private List<Genstand> emne;
    private Filter emneFilter;
    private List<Genstand> orgEmne;

    Adapter(Context c, List<Genstand> emne) {
        super(c, R.layout.single_row, emne);
        this.context = c;
        this.emne = emne;
        this.orgEmne = emne;
    }

    public int getCount() {
        return this.emne.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        EmneHolder holder = new EmneHolder();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(R.layout.single_row, null);

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
            holder = (EmneHolder) row.getTag();
        }

        Genstand n = this.emne.get(position);

        holder.imgView.setImageResource(n.getImage());
        holder.titleView.setText(n.getTitle());
        holder.idView.setText(n.getID());

        return row;
    }

    private static class EmneHolder {
        public TextView titleView;
        public TextView idView;
        public ImageView imgView;
    }

    public void resetData() {
        this.emne = this.orgEmne;
    }

    @Override
    public Filter getFilter() {
        if (this.emneFilter == null)
        {
            this.emneFilter = new EmneFilter();
        }

        return emneFilter;
    }

    private class EmneFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {

                results.values = orgEmne;
                results.count = orgEmne.size();
            }
            else {
                List<Genstand> nEmne = new ArrayList<Genstand>();

                for (Genstand n : emne)
                {
                    if (n.getTitle().toUpperCase().contains(constraint.toString().toUpperCase()) || n.getID().toUpperCase().contains(constraint.toString().toUpperCase()))
                        nEmne.add(n);
                }

                results.values = nEmne;
                results.count = nEmne.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            emne = (List<Genstand>) results.values;
            notifyDataSetChanged();
        }

    }

}

