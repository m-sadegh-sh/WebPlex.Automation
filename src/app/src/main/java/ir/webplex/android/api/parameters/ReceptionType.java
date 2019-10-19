package ir.webplex.android.api.parameters;

import com.google.gson.annotations.SerializedName;

public enum ReceptionType {
    @SerializedName("0")
    NOT_RECEIVED,

    @SerializedName("1")
    RECEIVED;
}
