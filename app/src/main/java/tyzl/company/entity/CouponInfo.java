package tyzl.company.entity;

import java.io.Serializable;

/**
 * Created by hjy on 2016/11/5.
 * 优惠券
 */
public class CouponInfo implements Serializable {

    private String id;
    private double amount;
    private String time;

    private boolean isSelected;//是否选中
    private int nextPage;//下一页
    private int totalPages;//总页数
    private String send_time;//发送优惠券的时间
    private String use_end_time;//使用截止的时间
    private double money;//金额
    private double condition;//限制金额
    private String rule;//优惠券规则
    private String name;//优惠券的名称
    private String code;//优惠券兑换码
    private int order_id;//订单ID
    private String useType;//优惠券的状态
    private int countNum;//优惠券的个数


    public int getCountNum() {
        return countNum;
    }

    public void setCountNum(int countNum) {
        this.countNum = countNum;
    }

    public CouponInfo(String id, int order_id, String code, String name, String rule, double condition, double money, String use_end_time, String send_time, int totalPages, int nextPage) {
        this.id = id;
        this.order_id = order_id;
        this.code = code;
        this.name = name;
        this.rule = rule;
        this.condition = condition;
        this.money = money;
        this.use_end_time = use_end_time;
        this.send_time = send_time;
        this.totalPages = totalPages;
        this.nextPage = nextPage;
    }

    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }

    public CouponInfo(String useType) {
        this.useType = useType;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public double getCondition() {
        return condition;
    }

    public void setCondition(double condition) {
        this.condition = condition;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getUse_end_time() {
        return use_end_time;
    }

    public void setUse_end_time(String use_end_time) {
        this.use_end_time = use_end_time;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CouponInfo(String id, String name, double amount, String time, boolean isSelected) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.time = time;
        this.isSelected = isSelected;
    }

    public CouponInfo(String id, String name, double amount, String time) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.time = time;
        this.isSelected = isSelected;
    }
}
