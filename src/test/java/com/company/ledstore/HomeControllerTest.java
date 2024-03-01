package com.company.ledstore;

import com.company.ledstore.data.repository.FeatureRepository;
import com.company.ledstore.data.repository.LightOrderRepository;
import com.company.ledstore.data.repository.LightRepository;
import com.company.ledstore.data.repository.UserRepository;
import com.company.ledstore.service.FeatureService;
import com.company.ledstore.service.LightOrderService;
import com.company.ledstore.service.LightService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeatureService featureService;

    @MockBean
    private LightService lightService;

    @MockBean
    private LightOrderService lightOrderService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(content().string(
                        containsString("Welcome to...")));
    }

}







