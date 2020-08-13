package com.syz.eurekaserver.filter;

import com.syz.eurekaserver.entity.Link;
import com.syz.eurekaserver.service.LinkService;
import com.syz.eurekaserver.utils.ShortUrl62Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class ShortLinkFilter implements Filter {

    private static final int shortUrlKeyLength = 6;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
      log.info("filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("filter url mapping start......");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        BeanFactory factory = WebApplicationContextUtils
                .getRequiredWebApplicationContext(request.getServletContext());
        LinkService shortUrlService = (LinkService) factory.getBean("linkService");
        log.info("request uri : {}" , request.getRequestURI());
        if(request.getRequestURI().substring(1).trim().length()== shortUrlKeyLength){
            //1.通过62进制进行短链接处理
//            Long linkId = ShortUrl62Utils.base62Decode(request.getRequestURI().substring(1).trim());
//            Link link = shortUrlService.queryById(linkId);
            //2.通过md5进行短链接处理
            Link link = shortUrlService.queryByShort(request.getRequestURI().substring(1));
            Long linkId = link == null?null:link.getId();
            if (linkId == null || link == null) {
                log.error("resource is null ,request short link : {}", linkId);
                throw new ServletException();
            } else {
                String fullUrl = link.getFullUrl();
                log.info("resource :{} , request resource id : {}", fullUrl, linkId.toString());
                log.info("process short link request-------------------");
                response.sendRedirect(fullUrl);
            }
        }else{
            filterChain.doFilter(request, response);
            log.info("process normal request-------------------");
        }
    }

    @Override
    public void destroy() {
        log.info("destroy");
    }
}
