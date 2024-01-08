package com.company.ledstore.web;

import com.company.ledstore.Feature;
import com.company.ledstore.Light;
import com.company.ledstore.LightOrder;
import com.company.ledstore.data.FeatureRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/design")
@SessionAttributes("lightOrder")
public class DesignLightController {

    private final FeatureRepository featureRepository;

    @Autowired
    public DesignLightController(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    @ModelAttribute
    public void addFeaturesToModel(Model model) {
        List<Feature> features = new ArrayList<>();
        featureRepository.findAll().forEach(i -> features.add(i));

        Feature.Power[] powers = Feature.Power.values();
        for (Feature.Power power : powers) {
            model.addAttribute(power.toString().toLowerCase(), filterByPower(features, power));
        }
        Feature.Length[] lengths = Feature.Length.values();
        for (Feature.Length length : lengths) {
            model.addAttribute(length.toString().toLowerCase(),
                    filterByLength(features, length));
        }
        Feature.Flux[] fluxes = Feature.Flux.values();
        for (Feature.Flux flux : fluxes) {
            model.addAttribute(flux.toString().toLowerCase(), filterByFlux(features, flux));
        }
        Feature.Temperature[] temperatures = Feature.Temperature.values();
        for (Feature.Temperature temperature : temperatures) {
            model.addAttribute(temperature.toString().toLowerCase(), filterByTemperature(features, temperature));
        }
        Feature.Profile[] profiles = Feature.Profile.values();
        for (Feature.Profile profile : profiles) {
            model.addAttribute(profile.toString().toLowerCase(), filterByProfile(features, profile));
        }

    }

    @ModelAttribute(name = "lightOrder")
    public LightOrder order() {
        return new LightOrder();
    }

    @ModelAttribute(name = "light")
    public Light light() {
        return new Light();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processLEDLight(
            @Valid Light light, Errors errors,
            @ModelAttribute LightOrder lightOrder) {

        if (errors.hasErrors()) {
            return "design";
        }

        lightOrder.addLight(light);

        return "redirect:/orders/current";
    }

    private Iterable<Feature> filterByLength(List<Feature> features, Feature.Length length) {
        return features
                .stream()
                .filter(x -> x.getLength().equals(length))
                .collect(Collectors.toList());
    }
    private Iterable<Feature> filterByPower(List<Feature> features, Feature.Power power) {
        return features.stream()
                .filter(x -> x.getPower().equals(power))
                .collect(Collectors.toList());
    }

    private Iterable<Feature> filterByFlux(List<Feature> features, Feature.Flux flux) {
        return features.stream()
                .filter(x -> x.getFlux().equals(flux))
                .collect(Collectors.toList());
    }

    private Iterable<Feature> filterByTemperature(List<Feature> features, Feature.Temperature temperature) {
        return features.stream()
                .filter(x -> x.getTemperature().equals(temperature))
                .collect(Collectors.toList());
    }

    private Iterable<Feature> filterByProfile(List<Feature> features, Feature.Profile profile) {
        return features.stream()
                .filter(x -> x.getProfile().equals(profile))
                .collect(Collectors.toList());
    }
}
