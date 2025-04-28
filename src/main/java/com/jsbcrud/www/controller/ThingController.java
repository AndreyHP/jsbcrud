package com.jsbcrud.www.controller;

import com.jsbcrud.www.config.Config;
import com.jsbcrud.www.model.Account;
import com.jsbcrud.www.model.Category;
import com.jsbcrud.www.model.Thing;
import com.jsbcrud.www.repository.CategoryRepository;
import com.jsbcrud.www.repository.ThingRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/thing")
public class ThingController {

    private final Config config;
    private final ThingRepository thingRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping("/view/{id}")
    public String viewThing(@PathVariable Long id, Model model, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            model.addAttribute("loggedUser", session.getAttribute("user"));
        }

        // Buscar o Thing pelo ID e garantir que o status é ON
        Optional<Thing> thingOpt = thingRepository.findById(id);
        if (thingOpt.isEmpty() || thingOpt.get().getStatus() != Thing.Status.ON) {
            return "redirect:/"; // Redireciona se não existir ou estiver desativado
        }

        Thing thing = thingOpt.get();

        model.addAttribute("thing", thing);
        model.addAttribute("owner", thing.getAccount()); // Passa o 'Account' relacionado
        model.addAttribute("categories", thing.getCategories());
        model.addAttribute("title", config.getShortName() + " - " + thing.getName());

        return "thing/view";
    }

    @GetMapping("/edit/{id}")
    public String editThing(@PathVariable Integer id, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        Account loggedUser = (Account) request.getAttribute("loggedUser");

        Optional<Thing> thingOpt = thingRepository.findByIdAndStatus(id, Thing.Status.ON);
        if (thingOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Item não encontrado ou inativo!");
            return "redirect:/";
        }

        Thing thing = thingOpt.get();

        if (!loggedUser.getId().equals(thing.getAccount().getId()) && loggedUser.getType() != Account.Type.ADMIN) {
            redirectAttributes.addFlashAttribute("error", "Acesso negado!");
            return "redirect:/thing/view/" + id;
        }

        List<Category> categories = categoryRepository.findByStatusOrderByNameAsc(Category.Status.ON);

        model.addAttribute("title", "Editar Item");
        model.addAttribute("thing", thing);
        model.addAttribute("categories", categories);
        model.addAttribute("loggedUser", loggedUser);

        return "thing/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateThing(@PathVariable Integer id,
                              @RequestParam String name,
                              @RequestParam(required = false) String description,
                              @RequestParam(required = false) String location,
                              @RequestParam(required = false) String photo,
                              @RequestParam(required = false) BigDecimal price,
                              @RequestParam(required = false) List<Integer> categories,
                              HttpServletRequest request,
                              RedirectAttributes redirectAttributes) {
        // Obtém o usuário logado
        Account loggedUser = (Account) request.getAttribute("loggedUser");

        // Busca o Thing pelo ID
        Optional<Thing> thingOpt = thingRepository.findByIdAndStatus(id, Thing.Status.ON);
        if (thingOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Item não encontrado!");
            return "redirect:/";
        }

        Thing thing = thingOpt.get();

        // Verifica permissão (somente o dono ou ADMIN pode editar)
        if (!loggedUser.getId().equals(thing.getAccount().getId()) && loggedUser.getType() != Account.Type.ADMIN) {
            redirectAttributes.addFlashAttribute("error", "Acesso negado!");
            return "redirect:/thing/view/" + id;
        }

        // Atualiza os dados
        thing.setName(name);
        thing.setDescription(description);
        thing.setLocation(location);
        thing.setPhoto(photo);
        thing.setPrice(price);

        // Atualiza categorias selecionadas
        if (categories != null && !categories.isEmpty()) {
            List<Category> selectedCategories = categoryRepository.findAllById(categories);
            thing.setCategories(new HashSet<>(selectedCategories));
        }

        // Salva no banco
        thingRepository.save(thing);

        redirectAttributes.addFlashAttribute("success", "Item atualizado com sucesso!");
        return "redirect:/thing/view/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteThing(@PathVariable Long id, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Optional<Thing> thingOpt = thingRepository.findById(id);

        if (thingOpt.isEmpty() || thingOpt.get().getStatus() != Thing.Status.ON) {
            redirectAttributes.addFlashAttribute("error", "Item não encontrado ou já desativado.");
            return "redirect:/";
        }

        Thing thing = thingOpt.get();
        Account loggedUser = (Account) request.getAttribute("loggedUser");

        if (!thing.getAccount().getId().equals(loggedUser.getId()) && loggedUser.getType() != Account.Type.ADMIN) {
            redirectAttributes.addFlashAttribute("error", "Acesso negado!");
            return "redirect:/";
        }

        thing.setStatus(Thing.Status.OFF);
        thingRepository.save(thing);

        redirectAttributes.addFlashAttribute("success", "Item removido com sucesso.");
        return "redirect:/";
    }

    @GetMapping("/new")
    public String newThing(Model model, HttpServletRequest request) {

        Account loggedUser = (Account) request.getAttribute("loggedUser");

        List<Category> categories = categoryRepository.findByStatusOrderByNameAsc(Category.Status.ON);

        model.addAttribute("title", "Novo Item");
        model.addAttribute("categories", categories);
        model.addAttribute("loggedUser", loggedUser);

        return "thing/new";
    }

    @PostMapping("/new")
    public String createThing(
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String photo,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) List<Integer> categories,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {

        Account loggedUser = (Account) request.getAttribute("loggedUser");

        Thing thing = new Thing();
        thing.setName(name);
        thing.setDescription(description);
        thing.setLocation(location);
        thing.setPhoto(photo);
        thing.setPrice(price);
        thing.setAccount(loggedUser);
        thing.setStatus(Thing.Status.ON);

        if (categories != null && !categories.isEmpty()) {
            List<Category> selectedCategories = categoryRepository.findAllById(categories);
            thing.setCategories(new HashSet<>(selectedCategories));
        }

        thingRepository.save(thing);

        redirectAttributes.addFlashAttribute("success", "Item cadastrado com sucesso!");
        return "redirect:/thing/new";
    }
}