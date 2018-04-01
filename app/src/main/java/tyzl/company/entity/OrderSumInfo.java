package tyzl.company.entity;

import java.io.Serializable;

/**
 * Created by geek on 2016/11/1.
 * 商品
 */
public class OrderSumInfo implements Serializable {
    private double total_real_price;//总金额
    private double total_real_integral;//总积分

    public double getTotal_real_price() {
        return total_real_price;
    }

    public void setTotal_real_price(double total_real_price) {
        this.total_real_price = total_real_price;
    }

    public double getTotal_real_integral() {
        return total_real_integral;
    }

    public void setTotal_real_integral(double total_real_integral) {
        this.total_real_integral = total_real_integral;
    }
}
