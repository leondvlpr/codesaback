package com.crudcodesa.crud.application.service;

import org.springframework.http.ResponseEntity;

import com.crudcodesa.crud.application.dto.InvoiceDTO;
import com.crudcodesa.crud.application.dto.ResponseDTO;

public interface InvoiceService {

    ResponseEntity<ResponseDTO> createInvoice(InvoiceDTO invoiceDTO);

    ResponseEntity<ResponseDTO> updateInvoice(InvoiceDTO invoiceDTO);

    ResponseEntity<ResponseDTO> deleteInvoice(Long id);

    ResponseEntity<ResponseDTO> findAll();
    
}
