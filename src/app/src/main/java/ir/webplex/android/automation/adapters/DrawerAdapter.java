package ir.webplex.android.automation.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import ir.webplex.android.automation.R;
import ir.webplex.android.automation.models.DrawerItem;
import ir.webplex.android.automation.models.NavigableItem;
import ir.webplex.android.automation.models.ProfileItem;
import ir.webplex.android.automation.ui.CustomTextView;
import ir.webplex.android.core.helpers.ConversionUtils;
import ir.webplex.android.core.helpers.InflatingUtils;

public class DrawerAdapter extends BaseAdapter {
    private ArrayList<DrawerItem> mItems;

    public DrawerAdapter(ArrayList<DrawerItem> items) {
        mItems = items;
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
        return mItems.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DrawerItem item = mItems.get(position);

        if (item instanceof NavigableItem) {
            NavigableItem navigable = (NavigableItem) item;
            NavigableHolder holder;

            if (convertView == null) {
                convertView = InflatingUtils.inflate(R.layout.item_navigable, parent);
                holder = new NavigableHolder();
                holder.topDivider = (RelativeLayout) convertView.findViewById(R.id.item_navigable_top_divider);
                holder.title = (CustomTextView) convertView.findViewById(R.id.item_navigable_title);
                holder.icon = (ImageView) convertView.findViewById(R.id.item_navigable_icon);
                holder.counter = (CustomTextView) convertView.findViewById(R.id.item_navigable_counter);
                holder.bottomDivider = (RelativeLayout) convertView.findViewById(R.id.item_navigable_bottom_divider);
                convertView.setTag(holder);
            } else
                holder = (NavigableHolder) convertView.getTag();

            if (!navigable.hasTopDivider())
                holder.topDivider.setVisibility(View.GONE);

            holder.title.setText(navigable.getTitle());
            holder.icon.setImageResource(navigable.getIcon());

            if (navigable.isCounterVisible())
                holder.counter.setText(String.valueOf(navigable.getCount()));
            else
                holder.counter.setVisibility(View.GONE);

            if (!navigable.hasBottomDivider())
                holder.bottomDivider.setVisibility(View.GONE);
        } else if (item instanceof ProfileItem) {
            ProfileItem profile = (ProfileItem) item;
            ProfileHolder holder;

            if (convertView == null) {
                convertView = InflatingUtils.inflate(R.layout.item_profile, parent);
                convertView.setClickable(false);
                holder = new ProfileHolder();
                holder.postTitle = (CustomTextView) convertView.findViewById(R.id.item_profile_title);
                holder.postEmail = (CustomTextView) convertView.findViewById(R.id.item_profile_email);
                holder.avatar = (ImageView) convertView.findViewById(R.id.item_profile_avatar);
            } else
                holder = (ProfileHolder) convertView.getTag();

            holder.postTitle.setText(profile.getPostTitle());
            holder.postEmail.setText(profile.getEmail());
            ConversionUtils.setImageFromBase64(holder.avatar, profile.getAvatar(), R.mipmap.ic_shared_avatar);
        }

        return convertView;
    }

    private class NavigableHolder {
        RelativeLayout topDivider;
        CustomTextView title;
        ImageView icon;
        CustomTextView counter;
        RelativeLayout bottomDivider;
    }

    private class ProfileHolder {
        CustomTextView postTitle;
        CustomTextView postEmail;
        ImageView avatar;
    }
}
