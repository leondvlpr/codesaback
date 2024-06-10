package com.crudcodesa.crud.infraestructure.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crudcodesa.crud.application.dto.InvoiceDTO;
import com.crudcodesa.crud.application.dto.ResponseDTO;
import com.crudcodesa.crud.application.service.InvoiceServiceImpl;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/invoice")
@AllArgsConstructor
public class InvoiceController {

    private InvoiceServiceImpl invoiceServiceImpl;

    @GetMapping("")
    public ResponseDTO findAll() {

        return invoiceServiceImpl.findAll();

    }

    @PostMapping("/create")
    public ResponseDTO createInvoice(@RequestBody InvoiceDTO invoiceDTO) {

        return invoiceServiceImpl.createInvoice(invoiceDTO);

    }

    @PostMapping("/update")
    public ResponseDTO updateInvoice(@RequestBody InvoiceDTO invoiceDTO) {

        return invoiceServiceImpl.updateInvoice(invoiceDTO);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteInvoice(@PathVariable Long id) {

        return invoiceServiceImpl.deleteInvoice(id);

    }
    

}
