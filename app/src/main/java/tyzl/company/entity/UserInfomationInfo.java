package tyzl.company.entity;

/**
 * Created by hjy on 2017/3/19.
 */

public class UserInfomationInfo {

    private String sex;//性别
    private String nickname;//呢称
    private String birthday;//出生日期
    private String head_pic;//获取的图片地址
    private String mobile;//手机号码

    private int yifu;
    private int daifu;
    private int daifa;

    public int getYifu() {
        return yifu;
    }

    public void setYifu(int yifu) {
        this.yifu = yifu;
    }

    public int getDaifu() {
        return daifu;
    }

    public void setDaifu(int daifu) {
        this.daifu = daifu;
    }

    public int getDaifa() {
        return daifa;
    }

    public void setDaifa(int daifa) {
        this.daifa = daifa;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String gethead_pic() {
        return head_pic;
    }

    public void sethead_pic(String userImageUrl) {
        this.head_pic = userImageUrl;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public UserInfomationInfo(String birthday, String nickname) {
        this.birthday = birthday;
        this.nickname = nickname;
    }

    public UserInfomationInfo(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
