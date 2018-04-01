package tyzl.company.global;

/**
 * 接口地址
 */
public class Config {
    // 正式环境
//    public static final String SERVER_URL = "https://pre.xzgjf.com/";

    //    public static final String SERVER_URL = "https://api.xzgjf.com/";//https 预发布环境
    public static final String SERVER_URL = "http://192.168.0.40/";
    public static final String SERVER_URL_MMM = "http://m.xzgjf.com/";// 正式环境H5地址

    public static String base_data_path = "company";// 需要存储在SD卡上的文件的基路径
    public static String images_cache_path = "images_cache";// 存储图片缓存

    /**
     * UI的主色调
     * colorPrimary 蓝色
     * colorPrimary2 橘黄色
     */
    public static String colorPrimary = "#4BAEFE";
    public static String colorPrimary2 = "#FF7F00";
    /**
     * UI设计的基准宽度.
     */
    public static int UI_WIDTH = 720;

    /**
     * UI设计的基准高度.
     */
    public static int UI_HEIGHT = 1280;

    /**
     * UI设计的密度.
     */
    public static int UI_DENSITY = 2;

    /**
     * 版本检测接口,根据返回信息，判断是否需要强制升级
     */
    public static final String URL_VERSIONON = SERVER_URL + "wd_api/userCenter/versionOn";
    /**
     * 获取首页banner图
     */
    public static final String URL_INDEX_BANNER = SERVER_URL + "Api/Index/indexBanner";
    /**
     * 获取投资列表标的数据
     */
    public static final String URL_GETINVESTSUBJECTON = "https://pre.xzgjf.com/" + "wd_api/subject/getInvestSubjectOn";

    /**
     * 获取购物车列表
     */
    public static final String CART_LIST_URL = SERVER_URL + "Api/Cart/cartList";

    /**
     * 添加收货地址
     */
    public static final String URL_ADD_ADDRESS = SERVER_URL + "Api/User/addAddress";

    /**
     * 获取收货地址列表
     */
    public static final String URL_GET_ADDRESS_LIST = SERVER_URL + "Api/User/getAddress";

    /**
     * 删除收货地址
     */
    public static final String URL_DEL_ADDRESS = SERVER_URL + "Api/User/delAddress";

    /**
     * 修改收货地址
     */
    public static final String URL_EDIT_ADDRESS = SERVER_URL + "Api/User/editAddress";

    /**
     * 设置默认收货地址
     */
    public static final String URL_SET_DEFAULT_ADDRESS = SERVER_URL + "Api/User/setDefaultAddress";

    /**
     * 获取省 市 区
     */
    public static final String URL_GET_AREA = SERVER_URL + "Api/User/getArea";
    /**
     * 获取某件商品的详细信息
     */
    public static final String URL_GOODS_INFO = SERVER_URL + "Api/Goods/goodsInfo";
    /**
     * 商品详情—订单生成页面
     */
    public static final String CREATE_ORDER_URL2 = SERVER_URL + "index.php/api/Cart/Cartt";
    /**
     * 获取商品规格参数接口
     */
    public static final String URL_GOOD_SPEC = SERVER_URL + "Api/Goods/goodSpec";
    /**
     * 获取商品规格组合详情接口
     */
    public static final String URL_GET_GOODS_SPCINFO = SERVER_URL + "Api/Goods/getGoodsSpcInfo";


    /**
     * 注册
     */
    public static final String URL_REGISTER = SERVER_URL + "Api/Login/reg";


    /**
     * 注册时的短信验证码的发送
     */
    public static final String URL_REGISTER_MESSAGE_CODE = SERVER_URL + "Api/Login/getCode";
    /**
     * 首页
     */
    public static final String URL_INDEX = SERVER_URL + "Api/Index/index";
    /**
     * 商品一级分类列表
     */
    public static final String URL_GOODS_CATEGORY_LIST = SERVER_URL + "Api/Goods/goodsCategoryList";


    /**
     * 商品二级分类列表
     */
    public static final String URL_GOODS_TOW_LIST = SERVER_URL + "Api/Goods/goodsTowList";

    /**
     * 商品列表
     */
    public static final String URL_GOODS_LIST = SERVER_URL + "Api/Goods/goodsList";

    /**
     * 注册时的校验短信验证码
     */
    public static final String URL_CHECKVCODE = SERVER_URL + "Api/Login/checkVcode";


    /**
     * 登录
     */
    public static final String URL_LOGIN = SERVER_URL + "Api/Login/login";


    /**
     * 忘记密码
     */
    public static final String URL_FORGETPWD = SERVER_URL + "Api/Login/forgetPwd";


    /**
     * 修改个人信息
     */
    public static final String URL_UPUSERINFO = SERVER_URL + "Api/User/upUserInfo";


    /**
     * 获取个人信息
     */
    public static final String URL_GETUSERINFO = SERVER_URL + "Api/User/getUserInfo";


    /**
     * 上传个人头像
     */
    public static final String URL_UPICON = SERVER_URL + "Api/User/upIcon";


    /**
     * 关于我们
     */
    public static final String URL_ABOUTUS = SERVER_URL + "Api/Wap/aboutUs";


    /**
     * 我的优惠券
     */
    public static final String URL_GETCOUPON = SERVER_URL + "Api/User/getCoupon";




    /**
     * 获取优惠券的数量
     */
    public static final String URL_GETCOUPONNUM = SERVER_URL + "Api/User/getCouponNum";



    /**
     * 订单管理
     */
    public static final String URL_ORDERMANAGER = SERVER_URL + "Api/User/getOrderList";




    /**
     * 取消订单
     */
    public static final String URL_CANCELORDER = SERVER_URL + "Api/User/cancelOrder";

    /**
     * 添加购物车
     */
    public static final String URL_ADD_CART = SERVER_URL + "Api/Cart/addCart";

}
