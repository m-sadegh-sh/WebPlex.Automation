package ir.webplex.android.automation.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ir.webplex.android.automation.R;
import ir.webplex.android.automation.models.ReceptorItem;
import ir.webplex.android.core.helpers.InflatingUtils;

public class ReceptorsAdapter extends BaseAdapter {
    private ArrayList<ReceptorItem> mItems;

    public ReceptorsAdapter(ArrayList<ReceptorItem> items) {
        mItems = items;
    }

    public void appendItems(ArrayList<ReceptorItem> newItems) {
        mItems.addAll(newItems);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReceptorItem item = mItems.get(position);
        ReceptorHolder holder;

        if (convertView == null) {
            convertView = InflatingUtils.inflate(R.layout.item_receptor, parent);
            holder = new ReceptorHolder();
            holder.title = (TextView) convertView.findViewById(R.id.item_receptor_title);
            holder.email = (TextView) convertView.findViewById(R.id.item_receptor_email);
            holder.divider = (RelativeLayout) convertView.findViewById(R.id.item_receptor_divider);
            convertView.setTag(holder);
        } else
            holder = (ReceptorHolder) convertView.getTag();

        holder.title.setText(item.getTitle());
        holder.email.setText(item.getEmail());
        holder.divider.setVisibility(position + 1 < getCount() ? View.VISIBLE : View.GONE);

        return convertView;
    }

    private class ReceptorHolder {
        TextView title;
        TextView email;
        RelativeLayout divider;
    }
}
