package ir.webplex.android.api.utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public abstract class CancelableCallback<T> implements Callback<T> {
    private static List<CancelableCallback> mCancelables = new ArrayList<>();
    private boolean mIsCanceled = false;
    private String mTag = null;

    public CancelableCallback(String tag) {
        mTag = tag;
        mCancelables.add(this);
    }

    public static void cancelAll(String tag) {
        if (tag == null)
            return;

        for (int i = 0; i < mCancelables.size(); i++) {
            CancelableCallback callback = mCancelables.get(0);

            if (callback.mTag == tag) {
                callback.cancel();
                i--;
            }
        }
    }

    public void cancel() {
        mIsCanceled = true;
        mCancelables.remove(this);
    }

    @Override
    public final void onResponse(Response<T> response) {
        if (!mIsCanceled) {
            T result = response.body();

            if (result != null)
                onSuccess(result);
            else
                onError(new Exception("No body"));
        }

        mCancelables.remove(this);
    }

    @Override
    public final void onFailure(Throwable throwable) {
        if (!mIsCanceled)
            onError(throwable);

        mCancelables.remove(this);
    }

    public abstract void onSuccess(T result);

    public abstract void onError(Throwable error);
}