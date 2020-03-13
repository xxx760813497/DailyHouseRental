package com.xmj.demo.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

//这个类用来判断用户是否有需要的权限，称为权限管理决断器
@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {


    //方法参数：authentication是发出请求的用户权限信息集合
    //object是请求的参数，里面包含了request请求
    //colloection则是在权限资源管理器中配置的url请求需要的角色权限
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        System.out.println("AccessDecisionManager：接收到请求，进行权限验证");

        if (collection == null) {
            System.out.println("公共URL，无需权限");
            return;
        }
        //config urlroles
        Iterator<ConfigAttribute> iterator = collection.iterator();
        while (iterator.hasNext()) {
            ConfigAttribute configAttribute = iterator.next();
            //need role
            String needRole = configAttribute.getAttribute();
            //user roles
            System.out.println("需要的角色是："+needRole);
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                System.out.println("请求发出的角色："+ga.getAuthority());
                if (needRole.equals(ga.getAuthority())) {
                    System.out.println("AccessDecisionManager：是有权限的用户，通行");
                    
                    return;
                }
            }
        }
        System.out.println("AccessDecisionManager：没有权限！此用户为："+authentication.getAuthorities());
        throw new AccessDeniedException("没有权限访问!");
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
