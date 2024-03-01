package com.company.ledstore.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Light {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min=5, message="Name must be at least 5 characters long")
    private String name;
    private Date createdAt;
    @Size(min=1, message="You must choose at least 1 feature")
    @ManyToMany(targetEntity=Feature.class)
    private List<Feature> features;
    @PrePersist
    void createdAt() {
        this.createdAt = new Date();
    }

}
