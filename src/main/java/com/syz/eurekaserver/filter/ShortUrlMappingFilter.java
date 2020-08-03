package com.syz.eurekaserver.filter;


import com.syz.eurekaserver.entity.Link;
import com.syz.eurekaserver.service.LinkService;
import com.syz.eurekaserver.utils.ShortUrlUtils;
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
public class ShortUrlMappingFilter implements Filter {

    private static final int shortUrlKeyLength = 6;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
      log.info("ShortUrlMappingFilter start");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("ShortUrlMappingFilter url mapping start......");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        BeanFactory factory = WebApplicationContextUtils
                .getRequiredWebApplicationContext(request.getServletContext());
        LinkService shortUrlService = (LinkService) factory.getBean("linkService");
        log.info("request uri : {}" , request.getRequestURI());
        if(request.getRequestURI().substring(1).trim().length()== shortUrlKeyLength){
            Long linkId = ShortUrlUtils.base62Decode(request.getRequestURI().substring(1).trim());
            Link link = shortUrlService.queryById(linkId);
            if (link == null) {
                log.error("ShortUrlMappingFilter shortUrl is null ...........dto is {}", linkId);
                throw new ServletException();
            } else {
                String fullUrl = link.getFullUrl();
                log.info("ShortUrlMappingFilter fullUrl is not null .shortUrl is {}...........dto is {}", fullUrl, linkId.toString());
                response.sendRedirect(fullUrl);
            }
        }else{
            filterChain.doFilter(request, response);
            log.info("非短链接正常后续的doFilter..........");
        }
    }

    @Override
    public void destroy() {
        log.info("ShortUrlMappingFilter destory");
    }
}
