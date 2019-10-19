package ir.webplex.android.automation.models;

public class NavigableItem extends DrawerItem {
    private String mTitle;
    private int mIcon;
    private int mCount = 0;
    private boolean mHasTopDivider;
    private boolean mHasBottomDivider;

    public NavigableItem(long id, String title, int icon) {
        this(id, title, icon, false, false);
    }

    public NavigableItem(long id, String title, int icon, boolean hasTopDivider, boolean hasBottomDivider) {
        super(id);
        mTitle = title;
        mIcon = icon;
        mHasTopDivider = hasTopDivider;
        mHasBottomDivider = hasBottomDivider;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int icon) {
        mIcon = icon;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }

    public boolean isCounterVisible() {
        return mCount > 0;
    }

    public boolean hasTopDivider() {
        return mHasTopDivider;
    }

    public void setTopDivider(boolean hasTopDivider) {
        mHasTopDivider = hasTopDivider;
    }

    public boolean hasBottomDivider() {
        return mHasBottomDivider;
    }

    public void setBottomDivider(boolean hasBottomDivider) {
        mHasBottomDivider = hasBottomDivider;
    }
}
