package ir.webplex.android.automation.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import ir.webplex.android.automation.R;

public class CustomTextView extends TextView {
    private TypefaceId mTypeface;

    public CustomTextView(Context context) {
        super(context);

        if (isInEditMode())
            return;

        mTypeface = TypefaceId.IRANIAN_SANS_REGULAR;
        setTypeface();
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode())
            return;

        initProperties(attrs);
        setTypeface();
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (isInEditMode())
            return;

        initProperties(attrs);
        setTypeface();
    }

    private void setTypeface() {
        TypefaceLoader loader = TypefaceLoader.getInstance();
        Typeface typeface = loader.getTypeface(mTypeface);

        setTypeface(typeface);
    }

    private void initProperties(AttributeSet attrs) {
        TypedArray styles = getStyles(attrs);

        int id = styles.getInt(R.styleable.CustomTypeface_typefaceId, 2);
        mTypeface = TypefaceId.getById(id);

        styles.recycle();
    }

    private TypedArray getStyles(AttributeSet attrs) {
        return getContext().obtainStyledAttributes(attrs, R.styleable.CustomTypeface);
    }
}

