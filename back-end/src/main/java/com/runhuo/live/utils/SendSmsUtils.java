package com.runhuo.live.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/8 16:34
 */
public class SendSmsUtils {
    //下面这四个数据可以去腾讯云找
    private static final String secretId = "";
    private static final String secretKey = "";
    private static final String sdkAppId = "";
    private static final String templateId = "";

    public static String send(String phone) {
        //生成短息验证码
        String template = createCaptcha();
        try {
            Credential cred = new Credential(secretId, secretKey);
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setReqMethod("POST");
            httpProfile.setConnTimeout(60);
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setSignMethod("HmacSHA256");
            clientProfile.setHttpProfile(httpProfile);
            SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);
            SendSmsRequest req = new SendSmsRequest();
            req.setSmsSdkAppId(sdkAppId);
            req.setSignName("");
            req.setTemplateId(templateId);
            String[] templateParamSet = {template};
            req.setTemplateParamSet(templateParamSet);

            String[] phoneNumberSet = {"+86" + phone};
            req.setPhoneNumberSet(phoneNumberSet);
            SendSmsResponse res = client.SendSms(req);
            return template;
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String createCaptcha() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int random = (int) (Math.random() * 10);
            stringBuilder.append(random);
        }
        return stringBuilder.toString();
    }
}