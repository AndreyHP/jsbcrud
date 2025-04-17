package com.jsbcrud.www.controller;

import com.jsbcrud.www.config.Config;
import com.jsbcrud.www.model.Account;
import com.jsbcrud.www.repository.AccountRepository;
import com.jsbcrud.www.util.HashUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final Config config;
    private final AccountRepository accountRepository;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", config.getShortName() + " - Faça login");
        model.addAttribute("disable_nav", true); // Oculta <nav>
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(
            @RequestParam String email,
            @RequestParam String password,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) {
        Optional<Account> userOpt = accountRepository.findByEmailAndStatus(email, Account.Status.ON);

        if (userOpt.isPresent()) {
            String hashedPassword = HashUtil.sha256(password);

            if (userOpt.get().getPassword().equals(hashedPassword)) {
                // Criar cookie persistente
                Cookie loginCookie = new Cookie("user", userOpt.get().getId().toString());
                loginCookie.setMaxAge(config.getCookieHoursLive() * 60 * 60);
                loginCookie.setHttpOnly(true);
                loginCookie.setPath("/");
                response.addCookie(loginCookie);

                return "redirect:/";
            }
        }

        model.addAttribute("title", config.getShortName() + " - Faça login");
        model.addAttribute("error", "E-mail ou senha inválidos!");
        model.addAttribute("disable_nav", true); // Oculta <nav>
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie loginCookie = new Cookie("user", "");
        loginCookie.setMaxAge(0); // Expira imediatamente
        loginCookie.setPath("/");
        response.addCookie(loginCookie);
        return "redirect:/login";
    }
}