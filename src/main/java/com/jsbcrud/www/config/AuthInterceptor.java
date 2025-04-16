package com.jsbcrud.www.config;

import com.jsbcrud.www.model.Account;
import com.jsbcrud.www.repository.AccountRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final AccountRepository accountRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().equals("/login")) {
            return true;
        }

        String userId = getUserIdFromCookies(request);
        if (userId != null) {
            Optional<Account> user = accountRepository.findById(Long.parseLong(userId));
            if (user.isPresent()) {
                request.setAttribute("loggedUser", user.get());
                return true;
            }
        }

        response.sendRedirect("/login");
        return false;
    }

    private String getUserIdFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> "user".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
}