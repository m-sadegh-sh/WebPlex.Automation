package ir.webplex.android.api.parameters;

import com.google.gson.annotations.SerializedName;

public enum LetterType {
    @SerializedName("0")
    OUTGOING,

    @SerializedName("1")
    INCOMING,

    @SerializedName("2")
    INTERNAL;
}
