package ir.webplex.android.core.logging;

import android.text.TextUtils;
import android.util.Log;

import ir.webplex.android.api.requests.FindDetailsRequest;
import ir.webplex.android.api.requests.FindLettersRequest;
import ir.webplex.android.api.requests.FindReceiversRequest;
import ir.webplex.android.api.requests.FindReceptorsRequest;
import ir.webplex.android.api.requests.LoginRequest;
import ir.webplex.android.api.requests.SubmitReceiptRequest;
import ir.webplex.android.api.responses.FindDetailsResponse;
import ir.webplex.android.api.responses.FindLettersResponse;
import ir.webplex.android.api.responses.FindReceiversResponse;
import ir.webplex.android.api.responses.FindReceptorsResponse;
import ir.webplex.android.api.responses.LoginResponse;
import ir.webplex.android.api.responses.SubmitReceiptResponse;
import ir.webplex.android.automation.BuildConfig;
import ir.webplex.android.core.Constants;

public class Logger implements Constants {
    public static boolean isLoggable() {
        return BuildConfig.DEBUG;
    }

    public static void onEntering(String tag, Object name) {
        verbose(tag, String.format("Entering %s.", name));
    }

    public static void onExited(String tag, Object name) {
        verbose(tag, String.format("%s exited.", name));
    }

    public static void onInitiating(String tag) {
        verbose(tag, String.format("Initializing %s.", tag));
    }

    public static void onInited(String tag) {
        verbose(tag, String.format("%s initialized.", tag));
    }

    public static void onInstanceCreating(String tag) {
        verbose(tag, "Creating an instance for %s...", tag);
    }

    public static void onAlreadyLoggedIn(String tag) {
        verbose(tag, "Already logged in.");
    }

    public static void onLoggingIn(String tag, LoginRequest request) {
        verbose(tag, "Sending login request. %s.", request);
    }

    public static void onFindingLetters(String tag, FindLettersRequest request) {
        verbose(tag, "Sending find-letters request. %s.", request);
    }

    public static void onFindingReceivers(String tag, FindReceiversRequest request) {
        verbose(tag, "Sending find-receptors request. %s.", request);
    }

    public static void onFindingReceptors(String tag, FindReceptorsRequest request) {
        verbose(tag, "Sending find-receptors request. %s.", request);
    }

    public static void onFindingDetails(String tag, FindDetailsRequest request) {
        verbose(tag, "Sending find-details request. %s.", request);
    }

    public static void onSubmittingReceipt(String tag, SubmitReceiptRequest request) {
        verbose(tag, "Sending submit-receipt request. %s.", request);
    }

    public static void onViewLookedUp(String tag) {
        verbose(tag, "View components has been looked up.");
    }

    public static void onLoginResponse(String tag, LoginResponse response) {
        verbose(tag, "Received login response. %s.", response);
    }

    public static void onFindLettersResponse(String tag, FindLettersResponse response) {
        verbose(tag, "Received find-letters response. %s.", response);
    }

    public static void onFindReceptorsResponse(String tag, FindReceptorsResponse response) {
        verbose(tag, "Received find-receptors response. %s.", response);
    }

    public static void onFindDetailsResponse(String tag, FindDetailsResponse response) {
        verbose(tag, "Received find-details response. %s.", response);
    }

    public static void onSubmitReceiptResponse(String tag, SubmitReceiptResponse response) {
        verbose(tag, "Received submit-receipt response. %s.", response);
    }

    public static void onFindReceiversResponse(String tag, FindReceiversResponse response) {
        verbose(tag, "Received find-receivers response. %s.", response);
    }

    public static void onError(String tag, Throwable error) {
        error(tag, "Request has been failed. Error: %s", error.getMessage());
    }

    public static void onTransferring(String tag, Object source, Object destination) {
        verbose(tag, "Transferring from %s to %s.", source, destination);
    }

    public static void onHttp(String tag, String message) {
        verbose(tag, message);
    }

    public static void verbose(String tag, String message) {
        verbose(tag, message, (Object[]) null);
    }

    public static void verbose(String tag, String message, Object... args) {
        if (isLoggable())
            Log.v(TextUtils.isEmpty(tag) ? TAG : tag, String.format(message, args));
    }

    public static void error(String tag, String message) {
        error(tag, message, (Object[]) null);
    }

    public static void error(String tag, String message, Object... args) {
        if (isLoggable())
            Log.e(TextUtils.isEmpty(tag) ? TAG : tag, String.format(message, args));
    }

    public static void error(String tag, Throwable exception) {
        if (isLoggable())
            Log.e(TextUtils.isEmpty(tag) ? TAG : tag, exception.getMessage(), exception);
    }
}
