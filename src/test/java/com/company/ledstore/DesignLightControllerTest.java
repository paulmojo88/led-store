package com.company.ledstore;

import com.company.ledstore.data.entity.User;
import com.company.ledstore.data.repository.UserRepository;
import com.company.ledstore.service.FeatureService;
import com.company.ledstore.service.LightOrderService;
import com.company.ledstore.service.LightService;
import com.company.ledstore.service.UserService;
import com.company.ledstore.service.dto.FeatureDTO;
import com.company.ledstore.service.dto.LightDTO;
import com.company.ledstore.service.dto.UserDTO;
import com.company.ledstore.web.DesignLightController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DesignLightController.class)
public class DesignLightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private List<FeatureDTO> features;

    private LightDTO design;

    @MockBean
    private FeatureService featureService;

    @MockBean
    private LightService lightService;

    @MockBean
    private LightOrderService lightOrderService;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        features = Arrays.asList(
                new FeatureDTO(1L, "10W 3000K 500mm WOOD", FeatureDTO.Flux.LM1000, FeatureDTO.Power.W10,
                        FeatureDTO.Temperature.K3000, FeatureDTO.Length.MM500, FeatureDTO.Profile.WOOD),
                new FeatureDTO(2L, "20W 3000K 1000mm WOOD", FeatureDTO.Flux.LM2000, FeatureDTO.Power.W20,
                        FeatureDTO.Temperature.K3000, FeatureDTO.Length.MM1000, FeatureDTO.Profile.WOOD),
                new FeatureDTO(3L, "40W 3000K 2000mm WOOD", FeatureDTO.Flux.LM4000, FeatureDTO.Power.W40,
                        FeatureDTO.Temperature.K3000, FeatureDTO.Length.MM2000, FeatureDTO.Profile.WOOD),
                new FeatureDTO(4L, "10W 4000K 500mm ALUMINIUM", FeatureDTO.Flux.LM1000, FeatureDTO.Power.W10,
                        FeatureDTO.Temperature.K4000, FeatureDTO.Length.MM500, FeatureDTO.Profile.ALUMINIUM),
                new FeatureDTO(5L, "20W 4000K 1000mm ALUMINIUM", FeatureDTO.Flux.LM2000, FeatureDTO.Power.W20,
                        FeatureDTO.Temperature.K4000, FeatureDTO.Length.MM1000, FeatureDTO.Profile.ALUMINIUM),
                new FeatureDTO(6L, "40W 4000K 2000mm ALUMINIUM", FeatureDTO.Flux.LM4000, FeatureDTO.Power.W40,
                        FeatureDTO.Temperature.K4000, FeatureDTO.Length.MM2000, FeatureDTO.Profile.ALUMINIUM),
                new FeatureDTO(7L, "10W 5000K 500mm WOOD", FeatureDTO.Flux.LM1000, FeatureDTO.Power.W10,
                        FeatureDTO.Temperature.K5000, FeatureDTO.Length.MM500, FeatureDTO.Profile.WOOD),
                new FeatureDTO(8L, "20W 5000K 1000mm WOOD", FeatureDTO.Flux.LM2000, FeatureDTO.Power.W20,
                        FeatureDTO.Temperature.K5000, FeatureDTO.Length.MM1000, FeatureDTO.Profile.WOOD),
                new FeatureDTO(9L, "40W 5000K 2000mm WOOD", FeatureDTO.Flux.LM4000, FeatureDTO.Power.W40,
                        FeatureDTO.Temperature.K5000, FeatureDTO.Length.MM2000, FeatureDTO.Profile.WOOD),
                new FeatureDTO(10L, "10W 3000K 500mm ALUMINIUM", FeatureDTO.Flux.LM1000, FeatureDTO.Power.W10,
                        FeatureDTO.Temperature.K3000, FeatureDTO.Length.MM500, FeatureDTO.Profile.ALUMINIUM),
                new FeatureDTO(11L, "20W 3000K 1000mm ALUMINIUM", FeatureDTO.Flux.LM2000, FeatureDTO.Power.W20,
                        FeatureDTO.Temperature.K3000, FeatureDTO.Length.MM1000, FeatureDTO.Profile.ALUMINIUM),
                new FeatureDTO(12L, "40W 3000K 2000mm ALUMINIUM", FeatureDTO.Flux.LM4000, FeatureDTO.Power.W40,
                        FeatureDTO.Temperature.K3000, FeatureDTO.Length.MM2000, FeatureDTO.Profile.ALUMINIUM),
                new FeatureDTO(13L, "10W 4000K 500mm WOOD", FeatureDTO.Flux.LM1000, FeatureDTO.Power.W10,
                        FeatureDTO.Temperature.K4000, FeatureDTO.Length.MM500, FeatureDTO.Profile.WOOD),
                new FeatureDTO(14L, "20W 4000K 1000mm WOOD", FeatureDTO.Flux.LM2000, FeatureDTO.Power.W20,
                        FeatureDTO.Temperature.K4000, FeatureDTO.Length.MM1000, FeatureDTO.Profile.WOOD),
                new FeatureDTO(15L, "40W 4000K 2000mm WOOD", FeatureDTO.Flux.LM4000, FeatureDTO.Power.W40,
                        FeatureDTO.Temperature.K4000, FeatureDTO.Length.MM2000, FeatureDTO.Profile.WOOD),
                new FeatureDTO(16L, "10W 5000K 500mm ALUMINIUM", FeatureDTO.Flux.LM1000, FeatureDTO.Power.W10,
                        FeatureDTO.Temperature.K5000, FeatureDTO.Length.MM500, FeatureDTO.Profile.ALUMINIUM),
                new FeatureDTO(17L, "20W 5000K 1000mm ALUMINIUM", FeatureDTO.Flux.LM2000, FeatureDTO.Power.W20,
                        FeatureDTO.Temperature.K5000, FeatureDTO.Length.MM1000, FeatureDTO.Profile.ALUMINIUM),
                new FeatureDTO(18L, "40W 5000K 2000mm ALUMINIUM", FeatureDTO.Flux.LM4000, FeatureDTO.Power.W40,
                        FeatureDTO.Temperature.K5000, FeatureDTO.Length.MM2000, FeatureDTO.Profile.ALUMINIUM)
        );
        when(featureService.findAll())
                .thenReturn(features);
        when(featureService.getById(10L)).thenReturn(new FeatureDTO(10L, "20W 3000K 1000mm ALUMINIUM", FeatureDTO.Flux.LM2000, FeatureDTO.Power.W20,
                FeatureDTO.Temperature.K3000, FeatureDTO.Length.MM1000, FeatureDTO.Profile.ALUMINIUM));
        when(featureService.getById(17L)).thenReturn(new FeatureDTO(17L, "40W 5000K 2000mm ALUMINIUM", FeatureDTO.Flux.LM4000, FeatureDTO.Power.W40,
                FeatureDTO.Temperature.K5000, FeatureDTO.Length.MM2000, FeatureDTO.Profile.ALUMINIUM));
        design = new LightDTO();
        design.setName("Test Light");
        design.setFeatures(
                Arrays.asList(
                        new FeatureDTO(10L, "20W 3000K 1000mm ALUMINIUM", FeatureDTO.Flux.LM2000, FeatureDTO.Power.W20,
                                FeatureDTO.Temperature.K3000, FeatureDTO.Length.MM1000, FeatureDTO.Profile.ALUMINIUM),
                        new FeatureDTO(17L, "40W 5000K 2000mm ALUMINIUM", FeatureDTO.Flux.LM4000, FeatureDTO.Power.W40,
                                FeatureDTO.Temperature.K5000, FeatureDTO.Length.MM2000, FeatureDTO.Profile.ALUMINIUM)));

        when(userRepository.findByUsername("testuser"))
                .thenReturn(new User(1L,"testuser", "testpass", "Test User", "123 Street", "Someville", "CO", "12345", "123-123-1234"));
    }

    @Test
    @WithMockUser(username="testuser", password="testpass")
    public void testShowDesignForm() throws Exception {
        List<FeatureDTO> k3000Features = features.stream()
                .filter(feature -> feature.getTemperature() == FeatureDTO.Temperature.K3000)
                .collect(Collectors.toList());
        List<FeatureDTO> k4000Features = features.stream()
                .filter(feature -> feature.getTemperature() == FeatureDTO.Temperature.K4000)
                .collect(Collectors.toList());
        List<FeatureDTO> k5000Features = features.stream()
                .filter(feature -> feature.getTemperature() == FeatureDTO.Temperature.K5000)
                .collect(Collectors.toList());

        mockMvc.perform(get("/design"))
                .andExpect(status().isOk())
                .andExpect(view().name("design"))
                .andExpect(model().attribute("k3000", k3000Features))
                .andExpect(model().attribute("k4000", k4000Features))
                .andExpect(model().attribute("k5000", k5000Features));
    }


    @Test
    @WithMockUser(username="testuser", password="testpass", authorities="ROLE_USER")
    public void processLight() throws Exception {
        when(lightService.create(design))
                .thenReturn(design);
        mockMvc.perform(post("/design").with(csrf())
                        .content("name=Test+Light&features=11,17")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().stringValues("Location", "/orders/current"));
    }
}
