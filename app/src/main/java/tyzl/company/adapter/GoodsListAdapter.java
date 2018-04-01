package tyzl.company.adapter;

/**
 * 投资记录
 */

import android.content.Context;
import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

import tyzl.company.R;
import tyzl.company.entity.GoodsInfo;
import tyzl.company.utils.AbImageUtil;

public class GoodsListAdapter extends CommonAdapter<GoodsInfo> {

    public GoodsListAdapter(Context context, int layoutId, List<GoodsInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, GoodsInfo info, int position) {
        TextView market_price = viewHolder.getView(R.id.market_price);
        ImageView goods_icon = viewHolder.getView(R.id.goods_icon);
        viewHolder.setText(R.id.goods_name, info.getGoods_name());
        viewHolder.setText(R.id.shop_price, "￥" + info.getShop_price());
        viewHolder.setText(R.id.sales_sum, info.getSales_sum() + "人已购买");
        market_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        market_price.getPaint().setAntiAlias(true);
        market_price.setText("￥" + info.getMarket_price());
        AbImageUtil.glideImageList(info.getOriginal_img(), goods_icon, R.drawable.loading_img, R.drawable.load_fail);
    }
}
