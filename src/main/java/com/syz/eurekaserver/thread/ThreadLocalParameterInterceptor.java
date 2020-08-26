package com.syz.eurekaserver.thread;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ThreadLocalParameterInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestId = request.getParameter("id");
        RequestVO requestVO = new RequestVO();
        requestVO.setId(Integer.parseInt(requestId));
        ThreadLocalCache.baseSignatureRequestThreadLocal.set(requestVO);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        ThreadLocalCache.baseSignatureRequestThreadLocal.remove();
    }
}
