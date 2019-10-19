package ir.webplex.android.automation.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ir.webplex.android.automation.R;
import ir.webplex.android.automation.models.LetterItem;
import ir.webplex.android.core.helpers.InflatingUtils;

public class LettersAdapter extends BaseAdapter {
    private ArrayList<LetterItem> mItems;

    public LettersAdapter(ArrayList<LetterItem> items) {
        mItems = items;
    }

    public void appendItems(ArrayList<LetterItem> newItems) {
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
        LetterItem item = mItems.get(position);
        LetterHolder holder;

        if (convertView == null) {
            convertView = InflatingUtils.inflate(R.layout.item_letter, parent);
            holder = new LetterHolder();
            holder.receiversCount = (TextView) convertView.findViewById(R.id.item_letter_receivers_count);
            holder.subject = (TextView) convertView.findViewById(R.id.item_letter_subject);
            holder.indicatorDate = (TextView) convertView.findViewById(R.id.item_letter_indicator_date);
            holder.indicatorNumber = (TextView) convertView.findViewById(R.id.item_letter_indicator_number);
            holder.divider = (RelativeLayout) convertView.findViewById(R.id.item_letter_divider);
            convertView.setTag(holder);
        } else
            holder = (LetterHolder) convertView.getTag();

        holder.receiversCount.setText(String.valueOf(item.getReceiversCount()));
        holder.subject.setText(item.getSubject());
        holder.indicatorDate.setText(item.getIndicatorDate());
        holder.indicatorNumber.setText(item.getIndicatorNumber());
        holder.divider.setVisibility(position + 1 < getCount() ? View.VISIBLE : View.GONE);

        return convertView;
    }

    private class LetterHolder {
        TextView receiversCount;
        TextView subject;
        TextView indicatorDate;
        TextView indicatorNumber;
        RelativeLayout divider;
    }
}
