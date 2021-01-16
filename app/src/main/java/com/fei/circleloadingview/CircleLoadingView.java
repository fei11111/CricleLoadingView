package com.fei.circleloadingview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import androidx.annotation.ColorInt;

/**
 * @ClassName: CircleLoadingView
 * @Description: java类作用描述
 * @Author: Fei
 * @CreateDate: 2021-01-16 11:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-01-16 11:57
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CircleLoadingView extends RelativeLayout {

    private float mDistance = 30;//滑动距离
    private float mCircleWidth = 10;//圆形直径
    private int mLeftColor = Color.RED;
    private int mMiddleColor = Color.BLUE;
    private int mRightColor = Color.GREEN;
    private CircleView mLeftView;
    private CircleView mMiddleView;
    private CircleView mRightView;
    private boolean mIsRevers = false;//反转
    private int DURATION = 350;

    public CircleLoadingView(Context context) {
        this(context, null);
    }

    public CircleLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleLoadingView);
        mLeftColor = typedArray.getColor(R.styleable.CircleLoadingView_leftColor, mLeftColor);
        mMiddleColor = typedArray.getColor(R.styleable.CircleLoadingView_middleColor, mMiddleColor);
        mRightColor = typedArray.getColor(R.styleable.CircleLoadingView_rightColor, mRightColor);
        mDistance = typedArray.getDimension(R.styleable.CircleLoadingView_distance, dp2px(mDistance));
        mCircleWidth = typedArray.getDimension(R.styleable.CircleLoadingView_circleWidth, dp2px(mCircleWidth));
        typedArray.recycle();

        initChild(context);

        post(new Runnable() {
            @Override
            public void run() {
                //执行动画
                executeAnimation();
            }
        });
    }

    /**
     * 开始动画
     */
    private void executeAnimation() {
        Animator leftAnimator;
        Animator rightAnimator;
        if (mIsRevers) {
            leftAnimator = getAnimator(mLeftView, -mDistance, 0);
            rightAnimator = getAnimator(mRightView, mDistance, 0);
        } else {
            leftAnimator = getAnimator(mLeftView, 0, -mDistance);
            rightAnimator = getAnimator(mRightView, 0, mDistance);
        }
        AnimatorSet set = new AnimatorSet();
        set.setDuration(DURATION);
        set.playTogether(leftAnimator, rightAnimator);
        if (mIsRevers) {
            set.setInterpolator(new AccelerateInterpolator());
        } else {
            set.setInterpolator(new DecelerateInterpolator());
        }
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mIsRevers) {
                    int leftColor = mLeftView.getColor();
                    int middleColor = mMiddleView.getColor();
                    int rightColor = mRightView.getColor();

                    mLeftView.setColor(middleColor);
                    mMiddleView.setColor(rightColor);
                    mRightView.setColor(leftColor);
                }
                mIsRevers = !mIsRevers;
                executeAnimation();
            }
        });
        set.start();
    }

    /**
     * 返回动画
     *
     * @param targetView
     * @param begin
     * @param end
     * @return
     */
    private Animator getAnimator(View targetView, float begin, float end) {
        return ObjectAnimator.ofFloat(targetView, "translationX", begin, end);
    }

    private float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    /**
     * 创建三个圆形，并添加入父类
     *
     * @param context
     */
    private void initChild(Context context) {
        setBackgroundColor(Color.WHITE);
        mLeftView = getCircleView(context, mLeftColor);
        mMiddleView = getCircleView(context, mMiddleColor);
        mRightView = getCircleView(context, mRightColor);
        addView(mLeftView);
        addView(mRightView);
        addView(mMiddleView);
    }

    /**
     * 获取圆形
     *
     * @param context
     * @param color
     * @return
     */
    private CircleView getCircleView(Context context, @ColorInt int color) {
        CircleView circleView = new CircleView(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) mCircleWidth, (int) mCircleWidth);
        layoutParams.addRule(CENTER_IN_PARENT);
        circleView.setLayoutParams(layoutParams);
        circleView.setColor(color);
        return circleView;
    }
}
