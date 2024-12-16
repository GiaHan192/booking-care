package com.company.myweb.security;

import com.company.myweb.entity.common.ApiException;
import com.company.myweb.entity.common.ApiResponse;
import com.company.myweb.utils.JwtUtilsHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class CustomerJwtFilter extends OncePerRequestFilter {
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final JwtUtilsHelper jwtUtil;
    private final ObjectMapper objectMapper;

    public CustomerJwtFilter(JwtUtilsHelper jwtUtil, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        if (EscapeUrlConfig.shouldBypassAuthentication(antPathMatcher, requestURI, method)) {
            filterChain.doFilter(request, response);
            return;
        }

        String tokenHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            token = tokenHeader.substring(7);
            try {
                username = jwtUtil.getUsernameFromToken(token);
            } catch (Exception e) {
                handleFailJwtVerify(e, response);
                return;
            }
        } else {
            handleFailJwtVerify(ApiException.create(HttpStatus.UNAUTHORIZED)
                    .withMessage("Bearer String not found in token"), response);
            return;
        }
        if (null != username && SecurityContextHolder.getContext().getAuthentication() == null) {
            String role = jwtUtil.getAuthorizesFromToken(token);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    username, null, List.of(new SimpleGrantedAuthority(role)));
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private void handleFailJwtVerify(Exception e, HttpServletResponse response) throws IOException {
        ApiResponse<Object> apiResponse = ApiResponse.failed(e.getMessage());
        response.setContentType("application/json");
        if (e instanceof ApiException) {
            response.setStatus(((ApiException) e).getStatus().value());
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
