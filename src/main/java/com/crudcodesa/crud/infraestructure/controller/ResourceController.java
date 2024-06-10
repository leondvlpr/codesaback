package com.crudcodesa.crud.infraestructure.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crudcodesa.crud.application.dto.ResourceDTO;
import com.crudcodesa.crud.application.dto.ResponseDTO;
import com.crudcodesa.crud.application.service.ResourceServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/resources")
@AllArgsConstructor
public class ResourceController {

    private final ResourceServiceImpl resourceServiceImpl;

    @GetMapping("")
    public ResponseDTO findAll() {
        return resourceServiceImpl.findAll();
    }

    @PostMapping("/create")
    public ResponseDTO saveResource(@RequestBody ResourceDTO resource) {

        try {
            resourceServiceImpl.createResource(resource);
            return new ResponseDTO("Insumo creado con éxito", 200, null);
        } catch (Exception e) {
            return new ResponseDTO("Hubo un error en la creación del insumo", 500, resourceServiceImpl.createResource(resource));
        }
    }

}
