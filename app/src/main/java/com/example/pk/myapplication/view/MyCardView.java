package com.example.pk.myapplication.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

/**
 * Created by pk on 21.06.2017.
 */

public class MyCardView extends CardView {
    boolean mFlag;

    public MyCardView(Context context) {
        super(context);
    }

    public MyCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAnimationEnd() {
        super.onAnimationEnd();
        if (mFlag) {
            setVisibility(INVISIBLE);
        } else {
            setVisibility(VISIBLE);
        }
    }

    void hide(boolean flag) {
        mFlag = flag;
    }
}
