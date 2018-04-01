package tyzl.company.entity;

/**
 * 投资记录
 */
public class InvestListInfo {

    private String interestType;//	计息方式
    private String repayType;//	还款方式
    private long lastTime;//	有效期限 （发标时间+募集天数 - 当前时间）
    private String publishTime;//	发布时间
    private double rewardRate;//	投标奖励(直投)
    private boolean isLimitBeginner;// 是否新手投资(直投)
    private double subsidyRate;//	贴息奖励
    private int alreadyNumber;//	已投资人数
    private double alreadyAmount;//	已募集金额(直投)
    private String status;//	状态
    private String cycleType;//投资期限单位(直投)
    private String cycle;//		投资期限单位(直投)
    private double loanRate;//	年化收益
    private double amount;//标的金额(直投)
    private String title;//标题
    private String classify;//	类别
    private String classifyStr;//标的种类中文名
    private int max;//最大投资额
    private int number;
    private boolean isExclusiveApp;//是否APP专享
    private int min;//最低投资额
    private String id;//标的ID
    private float process;//进度
    private String newCatalog;//活动角标

    public String getNewCatalog() {
        return newCatalog;
    }

    public void setNewCatalog(String newCatalog) {
        this.newCatalog = newCatalog;
    }

    public String getInterestType() {
        return interestType;
    }

    public void setInterestType(String interestType) {
        this.interestType = interestType;
    }

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public double getRewardRate() {
        return rewardRate;
    }

    public void setRewardRate(double rewardRate) {
        this.rewardRate = rewardRate;
    }

    public boolean isLimitBeginner() {
        return isLimitBeginner;
    }

    public void setIsLimitBeginner(boolean isLimitBeginner) {
        this.isLimitBeginner = isLimitBeginner;
    }

    public double getSubsidyRate() {
        return subsidyRate;
    }

    public void setSubsidyRate(double subsidyRate) {
        this.subsidyRate = subsidyRate;
    }

    public int getAlreadyNumber() {
        return alreadyNumber;
    }

    public void setAlreadyNumber(int alreadyNumber) {
        this.alreadyNumber = alreadyNumber;
    }

    public double getAlreadyAmount() {
        return alreadyAmount;
    }

    public void setAlreadyAmount(double alreadyAmount) {
        this.alreadyAmount = alreadyAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCycleType() {
        return cycleType;
    }

    public void setCycleType(String cycleType) {
        this.cycleType = cycleType;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public double getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(double loanRate) {
        this.loanRate = loanRate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isExclusiveApp() {
        return isExclusiveApp;
    }

    public void setIsExclusiveApp(boolean isExclusiveApp) {
        this.isExclusiveApp = isExclusiveApp;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getProcess() {
        return process;
    }

    public void setProcess(float process) {
        this.process = process;
    }

    public String getClassifyStr() {
        return classifyStr;
    }

    public void setClassifyStr(String classifyStr) {
        this.classifyStr = classifyStr;
    }

    @Override
    public String toString() {
        return "HomeInvestInfo{" +
                "interestType='" + interestType + '\'' +
                ", repayType='" + repayType + '\'' +
                ", lastTime=" + lastTime +
                ", publishTime='" + publishTime + '\'' +
                ", rewardRate=" + rewardRate +
                ", isLimitBeginner=" + isLimitBeginner +
                ", subsidyRate=" + subsidyRate +
                ", alreadyNumber=" + alreadyNumber +
                ", alreadyAmount=" + alreadyAmount +
                ", status='" + status + '\'' +
                ", cycleType='" + cycleType + '\'' +
                ", cycle='" + cycle + '\'' +
                ", loanRate=" + loanRate +
                ", amount=" + amount +
                ", title='" + title + '\'' +
                ", classify='" + classify + '\'' +
                ", classifyStr='" + classifyStr + '\'' +
                ", max=" + max +
                ", number=" + number +
                ", isExclusiveApp=" + isExclusiveApp +
                ", min=" + min +
                ", id=" + id +
                ", process=" + process +
                '}';
    }
}
