package tyzl.company.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

import tyzl.company.R;
import tyzl.company.entity.GoodsInfo;
import tyzl.company.utils.AbImageUtil;
import tyzl.company.utils.AbToastUtil;

/**
 * Created by geek on 2016/11/1.
 * 商品
 */
public class HomeGoodsListAdapter extends CommonAdapter<GoodsInfo> {
    private Context context;

    public HomeGoodsListAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, final GoodsInfo info, int position) {
        AbImageUtil.glideImageList(info.getOriginal_img(), (ImageView) viewHolder.getView(R.id.goods_img), R.drawable.loading_img, R.drawable.load_fail);
        viewHolder.setText(R.id.shopping_title, info.getGoods_name());
        viewHolder.setText(R.id.goods_amount, "库存：" + info.getStore_count());
        viewHolder.setText(R.id.goods_money, "￥" + info.getShop_price());
        viewHolder.setOnClickListener(R.id.shop_car_img, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbToastUtil.showToast(context, info.getGoods_name());
            }
        });

    }
}
