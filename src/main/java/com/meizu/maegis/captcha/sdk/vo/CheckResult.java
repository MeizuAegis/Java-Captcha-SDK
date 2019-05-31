package com.meizu.maegis.captcha.sdk.vo;

/**
 * User: jasperxgwang
 * Date: 2018-12-6 15:08
 */
public class CheckResult {

    private String code;
    private String message;

    public CheckResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "CheckResult{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
