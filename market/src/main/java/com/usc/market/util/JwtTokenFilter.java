package com.usc.market.util;

import com.google.gson.Gson;
import com.usc.market.model.UserEntity;
import com.usc.market.repo.UserRepository;
import com.usc.market.vo.Response;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private Gson gson = new Gson();

    /**
     * Key to store token
     */
    public static final String HEADER_STRING = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // ignore all auth checker
        String url = request.getRequestURI();
        if (url.startsWith("/auth/") || url.equals("/listing/findAll")) {
            System.out.println("Ignore " + url);
            chain.doFilter(request, response);
            return;
        }
        String token = request.getHeader(HEADER_STRING);
        System.out.println(request.getRequestURI() + " " + token);

        if (null != token) {
            System.out.println(token);
            try {
                String username = jwtTokenUtil.getUsernameFromToken(token);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserEntity userEntity = this.userRepository.findByUsername(username);
                    UserDetails userDetails = new User(userEntity.getUsername(), userEntity.getPassword(), Collections.emptyList());
                    if (jwtTokenUtil.validateToken(token, userDetails)) {
                        log.info("Available user: {}, {}, {}", userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        // add role & id to exists header
                        HeaderMapRequestWrapper wrapper = new HeaderMapRequestWrapper(request);
                        wrapper.addHeader("username", username);
                        wrapper.addHeader("id", String.valueOf(userEntity.getId()));
                        request = wrapper;
                        System.out.println(request.getRequestURI());
                        chain.doFilter(request, response);
                        return;
                    }
                }
            } catch (ExpiredJwtException ex) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                response.getWriter().println(
                        gson.toJson(Response.failAuth(new String("Login outdated. Please re-login。".getBytes(), StandardCharsets.UTF_8)))
                );
                return;
            }
        }
        chain.doFilter(request, response);
    }

}
