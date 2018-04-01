package tyzl.company.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

import tyzl.company.R;
import tyzl.company.activity.address.CreateAddressActivity;
import tyzl.company.entity.AddressInfo;
import tyzl.company.global.Config;
import tyzl.company.utils.AbJsonUtil;
import tyzl.company.utils.AbStringUtil;
import tyzl.company.utils.AbToastUtil;
import tyzl.company.volley.IRequest;
import tyzl.company.volley.RequestListener;
import tyzl.company.volley.RequestParams;
import tyzl.company.weight.AlertDialog;

/**
 * Created by hjy on 2016/11/5.
 * 地址管理
 */
public class ManageAddressAdapter extends CommonAdapter<AddressInfo> {
    private Context context;
    private List<AddressInfo> list;
    private AlertDialog dialog;
    private OnDelListener onDelListener;

    public ManageAddressAdapter(Context context, int layoutId, List<AddressInfo> datas) {
        super(context, layoutId, datas);
        this.context = context;
        this.list = datas;
    }

    public OnDelListener getOnDelListener() {
        return onDelListener;
    }

    public void setOnDelListener(OnDelListener onDelListener) {
        this.onDelListener = onDelListener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, final AddressInfo info, final int position) {
        viewHolder.setText(R.id.goods_receipt_phone, info.getMobile());
        viewHolder.setText(R.id.goods_receipt_name, "收货人：" + info.getConsignee());
        viewHolder.setText(R.id.tv_address, AbStringUtil.getAddress(info.getProvince(), info.getCity(), info.getDistrict(), info.getAddress()));
        CheckBox select_default_address = viewHolder.getView(R.id.select_default_address);
        if (info.getIs_default().equals("1")) {
            select_default_address.setChecked(true);
            select_default_address.setEnabled(false);
            select_default_address.setSelected(true);
        } else {
            select_default_address.setChecked(false);
            select_default_address.setEnabled(true);
            select_default_address.setSelected(false);
        }
        viewHolder.setOnClickListener(R.id.edit_address, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAddressActivity.startActivity(context, info);
            }
        });

        viewHolder.setOnClickListener(R.id.delete_address, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDelTip(position);
            }
        });
        viewHolder.setOnClickListener(R.id.select_default_address, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressInfo info = list.get(position);
                setDefaultAddress(position, info);
            }
        });
    }

    private void alertDelTip(final int position) {
        dialog = new AlertDialog(context);
        dialog.setBtColorConfirm(R.drawable.shape_btn_background_blue);
        dialog.showDialog("确认要删除此地址吗?", new AlertDialog.DialogOnClickListener() {
            @Override
            public void onPositiveClick() {
                AddressInfo info = list.get(position);
                delAddress(info);
                dialog.dismiss();
            }

            @Override
            public void onNegativeClick() {
                dialog.dismiss();
            }
        });
    }

    private void delAddress(AddressInfo info) {
        RequestParams params = new RequestParams();
        params.put("address_id", info.getAddress_id());
        IRequest.post(context, Config.URL_DEL_ADDRESS, params, "正在删除...", new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                if (AbJsonUtil.isSuccess(json)) {
                    if (onDelListener != null) {
                        onDelListener.onDelListener();
                    }
                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
            }

            @Override
            public void requestError(String message) {
                AbToastUtil.showToast(context, message);
            }
        });
    }

    private void setDefaultAddress(final int position, AddressInfo info) {
        RequestParams params = new RequestParams();
        params.put("address_id", info.getAddress_id());
        IRequest.post(context, Config.URL_SET_DEFAULT_ADDRESS, params, "正在设置...", new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                if (AbJsonUtil.isSuccess(json)) {
                    for (int i1 = 0; i1 < list.size(); i1++) {
                        AddressInfo info1 = list.get(i1);
                        if (i1 != position) {
                            info1.setIs_default("0");
                        } else {
                            info1.setIs_default("1");
                        }
                        notifyDataSetChanged();
                    }
                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
            }

            @Override
            public void requestError(String message) {
                AbToastUtil.showToast(context, message);
            }
        });
    }

    public interface OnDelListener {
        void onDelListener();
    }
}
