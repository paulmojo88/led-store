package com.company.ledstore;

import com.company.ledstore.data.FeatureRepository;
import com.company.ledstore.data.OrderRepository;
import com.company.ledstore.web.DesignLightController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DesignLightController.class)
public class DesignLightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private List<Feature> features;

    private Light design;

    @MockBean
    private FeatureRepository featureRepository;

    @MockBean
    private OrderRepository orderRepository;

    @BeforeEach
    public void setup() {
        features = Arrays.asList(
                new Feature(1L, "10W 3000K 500mm WOOD", Feature.Flux.LM1000, Feature.Power.W10,
                        Feature.Temperature.K3000, Feature.Length.MM500, Feature.Profile.WOOD),
                new Feature(2L, "20W 3000K 1000mm WOOD", Feature.Flux.LM2000, Feature.Power.W20,
                        Feature.Temperature.K3000, Feature.Length.MM1000, Feature.Profile.WOOD),
                new Feature(3L, "40W 3000K 2000mm WOOD", Feature.Flux.LM4000, Feature.Power.W40,
                        Feature.Temperature.K3000, Feature.Length.MM2000, Feature.Profile.WOOD),
                new Feature(4L, "10W 4000K 500mm ALUMINIUM", Feature.Flux.LM1000, Feature.Power.W10,
                        Feature.Temperature.K4000, Feature.Length.MM500, Feature.Profile.ALUMINIUM),
                new Feature(5L, "20W 4000K 1000mm ALUMINIUM", Feature.Flux.LM2000, Feature.Power.W20,
                        Feature.Temperature.K4000, Feature.Length.MM1000, Feature.Profile.ALUMINIUM),
                new Feature(6L, "40W 4000K 2000mm ALUMINIUM", Feature.Flux.LM4000, Feature.Power.W40,
                        Feature.Temperature.K4000, Feature.Length.MM2000, Feature.Profile.ALUMINIUM),
                new Feature(7L, "10W 5000K 500mm WOOD", Feature.Flux.LM1000, Feature.Power.W10,
                        Feature.Temperature.K5000, Feature.Length.MM500, Feature.Profile.WOOD),
                new Feature(8L, "20W 5000K 1000mm WOOD", Feature.Flux.LM2000, Feature.Power.W20,
                        Feature.Temperature.K5000, Feature.Length.MM1000, Feature.Profile.WOOD),
                new Feature(9L, "40W 5000K 2000mm WOOD", Feature.Flux.LM4000, Feature.Power.W40,
                        Feature.Temperature.K5000, Feature.Length.MM2000, Feature.Profile.WOOD),
                new Feature(10L, "10W 3000K 500mm ALUMINIUM", Feature.Flux.LM1000, Feature.Power.W10,
                        Feature.Temperature.K3000, Feature.Length.MM500, Feature.Profile.ALUMINIUM),
                new Feature(11L, "20W 3000K 1000mm ALUMINIUM", Feature.Flux.LM2000, Feature.Power.W20,
                        Feature.Temperature.K3000, Feature.Length.MM1000, Feature.Profile.ALUMINIUM),
                new Feature(12L, "40W 3000K 2000mm ALUMINIUM", Feature.Flux.LM4000, Feature.Power.W40,
                        Feature.Temperature.K3000, Feature.Length.MM2000, Feature.Profile.ALUMINIUM),
                new Feature(13L, "10W 4000K 500mm WOOD", Feature.Flux.LM1000, Feature.Power.W10,
                        Feature.Temperature.K4000, Feature.Length.MM500, Feature.Profile.WOOD),
                new Feature(14L, "20W 4000K 1000mm WOOD", Feature.Flux.LM2000, Feature.Power.W20,
                        Feature.Temperature.K4000, Feature.Length.MM1000, Feature.Profile.WOOD),
                new Feature(15L, "40W 4000K 2000mm WOOD", Feature.Flux.LM4000, Feature.Power.W40,
                        Feature.Temperature.K4000, Feature.Length.MM2000, Feature.Profile.WOOD),
                new Feature(16L, "10W 5000K 500mm ALUMINIUM", Feature.Flux.LM1000, Feature.Power.W10,
                        Feature.Temperature.K5000, Feature.Length.MM500, Feature.Profile.ALUMINIUM),
                new Feature(17L, "20W 5000K 1000mm ALUMINIUM", Feature.Flux.LM2000, Feature.Power.W20,
                        Feature.Temperature.K5000, Feature.Length.MM1000, Feature.Profile.ALUMINIUM),
                new Feature(18L, "40W 5000K 2000mm ALUMINIUM", Feature.Flux.LM4000, Feature.Power.W40,
                        Feature.Temperature.K5000, Feature.Length.MM2000, Feature.Profile.ALUMINIUM)
        );
        when(featureRepository.findAll())
                .thenReturn(features);
        when(featureRepository.findById(10L)).thenReturn(Optional.of(new Feature(10L, "20W 3000K 1000mm ALUMINIUM", Feature.Flux.LM2000, Feature.Power.W20,
                Feature.Temperature.K3000, Feature.Length.MM1000, Feature.Profile.ALUMINIUM)));
        when(featureRepository.findById(17L)).thenReturn(Optional.of(new Feature(17L, "40W 5000K 2000mm ALUMINIUM", Feature.Flux.LM4000, Feature.Power.W40,
                Feature.Temperature.K5000, Feature.Length.MM2000, Feature.Profile.ALUMINIUM)));
        design = new Light();
        design.setName("Test Light");
        design.setFeatures(
                Arrays.asList(
                        new Feature(10L, "20W 3000K 1000mm ALUMINIUM", Feature.Flux.LM2000, Feature.Power.W20,
                                Feature.Temperature.K3000, Feature.Length.MM1000, Feature.Profile.ALUMINIUM),
                        new Feature(17L, "40W 5000K 2000mm ALUMINIUM", Feature.Flux.LM4000, Feature.Power.W40,
                                Feature.Temperature.K5000, Feature.Length.MM2000, Feature.Profile.ALUMINIUM)));
    }

    @Test
    public void testShowDesignForm() throws Exception {
        List<Feature> k3000Features = features.stream()
                .filter(feature -> feature.getTemperature() == Feature.Temperature.K3000)
                .collect(Collectors.toList());
        List<Feature> k4000Features = features.stream()
                .filter(feature -> feature.getTemperature() == Feature.Temperature.K4000)
                .collect(Collectors.toList());
        List<Feature> k5000Features = features.stream()
                .filter(feature -> feature.getTemperature() == Feature.Temperature.K5000)
                .collect(Collectors.toList());

        mockMvc.perform(get("/design"))
                .andExpect(status().isOk())
                .andExpect(view().name("design"))
                .andExpect(model().attribute("k3000", k3000Features))
                .andExpect(model().attribute("k4000", k4000Features))
                .andExpect(model().attribute("k5000", k5000Features));
    }

    @Test
    public void processLight() throws Exception {
        mockMvc.perform(post("/design")
                        .content("name=Test+Light&features=11,17")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().stringValues("Location", "/orders/current"));
    }



}
