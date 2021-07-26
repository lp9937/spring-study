package com.study.security.component;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * 动态权限决策管理器，用于判断用户是否有访问权限
 */
public class DynamicAccessDecisionManager
        implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o,
                       Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        //接口未被配置成资源，直接返回
        if(CollectionUtils.isEmpty(collection)){
            return;
        }
        collection.forEach(item->{
            //访问的资源
            String needAttribute=item.getAttribute();
            //用户拥有的资源
            Collection<? extends GrantedAuthority> ownedAttribute =authentication.getAuthorities();
            ownedAttribute.forEach(grantedAuthority->{
                if(needAttribute.trim().equals(grantedAuthority.getAuthority())){
                    return;
                }
            });
        });
        throw new AccessDeniedException("没有访问权限");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
