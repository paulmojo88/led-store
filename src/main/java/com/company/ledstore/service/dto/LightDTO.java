package com.company.ledstore.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class LightDTO {
    private Long id;
    @NotNull
    @Size(min=5, message="Name must be at least 5 characters long")
    private String name;
    private Date createdAt;
    @Size(min=1, message="You must choose at least 1 feature")
    private List<FeatureDTO> features;

}
