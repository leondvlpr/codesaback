package com.crudcodesa.crud.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crudcodesa.crud.domain.entity.InvoiceEntity;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
    
}
