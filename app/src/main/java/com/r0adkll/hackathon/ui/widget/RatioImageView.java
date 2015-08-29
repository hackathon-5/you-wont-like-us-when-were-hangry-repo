package com.r0adkll.hackathon.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.r0adkll.hackathon.R;


public class RatioImageView extends ImageView {

    /***********************************************************************************************
     *
     * Constants
     *
     */

    private static final float DEFAULT_RATIO = 9f / 16f;

    /***********************************************************************************************
     *
     * Variables
     *
     */

    private float mRatio = DEFAULT_RATIO;

    /***********************************************************************************************
     *
     * Constructors
     *
     */

    public RatioImageView(Context context) {
        this(context, null);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttrs(context, attrs, defStyleAttr);
    }

    /***********************************************************************************************
     *
     * Helper Methods
     *
     */

    /**
     * Parse the attributes from the constructor when this view is constructed from layout
     */
    private void parseAttrs(Context context, AttributeSet attrs, int defStyle){
        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RatioImageView, defStyle, 0);
        if(a != null){
            mRatio = a.getFloat(R.styleable.RatioImageView_riv_aspect_ratio, DEFAULT_RATIO);
            a.recycle();
        }
    }

    public void setRatio(float ratio){
        mRatio = ratio;
    }

    /***********************************************************************************************
     *
     * View Methods
     *
     */

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Force 16:9 aspect ratio
        int width = getMeasuredWidth();
        int newHeight = (int) (width * mRatio);

        setMeasuredDimension(width, newHeight);
    }

}
