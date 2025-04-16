package com.jsbcrud.www.controller;

import com.jsbcrud.www.config.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador responsável por lidar com as operações relacionadas à entidade "Thing".
 *
 * Mapeado sob o caminho base "/thing", este controller fornece endpoints para
 * visualização e criação de novos registros da entidade.
 *
 * Utiliza a configuração definida em {@link Config} para obter informações como o nome curto da aplicação.
 *
 * Anotado com {@link Controller} para indicar que é um controlador Spring MVC e
 * {@link RequiredArgsConstructor} para injeção automática de dependências via construtor.
 *
 * @author SeuNome
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/thing")
public class ThingController {

    /** Instância de configuração compartilhada usada para definir títulos dinâmicos */
    private final Config config;

    /**
     * Exibe os detalhes de uma entidade "Thing" com base no ID fornecido.
     *
     * @param id    Identificador único da entidade "Thing" a ser visualizada.
     * @param model Objeto {@link Model} usado para adicionar atributos à view.
     * @return O nome da view que será renderizada (nesse caso, "thing/view").
     */
    @GetMapping("/view/{id}")
    public String viewThing(@PathVariable Long id, Model model) {
        model.addAttribute("title", config.getShortName() + " - Nome da coisa aqui");
        return "thing/view";
    }

    /**
     * Exibe o formulário para criação de uma nova entidade "Thing".
     *
     * @param model Objeto {@link Model} usado para adicionar atributos à view.
     * @return O nome da view que será renderizada (nesse caso, "thing/new").
     */
    @GetMapping("/new")
    public String newThing(Model model) {
        model.addAttribute("title", config.getShortName() + " - Nova coisa");
        return "thing/new";
    }
}
