package com.meizu.maegis.captcha.sdk.server;

import com.meizu.maegis.captcha.sdk.constant.SystemConstants;
import com.meizu.maegis.captcha.sdk.utils.HttpClient;
import com.meizu.maegis.captcha.sdk.utils.HttpResult;
import com.meizu.maegis.captcha.sdk.utils.ResultPack;
import com.meizu.maegis.captcha.sdk.utils.StringUtils;
import com.meizu.maegis.captcha.sdk.vo.CheckResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * User: jasperxgwang
 * Date: 2018-12-6 14:48
 */
public class CaptchaService extends HttpClient {

    private static final Logger logger = LoggerFactory.getLogger(CaptchaService.class);

    private static final String SUCCESS_CODE = "200";

    /**
     * 是否使用https接口调用：true 使用https连接，false使用http连接；默认使用https
     */
    private boolean useSSL = Boolean.TRUE;


    public CaptchaService(String appId, String appSecret) {
        super(nonNull(appId), nonNull(appSecret));
    }

    public CaptchaService(String appId, String appSecret, boolean useSSL) {
        super(nonNull(appId), nonNull(appSecret));
        this.useSSL = useSSL;
    }

    /**
     * token验证 不重试
     *
     * @param captchaId 验证码客户端验证回调的随机串 必填
     * @param token     验证码客户端验证回调的token 必填
     * @param userIP    提交验证的用户的IP地址（eg: 10.127.10.2）必填
     * @return
     * @throws IOException
     */
    public ResultPack<CheckResult> checkToken(String captchaId, String token, String userIP) throws IOException {
        return this.checkToken(captchaId, token, userIP, 0);
    }

    /**
     * token验证 重试
     *
     * @param captchaId 验证码客户端验证回调的随机串 必填
     * @param token     验证码客户端验证回调的token 必填
     * @param userIP    提交验证的用户的IP地址（eg: 10.127.10.2）必填
     * @param retries   重试次数
     * @return
     */
    public ResultPack<CheckResult> checkToken(String captchaId, String token, String userIP, int retries) throws IOException {
        if (StringUtils.isEmpty(captchaId)) {
            return ResultPack.failed("captchaId is empty");
        }
        if (StringUtils.isEmpty(token)) {
            return ResultPack.failed("token is empty");
        }
        if (StringUtils.isEmpty(userIP)) {
            return ResultPack.failed("userIP is empty");
        }

        int attempt = 0;
        ResultPack<CheckResult> result;
        int backoff = 1000;
        boolean tryAgain;
        do {
            ++attempt;
            result = this.checkTokenNoRetry(captchaId, token, userIP);
            tryAgain = result == null && attempt <= retries;
            backoff = getBackoffTime(backoff, tryAgain);
        } while (tryAgain);
        if (result == null) {
            logger.warn("Could not send message after {} attempts", attempt);
            throw new IOException(String.format("Could not send message after [%s] attempts", attempt));
        } else {
            return result;
        }
    }

    private ResultPack<CheckResult> checkTokenNoRetry(String captchaId, String token, String userIP) throws IOException {
        String _url = SystemConstants.SRV_CHECK;
        StringBuilder body = newBody("appId", appId);
        addParameter(body, "token", token);
        addParameter(body, "userIP", userIP);
        addParameter(body, "captchaId", captchaId);

        HttpResult httpResult = super.post(useSSL, _url, body.toString());
        if (httpResult == null) {
            return null;
        }

        String code = httpResult.getCode();
        String msg = httpResult.getMessage();
        if (SUCCESS_CODE.equals(code)) {
            CheckResult checkResult = new CheckResult(code, msg);
            return ResultPack.succeed(code, msg, checkResult);
        } else {
            return ResultPack.failed(code, msg);
        }
    }

    private int getBackoffTime(int backoff, boolean tryAgain) {
        if (tryAgain) {
            int sleepTime = backoff / 2 + this.random.nextInt(backoff);
            this.sleep((long) sleepTime);
            if (2 * backoff < 60000) {
                backoff *= 2;
            }
        }
        return backoff;
    }

}
