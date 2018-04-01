package tyzl.company.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import tyzl.company.R;
import tyzl.company.entity.ShopCartInfo;
import tyzl.company.utils.AbImageUtil;
import tyzl.company.utils.MathUtil;

/**
 * Created by Administrator on 2017/3/7.
 */

public class ShoppingCarRecyclerViewAdapter extends RecyclerView.Adapter<ShoppingCarRecyclerViewAdapter.ShpoppingCarHolder> {

    private List<ShopCartInfo> list;
    private Context context;
    private  OnAdapterCallBackListener onAdapterCallBackListener;
    public ShoppingCarRecyclerViewAdapter(List<ShopCartInfo> list){
        this.list=list;
    }

    @Override
    public ShpoppingCarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context= parent.getContext();
       View item= LayoutInflater.from(context).inflate(R.layout.item_shopping,null);
        item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return  new  ShpoppingCarHolder(item);
    }

    @Override
    public void onBindViewHolder(final ShpoppingCarHolder holder, int position) {
       /* final ShopCartInfo shoppingCartInfo= list.get(holder.getAdapterPosition());
        holder.tv_discrip.setText(shoppingCartInfo.getGoods_name());
       // holder.tv_service.setText(shoppingCartInfo.getService());
        holder.tv_color.setText(shoppingCartInfo.getSpec_key_name());
        holder.tv_price.setText("￥"+ shoppingCartInfo.getMember_goods_price());
        holder.tv_number.setText("X "+ String.valueOf(shoppingCartInfo.getGoods_num()));
        AbImageUtil.loadImgWithGlide(shoppingCartInfo.getImgUrl(),holder.img_content);

        if (shoppingCartInfo.isSelect()){
            holder.img_select.setBackgroundResource(R.mipmap.shopping_select);
        }else{
            holder.img_select.setBackgroundResource(R.mipmap.shopping_normal);
        }
        holder.img_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shoppingCartInfo.isSelect()){
                    shoppingCartInfo.setSelect(false);
                }else{
                    shoppingCartInfo.setSelect(true);
                }
                notifyItemChanged(holder.getAdapterPosition());
                        if (onAdapterCallBackListener!=null){
                            onAdapterCallBackListener.getAllPriceListner(computerAllPrice(),isAllSelect());
                }
            }
        });*/

    }

    @Override
    public int getItemCount() {
       return list.size();
    }

    class ShpoppingCarHolder extends RecyclerView.ViewHolder{
          ImageView img_select,img_content;
         TextView tv_discrip,tv_service,tv_color,tv_price,tv_number;

        public ShpoppingCarHolder(View itemView) {
            super(itemView);
            img_select=(ImageView)itemView.findViewById(R.id.img_select);
            img_content=(ImageView)itemView.findViewById(R.id.img_content);
            tv_discrip=(TextView)itemView.findViewById(R.id.tv_discrip);
            tv_service=(TextView)itemView.findViewById(R.id.tv_service);
            tv_color=(TextView)itemView.findViewById(R.id.tv_color);
            tv_price=(TextView)itemView.findViewById(R.id.tv_price);
            tv_number=(TextView)itemView.findViewById(R.id.tv_number);
        }
    }

    public void selectAll(){
        for (ShopCartInfo shoppingCartInfo:list){
            shoppingCartInfo.setSelect(true);
        }
        notifyDataSetChanged();
        if (onAdapterCallBackListener!=null){
            onAdapterCallBackListener.getAllPriceListner(computerAllPrice(),true);
        }
    }
    public void quiteSelectAll(){
        for (ShopCartInfo shoppingCartInfo:list){
            shoppingCartInfo.setSelect(false);
        }
        notifyDataSetChanged();
        if (onAdapterCallBackListener!=null){
            onAdapterCallBackListener.getAllPriceListner(computerAllPrice(),false);
        }
    }

    public interface OnAdapterCallBackListener{
        void getAllPriceListner(String allPrice, boolean isShowSelect);
    }
    public void setOnAdapterCallBackListner(OnAdapterCallBackListener onAdapterCallBackListner){
        this.onAdapterCallBackListener=onAdapterCallBackListner;
    }

    public String computerAllPrice(){
        double allPrice=0.00;
        for (ShopCartInfo shoppingCartInfo:list){
            if (shoppingCartInfo.isSelect()){
                allPrice= MathUtil.add(allPrice,MathUtil.multiply(Double.parseDouble(shoppingCartInfo.getMember_goods_price()), Double.parseDouble(String.valueOf(shoppingCartInfo.getGoods_num()))));
            }
        }
        return  MathUtil.round(String.valueOf(allPrice),2);
    }

    public void removeItem(){
        if (list!=null) {
            for (int i = list.size()-1; i >=0; i--) {
                ShopCartInfo shoppingCartInfo = list.get(i);
                if (shoppingCartInfo.isSelect()) {
                    list.remove(i);
                    notifyItemRemoved(i);
                }
            }

            if (onAdapterCallBackListener!=null){
//                onAdapterCallBackListener.getAllPriceListner(computerAllPrice(),isItemSelect());
                onAdapterCallBackListener.getAllPriceListner("0.00",false);
            }
        }
    }
    public boolean isItemSelect(){
        if (list!=null){
            for (ShopCartInfo shoppingCartInfo:list){
                if(shoppingCartInfo.isSelect()){
                    return  true;
                }
            }
        }
        return false;
    }
    public void set(List<ShopCartInfo> list){
        this.list=list;
        notifyDataSetChanged();
    }
    public void addAll(List<ShopCartInfo> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    private  boolean isAllSelect(){
        if (list!=null){
            for (ShopCartInfo shoppingCartInfo:list){
                if(!shoppingCartInfo.isSelect()){
                    return  false;
                }
            }
        }
        return true;
    }

}
