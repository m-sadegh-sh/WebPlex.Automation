package ir.webplex.android.automation.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DrawingView extends View {
    private static final float STROKE_WIDTH = 5f;
    private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
    private final RectF mDirtyRect;
    private Paint mPaint;
    private Path mPath;
    private float mLastTouchX;
    private float mLastTouchY;
    private OnDrawingListener mOnDrawingListener;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(STROKE_WIDTH);

        mPath = new Path();
        mDirtyRect = new RectF();
    }

    public void setOnDrawingListener(OnDrawingListener l) {
        mOnDrawingListener = l;
    }

    public String exportSurface() throws IOException {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(0xffffffff);

        draw(canvas);

        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
            return Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
        }
    }

    public void clearSurface() {
        mPath.reset();
        invalidate();
    }

    public boolean isSurfaceDirty() {
        return !mPath.isEmpty();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(eventX, eventY);

                break;

            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                resetDirtyRect(eventX, eventY);
                int historySize = event.getHistorySize();
                for (int i = 0; i < historySize; i++) {
                    float historicalX = event.getHistoricalX(i);
                    float historicalY = event.getHistoricalY(i);
                    expandDirtyRect(historicalX, historicalY);
                    mPath.lineTo(historicalX, historicalY);
                }

                mPath.lineTo(eventX, eventY);

                invalidate(
                        (int) (mDirtyRect.left - HALF_STROKE_WIDTH),
                        (int) (mDirtyRect.top - HALF_STROKE_WIDTH),
                        (int) (mDirtyRect.right + HALF_STROKE_WIDTH),
                        (int) (mDirtyRect.bottom + HALF_STROKE_WIDTH)
                );

                break;

            default:
                return false;
        }

        mLastTouchX = eventX;
        mLastTouchY = eventY;

        if (mOnDrawingListener != null)
            mOnDrawingListener.onDrawing(this);

        return true;
    }

    private void expandDirtyRect(float historicalX, float historicalY) {
        if (historicalX < mDirtyRect.left)
            mDirtyRect.left = historicalX;
        else if (historicalX > mDirtyRect.right)
            mDirtyRect.right = historicalX;

        if (historicalY < mDirtyRect.top)
            mDirtyRect.top = historicalY;
        else if (historicalY > mDirtyRect.bottom)
            mDirtyRect.bottom = historicalY;
    }

    private void resetDirtyRect(float eventX, float eventY) {
        mDirtyRect.left = Math.min(mLastTouchX, eventX);
        mDirtyRect.right = Math.max(mLastTouchX, eventX);
        mDirtyRect.top = Math.min(mLastTouchY, eventY);
        mDirtyRect.bottom = Math.max(mLastTouchY, eventY);
    }
}