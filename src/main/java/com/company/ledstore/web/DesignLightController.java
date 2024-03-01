package com.company.ledstore.web;

import com.company.ledstore.service.FeatureService;
import com.company.ledstore.service.LightService;
import com.company.ledstore.service.UserService;
import com.company.ledstore.service.dto.FeatureDTO;
import com.company.ledstore.service.dto.LightDTO;
import com.company.ledstore.service.dto.LightOrderDTO;
import com.company.ledstore.service.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignLightController {

    private final FeatureService featureService;
    private LightService lightService;
    private UserService userService;


    @Autowired
    public DesignLightController(FeatureService featureService, LightService lightService, UserService userService) {
        this.featureService = featureService;
        this.lightService = lightService;
        this.userService = userService;
    }

    @ModelAttribute
    public void addFeaturesToModel(Model model) {
        List<FeatureDTO> features = new ArrayList<>();
        featureService.findAll().forEach(i -> features.add(i));

        FeatureDTO.Power[] powers = FeatureDTO.Power.values();
        for (FeatureDTO.Power power : powers) {
            model.addAttribute(power.toString().toLowerCase(), filterByPower(features, power));
        }
        FeatureDTO.Length[] lengths = FeatureDTO.Length.values();
        for (FeatureDTO.Length length : lengths) {
            model.addAttribute(length.toString().toLowerCase(), filterByLength(features, length));
        }
        FeatureDTO.Flux[] fluxes = FeatureDTO.Flux.values();
        for (FeatureDTO.Flux flux : fluxes) {
            model.addAttribute(flux.toString().toLowerCase(), filterByFlux(features, flux));
        }
        FeatureDTO.Temperature[] temperatures = FeatureDTO.Temperature.values();
        for (FeatureDTO.Temperature temperature : temperatures) {
            model.addAttribute(temperature.toString().toLowerCase(), filterByTemperature(features, temperature));
        }
        FeatureDTO.Profile[] profiles = FeatureDTO.Profile.values();
        for (FeatureDTO.Profile profile : profiles) {
            model.addAttribute(profile.toString().toLowerCase(), filterByProfile(features, profile));
        }

    }

    @ModelAttribute(name = "lightOrderDTO")
    public LightOrderDTO lightOrderDTO() {
        return new LightOrderDTO();
    }

    @ModelAttribute(name = "lightDTO")
    public LightDTO lightDTO() {
        return new LightDTO();
    }

    @ModelAttribute(name = "userDTO")
    public UserDTO userDTO(Principal principal) {
        String username = principal.getName();
        UserDTO user = userService.findByUsername(username);
        return user;
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processLight(
            @Valid LightDTO light, Errors errors,
            @ModelAttribute LightOrderDTO order) {
        if (errors.hasErrors()) {
            return "design";
        }
        LightDTO saved = lightService.create(light);
        order.addLight(saved);

        return "redirect:/orders/current";
    }

    private Iterable<FeatureDTO> filterByLength(List<FeatureDTO> features, FeatureDTO.Length length) {
        return features
                .stream()
                .filter(x -> x.getLength().equals(length))
                .collect(Collectors.toList());
    }
    private Iterable<FeatureDTO> filterByPower(List<FeatureDTO> features, FeatureDTO.Power power) {
        return features.stream()
                .filter(x -> x.getPower().equals(power))
                .collect(Collectors.toList());
    }

    private Iterable<FeatureDTO> filterByFlux(List<FeatureDTO> features, FeatureDTO.Flux flux) {
        return features.stream()
                .filter(x -> x.getFlux().equals(flux))
                .collect(Collectors.toList());
    }

    private Iterable<FeatureDTO> filterByTemperature(List<FeatureDTO> features, FeatureDTO.Temperature temperature) {
        return features.stream()
                .filter(x -> x.getTemperature().equals(temperature))
                .collect(Collectors.toList());
    }

    private Iterable<FeatureDTO> filterByProfile(List<FeatureDTO> features, FeatureDTO.Profile profile) {
        return features.stream()
                .filter(x -> x.getProfile().equals(profile))
                .collect(Collectors.toList());
    }
}
