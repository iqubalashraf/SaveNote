package note.save.app.savenote.animation;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.RelativeLayout;

import note.save.app.savenote.utils.GeneralUtil;

/**
 * Created by ashrafiqubal on 24/01/18.
 */

public class ExpandAnimation extends TranslateAnimation implements
        Animation.AnimationListener {

    int panelWidth;
    private RelativeLayout mainLayout;
    private FrameLayout menuPanel;

    public ExpandAnimation(RelativeLayout layout, int width, int fromXType,
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
        mainLayout.startAnimation(this);
    }

    public void onAnimationEnd(Animation arg0) {

        // Create margin and align left
        LayoutParams params = (LayoutParams) mainLayout.getLayoutParams();
        params.leftMargin = panelWidth;
        mainLayout.clearAnimation();
        mainLayout.setLayoutParams(params);
        mainLayout.requestLayout();

    }

    public void onAnimationRepeat(Animation arg0) {

    }

    public void onAnimationStart(Animation arg0) {

    }

}
