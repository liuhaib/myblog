package com.liuhaibin.myblog.config;

import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
/**
 * @Description:  七牛云配置类
 * @Auther: HaiBin
 * @Date: 2022/11/13 15:59
 * @Version 1.0
 */
@Component
public class UploadConfig {

    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Bean
    public Auth getAuth() {
        return Auth.create(accessKey, secretKey);
    }

    @Bean
    public UploadManager getUploadManager() {
        return new UploadManager(new com.qiniu.storage.Configuration());
    }
}
