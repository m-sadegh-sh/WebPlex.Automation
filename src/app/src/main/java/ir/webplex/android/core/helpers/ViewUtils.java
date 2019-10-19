package ir.webplex.android.core.helpers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import ir.webplex.android.automation.AppController;
import ir.webplex.android.automation.R;
import ir.webplex.android.automation.ui.TypefaceSpan;

public class ViewUtils {
    public static void disableOnEmpty(MenuItem item, EditText... editTexts) {
        validate(item, editTexts);

        for (EditText editText : editTexts)
            editText.addTextChangedListener(new RestrictedEditTextWatcher(item, editTexts));
    }

    public static void validate(MenuItem item, EditText... editTexts) {
        for (EditText editText : editTexts)
            if (TextUtils.isEmpty(editText.getText())) {
                item.setEnabled(false);
                return;
            }

        item.setEnabled(true);
    }

    public static int getStatusBarHeight() {
        Resources resources = AppController.getInstance().getResources();

        int result = 0;
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0)
            result = resources.getDimensionPixelSize(resourceId);

        return result;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void updateLayoutDirection(Activity activity) {
        activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
    }

    public static void updateActionBarTitle(AppCompatActivity activity) {
        ActionBar actionBar = activity.getSupportActionBar();

        if (actionBar == null)
            return;

        SpannableString title = new SpannableString(activity.getTitle());
        title.setSpan(new TypefaceSpan(), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        actionBar.setTitle(title);
    }

    public static void updateActionBarIcon(AppCompatActivity activity) {
        ActionBar actionBar = activity.getSupportActionBar();

        if (actionBar == null)
            return;

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_shared_action_bar);
    }

    public static void clearCheckedState(Menu menu) {
        for (int i = 0; i < menu.size(); i++)
            menu.getItem(i).setChecked(false);
    }

    public static void hideNativeActionBar(Activity activity) {
        android.app.ActionBar actionBar = activity.getActionBar();

        if (actionBar != null)
            actionBar.hide();
    }
}

