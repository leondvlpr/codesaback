package com.crudcodesa.crud.application.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public ResponseDTO createInvoice(InvoiceDTO invoice) {

        try {

            InvoiceEntity invoiceEntity = new InvoiceEntity();

            List<ResourceEntity> invoiceResources = this.convertToResourceEntities(invoice.getResources());
            Long totalPrice = this.calculateTotalInvoicePrice(invoiceResources);

            invoiceEntity.setDate(this.getCurrentDate());
            invoiceEntity.setNameCustomer(invoice.getNameCustomer());
            invoiceEntity.setResources(invoiceResources);
            invoiceEntity.setTotalPrice(totalPrice);

            invoiceRepository.save(invoiceEntity);

            return new ResponseDTO("Factura creada con éxito", 200, null);

        } catch (Exception e) {
            logger.error("Error al crear la factura", e);
            return new ResponseDTO("Hubo un error al crear la factura", 500, null);
        }
    }

    @Override
    public ResponseDTO deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
        return new ResponseDTO("Factura eliminada con éxito", 200, null);
    }

    @Override
    public ResponseDTO findAll() {
        List<InvoiceEntity> invoices = invoiceRepository.findAll();
        return new ResponseDTO("Facturas encontradas con éxito", 200, invoices);
    }

    @Override
    public ResponseDTO updateInvoice(InvoiceDTO invoice) {
        try {
            if (invoice.getInvoiceId() == null) {
                return new ResponseDTO("El ID de la factura no puede ser nulo", 400, null);
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

                return new ResponseDTO("Factura actualizada con éxito", 200, null);
            } else {
                return new ResponseDTO("Factura no encontrada", 404, null);
            }
        } catch (Exception e) {
            logger.error("Error al actualizar la factura", e);
            return new ResponseDTO("Hubo un error al actualizar la factura", 500, null);
        }
    }

    private List<ResourceEntity> convertToResourceEntities(List<ResourceDTO> resourceDTOs) {
        return resourceDTOs.stream()
                .map(dto -> {
                    ResourceEntity entity = new ResourceEntity();

                    entity.setId(dto.getResourceId());
                    entity.setName(this.getResourceData(dto.getResourceId()).getName());
                    entity.setPrice(dto.getPrice());
                    return entity;
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
