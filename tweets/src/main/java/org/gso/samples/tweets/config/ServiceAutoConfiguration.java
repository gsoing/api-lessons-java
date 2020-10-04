package org.gso.samples.tweets.config;

import org.gso.samples.tweets.mvc.RestControllerAdvice;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnWebApplication
@Import({RestControllerAdvice.class})
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
public class ServiceAutoConfiguration {
}
