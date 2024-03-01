package com.company.ledstore.service.impl;

import com.company.ledstore.data.entity.Feature;
import com.company.ledstore.data.repository.FeatureRepository;
import com.company.ledstore.service.FeatureService;
import com.company.ledstore.service.dto.FeatureDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FeatureServiceImpl implements FeatureService {
    private final FeatureRepository featureRepository;
    private final ModelMapper mapper;
    @Autowired
    public FeatureServiceImpl(FeatureRepository featureRepository, ModelMapper mapper) {
        this.featureRepository = featureRepository;
        this.mapper = mapper;
    }
    @Override
    @Transactional
    public FeatureDTO create(FeatureDTO featureDTO) {
        Feature feature = mapper.map(featureDTO, Feature.class);
        Feature savedFeature = featureRepository.save(feature);
        return mapper.map(savedFeature, FeatureDTO.class);
    }

    @Override
    public FeatureDTO getById(Long id) {
        Feature feature = featureRepository.findById(id).orElseThrow(() -> new RuntimeException("Feature not found"));
        return mapper.map(feature, FeatureDTO.class);
    }

    @Override
    @Transactional
    public FeatureDTO update(FeatureDTO featureDTO) {
        if (featureDTO == null || featureDTO.getId() == null) {
            throw new RuntimeException("Could not update feature");
        }
        Feature existingFeature = featureRepository.findById(featureDTO.getId())
                .orElseThrow(() -> new RuntimeException("Feature not found"));
        // Update the necessary fields of the existingFeature using the featureDTO
        // For example: existingFeature.setName(featureDTO.getName());
        Feature updatedFeature = featureRepository.save(existingFeature);
        return mapper.map(updatedFeature, FeatureDTO.class);
    }

    @Override
    public void delete(Long id) { featureRepository.deleteById(id); }

    @Override
    public List<FeatureDTO> findAll() {
        Iterable<Feature> iterableFeatures = featureRepository.findAll();
        List<Feature> features = StreamSupport.stream(iterableFeatures.spliterator(), false).collect(Collectors.toList());
        return features.stream().map(feature -> mapper.map(feature, FeatureDTO.class)).collect(Collectors.toList());
    }

}
