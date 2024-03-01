package com.company.ledstore.web;

import com.company.ledstore.service.FeatureService;
import com.company.ledstore.service.dto.FeatureDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FeatureByIdConverter implements Converter<String, FeatureDTO> {

    private FeatureService featureService;

    @Autowired
    public FeatureByIdConverter(FeatureService featureService) {
        this.featureService = featureService;
    }

    @Override
    public FeatureDTO convert(String id) {
        return featureService.getById(Long.valueOf(id));
    }

}
