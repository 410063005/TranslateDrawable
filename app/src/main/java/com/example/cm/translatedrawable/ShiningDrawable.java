package com.example.cm.translatedrawable;


import android.graphics.Canvas;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.SystemClock;
import android.support.annotation.NonNull;

public class ShiningDrawable extends LayerDrawable implements Animatable, Runnable {
    private static final String TAG = "ShiningDrawable";
    private boolean mRunning;

    private Drawable mBgDrawable;
    private TranslateDrawable mFgDrawable;

    public ShiningDrawable(Drawable bgDrawable, TranslateDrawable fgDrawable) {
        super(new Drawable[] {bgDrawable, fgDrawable});
        mBgDrawable = bgDrawable;
        mFgDrawable = fgDrawable;
        setFrame(0, true, false);
    }

    private void setFrame(int level, boolean unschedule, boolean animate) {
        setLevel(level);
        if (unschedule || animate) {
            unscheduleSelf(this);
        }
        if (animate) {
            // Unscheduling may have clobbered these values; restore them
            mRunning = true;
            scheduleSelf(this, SystemClock.uptimeMillis() + 50);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (mBgDrawable != null) {
            mBgDrawable.draw(canvas);
        }
        if (mFgDrawable != null) {// && getLevel() > 0) {
            mFgDrawable.draw(canvas);
        }
    }

    @Override
    public boolean setVisible(boolean visible, boolean restart) {
        final boolean changed = super.setVisible(visible, restart);
        if (visible) {
            if (restart || changed) {
                boolean startFromZero = restart || (!mRunning) ||
                        getLevel() >= 10000;
                setFrame(startFromZero ? 0 : getLevel(), true, true);
            }
        } else {
            unscheduleSelf(this);
        }
        return changed;
    }

    @Override
    public void start() {
        if (!isRunning()) {
            // Start from 0th frame.
            setFrame(0, false, true);
        }
    }

    @Override
    public void stop() {
        if (isRunning()) {
            setLevel(0);
            unscheduleSelf(this);
        }
    }

    @Override
    public boolean isRunning() {
        return mRunning;
    }

    @Override
    public void run() {
        nextFrame(false);
    }

    @Override
    public void unscheduleSelf(@NonNull Runnable what) {
        mRunning = false;
        super.unscheduleSelf(what);
    }

    private void nextFrame(boolean unschedule) {
        int nextLevel = getLevel() + 300;
        if (nextLevel >= 10000) {
            nextLevel = 0;
        }

        setFrame(nextLevel, unschedule, true);
    }
}
