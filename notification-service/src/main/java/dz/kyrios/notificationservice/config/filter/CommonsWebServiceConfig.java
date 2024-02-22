package dz.kyrios.notificationservice.config.filter;

import dz.kyrios.notificationservice.config.filter.handlerMethodArgumentResolver.CritiriaParamsArgumentResolver;
import dz.kyrios.notificationservice.config.filter.handlerMethodArgumentResolver.SearchValueParamsArgumentResolver;
import dz.kyrios.notificationservice.config.filter.handlerMethodArgumentResolver.SortParamsArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CommonsWebServiceConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new SortParamsArgumentResolver());
        resolvers.add(new CritiriaParamsArgumentResolver());
        resolvers.add(new SearchValueParamsArgumentResolver());
    }
}
