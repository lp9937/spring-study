package com.study.security.component;

import cn.hutool.core.util.URLUtil;
import com.study.security.service.DynamicSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

/**
 * 动态权限数据源，用于获取动态权限规则
 */
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    //private FilterInvocationSecurityMetadataSource superMetadataSource;
    @Autowired
    private DynamicSecurityService dynamicSecurityService;
    private Map<String,ConfigAttribute> configAttributeMap;

    /**
     * @PostConstruct注解的方法，执行顺序:Constructor(构造方法)->@Autowired(依赖注入)->@PostConstructor
     */
    @PostConstruct
    public void loadConfigAttribute(){
        //todo 从数据库加载
        configAttributeMap=dynamicSecurityService.loadDataSource();
    }

    /**
     * @PreDestroy注解的方法，执行顺序@PreDestroy->DisposableBean接口destroy方法->bean定义的销毁方法
     */
    @PreDestroy
    public void clearDataSource() {
        if(!CollectionUtils.isEmpty(configAttributeMap)){
            configAttributeMap.clear();
        }
        configAttributeMap=null;
    }


    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if(CollectionUtils.isEmpty(configAttributeMap)){
            //返回默认配置
            return null;
        }
        FilterInvocation fi=(FilterInvocation)o;
        //当前请求url
        String url=fi.getRequestUrl();
        String path= URLUtil.getPath(url);
        PathMatcher pathMatcher=new AntPathMatcher();
        List<ConfigAttribute> configAttributes=new ArrayList<>();
        configAttributeMap.forEach((k,v)->{
            if(pathMatcher.match(k,path)){
                configAttributes.add(v);
            }
        });
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        if(CollectionUtils.isEmpty(configAttributeMap)){
            //返回默认配置
            //superMetadataSource.getAllConfigAttributes();
            return null;
        }
        Set<ConfigAttribute> allAttributes=new HashSet<>();
        allAttributes.addAll(configAttributeMap.values());
        return allAttributes;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
