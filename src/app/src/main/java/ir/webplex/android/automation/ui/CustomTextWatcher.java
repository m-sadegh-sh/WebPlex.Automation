package ir.webplex.android.automation.ui;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class CustomTextWatcher implements TextWatcher {
    public abstract void afterTextChanged(String text);

    @Override
    final public void afterTextChanged(Editable s) {
        String text = String.valueOf(s);
        afterTextChanged(text);
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
}