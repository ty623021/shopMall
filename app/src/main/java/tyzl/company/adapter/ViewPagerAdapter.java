package tyzl.company.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import tyzl.company.R;

/**
 * Created by geek on 2016/6/21.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private List<View> list = new ArrayList<View>();
    private Context context;

    public ViewPagerAdapter(Context context, List<View> list) {
        this.list = list;
        this.context = context;
    }

    public void addViewList(List<View> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (0 != list.size()) {
            position = position % list.size();
            if (position < 0) {
                position = list.size() + position;
            }
            View view = list.get(position);
            //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
            ViewParent vp = view.getParent();
            if (vp != null) {
                ViewGroup parent = (ViewGroup) vp;
                parent.removeView(view);
            }
            container.addView(view);
            return view;
        } else {
            ImageView view = new ImageView(context);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            ViewParent vp = view.getParent();
            if (vp != null) {
                ViewGroup parent = (ViewGroup) vp;
                parent.removeView(view);
            }
            view.setBackgroundResource(R.drawable.default_banner);
            container.addView(view);
            return view;
        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
//        return list.size();
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView(list.get(position % list.size()));
    }

    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        return super.getItemPosition(object);
    }

}
