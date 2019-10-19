package ir.webplex.android.automation.ui;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import ir.webplex.android.automation.AppController;

public class TypefaceLoader {
    private static TypefaceLoader mInstance;
    private static Typeface mIranianSansUltraLightTypeface;
    private static Typeface mIranianSansLightTypeface;
    private static Typeface mIranianSansRegularTypeface;
    private static Typeface mIranianSansMediumTypeface;
    private static Typeface mIranianSansBoldTypeface;
    private static Typeface mRobotoLightTypeface;
    private static Typeface mRobotoRegularTypeface;
    private static Typeface mRobotoMediumTypeface;
    private static Typeface mRobotoBoldTypeface;

    private TypefaceLoader() {
        AssetManager assets = AppController.getInstance().getAssets();

        mIranianSansUltraLightTypeface = Typeface.createFromAsset(assets, String.format("fonts/%s.ttf", TypefaceId.IRANIAN_SANS_ULTRA_LIGHT));
        mIranianSansLightTypeface = Typeface.createFromAsset(assets, String.format("fonts/%s.ttf", TypefaceId.IRANIAN_SANS_LIGHT));
        mIranianSansRegularTypeface = Typeface.createFromAsset(assets, String.format("fonts/%s.ttf", TypefaceId.IRANIAN_SANS_REGULAR));
        mIranianSansMediumTypeface = Typeface.createFromAsset(assets, String.format("fonts/%s.ttf", TypefaceId.IRANIAN_SANS_MEDIUM));
        mIranianSansBoldTypeface = Typeface.createFromAsset(assets, String.format("fonts/%s.ttf", TypefaceId.IRANIAN_SANS_BOLD));
        mRobotoLightTypeface = Typeface.createFromAsset(assets, String.format("fonts/%s.ttf", TypefaceId.ROBOTO_LIGHT));
        mRobotoRegularTypeface = Typeface.createFromAsset(assets, String.format("fonts/%s.ttf", TypefaceId.ROBOTO_REGULAR));
        mRobotoMediumTypeface = Typeface.createFromAsset(assets, String.format("fonts/%s.ttf", TypefaceId.ROBOTO_MEDIUM));
        mRobotoBoldTypeface = Typeface.createFromAsset(assets, String.format("fonts/%s.ttf", TypefaceId.ROBOTO_BOLD));
    }

    public static synchronized TypefaceLoader getInstance() {
        if (mInstance == null)
            mInstance = new TypefaceLoader();

        return mInstance;
    }

    public Typeface getTypeface(TypefaceId id) {
        switch (id) {
            case IRANIAN_SANS_ULTRA_LIGHT:
                return mIranianSansUltraLightTypeface;
            case IRANIAN_SANS_LIGHT:
                return mIranianSansLightTypeface;
            case IRANIAN_SANS_REGULAR:
                return mIranianSansRegularTypeface;
            case IRANIAN_SANS_MEDIUM:
                return mIranianSansMediumTypeface;
            case IRANIAN_SANS_BOLD:
                return mIranianSansBoldTypeface;
            case ROBOTO_LIGHT:
                return mRobotoLightTypeface;
            case ROBOTO_REGULAR:
                return mRobotoRegularTypeface;
            case ROBOTO_MEDIUM:
                return mRobotoMediumTypeface;
            case ROBOTO_BOLD:
                return mRobotoBoldTypeface;
        }

        return null;
    }
}
