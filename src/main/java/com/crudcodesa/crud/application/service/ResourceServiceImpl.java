package com.crudcodesa.crud.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.crudcodesa.crud.application.dto.ResourceDTO;
import com.crudcodesa.crud.application.dto.ResponseDTO;
import com.crudcodesa.crud.domain.entity.ResourceEntity;
import com.crudcodesa.crud.domain.repository.ResourceRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;

    @Override
    public ResourceEntity createResource(ResourceDTO resource) {

        ResourceEntity resourceEntity = new ResourceEntity();
        resourceEntity.setName(resource.getName());
        resourceEntity.setPrice(resource.getPrice());

        return  resourceRepository.save(resourceEntity);
    }

    @Override
    public Optional<ResourceEntity> findResourceById(Long id) {
        return resourceRepository.findById(id);
    }

    @Override
    public ResponseDTO findAll() {

        List<ResourceEntity> resources = resourceRepository.findAll();

        try {
            return new ResponseDTO("Listado de insumos encontrado con éxito", 200, resources);
        } catch (Exception e) {
            return new ResponseDTO("", 500, resources);
        }
    }

}
