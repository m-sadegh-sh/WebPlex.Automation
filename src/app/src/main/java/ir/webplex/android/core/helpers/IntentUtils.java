package ir.webplex.android.core.helpers;

import android.app.Activity;
import android.content.Intent;

import ir.webplex.android.automation.activities.DetailsActivity;
import ir.webplex.android.automation.activities.LettersActivity;
import ir.webplex.android.automation.activities.LoginActivity;
import ir.webplex.android.automation.activities.MainActivity;
import ir.webplex.android.automation.activities.ReceiversActivity;
import ir.webplex.android.automation.activities.ReceptorsActivity;
import ir.webplex.android.automation.activities.SubmitReceiptActivity;
import ir.webplex.android.automation.models.LetterItem;
import ir.webplex.android.automation.models.ReceptorItem;
import ir.webplex.android.core.Constants;

public class IntentUtils implements Constants {
    public static void goToLogin(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);

        goToActivity(activity, intent, true);
    }

    public static void goToMain(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);

        goToActivity(activity, intent, true);
    }

    public static void goToSubmitReceipt(MainActivity activity) {
        Intent intent = new Intent(activity, SubmitReceiptActivity.class);

        goToActivity(activity, intent, false);
    }

    public static void goToDetails(SubmitReceiptActivity activity, LetterItem item) {
        Intent intent = new Intent(activity, DetailsActivity.class);

        intent.putExtra(INTENT_KEYS_SUBMIT_RECEIPT_SELECTED_LETTER, item);

        goToActivityForResult(activity, intent, INTENT_REQUEST_CODES_SUBMIT_RECEIPT_SHOW_DETAILS);
    }

    public static void goToLetterReceivers(SubmitReceiptActivity activity, LetterItem item) {
        Intent intent = new Intent(activity, ReceiversActivity.class);

        intent.putExtra(INTENT_KEYS_SUBMIT_RECEIPT_SELECTED_LETTER, item);

        goToActivityForResult(activity, intent, INTENT_REQUEST_CODES_SUBMIT_RECEIPT_SHOW_RECEIVERS);
    }

    public static void goToLetters(SubmitReceiptActivity activity) {
        Intent intent = new Intent(activity, LettersActivity.class);

        goToActivityForResult(activity, intent, INTENT_REQUEST_CODES_SUBMIT_RECEIPT_SELECT_LETTER);
    }

    public static void goToReceptors(SubmitReceiptActivity activity, LetterItem item) {
        Intent intent = new Intent(activity, ReceptorsActivity.class);

        intent.putExtra(INTENT_KEYS_SUBMIT_RECEIPT_SELECTED_LETTER, item);

        goToActivityForResult(activity, intent, INTENT_REQUEST_CODES_SUBMIT_RECEIPT_SELECT_RECEPTOR);
    }

    public static void putSelectedLetter(Activity activity, LetterItem item) {
        Intent intent = new Intent(activity, SubmitReceiptActivity.class);

        intent.putExtra(INTENT_KEYS_SUBMIT_RECEIPT_SELECTED_LETTER, item);

        putActivityResult(activity, intent);
    }

    public static void putSelectedReceptor(Activity activity, ReceptorItem item) {
        Intent intent = new Intent(activity, SubmitReceiptActivity.class);

        intent.putExtra(INTENT_KEYS_SUBMIT_RECEIPT_SELECTED_RECEPTOR, item);

        putActivityResult(activity, intent);
    }

    public static LetterItem getSelectedLetter(Intent intent) {
        return intent.getParcelableExtra(INTENT_KEYS_SUBMIT_RECEIPT_SELECTED_LETTER);
    }

    public static ReceptorItem getSelectedReceptor(Intent intent) {
        return intent.getParcelableExtra(INTENT_KEYS_SUBMIT_RECEIPT_SELECTED_RECEPTOR);
    }

    private static void goToActivity(Activity activity, Intent intent, boolean finish) {
        activity.startActivity(intent);

        if (finish)
            activity.finish();
    }

    private static void goToActivityForResult(Activity activity, Intent intent, int requestCode) {
        activity.startActivityForResult(intent, requestCode);
    }

    private static void putActivityResult(Activity activity, Intent intent) {
        activity.setResult(activity.RESULT_OK, intent);
        activity.finish();
    }
}
