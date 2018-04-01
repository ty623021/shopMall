package tyzl.company.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tyzl.company.R;
import tyzl.company.entity.ShoppingCartInfo;
import tyzl.company.utils.AbLogUtil;
import tyzl.company.weight.AlertDialog;

/**
 * Created by hjy on 2016/11/5.
 */
public class ShoppingCarAdapter extends BaseAdapter {
    private Context context;
    private List<ShoppingCartInfo> list;
    private ShoppingAdapterClick selectClick;
    private AlertDialog dialog;

    public ShoppingAdapterClick getSelectClick() {
        return selectClick;
    }

    public void setSelectClick(ShoppingAdapterClick selectClick) {
        this.selectClick = selectClick;
    }

    public List<ShoppingCartInfo> getList() {
        return list;
    }

    public void setList(List<ShoppingCartInfo> list) {
        this.list = list;
    }

    public ShoppingCarAdapter(Context context, List<ShoppingCartInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View contentView, ViewGroup viewGroup) {
        final ShoppingCartInfo info = list.get(i);
        MyHolder myHolder = null;
        if (contentView == null) {
            myHolder = new MyHolder();
            contentView = LayoutInflater.from(context).inflate(R.layout.shopping_car_fragment_item, null);
            myHolder.checkBox = (CheckBox) contentView.findViewById(R.id.checkBox);
            myHolder.goods_icon = (ImageView) contentView.findViewById(R.id.goods_icon);
            myHolder.goods_name = (TextView) contentView.findViewById(R.id.goods_name);
            contentView.setTag(myHolder);
        } else {
            myHolder = (MyHolder) contentView.getTag();
        }
//        if (info.getSelected() == 0) {
//            myHolder.checkBox.setChecked(false);
//        } else {
//            myHolder.checkBox.setChecked(true);
//        }
//        myHolder.goods_name.setText(info.getGoods_name());
//        myHolder.money_Amount.setText((info.getGoods_num() * info.getReal_price()) + "元");
//        myHolder.mall_Amount.setText("+" + (info.getGoods_num() * info.getReal_integral()) + "积分");
//        myHolder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDelTip(i);
//            }
//        });
//        myHolder.goods_number.setText(info.getGoods_num() + "");
//        myHolder.remove.setOnClickListener(new RemoveOnclick(i));
//        myHolder.add.setOnClickListener(new AddOnclick(i));
//        myHolder.checkBox.setOnClickListener(new CheckOnclick(i));
//        AbImageUtil.glideImageList(info.getImgUrl(), myHolder.goods_icon, R.drawable.fail_2);

        return contentView;
    }


    /**
     * 设置全选
     *
     * @param is
     */
    public void setSelectAll(boolean is) {
//        for (ShoppingCartInfo info : list) {
//            if (is) {
//                info.setSelected(1);
//            } else {
//                info.setSelected(0);
//            }
//        }
        notifyDataSetChanged();
        triggerClick();
    }

    /**
     * 计算
     */
    private void triggerClick() {
        if (selectClick != null) {
            selectClick.shoppingAdapterClick(list);
        }
    }


    class CheckOnclick implements View.OnClickListener {
        private int position;

        public CheckOnclick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            ShoppingCartInfo info = list.get(position);
            AbLogUtil.e("position", position + "");
//            if (info.getSelected() == 1) {
//                info.setSelected(0);
//            } else {
//                info.setSelected(1);
//            }
            notifyDataSetChanged();
            triggerClick();
        }
    }

    class RemoveOnclick implements View.OnClickListener {
        private int position;

        public RemoveOnclick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            AbLogUtil.e("position", position + "");
            ShoppingCartInfo info = list.get(position);
//            if (info.getGoods_num() > 1) {
//                info.setGoods_num(info.getGoods_num() - 1);
//                notifyDataSetChanged();
//                triggerClick();
//            }
        }
    }

    class AddOnclick implements View.OnClickListener {
        private int position;

        public AddOnclick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            AbLogUtil.e("position", position + "");
            ShoppingCartInfo info = list.get(position);
            //选择的数量小于库存
//            if (info.getGoods_num() < info.getStore_count()) {
//                info.setGoods_num(info.getGoods_num() + 1);
//                notifyDataSetChanged();
//                triggerClick();
//            } else {
//                AbToastUtil.showToast(context, "库存不足");
//            }
        }
    }


    public class MyHolder {
        private CheckBox checkBox;
        private ImageView goods_icon, delete;
        private TextView goods_name, remove, add, goods_number;
        private TextView money_Amount;
        private TextView mall_Amount;
    }

    private void alertDelTip(final int position) {
        dialog = new AlertDialog(context);
        dialog.setBtCancel("确定");
        dialog.setBtConfirm("再想想");
        dialog.showDialog("确认要删除此商品吗?", new AlertDialog.DialogOnClickListener() {
            @Override
            public void onPositiveClick() {
                dialog.dismiss();
            }

            @Override
            public void onNegativeClick() {
                ShoppingCartInfo info = list.get(position);
//                delGoods(position, info);
                dialog.dismiss();
            }
        });
    }

//    /**
//     * 删除商品
//     */
//    public void delGoods(final int position, ShoppingCartInfo info) {
//        RequestParams params = new RequestParams();
//        params.put("ids", info.getId());
//        IRequest.post(context, MallConfig.DEL_CART_URL, params, "正在删除", new RequestListener() {
//            @Override
//            public void requestSuccess(String json) {
//                if (AbJsonUtil.isMallSuccess(json)) {
//                    list.remove(position);
//                    notifyDataSetChanged();
//                    triggerClick();
//                } else {
//                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
//                }
//            }
//
//            @Override
//            public void requestError(String message) {
//                AbToastUtil.showToast(context, message);
//            }
//        });
//
//    }

    public interface ShoppingAdapterClick {
        void shoppingAdapterClick(List<ShoppingCartInfo> list);
    }
}
