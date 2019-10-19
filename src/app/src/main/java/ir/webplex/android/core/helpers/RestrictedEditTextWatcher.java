package ir.webplex.android.core.helpers;

import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import ir.webplex.android.automation.ui.CustomTextWatcher;

public class RestrictedEditTextWatcher extends CustomTextWatcher {
    private EditText[] mEditTexts;
    private MenuItem mItem;

    public RestrictedEditTextWatcher(MenuItem item, EditText... editTexts) {
        mEditTexts = editTexts;
        mItem = item;
    }

    @Override
    public void afterTextChanged(String text) {
        ViewUtils.validate(mItem, mEditTexts);
    }
}
