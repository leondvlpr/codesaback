package com.crudcodesa.crud.application.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.crudcodesa.crud.application.dto.InvoiceDTO;
import com.crudcodesa.crud.application.dto.ResourceDTO;
import com.crudcodesa.crud.application.dto.ResponseDTO;
import com.crudcodesa.crud.domain.entity.InvoiceEntity;
import com.crudcodesa.crud.domain.entity.ResourceEntity;
import com.crudcodesa.crud.domain.repository.InvoiceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    private final ResourceServiceImpl resourceServiceImpl;

    private static final Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    @Override
    public ResponseEntity<ResponseDTO> createInvoice(InvoiceDTO invoice) {
        try {
            if (invoice == null || invoice.getNameCustomer() == null || invoice.getResources() == null) {
                return new ResponseEntity<ResponseDTO>(new ResponseDTO("Por favor, ingrese todos los datos de la factura", 400, null),
                        HttpStatus.BAD_REQUEST);
            }

            InvoiceEntity invoiceEntity = new InvoiceEntity();

            List<ResourceEntity> invoiceResources = this.convertToResourceEntities(invoice.getResources());

            if (invoiceResources.isEmpty()) {
                return new ResponseEntity<ResponseDTO>(new ResponseDTO("Recursos no encontrados", 404, null),
                        HttpStatus.NOT_FOUND);
            }

            Long totalPrice = this.calculateTotalInvoicePrice(invoiceResources);
            invoiceEntity.setDate(this.getCurrentDate());
            invoiceEntity.setNameCustomer(invoice.getNameCustomer());
            invoiceEntity.setResources(invoiceResources);
            invoiceEntity.setTotalPrice(totalPrice);

            invoiceRepository.save(invoiceEntity);

            return new ResponseEntity<ResponseDTO>(new ResponseDTO("Factura creada con éxito", 200, null), HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            logger.error("Error de validación al crear la factura", e.getMessage());
            return new ResponseEntity<ResponseDTO>(new ResponseDTO(e.getMessage(), 400, null),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error al crear la factura", e);
            return new ResponseEntity<ResponseDTO>(new ResponseDTO("Hubo un error interno al crear la factura", 500, null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> deleteInvoice(Long id) {
        try {

            Optional<InvoiceEntity> invoice = invoiceRepository.findById(id); 

            if (invoice.isPresent()) {
                invoiceRepository.deleteById(id);
                return new ResponseEntity<ResponseDTO>(new ResponseDTO("Factura eliminada con éxito", 200, null), HttpStatus.OK);
            } else {
                return new ResponseEntity<ResponseDTO>(new ResponseDTO("Factura no encontrada", 404, null),
                        HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error al crear la factura", e);
            return new ResponseEntity<ResponseDTO>(new ResponseDTO("Hubo un error interno al eliminar la factura", 500, null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> findAll() {

        try {
            List<InvoiceEntity> invoices = invoiceRepository.findAll();
            return new ResponseEntity<ResponseDTO>(new ResponseDTO("Facturas encontradas con éxito", 200, invoices), HttpStatus.OK);
            
        } catch (Exception e) {
            logger.error("Error al actualizar la factura", e);
            return new ResponseEntity<ResponseDTO>(new ResponseDTO("Hubo un error interno al actualizar la factura", 500, null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<ResponseDTO> updateInvoice(InvoiceDTO invoice) {
        try {
            if (invoice.getInvoiceId() == null) {
                return new ResponseEntity<ResponseDTO>(new ResponseDTO("El id de la factura no puede ser nulo", 400, null),
                        HttpStatus.BAD_REQUEST);
            }

            Optional<InvoiceEntity> optionalInvoiceEntity = invoiceRepository.findById(invoice.getInvoiceId());
            if (optionalInvoiceEntity.isPresent()) {
                InvoiceEntity invoiceEntity = optionalInvoiceEntity.get();

                List<ResourceEntity> invoiceResources = this.convertToResourceEntities(invoice.getResources());

                invoiceEntity.setDate(this.getCurrentDate());
                invoiceEntity.setNameCustomer(invoice.getNameCustomer());
                invoiceEntity.setResources(invoiceResources);

                Long totalPrice = this.calculateTotalInvoicePrice(invoiceResources);

                invoiceEntity.setTotalPrice(totalPrice);

                invoiceRepository.save(invoiceEntity);

                return new ResponseEntity<ResponseDTO>(new ResponseDTO("Factura actualizada con éxito", 200, null), HttpStatus.OK);
            } else {
                return new ResponseEntity<ResponseDTO>(new ResponseDTO("Factura no encontrada", 404, null),
                        HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error al actualizar la factura", e);
            return new ResponseEntity<ResponseDTO>(new ResponseDTO("Hubo un error interno al actualizar la factura", 500, null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<ResourceEntity> convertToResourceEntities(List<ResourceDTO> resourceDTOs) {
        return resourceDTOs.stream()
                .map(dto -> {
                    if (dto.getResourceId() == null) {
                        throw new IllegalArgumentException("No se encuentra el ID de recurso (resourceId)");
                    }

                    try {
                        ResourceEntity entity = new ResourceEntity();
    
                        entity.setId(dto.getResourceId());
                        entity.setName(this.getResourceData(dto.getResourceId()).getName());
                        entity.setPrice(dto.getPrice());
                        return entity;
                        
                    } catch (Exception e) {
                        throw new IllegalArgumentException("El recurso que intenta relacionar a la factura no existe");
                    }

                })
                .collect(Collectors.toList());
    }

    private Long calculateTotalInvoicePrice(List<ResourceEntity> resources) {
        return resources.stream()
                .mapToLong(resource -> {
                    return this.getResourceData(resource.getId()).getPrice();
                })
                .sum();
    }

    public ResourceEntity getResourceData(Long id) {
        Optional<ResourceEntity> resourceEntity = resourceServiceImpl.findResourceById(id);

        if (resourceEntity.isPresent()) {
            return resourceEntity.get();
        } else {
            return null;
        }
    }

    public Date getCurrentDate() {
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaFormateada = fechaActual.format(formatter);
        Date fecha = java.sql.Date.valueOf(fechaFormateada);
        return fecha;
    }

}
