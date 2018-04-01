package tyzl.company.adapter;

import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.List;

/**
 * viewpage 适配器
 *
 * @author Administrator
 */
public class LicenseViewPagerAdapter extends PagerAdapter {

    // 界面列表
    private List<View> views;
    public Activity context;

    public LicenseViewPagerAdapter(List<View> views, Activity context) {
        this.views = views;
        this.context = context;
    }

    // 删除arg1位置的界面
    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {

        ((ViewPager) arg0).removeView(views.get(arg1));
    }

    @Override
    public void finishUpdate(View arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }
        return 0;
    }

    // 初始化arg1位置的界面
    @Override
    public Object instantiateItem(ViewGroup container, final int arg1) {
        View view = views.get(arg1);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
        }
        container.addView(view);
        return view;
    }

    // 判断是否由对象生成界面
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public Parcelable saveState() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void startUpdate(View arg0) {

    }


}
