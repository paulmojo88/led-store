package com.company.ledstore.service.impl;

import com.company.ledstore.data.entity.Light;
import com.company.ledstore.data.repository.LightRepository;
import com.company.ledstore.service.LightService;
import com.company.ledstore.service.dto.LightDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LightServiceImpl implements LightService {
    private final LightRepository lightRepository;
    private final ModelMapper mapper;

    @Autowired
    public LightServiceImpl(LightRepository lightRepository, ModelMapper mapper) {
        this.lightRepository = lightRepository;
        this.mapper = mapper;
    }
    @Override
    public LightDTO create(LightDTO lightDTO) {
        Light light = mapper.map(lightDTO, Light.class);
        Light savedLight = lightRepository.save(light);
        return mapper.map(savedLight, LightDTO.class);
    }

    @Override
    public LightDTO getById(Long id) {
        Light light = lightRepository.findById(id).orElseThrow(() -> new RuntimeException("Light not found"));
        return mapper.map(light, LightDTO.class);
    }

    @Override
    public LightDTO update(LightDTO lightDTO) {
        if (lightDTO == null) {
            throw new RuntimeException("Could not update Light");
        }
        Light light = mapper.map(lightDTO, Light.class);
        Light updatedLight = lightRepository.save(light);
        return mapper.map(updatedLight, LightDTO.class);
    }

    @Override
    public void delete(Long id) {
        lightRepository.deleteById(id);
    }

    @Override
    public List<LightDTO> findAll() {
        List<Light> lights = (List<Light>) lightRepository.findAll();
        return lights.stream().map(light -> mapper.map(light, LightDTO.class)).collect(Collectors.toList());
    }
}
