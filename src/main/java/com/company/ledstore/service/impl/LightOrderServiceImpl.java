package com.company.ledstore.service.impl;

import com.company.ledstore.data.entity.LightOrder;
import com.company.ledstore.data.repository.LightOrderRepository;
import com.company.ledstore.service.LightOrderService;
import com.company.ledstore.service.dto.LightOrderDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class LightOrderServiceImpl implements LightOrderService {
    private final LightOrderRepository lightOrderRepository;
    private final ModelMapper mapper;
    @Autowired
    public LightOrderServiceImpl(LightOrderRepository lightOrderRepository, ModelMapper mapper) {
        this.lightOrderRepository = lightOrderRepository;
        this.mapper = mapper;
    }

    @Override
    public LightOrderDTO create(LightOrderDTO lightOrderDTO) {
        LightOrder order = mapper.map(lightOrderDTO, LightOrder.class);
        LightOrder savedOrder = lightOrderRepository.save(order);
        return mapper.map(savedOrder, LightOrderDTO.class);
    }

    @Override
    public LightOrderDTO getById(Long id) {
        LightOrder order = lightOrderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return mapper.map(order, LightOrderDTO.class);
    }

    @Override
    public LightOrderDTO update(LightOrderDTO lightOrderDTO) {
        if (lightOrderDTO == null) {
            throw new RuntimeException("Could not update order");
        }
        LightOrder order = mapper.map(lightOrderDTO, LightOrder.class);
        LightOrder updatedOrder = lightOrderRepository.save(order);
        return mapper.map(updatedOrder, LightOrderDTO.class);
    }

    @Override
    public void delete(Long id) {
        lightOrderRepository.deleteById(id);
    }

    @Override
    public List<LightOrderDTO> findAll() {
        List<LightOrder> orders = (List<LightOrder>) lightOrderRepository.findAll();
        return orders.stream().map(order -> mapper.map(order, LightOrderDTO.class)).collect(Collectors.toList());
    }
}
