package com.meizu.maegis.captcha.sdk.server;

import com.meizu.maegis.captcha.sdk.utils.ResultPack;
import com.meizu.maegis.captcha.sdk.vo.CheckResult;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaptchaServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(CaptchaServiceTest.class);

    /**
     * 平台注册应用secretKey
     */
    public static final String APP_SECRET = "APP_SECRET";

    /**
     * 平台注册应用ID
     */
    public static final String APP_ID = "APP_ID";


    @Test
    public void testCheckToken() throws Exception {
        CaptchaService captchaService = new CaptchaService(APP_ID, APP_SECRET);
        
        String captchaId = "captchaId";
        String token = "token";
        String userIP = "userIP";

        ResultPack<CheckResult> resultPack = captchaService.checkToken(captchaId, token, userIP);

        // 重试类接口
        // int retries = 3;
        // ResultPack<CheckResult> resultPack = captchaService.checkToken(captchaId, token, userIP, retries);
        if (resultPack.isSucceed()) {
            logger.info("resultPack success:{}", resultPack);
        } else {
            logger.info("resultPack fail:{}", resultPack);
        }
    }
}