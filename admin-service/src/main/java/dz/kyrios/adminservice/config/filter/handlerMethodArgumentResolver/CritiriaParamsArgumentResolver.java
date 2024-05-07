package dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver;

import dz.kyrios.adminservice.config.filter.clause.Clause;
import dz.kyrios.adminservice.config.filter.clause.ClauseOneArg;
import dz.kyrios.adminservice.config.filter.enums.Operation;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

public class CritiriaParamsArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Critiria sortParamParamsAnnotation = methodParameter.getParameterAnnotation(Critiria.class);
        if (sortParamParamsAnnotation != null) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String filter = null;
        HttpServletRequest httpRequest = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        if (hasText(httpRequest.getParameter("filter"))) {
            filter = httpRequest.getParameter("filter");
        }
        return getCriteria(filter);
    }

    public List<Clause> getCriteria(String filter) {
        List<Clause> criteria = new ArrayList<>();
        String input = filter;
        if (input == null || input.length() == 0) return criteria;
        try {
            input =input.replace("and,", "");
            input =input.replace("[", "");
            input =input.replace("]", "");
            String[] parts = input.split(",");
            int index=0;

            while (parts.length > 2 &&  index < parts.length){
                Clause searchCriteria = new ClauseOneArg(parts[index], Operation.valueOfLabel(parts[index+1]), parts[index+2]);
                criteria.add(searchCriteria);
                index +=3;
            }
        } catch (Exception e) {
            e.printStackTrace();
            criteria = new ArrayList<>();
        }

        return criteria;
    }
}
