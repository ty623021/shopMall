package tyzl.company.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tyzl.company.R;
import tyzl.company.activity.shopping.GoodsListActivity;
import tyzl.company.entity.HomeTypeInfo;
import tyzl.company.utils.AbImageUtil;


public class ShopTypeRecycleAdapter extends RecyclerView.Adapter<ShopTypeRecycleAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<HomeTypeInfo> list;
    private Context context;


    public ShopTypeRecycleAdapter(Context context, ArrayList<HomeTypeInfo> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_shop_type_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final HomeTypeInfo info = list.get(position);
        AbImageUtil.glideImageList(info.getImgUrl(), holder.icon, R.drawable.loading_img, R.drawable.load_fail);
        holder.name.setText(info.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsListActivity.startActivity(context, info);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView icon;
        private final TextView name;
        private View itemView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            icon = (ImageView) itemView.findViewById(R.id.type_icon);
            name = (TextView) itemView.findViewById(R.id.type_name);
        }
    }
}
