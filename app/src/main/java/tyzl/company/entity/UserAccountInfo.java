package tyzl.company.entity;

import java.io.Serializable;

/**
 * user--个人中心
 *
 * @author Administrator
 */
public class UserAccountInfo implements Serializable {

    private String realName;//真实姓名
    private boolean bindBank;//是否绑定了银行卡
    private boolean banSubName;//是否已经填写支行名称
    private boolean isBeginner;//是否是新手
    private boolean outPwdSafe;//用户的交易密码是否安全.
    private int voucherCount;//可使用代金券数量
    private int msgCount;//未读消息数量
    private double totalIncome;//累计净收益
    private double freezeBalance;//冻结金额
    private double briskAllMoney;//帐户总金额
    private double allMoney;//账户总资产
    private double availableBalance;//可用余额

    private double waitingReceiptCapital; //代收本金
    private double waitingReceiptInterest;//待收利息
    private double waitingReceiptAllMoney;//待收本息

    private double alreadyReceiptCapital; //已回款本金
    private double alreadyReceiptInterest;//已回款利息
    private double expAmount;//用户体验金总额
    private int integral;//积分总数

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public boolean isOutPwdSafe() {
        return outPwdSafe;
    }

    public void setOutPwdSafe(boolean outPwdSafe) {
        this.outPwdSafe = outPwdSafe;
    }

    public boolean isBeginner() {
        return isBeginner;
    }

    public void setIsBeginner(boolean isBeginner) {
        this.isBeginner = isBeginner;
    }

    public double getWaitingReceiptAllMoney() {
        return waitingReceiptAllMoney;
    }

    public void setWaitingReceiptAllMoney(double waitingReceiptAllMoney) {
        this.waitingReceiptAllMoney = waitingReceiptAllMoney;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public double getFreezeBalance() {
        return freezeBalance;
    }

    public void setFreezeBalance(double freezeBalance) {
        this.freezeBalance = freezeBalance;
    }

    public double getBriskAllMoney() {
        return briskAllMoney;
    }

    public void setBriskAllMoney(double briskAllMoney) {
        this.briskAllMoney = briskAllMoney;
    }

    public boolean isBindBank() {
        return bindBank;
    }

    public void setBindBank(boolean bindBank) {
        this.bindBank = bindBank;
    }

    public boolean isBanSubName() {
        return banSubName;
    }

    public void setBanSubName(boolean banSubName) {
        this.banSubName = banSubName;
    }

    public int getVoucherCount() {
        return voucherCount;
    }

    public void setVoucherCount(int voucherCount) {
        this.voucherCount = voucherCount;
    }

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public double getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(double allMoney) {
        this.allMoney = allMoney;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public double getWaitingReceiptCapital() {
        return waitingReceiptCapital;
    }

    public void setWaitingReceiptCapital(double waitingReceiptCapital) {
        this.waitingReceiptCapital = waitingReceiptCapital;
    }

    public double getWaitingReceiptInterest() {
        return waitingReceiptInterest;
    }

    public void setWaitingReceiptInterest(double waitingReceiptInterest) {
        this.waitingReceiptInterest = waitingReceiptInterest;
    }

    public double getAlreadyReceiptCapital() {
        return alreadyReceiptCapital;
    }

    public void setAlreadyReceiptCapital(double alreadyReceiptCapital) {
        this.alreadyReceiptCapital = alreadyReceiptCapital;
    }

    public double getAlreadyReceiptInterest() {
        return alreadyReceiptInterest;
    }

    public void setAlreadyReceiptInterest(double alreadyReceiptInterest) {
        this.alreadyReceiptInterest = alreadyReceiptInterest;
    }

    public double getExpAmount() {
        return expAmount;
    }

    public void setExpAmount(double expAmount) {
        this.expAmount = expAmount;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
