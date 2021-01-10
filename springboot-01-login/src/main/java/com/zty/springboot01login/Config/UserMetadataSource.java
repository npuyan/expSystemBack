package com.zty.springboot01login.Config;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import java.util.Collection;

@Component
public class UserMetadataSource implements FilterInvocationSecurityMetadataSource {
//    getAttributes提取出url，根据url判断该强求所需要的角色信息
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        SecurityConfig.createList("ROLE_"+"0");
        SecurityConfig.createList("ROLE_"+"1");
        return SecurityConfig.createList("ROLE_"+"2");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
