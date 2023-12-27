package dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SortParam {
}
