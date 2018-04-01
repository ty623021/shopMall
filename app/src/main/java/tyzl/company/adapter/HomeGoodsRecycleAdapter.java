package tyzl.company.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tyzl.company.R;
import tyzl.company.activity.shopping.GoodsDetailsActivity;
import tyzl.company.entity.GoodsInfo;
import tyzl.company.utils.AbImageUtil;
import tyzl.company.utils.AbToastUtil;


public class HomeGoodsRecycleAdapter extends RecyclerView.Adapter<HomeGoodsRecycleAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private List<GoodsInfo> list;
    private Context context;

    public HomeGoodsRecycleAdapter(Context context, List<GoodsInfo> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_home_goods_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final GoodsInfo info = list.get(position);
        AbImageUtil.glideImageList(info.getOriginal_img(), holder.goods_img, R.drawable.loading_img, R.drawable.load_fail);
        holder.shopping_title.setText(info.getGoods_name());
        holder.goods_amount.setText("库存：" + info.getStore_count());
        holder.goods_money.setText("￥" + info.getShop_price());
        holder.shop_car_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbToastUtil.showToast(context, info.getGoods_name());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsDetailsActivity.startActivity(context, info);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView goods_img, shop_car_img;
        private final TextView shopping_title, goods_amount, goods_money;
        private View itemView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            goods_img = (ImageView) itemView.findViewById(R.id.goods_img);
            shop_car_img = (ImageView) itemView.findViewById(R.id.shop_car_img);
            shopping_title = (TextView) itemView.findViewById(R.id.shopping_title);
            goods_amount = (TextView) itemView.findViewById(R.id.goods_amount);
            goods_money = (TextView) itemView.findViewById(R.id.goods_money);
        }
    }
}
