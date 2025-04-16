package com.jsbcrud.www.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime date = LocalDateTime.now();

    private String photo;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String tel;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private LocalDate birth;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'USER'")
    private Type type = Type.USER;

    @Lob
    private String metadata;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(3) DEFAULT 'ON'")
    private Status status = Status.ON;

    public enum Type {
        USER, OPERATOR, ADMIN
    }

    public enum Status {
        ON, OFF
    }
}