## 魅族风控验证码系统JAVA版本SDK

### 更新日志

#### [2019-05-15]1.0.0.20190515_release
*  初始版本


### Maven dependency

```xml
<properties>
    <java-captcha-sdk.version>1.0.0.20190515_release</java-captcha-sdk.version>
</properties>

<dependencies>
    <dependency>
        <groupId>com.meizu.flyme</groupId>
        <artifactId>java-captcha-sdk</artifactId>
        <version>${java-captcha-sdk.version}</version>
    </dependency>
</dependencies>

```

### 接口响应码定义(ErrorCode)
名称|Code|Comment
---|---|---
UNKNOWN_ERROR|-1|未知错误
SUCCESS|200|成功
INVALID_TOKEN|401|非法token
SYSTEM_ERROR|1001|系统错误
SYSTEM_BUSY|1001|服务器忙
PARAMETER_ERROR|1005|参数错误，请参考API文档
INVALID_SIGN|1006|签名认证失败
INVALID_APPLICATION_ID|110000|appId不合法
PARAM_BLANK|110004|参数不能为空
APP_IN_BLACK_LIST|110009|应用被加入黑名单
INVALID_APPLICATION_SECRET|110033|非法的appSecret

### 服务端验证码校验(checkToken)
#### 接口说明

接口|说明
---|---
`ResultPack<CheckResult> checkToken(String captchaId, String token, String userIP) throws IOException`| 服务端验证码校验
`ResultPack<CheckResult> checkToken(String captchaId, String token, String userIP, int retries) throws IOException ` | 服务端验证码校验(可重试)


#### 参数说明

参数名称|类型|必需|默认|描述
---|---|---|---|---
captchaId|String|是|null|验证码客户端验证回调的随机串
token|List<String>|是|null|验证码客户端验证回调的token
userIP|String|否|0|超时or异常重试次数
retries|int|否|0|超时or异常重试次数

#### 返回值

```
CheckResult

code：状态码
message：状态消息

```

#### Junit测试例子：

```java
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

        // 可重试接口
        // int retries = 3;
        // ResultPack<CheckResult> resultPack = captchaService.checkToken(captchaId, token, userIP, retries);
        if (resultPack.isSucceed()) {
            logger.info("resultPack success:{}", resultPack);
        } else {
            logger.info("resultPack fail:{}", resultPack);
        }
    }
}

```

#### 结果分析

1. 成功：
> resultPack success:RESULT:[1] code:[200] comment:[] value:[CheckResult{code='200', message=''}] error:[null]] errorCode:[null]

2. 失败：
> resultPack fail:RESULT:[-1] code:[401] comment:[非法验证码信息] value:[null] error:[java.lang.Throwable: 非法验证码信息]] errorCode:[ErrorCode{value=401, description='非法token'}]

3. 异常(检查网络是否正常，如果正常可以自行重试)：
> IOException posting:Read timed out <br />
> Could not send message after 1 attempts

