package tyzl.company.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/6.
 */

public class ShopCarModel implements Serializable {
    private int id,goods_id,goods_num;
    private String goods_name,member_goods_price,spec_key_name,imgUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }

    public String getMember_goods_price() {
        return member_goods_price;
    }

    public void setMember_goods_price(String member_goods_price) {
        this.member_goods_price = member_goods_price;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getSpec_key_name() {
        return spec_key_name;
    }

    public void setSpec_key_name(String spec_key_name) {
        this.spec_key_name = spec_key_name;
    }
}
