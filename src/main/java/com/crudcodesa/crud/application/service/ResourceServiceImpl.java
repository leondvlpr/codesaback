package com.crudcodesa.crud.application.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private static final Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    @Override
    public ResponseEntity<ResponseDTO> createResource(ResourceDTO resource) {

        try {

            if (resource == null || resource.getName() == null || resource.getPrice() == null) {
                return new ResponseEntity<ResponseDTO>(
                        new ResponseDTO("Por favor, ingrese todos los datos del recurso", 400, null),
                        HttpStatus.BAD_REQUEST);
            }

            ResourceEntity resourceEntity = new ResourceEntity();
            resourceEntity.setName(resource.getName());
            resourceEntity.setPrice(resource.getPrice());

            resourceRepository.save(resourceEntity);

            return new ResponseEntity<ResponseDTO>(new ResponseDTO("Recurso creado con éxito", 200, null),
                    HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            logger.error("Error de validación al crear el recurso", e.getMessage());
            return new ResponseEntity<ResponseDTO>(new ResponseDTO(e.getMessage(), 400, null),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error al crear el recurso", e);
            return new ResponseEntity<ResponseDTO>(
                    new ResponseDTO("Hubo un error interno al crear el recurso", 500, null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public Optional<ResourceEntity> findResourceById(Long id) {
        return resourceRepository.findById(id);
    }

    @Override
    public ResponseEntity<ResponseDTO> findAll() {

        try {
            List<ResourceEntity> resources = resourceRepository.findAll();
            return new ResponseEntity<ResponseDTO>(
                    new ResponseDTO("Listado de insumos encontrado con éxito", 200, resources), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ResponseDTO>(new ResponseDTO("", 500, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
