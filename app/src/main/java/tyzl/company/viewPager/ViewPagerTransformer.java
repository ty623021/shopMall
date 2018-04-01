package tyzl.company.viewPager;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * viewPager滑动动画
 * Created by geek on 2016/5/13.
 */
public class ViewPagerTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE_X = 0.9f;
    private static final float MIN_SCALE_Y = 0.9f;

    @Override
    public void transformPage(View view, float position) {
        if (-1 <= position && position <= 1) {
            // a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
            float scaleFactorX = Math.max(MIN_SCALE_X, 1 - Math.abs(position));
            float scaleFactorY = Math.max(MIN_SCALE_Y, 1 - Math.abs(position));
            ViewHelper.setScaleX(view, scaleFactorX);
            ViewHelper.setScaleY(view, scaleFactorY);
        }
    }
}
