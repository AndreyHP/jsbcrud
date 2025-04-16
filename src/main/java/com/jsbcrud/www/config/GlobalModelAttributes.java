package com.jsbcrud.www.config;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Classe responsável por adicionar atributos globais ao modelo de todas as views.
 *
 * <p>Utiliza {@link ControllerAdvice} para aplicar os atributos em todos os controllers
 * automaticamente. Essa abordagem é útil para definir informações que devem estar presentes
 * em todas as páginas, como nome do site, logotipo e direitos autorais.</p>
 *
 * <p>Os dados são fornecidos pela classe {@link Config}.</p>
 *
 * @author [Seu Nome]
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAttributes {

    /**
     * Instância de {@link Config} injetada automaticamente via construtor.
     * Fornece os dados configuráveis usados nas views.
     */
    private final Config config;

    /**
     * Adiciona atributos globais ao {@link Model} acessível em todas as views.
     *
     * <ul>
     *     <li><b>copyright:</b> Texto de direitos autorais configurado</li>
     *     <li><b>sitename:</b> Nome do cabeçalho ou título do site</li>
     *     <li><b>logo:</b> Caminho ou URL para o logotipo</li>
     * </ul>
     *
     * @param model o modelo usado nas views para renderizar os atributos
     */
    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        model.addAttribute("copyright", config.getCopyright());
        model.addAttribute("sitename", config.getHeaderName());
        model.addAttribute("logo", config.getLogo());
    }
}
