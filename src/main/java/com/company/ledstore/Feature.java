package com.company.ledstore;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED, force=true)
@Table (name = "feature")
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;
    private final String name;
    @Enumerated(EnumType.STRING)
    private final Flux flux;
    @Enumerated(EnumType.STRING)
    private final Power power;
    @Enumerated(EnumType.STRING)
    private final Temperature temperature;
    @Enumerated(EnumType.STRING)
    private final Length length;
    @Enumerated(EnumType.STRING)
    private final Profile profile;
    public enum Flux {LM1000, LM2000, LM4000}
    public enum Power {W10, W20, W40}
    public enum Temperature {K3000, K4000, K5000}
    public enum Length {MM500, MM1000, MM2000}
    public enum Profile {WOOD, ALUMINIUM}
}
