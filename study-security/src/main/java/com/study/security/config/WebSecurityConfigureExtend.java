package com.study.security.config;

import com.study.security.component.*;
import com.study.security.domain.Resource;
import com.study.security.service.DynamicSecurityService;
import com.study.security.service.ResourceService;
import com.study.security.service.UserInfoService;
import com.study.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 对SpringSecurity配置的扩展
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigureExtend extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ResourceService resourceService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
                registry=http.authorizeRequests();
//        http.authorizeRequests()
//                .antMatchers("/orders")
//                .access("hasRole('ROLE_USER')");
        //不需要保护的资源路劲允许访问
        List<String> urls=getIgnoreUrlsConfig().getUrls();
        String[]urlArray=urls.toArray(new String[urls.size()]);
        registry.antMatchers(urlArray).permitAll();
        //允许跨域请求的OPTIONS请求
        registry.antMatchers(HttpMethod.OPTIONS).permitAll();
        //其它请求需要身份认证
        registry.and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                //关闭跨域请求，及不使用session
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //自定义权限拒绝处理类
                .and()
                .exceptionHandling()
                .accessDeniedHandler(getRestfulAccessDeniedHandler())
                .authenticationEntryPoint(getRestfulAuthenticationEntryPoint())
                //自定义JWT权限拦截过滤器
                .and()
                .addFilterBefore(getJwtAuthenticationTokenFilter(),
                        UsernamePasswordAuthenticationFilter.class);
        //动态权限校验过滤器
        if(getDynamicSecurityService()!=null){
            registry.and().addFilterBefore(getDynamicSecurityFilter(),
                    FilterSecurityInterceptor.class);
        }
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        //super.configure(auth);
        builder.inMemoryAuthentication()
                .withUser("springcss_user")
                .password("password1")
                .roles("USER")
                .and()
                .withUser("springcss_admin")
                .password("password2")
                .roles("USER","ADMIN");

        builder.jdbcAuthentication()
                .dataSource(null)
                .usersByUsernameQuery(null)
                .authoritiesByUsernameQuery(null)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public IgnoreUrlsConfig getIgnoreUrlsConfig() {
        return new IgnoreUrlsConfig();
    }

    @Bean
    public RestfulAccessDeniedHandler getRestfulAccessDeniedHandler(){
        return new RestfulAccessDeniedHandler();
    }

    @Bean
    public RestfulAuthenticationEntryPoint getRestfulAuthenticationEntryPoint(){
        return new RestfulAuthenticationEntryPoint();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        //获取登录用户信息
        return username -> userInfoService.loadUserByUsername(username);
    }

    @Bean
    public JwtAuthenticationTokenFilter getJwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public DynamicSecurityService getDynamicSecurityService(){
        return ()->{
            List<Resource> resources=resourceService.listAll();
            Map<String, ConfigAttribute> map=resources.stream()
                    .collect(Collectors.toMap(Resource::getUrl,
                    item->new SecurityConfig(item.getId()+":"+item.getName())));
            return map;
        };
    }

    @Bean
    public DynamicSecurityMetadataSource getDynamicSecurityMetadataSource(){
        return new DynamicSecurityMetadataSource();
    }

    @Bean
    public DynamicAccessDecisionManager getDynamicAccessDecisionManager(){
        return new DynamicAccessDecisionManager();
    }

    @Bean
    public DynamicSecurityFilter getDynamicSecurityFilter(){
        return new DynamicSecurityFilter();
    }

    @Bean
    public JwtTokenUtil getJwtTokenUtil(){
        return new JwtTokenUtil();
    }

    //UsernamePasswordAuthenticationFilter
}
