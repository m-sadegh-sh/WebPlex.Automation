package ir.webplex.android.automation.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ir.webplex.android.automation.R;
import ir.webplex.android.automation.models.ReceiverItem;
import ir.webplex.android.core.helpers.InflatingUtils;

public class ReceiversAdapter extends BaseAdapter {
    private ArrayList<ReceiverItem> mItems;

    public ReceiversAdapter(ArrayList<ReceiverItem> items) {
        mItems = items;
    }

    public void appendItems(ArrayList<ReceiverItem> newItems) {
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
        ReceiverItem item = mItems.get(position);
        ReceiverHolder holder;

        if (convertView == null) {
            convertView = InflatingUtils.inflate(R.layout.item_receiver, parent);
            holder = new ReceiverHolder();
            holder.title = (TextView) convertView.findViewById(R.id.item_receiver_title);
            holder.email = (TextView) convertView.findViewById(R.id.item_receiver_email);
            holder.relativeDate = (TextView) convertView.findViewById(R.id.item_receiver_relative_date);
            holder.divider = (RelativeLayout) convertView.findViewById(R.id.item_receiver_divider);
            convertView.setTag(holder);
        } else
            holder = (ReceiverHolder) convertView.getTag();

        holder.title.setText(item.getTitle());
        holder.email.setText(item.getEmail());
        holder.relativeDate.setText(item.getRelativeDate());
        holder.divider.setVisibility(position + 1 < getCount() ? View.VISIBLE : View.GONE);

        return convertView;
    }

    private class ReceiverHolder {
        TextView title;
        TextView email;
        TextView relativeDate;
        RelativeLayout divider;
    }
}
