package tyzl.company.entity;

/**
 * Created by hjy on 2017/3/17.
 */

public class UserRegisterInfo {

    private String token;//
    private String msg;
    private String user_phone;//

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public UserRegisterInfo(String token, String msg) {
        this.token = token;
        this.msg = msg;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
