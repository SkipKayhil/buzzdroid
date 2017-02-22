package io.github.skipkayhil.buzz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SitesAdapter extends BaseAdapter {

    private enum type {
        ITEM, SEPARATOR
    }

    //private Context context;
    private LayoutInflater inflater;
    private List<Site> listItems;

    public SitesAdapter(Context context, List<Site> listItems) {
        // this.context = context;
        this.listItems = listItems;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Site getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_site, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.sitesName);
            holder.url = (TextView) convertView.findViewById(R.id.sitesURL);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Site site = getItem(position);

        holder.name.setText(site.getName());
        holder.url.setText(site.getDefaultUrl());

        return convertView;
    }

    private static class ViewHolder {
        private TextView name;
        private TextView url;
    }
}
