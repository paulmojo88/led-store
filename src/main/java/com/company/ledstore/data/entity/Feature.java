package com.company.ledstore.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Flux flux;
    @Enumerated(EnumType.STRING)
    private Power power;
    @Enumerated(EnumType.STRING)
    private Temperature temperature;
    @Enumerated(EnumType.STRING)
    private Length length;
    @Enumerated(EnumType.STRING)
    private Profile profile;
    public enum Flux {LM1000, LM2000, LM4000}
    public enum Power {W10, W20, W40}
    public enum Temperature {K3000, K4000, K5000}
    public enum Length {MM500, MM1000, MM2000}
    public enum Profile {WOOD, ALUMINIUM}
}
