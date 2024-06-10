package com.crudcodesa.crud.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crudcodesa.crud.domain.entity.ResourceEntity;

public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {
    
}
