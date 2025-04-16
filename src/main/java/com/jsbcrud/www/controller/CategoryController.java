package com.jsbcrud.www.controller;

import com.jsbcrud.www.config.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cat")
public class CategoryController {

    private final Config config;

    @GetMapping("/list")
    public String listCat(Model model) {
        model.addAttribute("title", config.getShortName() + " - Categorias");
        return "cat/list";
    }

    @GetMapping("/new")
    public String newCat(Model model) {
        model.addAttribute("title", config.getShortName() + " - Nova Categoria");
        return "cat/new";
    }
}