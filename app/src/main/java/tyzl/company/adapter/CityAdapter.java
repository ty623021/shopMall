package tyzl.company.adapter;

import android.content.Context;

import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

import tyzl.company.R;
import tyzl.company.entity.CityInfo;

/**
 * 城市列表适配器
 */
public class CityAdapter extends CommonAdapter<CityInfo> {

    public CityAdapter(Context context, int layoutId, List<CityInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, CityInfo info, int position) {
        viewHolder.setText(R.id.tv_address_name, info.getName());
    }
}
