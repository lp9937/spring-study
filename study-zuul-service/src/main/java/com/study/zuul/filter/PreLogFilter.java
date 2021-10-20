package com.study.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lp
 * @description TODO
 * @date 2021/10/20 10:16
 */
@Slf4j
@Component
public class PreLogFilter extends ZuulFilter {
    /**
     * 过滤器类型有pre、route、post、error四种
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 过滤器执行顺序，数值越小优先级越高
     * @return
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 是否进行过滤，返回true会执行过滤
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext=RequestContext.getCurrentContext();
        HttpServletRequest request=requestContext.getRequest();
        String host=request.getRemoteHost();
        String method=request.getMethod();
        String uri=request.getRequestURI();
        log.info("Remote host:{},method:{},uri:{}",host,method,uri);
        return null;
    }
}
