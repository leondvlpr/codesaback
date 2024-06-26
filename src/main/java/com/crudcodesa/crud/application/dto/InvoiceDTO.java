package com.crudcodesa.crud.application.dto;

import java.util.List;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {

    private Long invoiceId;

    private Date date;

    private String nameCustomer;

    private List<ResourceDTO> resources;
    
}
