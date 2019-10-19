package ir.webplex.android.automation.ui;

import android.graphics.Paint;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

public class TypefaceSpan extends MetricAffectingSpan {
    @Override
    public void updateMeasureState(TextPaint tp) {
        updateTypeface(tp);
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        updateTypeface(tp);
    }

    private void updateTypeface(TextPaint tp) {
        tp.setTypeface(TypefaceLoader.getInstance().getTypeface(TypefaceId.IRANIAN_SANS_REGULAR));
        tp.setFlags(tp.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
    }
}
