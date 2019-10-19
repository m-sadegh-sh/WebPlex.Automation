package ir.webplex.android.core.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;

import com.google.gson.Gson;

import ir.webplex.android.automation.AppController;
import ir.webplex.android.core.logging.Logger;

public class ConversionUtils {
    public static final String TAG = ConversionUtils.class.getSimpleName();
    private static Gson _gson = new Gson();

    public static void setImageFromBase64(ImageView imageView, String base64Image, int secondaryImage) {
        if (!TextUtils.isEmpty(base64Image)) {
            try {
                byte[] imageBytes = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                imageView.setImageBitmap(decodedByte);
                return;
            } catch (Exception exception) {
                Logger.error(TAG, exception);
            }
        }

        AppController context = AppController.getInstance();
        imageView.setImageDrawable(context.getResources().getDrawable(secondaryImage, context.getTheme()));
    }

    public static String getSerializedName(Object object) {
        return _gson.toJson(object).replace("\"", "");
    }
}