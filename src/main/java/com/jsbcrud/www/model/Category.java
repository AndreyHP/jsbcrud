package com.jsbcrud.www.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Lob
    private String metadata;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(3) DEFAULT 'ON'")
    private Status status = Status.ON;

    @ManyToMany(mappedBy = "categories")
    private Set<Thing> things;

    public enum Status {
        ON, OFF
    }
}