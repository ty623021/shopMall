package tyzl.company.entity;

/**
 * Created by hjy on 2017/3/28.
 */

public class MyCouponInfo {
    private int type;//优惠券的状态
    private int nums;//优惠券的个数

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }
}
