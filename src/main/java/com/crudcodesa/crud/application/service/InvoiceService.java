package com.crudcodesa.crud.application.service;

import com.crudcodesa.crud.application.dto.InvoiceDTO;
import com.crudcodesa.crud.application.dto.ResponseDTO;

public interface InvoiceService {

    ResponseDTO createInvoice(InvoiceDTO invoiceDTO);

    ResponseDTO updateInvoice(InvoiceDTO invoiceDTO);

    ResponseDTO deleteInvoice(Long id);

    ResponseDTO findAll();
    
}
