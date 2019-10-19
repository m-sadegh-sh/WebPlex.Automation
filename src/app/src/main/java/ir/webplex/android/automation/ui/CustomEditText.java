package ir.webplex.android.automation.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

import ir.webplex.android.automation.R;

public class CustomEditText extends EditText {
    private TypefaceId mTypeface;
    private TypefaceId mHintTypeface;

    public CustomEditText(Context context) {
        super(context);

        if (isInEditMode())
            return;

        mTypeface = isOppositeType() ? TypefaceId.ROBOTO_REGULAR : TypefaceId.IRANIAN_SANS_REGULAR;
        mHintTypeface = TypefaceId.IRANIAN_SANS_REGULAR;
        setTypeface(mHintTypeface);
        addTextChangedListener(OnTextChangedListener);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode())
            return;

        initProperties(attrs);
        setTypeface(mHintTypeface);
        addTextChangedListener(OnTextChangedListener);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (isInEditMode())
            return;

        initProperties(attrs);
        setTypeface(mHintTypeface);
        addTextChangedListener(OnTextChangedListener);
    }

    private void setTypeface(TypefaceId id) {
        TypefaceLoader loader = TypefaceLoader.getInstance();
        Typeface typeface = loader.getTypeface(id);

        setTypeface(typeface);
    }

    private void initProperties(AttributeSet attrs) {
        TypedArray styles = getStyles(attrs);

        int id = styles.getInt(R.styleable.CustomTypeface_typefaceId, 2);
        mTypeface = TypefaceId.getById(id);

        id = styles.getInt(R.styleable.CustomTypeface_hintTypefaceId, isOppositeType() ? 32 : 2);
        mHintTypeface = TypefaceId.getById(id);

        styles.recycle();
    }

    private TypedArray getStyles(AttributeSet attrs) {
        return getContext().obtainStyledAttributes(attrs, R.styleable.CustomTypeface);
    }

    private boolean isOppositeType() {
        switch (getInputType()) {
            case InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS:
            case InputType.TYPE_TEXT_VARIATION_URI:
            case InputType.TYPE_TEXT_VARIATION_PASSWORD:
                return true;
        }

        return false;
    }

    private CustomTextWatcher OnTextChangedListener = new CustomTextWatcher() {
        @Override
        public void afterTextChanged(String text) {
            if (isOppositeType())
                return;

            if (TextUtils.isEmpty(text))
                setTypeface(mHintTypeface);
            else
                setTypeface(mTypeface);
        }
    };
}

