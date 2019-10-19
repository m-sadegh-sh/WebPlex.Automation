package ir.webplex.android.core.helpers;

import android.text.TextUtils;
import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import ir.webplex.android.core.Constants;

public class SecurityHelpers implements Constants {
    public static String encrypt(String plainText) {
        if (TextUtils.isEmpty(plainText))
            return null;

        byte[] plainTextBytes = plainText.getBytes(UTF_8);
        byte[] privateKeyHash = createPrivateKeyHash(PRIVATE_KEY);

        return transformText(privateKeyHash, plainTextBytes, Cipher.ENCRYPT_MODE);
    }

    public static String decrypt(String encryptedText) {
        if (TextUtils.isEmpty(encryptedText))
            return null;

        byte[] encryptedTextBytes = Base64.decode(encryptedText, Base64.DEFAULT);
        byte[] privateKeyHash = createPrivateKeyHash(PRIVATE_KEY);

        return transformText(privateKeyHash, encryptedTextBytes, Cipher.DECRYPT_MODE);
    }

    private static final byte[] createPrivateKeyHash(String privateKey) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");

            return instance.digest(privateKey.getBytes(UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static final String transformText(byte[] privateKeyHash, byte[] textBytes, int mode) {
        try {
            Cipher instance = Cipher.getInstance("DESEDE/ECB/PKCS7Padding");
            instance.init(mode, new SecretKeySpec(privateKeyHash, "DESede"));

            byte[] transformedTextBytes = instance.doFinal(textBytes);

            if (mode == Cipher.ENCRYPT_MODE)
                return Base64.encodeToString(transformedTextBytes, Base64.DEFAULT);

            return new String(transformedTextBytes, UTF_8);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return null;
    }
}
