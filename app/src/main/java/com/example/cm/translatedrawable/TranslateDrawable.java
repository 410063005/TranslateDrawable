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

    private int mOffset;

    public TranslateDrawable() {
    }

    public void setOffset(int offset) {
        mOffset = offset;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        final Drawable d = getDrawable();
        if (d == null) {
            return;
        }

        final Rect bounds = getBounds();
        final int saveCount = canvas.save();
        final int level = getLevel();
        final int w = bounds.width();
        final int translateX = w - w * (MAX_LEVEL - level) / MAX_LEVEL;
        canvas.translate(translateX - mOffset, 0);
        d.draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        Drawable d = getDrawable();
        if (d != null) {
            if (d.getIntrinsicHeight() > 0) {
                float f = getBounds().height() * 1f / d.getIntrinsicHeight();
                d.setBounds(0, bounds.top, (int) (d.getIntrinsicWidth() * f), bounds.bottom);
            } else {
                d.setBounds(bounds);
            }
        }
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

    @Override
    protected boolean onLevelChange(int level) {
        super.onLevelChange(level);
        if (mDrawable != null) {
            mDrawable.setLevel(level);
        }
        invalidateSelf();
        return true;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(@Nullable Drawable dr) {
        if (mDrawable != null) {
            mDrawable.setCallback(null);
        }

        mDrawable = dr;

        if (dr != null) {

            // Only call setters for data that's stored in the base Drawable.
            dr.setVisible(isVisible(), true);
            dr.setState(getState());
            dr.setLevel(getLevel());
            dr.setBounds(getBounds());
            // dr.setLayoutDirection(getLayoutDirection());
        }

        invalidateSelf();
    }
}