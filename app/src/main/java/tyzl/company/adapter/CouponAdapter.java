package tyzl.company.adapter;

/**
 * 优惠券
 */

import android.content.Context;
import android.view.View;

import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

import tyzl.company.R;
import tyzl.company.entity.CouponInfo;

public class CouponAdapter extends CommonAdapter<CouponInfo> {
    private List<CouponInfo> item;
    private Context context;
    private CouponInfo selectInfo;

    public CouponAdapter(Context context, int layoutId, List<CouponInfo> datas) {
        super(context, layoutId, datas);
        this.item = datas;
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, CouponInfo info, final int position) {
        viewHolder.setText(R.id.coupon_name, info.getName());
        viewHolder.setText(R.id.coupon_time, info.getTime());
        viewHolder.setText(R.id.coupon_amount, info.getAmount() + "元");
        viewHolder.setChecked(R.id.checkBox, info.isSelected());

        viewHolder.setOnClickListener(R.id.checkBox, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatus(position);
            }
        });
        viewHolder.setOnClickListener(R.id.coupon_relative, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatus(position);
            }
        });
    }


    public void updateStatus(int position) {
        selectInfo = item.get(position);
        for (int i = 0; i < item.size(); i++) {
            CouponInfo info = item.get(i);
            if (position == i) {
                info.setIsSelected(true);
            } else {
                info.setIsSelected(false);
            }
        }
        notifyDataSetChanged();
    }
}
