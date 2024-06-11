package com.crudcodesa.crud.application.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.crudcodesa.crud.application.dto.ResourceDTO;
import com.crudcodesa.crud.application.dto.ResponseDTO;
import com.crudcodesa.crud.domain.entity.ResourceEntity;

public interface ResourceService {

    ResponseEntity<ResponseDTO> createResource(ResourceDTO resource);

    Optional<ResourceEntity> findResourceById(Long id);

    ResponseEntity<ResponseDTO> findAll();

}
