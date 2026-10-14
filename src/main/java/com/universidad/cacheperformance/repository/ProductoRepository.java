
package com.universidad.cacheperformance.repository;

import com.universidad.cacheperformance.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository
        extends JpaRepository<Producto, Long> {
}
