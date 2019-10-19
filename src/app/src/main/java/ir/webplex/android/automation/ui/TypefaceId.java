package ir.webplex.android.automation.ui;

import com.google.gson.annotations.SerializedName;

import ir.webplex.android.core.helpers.ConversionUtils;

public enum TypefaceId {
    @SerializedName("iranian_sans_ultra_light")
    IRANIAN_SANS_ULTRA_LIGHT(0),

    @SerializedName("iranian_sans_light")
    IRANIAN_SANS_LIGHT(1),

    @SerializedName("iranian_sans_regular")
    IRANIAN_SANS_REGULAR(2),

    @SerializedName("iranian_sans_medium")
    IRANIAN_SANS_MEDIUM(4),

    @SerializedName("iranian_sans_bold")
    IRANIAN_SANS_BOLD(8),

    @SerializedName("roboto_light")
    ROBOTO_LIGHT(16),

    @SerializedName("roboto_regular")
    ROBOTO_REGULAR(32),

    @SerializedName("roboto_medium")
    ROBOTO_MEDIUM(64),

    @SerializedName("roboto_bold")
    ROBOTO_BOLD(128);

    private int mId;

    TypefaceId(int id) {
        mId = id;
    }

    public static TypefaceId getById(int id) {
        for (TypefaceId typeface : values())
            if (typeface.mId == id)
                return typeface;

        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return ConversionUtils.getSerializedName(this);
    }
}
