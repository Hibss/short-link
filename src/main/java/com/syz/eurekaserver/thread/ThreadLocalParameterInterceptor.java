package com.syz.eurekaserver.thread;

import com.syz.eurekaserver.utils.JacksonUtil;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


public class ThreadLocalParameterInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestId = request.getParameter("id");
        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println("----------------------------------");
        System.out.println("request parameters map : "+JacksonUtil.obj2json(parameterMap));
        System.out.println("----------------------------------");
        RequestVO requestVO = new RequestVO();
        requestVO.setId(Long.parseLong(requestId));
        ThreadLocalCache.baseSignatureRequestThreadLocal.set(requestVO);
        ThreadLocalCache.requestMapLocalThread.set(parameterMap);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        ThreadLocalCache.baseSignatureRequestThreadLocal.remove();
        ThreadLocalCache.requestMapLocalThread.remove();
    }
}
