package ir.webplex.android.core;

import java.nio.charset.Charset;

import ir.webplex.android.api.parameters.LetterType;

public interface Constants {
    String PRIVATE_KEY = "" + (char) 142 + (char) 75 + (char) 95 + (char) 48 + (char) 30 + (char) 12 + (char) 163 + (char) 212 + (char) 46 + (char) 97;

    String TAG = "ir.webplex.android";

    int DELAYS_SPLASH = 2500;
    int DELAYS_HTTP_REQUEST = 0;
    int TIMEOUTS_CONNECT = 15;
    int TIMEOUTS_WRITE = 15;
    int TIMEOUTS_READ = 15;

    String PREFERENCES_FILE_NAME = "ir.webplex.android.preferences";
    int PREFERENCES_FILE_MODE = 0;
    String PREFERENCES_KEYS_BASE_URI = "preferences.baseUri";
    String PREFERENCES_KEYS_TOKEN = "preferences.token";
    String PREFERENCES_KEYS_TITLE = "preferences.title";
    String PREFERENCES_KEYS_EMAIL = "preferences.email";
    String PREFERENCES_KEYS_AVATAR = "preferences.avatar";
    String PREFERENCES_KEYS_PERMISSIONS = "preferences.permissions";

    int INTENT_REQUEST_CODES_SUBMIT_RECEIPT_SELECT_LETTER = 1;
    int INTENT_REQUEST_CODES_SUBMIT_RECEIPT_SHOW_DETAILS = 2;
    int INTENT_REQUEST_CODES_SUBMIT_RECEIPT_SHOW_RECEIVERS = 3;
    int INTENT_REQUEST_CODES_SUBMIT_RECEIPT_SELECT_RECEPTOR = 4;

    String INTENT_KEYS_SUBMIT_RECEIPT_SELECTED_LETTER = "selected.letter";
    String INTENT_KEYS_SUBMIT_RECEIPT_SELECTED_RECEPTOR = "selected.receptor";

    String API_URIS_BASE = "http://192.168.1.4:86/apis/";
    String API_URIS_AUTHENTICATE_LOGIN = "authenticate/login";
    String API_URIS_SEARCH_FIND_LETTERS = "search/find-letters";
    String API_URIS_SEARCH_FIND_DETAILS = "search/find-details";
    String API_URIS_SEARCH_FIND_RECEIVERS = "search/find-receivers";
    String API_URIS_SEARCH_SUBMIT_RECEIPT = "search/submit-receipt";
    String API_URIS_SEARCH_FIND_RECEPTORS = "search/find-receptors";

    LetterType API_PARAMETERS_SEARCH_FIND_LETTERS_LETTER_TYPES = LetterType.INCOMING;
    int API_PARAMETERS_SEARCH_FIND_LETTERS_PAGE_SIZE = 50;
    int API_PARAMETERS_SEARCH_FIND_RECEPTORS_PAGE_SIZE = 50;
    int API_PARAMETERS_SEARCH_FIND_RECEIVERS_PAGE_SIZE = 50;

    Charset UTF_8 = Charset.forName("UTF-8");
}
