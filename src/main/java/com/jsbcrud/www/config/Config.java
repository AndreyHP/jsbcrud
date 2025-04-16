package com.jsbcrud.www.config;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Classe de configuração que carrega as propriedades definidas no arquivo de configuração da aplicação.
 * A anotação {@link ConfigurationProperties} permite a leitura de propriedades com prefixo "app".
 * A anotação {@link EnableConfigurationProperties} garante que a configuração seja habilitada e utilizada.
 */

@RequiredArgsConstructor
@Getter
@Component
public class Config {
    private final String name = "Java Spring Boot CRUD";
    private final String headerName = "<span>Java Spring Boot</span><span> CRUD</span>";
    private final String shortName = "JSBCRUD";
    private final int year = 2025;
    private final String copyright = "&copy [YEAR] Hugo Azevedo";
    private final String logo = "&#128230";
    private final int cookieHoursLive = 48;

    public String getCopyright() {
        int currentYear = LocalDate.now().getYear();
        return currentYear > year
                ? copyright.replace("[YEAR]", year + " " + currentYear)
                : copyright.replace("[YEAR]", String.valueOf(year));
    }
}
