package tyzl.company.entity;

import java.io.Serializable;

/**
 * Created by geek on 2016/11/1.
 * 商品
 */
public class GoodsDetailsInfo implements Serializable {
    private String goods_content;//商品详情
    private String goods_name;//商品名称
    private String goods_id;//商品id
    private String original_img;//商品缩略图路径
    private int store_count;//库存
    private int sales_sum;//销售数量
    private double shop_price;//现金价
    private double market_price;//市场价格
    private double franking;//邮费

    public String getGoods_content() {
        return goods_content;
    }

    public void setGoods_content(String goods_content) {
        this.goods_content = goods_content;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getOriginal_img() {
        return original_img;
    }

    public void setOriginal_img(String original_img) {
        this.original_img = original_img;
    }

    public int getStore_count() {
        return store_count;
    }

    public void setStore_count(int store_count) {
        this.store_count = store_count;
    }

    public int getSales_sum() {
        return sales_sum;
    }

    public void setSales_sum(int sales_sum) {
        this.sales_sum = sales_sum;
    }

    public double getShop_price() {
        return shop_price;
    }

    public void setShop_price(double shop_price) {
        this.shop_price = shop_price;
    }

    public double getMarket_price() {
        return market_price;
    }

    public void setMarket_price(double market_price) {
        this.market_price = market_price;
    }

    public double getFranking() {
        return franking;
    }

    public void setFranking(double franking) {
        this.franking = franking;
    }
}
