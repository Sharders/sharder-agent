package org.sharder.agent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * WebConfiguration
 *
 * @author bubai
 * @date 2018/3/27
 */
@Configuration
public class WebConfiguration {

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipart = new CommonsMultipartResolver();
        multipart.setMaxUploadSize(42 * 1024);
        return multipart;
    }
}
