package com.study.security.component;

import com.study.security.util.JwtTokenUtil;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT登陆授权过滤器
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private static final Logger LOGGER= LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader=httpServletRequest.getHeader(this.tokenHeader);
        if(!Strings.isBlank(authHeader)&&
                authHeader.startsWith(this.tokenHead)){
            String authToken=authHeader.substring(this.tokenHead.length());
            String userName=jwtTokenUtil.getUserNameFromToken(authToken);
            LOGGER.info("验证用户:{}",userName);
            if(Strings.isNotBlank(userName)&&
                    SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails=userDetailsService.loadUserByUsername(userName);
                if(jwtTokenUtil.validateToken(authToken,userDetails)){
                    UsernamePasswordAuthenticationToken passwordAuthenticationToken=
                            new UsernamePasswordAuthenticationToken(userDetails,
                                    null,userDetails.getAuthorities());
                    passwordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(httpServletRequest));
                    LOGGER.info("被授权用户:{}",userName);
                    SecurityContextHolder.getContext()
                            .setAuthentication(passwordAuthenticationToken);
                }
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
