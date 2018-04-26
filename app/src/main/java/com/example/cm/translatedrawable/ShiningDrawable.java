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

    private boolean mReverse;
    private int mLevelStep = 200;
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
            scheduleSelf(this, SystemClock.uptimeMillis() + 30);
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

    public void setLevelStep(int levelStep) {
        if (levelStep <= 0 || levelStep >= 10000) {
            throw new IllegalArgumentException("levelStep " + levelStep + " out of range (0, 10000)");
        }

        mLevelStep = levelStep;
    }

    public void setReverse(boolean reverse) {
        mReverse = reverse;
    }

    @Override
    public void unscheduleSelf(@NonNull Runnable what) {
        mRunning = false;
        super.unscheduleSelf(what);
    }

    private void nextFrame(boolean unschedule) {
        int nextLevel = getNextLevel();
        setFrame(nextLevel, unschedule, true);
    }

    private int getNextLevel() {
        int nextLevel = getLevel();
        if (mDirection > 0) {
            nextLevel += mLevelStep;
        } else {
            nextLevel -= mLevelStep;
        }

        if (nextLevel >= 10000) {
            mDirection = mReverse ? -1 : 1;
            nextLevel = mReverse ? 10000 : 0;
        }

        if (nextLevel < 0) {
            mDirection = 1;
            nextLevel = 0;
        }

        return nextLevel;
    }

    private int mDirection = 1;
}
