package tyzl.company.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import tyzl.company.R;
import tyzl.company.entity.CouponInfo;
import tyzl.company.global.Constant;

/**
 * 我的优惠券的  adapter
 * Created by hjy on 2017/3/23.
 */

public class CenterMyCouponAdapter extends CommonAdapter<CouponInfo> {


    public CenterMyCouponAdapter(Context context, int layoutId, List<CouponInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, CouponInfo item, int position) {

        viewHolder.setText(R.id.coupon_amount, item.getMoney() + "元");
        viewHolder.setText(R.id.coupon_name, item.getName());
        viewHolder.setText(R.id.use_rule, item.getRule());
        viewHolder.setText(R.id.coupon_time, "有效期：" + item.getSend_time() + "-" + item.getUse_end_time());


        if (item.getUseType().equals(Constant.RED_PACKET_USED)) {
//            viewHolder.setAlpha(R.id.coupon_linear,0.5f);
            viewHolder.setBackgroundRes(R.id.coupon_linear, R.color.transparent);
            viewHolder.setImageResource(R.id.coupon_state_img, R.drawable.center_coupon_used);
        } else if (item.getUseType().equals(Constant.RED_PACKET_OVERDUE)) {
//            viewHolder.setAlpha(R.id.coupon_linear,0.5f);
            viewHolder.setBackgroundRes(R.id.coupon_linear, R.color.transparent);
            viewHolder.setImageResource(R.id.coupon_state_img, R.drawable.center_coupon_over);

        }





    }




}
