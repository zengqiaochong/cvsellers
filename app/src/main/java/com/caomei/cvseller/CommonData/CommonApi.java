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
//    public static String BASE_URL = "http://www.happycopy.cn:8080/zouma/";

    //这个是测试接口
    public static String BASE_URL = "http://app.zmbok.com:8080/zouma_t/";
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
     * 获取相应市级下的驿站列表
     */
    public static String URL_GET_STAGES="Reg_getAgent3OptionsForApp?city=%s";
    /**
     * 获取相应市级下的小区列表
     */
    public static String URL_GET_COMMUNITY="Reg_getCommunityOptionsForApp?county=%s";

    /**
     * <pre>
     * 用户注册接口
     * 1 phone  电话号码
     * 2 password
     * 3 realname
     * 4 Province
     * 5 City
     * 6 Communityid
     * 7 CompanyName
     * 8 Role_id
     * 9 Address
     * 10 Remark
     * 11 deliverType
     */
    public static String URL_REGISTER = "Reg_createUser?" +
            "phone=%s&" +
            "password=%s&" +
            "realname=%s&" +
            "Province=%s&" +
            "City=%s&" +
            "Communityid=%s&" +
            "CompanyName=%s&" +
            "Role_id=%s&" +
            "Address=%s&" +
            "Remark=%s&" +
            "deliverType=%s";
    /**
     * 用户登录接口 1 username 2 password 3 roleid
     */
    public static String URL_LOGIN = "Login_login_merchant?username=%s&password=%s&roleType=%s&user_id=-1";

   }
