package tyzl.company.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

import tyzl.company.R;
import tyzl.company.entity.HomeTypeInfo;
import tyzl.company.utils.AbImageUtil;

/**
 * Created by geek on 2016/11/1.
 * 商品
 */
public class HomeTypeAdapter extends CommonAdapter<HomeTypeInfo> {

    public HomeTypeAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, HomeTypeInfo info, int position) {
        viewHolder.setText(R.id.type_name, info.getName());
        AbImageUtil.glideImageList(info.getImgUrl(), (ImageView) viewHolder.getView(R.id.type_icon), R.drawable.loading_img, R.drawable.load_fail);
    }
}
