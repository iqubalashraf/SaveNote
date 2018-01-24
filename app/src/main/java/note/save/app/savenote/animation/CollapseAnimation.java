package note.save.app.savenote.animation;

import android.support.v7.widget.RecyclerView;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;

import note.save.app.savenote.utils.GeneralUtil;

/**
 * Created by ashrafiqubal on 24/01/18.
 */

public class CollapseAnimation extends TranslateAnimation implements
        TranslateAnimation.AnimationListener {

    private RecyclerView mainLayout;
    int panelWidth;

    public CollapseAnimation(RecyclerView layout, int width, int fromXType,
                             float fromXValue, int toXType, float toXValue, int fromYType,
                             float fromYValue, int toYType, float toYValue) {

        super(fromXType, fromXValue, toXType, toXValue, fromYType, fromYValue,
                toYType, toYValue);

        // Initialize
        mainLayout = layout;
        panelWidth = width;
        setDuration(GeneralUtil.ANIMATION_TIME_OUT);
        setFillAfter(false);
        setInterpolator(new AccelerateDecelerateInterpolator());
        setAnimationListener(this);

        // Clear left and right margins
        LayoutParams params = (LayoutParams) mainLayout.getLayoutParams();
        params.rightMargin = 0;
        params.leftMargin = 0;
        mainLayout.setLayoutParams(params);
        mainLayout.requestLayout();
        mainLayout.startAnimation(this);

    }

    public void onAnimationEnd(Animation animation) {
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }
}
