package com.company.ledstore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeatureDTO {
    private Long id;
    private String name;
    private FeatureDTO.Flux flux;
    private FeatureDTO.Power power;
    private FeatureDTO.Temperature temperature;
    private FeatureDTO.Length length;
    private FeatureDTO.Profile profile;
    public enum Flux {LM1000, LM2000, LM4000}
    public enum Power {W10, W20, W40}
    public enum Temperature {K3000, K4000, K5000}
    public enum Length {MM500, MM1000, MM2000}
    public enum Profile {WOOD, ALUMINIUM}
}
