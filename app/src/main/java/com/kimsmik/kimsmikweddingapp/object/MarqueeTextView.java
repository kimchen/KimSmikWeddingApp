package com.kimsmik.kimsmikweddingapp.object;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by Kim on 2015/6/15.
 */
public class MarqueeTextView extends TextView {
    private Context mContext;
    private Scroller mScroller;

    private int mDistance;
    private int mDuration;
    private float  mVelocity;
    public MarqueeTextView(Context context) {
        super(context);
        mContext = context;
        setSingleLine();
        mScroller = new Scroller(context,new LinearInterpolator());
        setScroller(mScroller);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setSingleLine();
        mScroller = new Scroller(context,new LinearInterpolator());
        setScroller(mScroller);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setSingleLine();
        mScroller = new Scroller(context,new LinearInterpolator());
        setScroller(mScroller);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
        } else {
            if(mScroller.isFinished()){
                mScroller.startScroll(-getWidth(), 0, calculateMoveDistance(false, mVelocity), 0, mDuration);
            }
        }
    }

    private int calculateMoveDistance(boolean isFirstRun, float velocity){
        Rect rect = new Rect();
        String textString = (String) getText();
        getPaint().getTextBounds(textString, 0, textString.length(), rect);
        int moveDistance = rect.width();
        this.mDistance = isFirstRun ? moveDistance : moveDistance + getWidth();
        this.mDuration = (int) (velocity * mDistance);
        return this.mDistance;
    }

    public void scrollText(float velocity){
        this.mVelocity = velocity;
        mScroller.startScroll(0, 0, calculateMoveDistance(true, velocity), 0, mDuration);
    }
}
