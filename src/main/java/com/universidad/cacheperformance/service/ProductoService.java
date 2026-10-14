
package com.universidad.cacheperformance.service;

import com.universidad.cacheperformance.model.Producto;
import com.universidad.cacheperformance.repository.ProductoRepository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    // Consulta directamente la base de datos H2
    public List<Producto> obtenerSinCache() {
        return repository.findAll();
    }

    // La primera consulta obtiene datos de H2.
    // Las siguientes reutilizan los datos de memoria.
    @Cacheable(value = "productos")
    public List<Producto> obtenerConCache() {
        return repository.findAll();
    }
}
