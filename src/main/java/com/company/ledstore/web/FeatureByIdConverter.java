package com.company.ledstore.web;

import com.company.ledstore.Feature;
import com.company.ledstore.data.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FeatureByIdConverter implements Converter<String, Feature> {

    private FeatureRepository featureRepo;

    @Autowired
    public FeatureByIdConverter(FeatureRepository featureRepo) {
        this.featureRepo = featureRepo;
    }

    @Override
    public Feature convert(String id) {
        return featureRepo.findById(Long.valueOf(id)).orElse(null);
    }

}
