package com.xmj.demo.config;


import com.xmj.demo.security.*;
import org.apache.ibatis.reflection.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import java.sql.Array;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(SecuritySettings.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    SecuritySettings securitySettings;

    @Autowired
    LoginService loginService;

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Autowired
    AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;

    @Autowired
    CustomAccessDecisionManager customAccessDecisionManager;

    @Autowired
    SecurityUrlFilterInvocation securityUrlFilterInvocation;

    @Autowired
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //这里是登录管理配置，配置了自己的登录处理器和密码加密
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //开启授权认证，并配置权限资源管理器类和权限决策管理器类
        http.authorizeRequests().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                @Override
                public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                    o.setSecurityMetadataSource(securityUrlFilterInvocation);
                    o.setAccessDecisionManager(customAccessDecisionManager);
                    return o;
                }
            })
                //配置登录和注册url请求可以不用认证即可通过
                .antMatchers(securitySettings.getPermitall().split(",")).permitAll()
                //其余所有都需要认证
                .anyRequest().authenticated()

                //将登录功能将由security管理，使用表单登录的形式，
                // loginPage就是登录的页面，因为这里是使用前后端用JSON交互，
                // 所以当未登录时会发出一个nologin的请求
                //同时配置了登录成功处理器和失败处理器
                .and().formLogin().loginPage("/nologin").permitAll().failureHandler(loginFailureHandler).successHandler(loginSuccessHandler)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)

                //登出成功后的页面，也是nologin请求
                .and().logout().logoutSuccessUrl(securitySettings.getLogoutsuccessurl())

                //当用户权限不足时会跳转的页面，这里也是一个nologin请求，以及权限不足的处理器
                .and().exceptionHandling()
                .accessDeniedPage(securitySettings.getDeniedpage())
                .accessDeniedHandler(authenticationAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                //这里方便测试关闭了csrf防御策略，同时开启了cors跨域访问
                .and()
                .csrf().disable().cors()

        //这里没有使用的是从JSON请求中获取用户名的过滤器，因为security自带的获取用户名的过滤器是从request中获取
        //后来我改用formdata的形式进行登录，所以用不到这个了。
//                .and().addFilterAt(customAuthenticationFilter(),
//                UsernamePasswordAuthenticationFilter.class);

        ;
    }

//    @Bean
//    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
//        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
//        filter.setAuthenticationSuccessHandler(loginSuccessHandler);
//        filter.setAuthenticationFailureHandler(loginFailureHandler);
//        filter.setFilterProcessesUrl("/login");
//
//        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
//        filter.setAuthenticationManager(authenticationManagerBean());
//        return filter;
//    }
}
