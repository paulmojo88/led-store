package com.company.ledstore;

import jakarta.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
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
    private Date createdAt = new Date();
    @Size(min=1, message="You must choose at least 1 feature")
    @ManyToMany()
    private List<Feature> features = new ArrayList<>();
    public void addFeature(Feature feature) {
        this.features.add(feature);
    }

}
