package dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver;

import dz.kyrios.adminservice.config.filter.clause.ClauseOneArg;
import dz.kyrios.adminservice.config.filter.enums.Operation;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletRequest;

import static org.springframework.util.StringUtils.hasText;

public class SearchValueParamsArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        SearchValue SearchValueParamsAnnotation = methodParameter.getParameterAnnotation(SearchValue.class);
        if (SearchValueParamsAnnotation != null) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String searchOperation = null;
        String searchValue = null;
        String searchExpr = null;
        HttpServletRequest httpRequest = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        if (hasText(httpRequest.getParameter("searchValue")) && !httpRequest.getParameter("searchValue").equals("undefined") && !httpRequest.getParameter("searchExpr").equals("undefined")) {
            searchValue = httpRequest.getParameter("searchValue");
            searchOperation = httpRequest.getParameter("searchOperation");
            searchExpr = httpRequest.getParameter("searchExpr");
            return new ClauseOneArg(searchExpr, Operation.valueOfLabel(searchOperation), searchValue);
        }
        return null;
    }
}