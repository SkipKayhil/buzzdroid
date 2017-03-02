package io.github.skipkayhil.buzz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.github.skipkayhil.buzz.fragments.SiteCategoryView;

public class SiteCategoryAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<SiteCategoryView.SiteCategory> listItems;

    public SiteCategoryAdapter(Context context, List<SiteCategoryView.SiteCategory> listItems) {
        // this.context = context;
        this.listItems = listItems;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public SiteCategoryView.SiteCategory getItem(int position) {
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
            convertView = inflater.inflate(R.layout.list_item_subheader, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.siteCategoryName);
            holder.description = (TextView) convertView.findViewById(R.id.siteCategoryDescription);
            holder.icon = (ImageView) convertView.findViewById(R.id.siteCategoryIcon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SiteCategoryView.SiteCategory category = getItem(position);

        holder.name.setText(category.name);
        holder.description.setText(category.description);
        holder.icon.setImageResource(category.icon);
        holder.icon.setImageAlpha(138);

        return convertView;
    }

    private static class ViewHolder {
        private ImageView icon;
        private TextView name;
        private TextView description;
    }
}
