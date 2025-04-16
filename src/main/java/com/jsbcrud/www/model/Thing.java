package com.jsbcrud.www.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "thing")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Thing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime date = LocalDateTime.now();

    @Column(nullable = false)
    private String name;

    @Lob
    private String description;

    private String location;
    private String photo;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Lob
    private String metadata;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(3) DEFAULT 'ON'")
    private Status status = Status.ON;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToMany
    @JoinTable(
            name = "thing_category",
            joinColumns = @JoinColumn(name = "thing_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnore
    private Set<Category> categories;

    public enum Status {
        ON, OFF
    }
}