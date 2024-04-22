package com.gongyongsheng.weblog.jwt.filter;

import com.gongyongsheng.weblog.jwt.utils.JwtTokenHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author: gys
 * @Date: 2024/4/15 15:51
 * @Version: v1.0.0
 * @Description: 校验token
 **/
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;

    @Value("${jwt.tokenHeaderKey}")
    private String tokenHeaderKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //从请求头中获取key为Authorization的值
        String header = request.getHeader(tokenHeaderKey);

        //判断是否以bearer 开头
        if(StringUtils.startsWith(header,tokenPrefix)){
            //获取token令牌
            String token = header.substring(7);
            log.info("token:{}",token);
            //判断token是否为空
            if (StringUtils.isNotBlank(token)) {
                try{
                    // 校验 Token 是否可用, 若解析异常，针对不同异常做出不同的响应参数
                    jwtTokenHelper.validateToken(token);
                }catch(SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e){
                    // 抛出异常，统一让 AuthenticationEntryPoint 处理响应参数
                    authenticationEntryPoint.commence(request, response, new AuthenticationServiceException("Token不可用"));
                    return;
                }catch (ExpiredJwtException e) {
                    authenticationEntryPoint.commence(request, response, new AuthenticationServiceException("Token 已失效"));
                    return;
                }

                //从Token中获取用户名
                String username = jwtTokenHelper.getUsernameByToken(token);
                if(StringUtils.isNotBlank(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())){
                    //从数据库中获取用户信息
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if(Objects.nonNull(userDetails)){
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        //将用户信息存入SecurityContextHolder
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
