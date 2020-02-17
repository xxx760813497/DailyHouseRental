package com.xmj.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import java.util.*;

//该类的作用是通过url找到需要的角色
@Component
public class SecurityUrlFilterInvocation implements FilterInvocationSecurityMetadataSource {

    @Autowired
    SecuritySettings securitySettings;

    private Map<String, Collection<ConfigAttribute>> resourceMap = null;
    AntPathMatcher pathMatcher =  new AntPathMatcher();
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String url = ((FilterInvocation) o).getRequestUrl();

        if(resourceMap == null)
            resourceMap = loadResourceMatchAuthority();

        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String resURL = ite.next();
            if (pathMatcher.match(resURL,url)) {
                return resourceMap.get(resURL);
            }
        }
        return resourceMap.get(url);




    }

    private Map<String, Collection<ConfigAttribute>> loadResourceMatchAuthority() {
        String urlroles=securitySettings.getUrlroles();
        Map<String, Collection<ConfigAttribute>> map = new HashMap<String, Collection<ConfigAttribute>>();

        if(urlroles != null && !urlroles.isEmpty()){
            String[] resouces = urlroles.split(";");
            for(String resource : resouces){
                String[] urls = resource.split("=");
                String[] roles = urls[1].split(",");
                Collection<ConfigAttribute> list = new ArrayList<ConfigAttribute>();
                for(String role : roles){
                    ConfigAttribute config = new SecurityConfig(role.trim());
                    list.add(config);
                }
                map.put(urls[0].trim(), list);
            }
        }else {
            System.out.println("url-role为空");
        }
        System.out.println("权限配置为："+map);
        return map;

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
