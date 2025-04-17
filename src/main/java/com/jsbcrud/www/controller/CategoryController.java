package com.jsbcrud.www.controller;

import com.jsbcrud.www.config.Config;
import com.jsbcrud.www.model.Account;
import com.jsbcrud.www.model.Category;
import com.jsbcrud.www.repository.CategoryRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cat")
public class CategoryController {

    private final Config config;
    private final CategoryRepository categoryRepository;

    @GetMapping("/list")
    public String listCat(Model model, HttpServletRequest request) {
        List<Category> categories = categoryRepository.findByStatusOrderByNameAsc(Category.Status.ON);
        model.addAttribute("categories", categories);

        Account loggedUser = (Account) request.getAttribute("loggedUser");
        model.addAttribute("loggedUser", loggedUser);

        model.addAttribute("title", config.getShortName() + " - Categorias");
        return "cat/list";
    }

    @GetMapping("/new")
    public String newCat(Model model) {
        model.addAttribute("title", config.getShortName() + " - Nova Categoria");
        return "cat/new";
    }

    @GetMapping("/delete/{id}")
    public String deleteCat(
            @PathVariable Integer id,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {

        Account loggedUser = (Account) request.getAttribute("loggedUser");

        if (loggedUser == null || loggedUser.getType() != Account.Type.ADMIN) {
            redirectAttributes.addFlashAttribute("error", "Acesso negado!");
            return "redirect:/cat/list";
        }

        Optional<Category> categoryOpt = categoryRepository.findById(id);

        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();

            if (category.getStatus() == Category.Status.ON) {
                category.setStatus(Category.Status.OFF);
                categoryRepository.save(category);
                redirectAttributes.addFlashAttribute("success", "Categoria '" + category.getName() + "' apagada com sucesso!");
            }
        }

        return "redirect:/cat/list";
    }
    @PostMapping("/new")
    public String createCat(
            @ModelAttribute Category category,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        // Obtém o usuário logado
        Account loggedUser = (Account) request.getAttribute("loggedUser");

        // Verifica se o usuário é ADMIN
        if (loggedUser == null || loggedUser.getType() != Account.Type.ADMIN) {
            redirectAttributes.addFlashAttribute("error", "Acesso negado!");
            return "redirect:/cat/list";
        }

        // Salva a nova categoria com status ON
        category.setStatus(Category.Status.ON);
        categoryRepository.save(category);

        // Mensagem de sucesso e redirecionamento
        redirectAttributes.addFlashAttribute("success", "Categoria '" + category.getName() + "' criada com sucesso!");
        return "redirect:/cat/new";
    }
}