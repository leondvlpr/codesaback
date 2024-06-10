package com.crudcodesa.crud.application.service;

import java.util.Optional;

import com.crudcodesa.crud.application.dto.ResourceDTO;
import com.crudcodesa.crud.application.dto.ResponseDTO;
import com.crudcodesa.crud.domain.entity.ResourceEntity;

public interface ResourceService {

    ResourceEntity createResource(ResourceDTO resource);

    Optional<ResourceEntity> findResourceById(Long id);

    ResponseDTO findAll();

}
