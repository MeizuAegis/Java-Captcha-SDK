package com.meizu.maegis.captcha.sdk.constant;

import java.util.Collection;
import java.util.HashMap;


public class ErrorCode {

    private int value;
    private String description;
    private static HashMap<Integer, ErrorCode> intErrorCodeMap = createIntegerErrorCodeMapping();
    public static ErrorCode UNKNOWN_ERROR = valueOf(Integer.valueOf(-1), "未知错误");
    public static ErrorCode SUCCESS = valueOf(Integer.valueOf(200), "成功");
    public static ErrorCode INVALID_TOKEN = valueOf(Integer.valueOf(401), "非法token");
    public static ErrorCode SYSTEM_ERROR = valueOf(Integer.valueOf(1001), "系统错误");
    public static ErrorCode SYSTEM_BUSY = valueOf(Integer.valueOf(1003), "服务器忙");

    public static ErrorCode PARAMETER_ERROR = valueOf(Integer.valueOf(1005), "参数错误，请参考API文档");
    public static ErrorCode INVALID_SIGN = valueOf(Integer.valueOf(1006), "签名认证失败");

    public static ErrorCode INVALID_APPLICATION_ID = valueOf(Integer.valueOf(110000), "appId不合法");
    public static ErrorCode PARAM_BLANK = valueOf(Integer.valueOf(110004), "参数不能为空");
    public static ErrorCode APP_IN_BLACK_LIST = valueOf(Integer.valueOf(110009), "应用被加入黑名单");
    public static ErrorCode INVALID_APPLICATION_SECRET = valueOf(Integer.valueOf(110033), "非法的appSecret");


    private static HashMap<Integer, ErrorCode> createIntegerErrorCodeMapping() {
        HashMap result = new HashMap();
        return result;
    }

    public static Collection<ErrorCode> getAllErrorCodes() {
        return intErrorCodeMap.values();
    }

    private ErrorCode(int value) {
        this.value = value;
    }

    private ErrorCode(int value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String toString() {
        return "ErrorCode{" +
                "value=" + value +
                ", description='" + description + '\'' +
                '}';
    }

    public String getFullDescription() {
        return this.getName() + "," + this.value + "," + this.description;
    }

    public String getName() {
        return this.description;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static ErrorCode valueOf(int value) {
        return (ErrorCode) intErrorCodeMap.get(Integer.valueOf(value));
    }

    public static ErrorCode valueOf(int value, ErrorCode defaultIfMissing) {
        ErrorCode code = (ErrorCode) intErrorCodeMap.get(Integer.valueOf(value));
        return code == null ? defaultIfMissing : code;
    }

    public static ErrorCode valueOf(Integer code, String reason) {
        ErrorCode result = (ErrorCode) intErrorCodeMap.get(code);
        if (result == null) {
            result = new ErrorCode(code.intValue(), reason);
            intErrorCodeMap.put(code, result);
        }

        return result;
    }
}
