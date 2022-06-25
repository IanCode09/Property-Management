package com.woyo.propertymanagement.service.impl;

import com.woyo.propertymanagement.converter.PropertyConverter;
import com.woyo.propertymanagement.dto.PropertyDTO;
import com.woyo.propertymanagement.entity.PropertyEntity;
import com.woyo.propertymanagement.repository.PropertyRepository;
import com.woyo.propertymanagement.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private PropertyConverter propertyConverter;

    @Override
    public PropertyDTO saveProperty(PropertyDTO propertyDTO) {
        PropertyEntity pe = propertyConverter.convertDTOtoEntity(propertyDTO);
        propertyRepository.save(pe);

        propertyDTO = propertyConverter.convertEntityToDTO(pe);
        return propertyDTO;
    }

    @Override
    public List<PropertyDTO> getAllProperties() {
        List<PropertyEntity> listOfProps = (List<PropertyEntity>)propertyRepository.findAll();
        List<PropertyDTO> propList = new ArrayList<>();

        for (PropertyEntity pe : listOfProps) {
            PropertyDTO dto = propertyConverter.convertEntityToDTO(pe);
            propList.add(dto);
        }

        return propList;
    }

    @Override
    public PropertyDTO updateProperty(PropertyDTO propertyDTO, Long propertyId) {
        Optional<PropertyEntity> property = propertyRepository.findById(propertyId);
        PropertyDTO dto = null;

        if (property.isPresent()) {
            PropertyEntity pe = property.get();
            pe.setTitle(propertyDTO.getTitle());
            pe.setAddress(propertyDTO.getAddress());
            pe.setOwnerName(propertyDTO.getOwnerName());
            pe.setOwnerEmail(propertyDTO.getOwnerEmail());
            pe.setPrice(propertyDTO.getPrice());
            pe.setDescription(propertyDTO.getDescription());

            dto = propertyConverter.convertEntityToDTO(pe);
            propertyRepository.save(pe);
        }

        return dto;
    }

    @Override
    public PropertyDTO updatePropertyDescription(PropertyDTO propertyDTO, Long propertyId) {
        Optional<PropertyEntity> property = propertyRepository.findById(propertyId);
        PropertyDTO dto = null;

        if (property.isPresent()) {
            PropertyEntity pe = property.get();
            pe.setDescription(propertyDTO.getDescription());

            dto = propertyConverter.convertEntityToDTO(pe);
            propertyRepository.save(pe);
        }

        return dto;
    }

    @Override
    public PropertyDTO updatePropertyPrice(PropertyDTO propertyDTO, Long propertyId) {
        Optional<PropertyEntity> property = propertyRepository.findById(propertyId);
        PropertyDTO dto = null;

        if (property.isPresent()) {
            PropertyEntity pe = property.get();
            pe.setPrice(propertyDTO.getPrice());

            dto = propertyConverter.convertEntityToDTO(pe);
            propertyRepository.save(pe);
        }

        return dto;
    }

    @Override
    public void deleteProperty(Long propertyId) {
        propertyRepository.deleteById(propertyId);
    }
}
