package com.github.avpcm.todo.filter;

import java.io.IOException;

import com.github.avpcm.todo.Constants;
import com.github.avpcm.todo.model.User;
import com.github.avpcm.todo.service.AuthService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthFilter implements Filter {

    private final AuthService service;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        if (isProtected(req)) {
            User user = validateUser(req);
            if (user == null) {
                setUnathorized(res);
                return;
            } else {
                req.setAttribute(Constants.USER_ATTRIBUTE_NAME, user);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @SneakyThrows
    private void setUnathorized(HttpServletResponse response) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write("please login and put your token into 'token' header");
    }

    private boolean isProtected(HttpServletRequest request) {
        return request.getServletPath().contains("/tasks");
    }

    private User validateUser(HttpServletRequest request) {
        String token = request.getHeader(Constants.TOKEN_HEADER_NAME);
        if (token == null) {
            return null;
        }

        return service.getByToken(token).orElse(null);
    }
}
