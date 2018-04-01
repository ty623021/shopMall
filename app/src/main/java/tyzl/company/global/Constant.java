package tyzl.company.global;

import tyzl.company.utils.AbDateUtil;

/**
 * Created by geek on 2016/6/21.
 * 全局属性
 */
public class Constant {
    /**
     * 客服电话
     */
    public static final String CUSTOMER_SERVICE_PHONE = "4006114589";
    /**
     * 所有的金额和百分比四舍五入后的位数
     */
    public static final int ROUND_DIGIT = 2;
    /**
     * 最低充值金额
     */
    public static final double MIN_RECHARGE_MONEY = 1;
    /**
     * 请求网络时显示的文字信息 正在加载...
     */
    public static final String LOADING = "正在加载...";
    /**
     * 客户端设备标识 "android"
     */
    public static final String CLIENT_ID = "2";
    /**
     * 客户端设备标识 "android"
     */
    public static final String CLIENT_ID_NAME = "android";
//    public static String time = AbDateUtil.getCurrentDate(AbDateUtil.DATE_FORMAT_YMDHMS);
//    public static final String time_stamp = AbDateUtil.getTime(time);
    /**
     * 签名key
     */
    public static final String APPSECRET = "rontengshangchengwang";
    /**
     * AppKey
     */
    public static final String APPKEY = "APP5f8e6bfa";
    /***
     * 充值通道
     */
    public static final String APP_WAY = "lland";
    /***
     * 第一次登录
     */
    public static final String APP_CUR_VERSION = "appCurVersion";
    /**
     * The Constant CONNECTEXCEPTION.
     */
    public static final String CONNECT_EXCEPTION = "网络连接失败，请稍后再试";

    /**
     * The Constant SOCKETEXCEPTION.
     */
    public static final String SOCKET_EXCEPTION = "网络异常，请稍后再试";

    /**
     * The Constant SOCKETTIMEOUTEXCEPTION.
     */
    public static final String SOCKET_TIMEOUT_EXCEPTION = "网络连接超时，请稍后再试";

    /**
     * 资源未找到.
     */
    public static final String NOT_FOUND_EXCEPTION = "请求资源无效404";

    /**
     * 没有权限访问资源.
     */
    public static final String FORBIDDEN_EXCEPTION = "没有权限访问资源";

    /**
     * The Constant REMOTESERVICEEXCEPTION.
     */
    public static final String SERVICE_UNAVAILABLE = "服务器正在维护，请稍后再试";

    /**
     * The Constant UNKNOWNHOSTEXCEPTION.
     */
    public static final String UNKNOWN_HOST_EXCEPTION = "连接服务器失败，请稍后再试";

    /**
     * 其他异常.
     */
    public static final String UNTREATED_EXCEPTION = "未处理的异常";

    /**
     * 登陆已过期，请重新登录
     */
    public static final String LOGIN_EXCEPTION = "登陆已过期，请重新登录";
    public static final String ACTIVITY_BANNER = "activity_banner";
    /**
     * 已加载全部
     */
    public static final String LOADED = "已加载全部";


    /**
     *
     * 红包状态
     * 未使用0
     * 已使用1
     * 已过期2
     *
     */
    public static final String RED_PACKET_NONE = "0";
    public static final String RED_PACKET_USED = "1";
    public static final String RED_PACKET_OVERDUE = "2";




}
