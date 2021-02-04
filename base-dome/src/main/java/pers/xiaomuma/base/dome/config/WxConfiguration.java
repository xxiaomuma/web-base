package pers.xiaomuma.base.dome.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.xiaomuma.base.thirdparty.wx.config.WxProperties;
import pers.xiaomuma.base.thirdparty.wx.mp.WxMpAPI;

@Configuration
public class WxConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "wx")
    public WxProperties properties () {
        return new WxProperties();
    }

    @Bean
    public WxMpAPI wxMpAPI() {
        return new WxMpAPI(properties());
    }
}
