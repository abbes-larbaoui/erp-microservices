package dz.kyrios.notificationservice.config.filter.handlerMethodArgumentResolver;

import dz.kyrios.notificationservice.config.filter.specification.RequestService;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletRequest;

import static org.springframework.util.StringUtils.hasText;

public class SortParamsArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        SortParam sortParamParamsAnnotation = methodParameter.getParameterAnnotation(SortParam.class);
        if (sortParamParamsAnnotation != null) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        int skip = 0;
        int take = 10;
        String sort;

        HttpServletRequest httpRequest = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        if (hasText(httpRequest.getParameter("skip")) && !httpRequest.getParameter("skip").equals("undefined")) {
            skip = Integer.parseInt(httpRequest.getParameter("skip"));
        }
        if (hasText(httpRequest.getParameter("take")) && !httpRequest.getParameter("take").equals("undefined")) {
            take = Integer.parseInt(httpRequest.getParameter("take"));
        }
        if (hasText(httpRequest.getParameter("sort")) && !httpRequest.getParameter("sort").equals("undefined")) {
            sort = httpRequest.getParameter("sort");
            return PageRequest.of(skip / take, take, Sort.by(RequestService.getOrders(sort)));
        }
        return PageRequest.of(skip / take, take);
    }
}
