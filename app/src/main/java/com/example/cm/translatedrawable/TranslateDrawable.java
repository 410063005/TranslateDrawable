package com.example.cm.translatedrawable;


import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class TranslateDrawable extends Drawable {
    private static final String TAG = "TranslateDrawable";

    private static final int MAX_LEVEL = 10000;

    private Drawable mDrawable;
    private TranslateState mState;

    private int mOffset;

    public TranslateDrawable() {
        this(new TranslateState());
    }

    private TranslateDrawable(TranslateState state) {
        mState = state;
    }

    public void setOffset(int offset) {
        mOffset = offset;
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);

        final Drawable d = getDrawable();
        if (d != null && d.getIntrinsicHeight() > 0) {
            float f = getBounds().height() * 1f / d.getIntrinsicHeight();
            d.setBounds(0, getBounds().top, (int) (d.getIntrinsicWidth() * f), getBounds().bottom);
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        final Drawable d = getDrawable();
        if (d != null) {
            final TranslateState state = mState;
            final int saveCount = canvas.save();
            canvas.translate(state.translateX, 0);
            d.draw(canvas);
            canvas.restoreToCount(saveCount);
        }
    }

    @Override
    protected boolean onLevelChange(int level) {
        super.onLevelChange(level);

        final Rect bounds = getBounds();
        final float value = level / (float) MAX_LEVEL;
        mState.translateX = bounds.width() * value - mOffset;
        invalidateSelf();
        return true;
    }

    @Override
    public void setAlpha(int alpha) {
        if (mDrawable != null) {
            mDrawable.setAlpha(alpha);
        }
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        if (mDrawable != null) {
            mDrawable.setColorFilter(colorFilter);
        }
    }

    @Override
    public int getOpacity() {
        return mDrawable != null ? mDrawable.getOpacity() : PixelFormat.TRANSPARENT;
    }

    public void setDrawable(@Nullable Drawable dr) {
        if (mDrawable != null) {
            mDrawable.setCallback(null);
        }

        mDrawable = dr;

        invalidateSelf();
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    static final class TranslateState extends ConstantState {

        private float translateX = 0;

        @NonNull
        @Override
        public Drawable newDrawable() {
            return new TranslateDrawable(this);
        }

        @Override
        public int getChangingConfigurations() {
            return 0;
        }
    }
}