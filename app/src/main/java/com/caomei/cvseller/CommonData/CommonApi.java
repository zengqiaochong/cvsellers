package com.caomei.cvseller.CommonData;

/**
 * 通用的接口地址
 *
 * @author Wang Xiaojian
 */
public class CommonApi {

    /**
     * 获取数据的基础URL
     */
    public static String BASE_URL = "http://www.happycopy.cn:8080/zouma_t/";
    /**
     * <pre>
     *  获取菜品图片信息的接口
     * 1 imgid
     * 2 user_id
     * </pre>
     */
    public static String URL_GET_IMG = "Picture_loadPicture?imgid=%s&clazzName=%s&user_id=%s";

    /**
     * 获取短信验证码 1 phoneNo 2 user_id:
     */
    public static String URL_GET_SMS_CODE = "SMSAuth_sendSMSAuthCode?phoneNO=%s&user_id=%s";

    /**
     * 检查短信验证码 1 phoneNO 2 smsCode 3 user_id
     */
    public static String URL_CHECK_SMS_CODE = "SMSAuth_checkSMSAuthCode?phoneNO=%s&smsCode=%s&user_id=%s";

    /**
     * <pre>
     * 用户注册接口
     * 1 username  电话号码
     * 2 password
     * 3 realname
     * 4 IDCode
     * 5 province
     * 6 communityid
     * 7 storeName
     * 8 storePlace
     * 9 deliverScope
     * 10 roleType
     * 11 agent_id
     * 12 user_id
     */
    public static String URL_REGISTER = "Reg_createUser?" +
            "username=%s&" +
            "password=%s&" +
            "realname=%s&" +
            "IDCode=%s&" +
            "province=%s&" +
            "communityid=%s&" +
            "storeName=%s&" +
            "storePlace=%s&" +
            "deliverScope=%s&" +
            "roleType=%s&" +
            "agent_id=%s&" +
            "user_id=%s";

    /**
     * 用户登录接口 1 username 2 password 3 roleid
     */
    public static String URL_LOGIN = "Login_login_merchant?username=%s&password=%s&roleType=%s&user_id=-1";

    /**
     *
     */
    public static String URL_GET_AGENT_ID="Reg_getAgent3Options?city=%s";
}
