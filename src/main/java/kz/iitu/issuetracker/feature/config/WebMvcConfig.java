package kz.iitu.issuetracker.feature.config;

import kz.iitu.issuetracker.controller.compoundrequestparam.argumentresolver.CompoundRequestParamArgumentResolver;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@AllArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final ApplicationContext context;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(compoundRequestParamArgumentResolver());
    }

    private CompoundRequestParamArgumentResolver compoundRequestParamArgumentResolver() {
        return context.getBean(CompoundRequestParamArgumentResolver.class);
    }
}
