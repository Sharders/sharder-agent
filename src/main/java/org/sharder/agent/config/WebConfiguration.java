package org.sharder.agent.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * WebConfiguration
 *
 * @author bubai
 * @date 2018/3/27
 */
@Configuration
public class WebConfiguration {
    @Value("${agent.upload_size}")
    private String uploadSize;
    private static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
    private static final Logger logger = LoggerFactory.getLogger(WebConfiguration.class);

    @Bean
    public CommonsMultipartResolver multipartResolver() throws ScriptException {
        CommonsMultipartResolver multipart = new CommonsMultipartResolver();
        long size = Long.valueOf(jse.eval(uploadSize).toString());
        multipart.setMaxUploadSize(size);
        logger.info("uploadSize:{} byte - {}k", size, Math.floorDiv(size,1024));
        return multipart;
    }
}
