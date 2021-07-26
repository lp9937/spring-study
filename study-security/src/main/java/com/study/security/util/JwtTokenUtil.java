package com.study.security.util;

import cn.hutool.core.date.DateUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt token生成工具类
 * Jwt token的结构:header.payload.signature
 * header结构:token类型和算法(RS256、BASE64、HS256等)
 * {“typ”:"JWT","alg":"RS256"}
 * payload结构:声明(用户实体或其他数据)，声明有三种类型:registered、public、private
 * registered claims:有一组预定义的声明，它们不是强制的，但是推荐。
 * 比如iss(issuer),exp(expiration time),sub(subject),aud(audience)等
 * public claims:可以随意定义
 * private claims:用于在同意使用它的各方之间的共享信息
 * {"sub":"lp","created":1489079981393,"exp":1489684781}
 * signature:编码过后的header、编码过后的payload、密钥secret
 * RS256(base64(header)+"."+base64(payload),secret)
 */
public class JwtTokenUtil {
    private static final Logger LOGGER= LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 生成token
     * @param claims
     * @return
     */
    private String generateToken(Map<String,Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    /**
     * 从token中获取payload中的声明
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token){
        Claims claims=null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            LOGGER.info("Token格式验证失败:{}",token);
        }
        return claims;
    }

    /**
     * token过期时间
     * @return
     */
    private Date generateExpirationDate(){
        return new Date(System.currentTimeMillis()+expiration*1000);
    }

    /**
     * 判断token是否过期
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token){
        Date expiredDate=getExpiredDateFormToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 获取token过期时间
     * @param token
     * @return
     */
    private Date getExpiredDateFormToken(String token){
        Claims claims=getClaimsFromToken(token);
        return claims.getExpiration();
    }

    private boolean tokenRefreshJustBefore(String token,int time){
        Claims claims=getClaimsFromToken(token);
        Date created=claims.get(CLAIM_KEY_CREATED,Date.class);
        Date refreshDate=new Date();
        //刷新时间在创建时间和指定时间之间
        if(refreshDate.after(created)&&
                refreshDate.before(DateUtil.offsetSecond(created,time))){
            return true;
        }
        return false;
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token){
        String userName= Strings.EMPTY;
        try{
            Claims claims=getClaimsFromToken(token);
            userName=claims.getSubject();
        }catch (Exception e){
            LOGGER.info("从Token获取用户信息失败:{}",e);
        }
        return userName;
    }

    /**
     * 验证token是否有效
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token, UserDetails userDetails){
        String uName=getUserNameFromToken(token);
        if(userDetails.getUsername().equals(uName) && !isTokenExpired(token)){
            return true;
        }
        return false;
    }

    /**
     * 根据用户生成token
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims=new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }

    /**
     * 刷新没有过期的token
     * @param oldToken
     * @return
     */
    public String refreshToken(String oldToken){
        if(Strings.isBlank(oldToken)){
            return Strings.EMPTY;
        }
        //去掉header
        String token=oldToken.substring(tokenHead.length());
        if(Strings.isBlank(token)){
            return Strings.EMPTY;
        }
        //token校验不通过
        Claims claims=getClaimsFromToken(token);
        if(claims==null){
            return Strings.EMPTY;
        }
        //判断token是否过期
        if(isTokenExpired(token)){
            return Strings.EMPTY;
        }
        //如果token在30分钟之内刚刚刷新过，返回原token
        if(tokenRefreshJustBefore(token,30*60)){
            return token;
        }else{
            claims.put(CLAIM_KEY_CREATED,new Date());
            claims.put(CLAIM_KEY_USERNAME,getUserNameFromToken(token));
            return generateToken(claims);
        }
    }

}
