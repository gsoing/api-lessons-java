package com.modelisation.tp.tpmodelisation.config;

import com.modelisation.tp.tpmodelisation.endpoint.ErrorController;
import com.modelisation.tp.tpmodelisation.mvc.RestControllerAdvice;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnWebApplication
@Import({RestControllerAdvice.class, ErrorController.class})
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
public class ServiceAutoConfiguration {
}
