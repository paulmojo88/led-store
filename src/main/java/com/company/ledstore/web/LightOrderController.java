package com.company.ledstore.web;

import com.company.ledstore.data.entity.User;
import com.company.ledstore.service.LightOrderService;
import com.company.ledstore.service.dto.LightOrderDTO;
import com.company.ledstore.service.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class LightOrderController {

    private LightOrderService lightOrderService;

    private ModelMapper mapper;

    @Autowired
    public LightOrderController(LightOrderService lightOrderService, ModelMapper mapper) {
        this.lightOrderService = lightOrderService;
        this.mapper = mapper;
    }

    @GetMapping("/current")
    public String orderForm(@AuthenticationPrincipal User user,
                            @ModelAttribute LightOrderDTO order) {
        if (order.getDeliveryName() == null) {
            order.setDeliveryName(user.getFullName());
        }
        if (order.getDeliveryStreet() == null) {
            order.setDeliveryStreet(user.getStreet());
        }
        if (order.getDeliveryCity() == null) {
            order.setDeliveryCity(user.getCity());
        }
        if (order.getDeliveryState() == null) {
            order.setDeliveryState(user.getState());
        }
        if (order.getDeliveryZip() == null) {
            order.setDeliveryZip(user.getZip());
        }

        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid LightOrderDTO lightOrderDTO, Errors errors, SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user) {

        if (errors.hasErrors()) {
            return "orderForm";
        }
        UserDTO userDTO = mapper.map(user, UserDTO.class);
        lightOrderDTO.setUser(userDTO);

        lightOrderService.create(lightOrderDTO);
        sessionStatus.setComplete();

        return "redirect:/";
    }

}
