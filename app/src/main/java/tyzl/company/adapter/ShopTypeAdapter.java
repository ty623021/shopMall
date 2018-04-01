package tyzl.company.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tyzl.company.R;
import tyzl.company.entity.GoodsInfo;
import tyzl.company.utils.AbImageUtil;


public class ShopTypeAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<GoodsInfo> list;
    private Context context;

    public ShopTypeAdapter(Context context, ArrayList<GoodsInfo> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (list != null && list.size() > 0)
            return list.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MyView view;
        if (convertView == null) {
            view = new MyView();
            convertView = mInflater.inflate(R.layout.list_shop_type_item, null);
            view.icon = (ImageView) convertView.findViewById(R.id.type_icon);
            view.name = (TextView) convertView.findViewById(R.id.type_name);
            convertView.setTag(view);
        } else {
            view = (MyView) convertView.getTag();
        }
        GoodsInfo info = list.get(position);
        AbImageUtil.glideImageList(info.getOriginal_img(), view.icon, R.drawable.fail);
        view.name.setText(info.getGoods_name());
        return convertView;
    }


    private class MyView {
        private ImageView icon;
        private TextView name;
    }

}
