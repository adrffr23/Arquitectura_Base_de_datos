
package com.universidad.cacheperformance.controller;

import com.universidad.cacheperformance.model.Producto;
import com.universidad.cacheperformance.service.ProductoService;
import com.universidad.cacheperformance.service.EstadisticasService;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final EstadisticasService estadisticasService;
    private final CacheManager cacheManager;

    public ProductoController(
            ProductoService productoService,
            EstadisticasService estadisticasService,
            CacheManager cacheManager) {

        this.productoService = productoService;
        this.estadisticasService = estadisticasService;
        this.cacheManager = cacheManager;
    }

    // CONSULTA SIN CACHE
    @GetMapping("/sin-cache")
    public Map<String, Object> sinCache() {

        long inicio = System.nanoTime();

        List<Producto> productos =
                productoService.obtenerSinCache();

        long fin = System.nanoTime();

        double tiempoMs = (fin - inicio) / 1_000_000.0;

        estadisticasService.registrar(false, tiempoMs);


        Map<String, Object> resultado = respuesta(
                "SIN CACHE",
                productos.size(),
                tiempoMs
        );

        resultado.putAll(
                estadisticasService.obtenerPorTipo(false)
        );

        return resultado;

    }

    // CONSULTA CON CACHE
    @GetMapping("/con-cache")
    public Map<String, Object> conCache() {

        long inicio = System.nanoTime();

        List<Producto> productos =
                productoService.obtenerConCache();

        long fin = System.nanoTime();

        double tiempoMs = (fin - inicio) / 1_000_000.0;

        estadisticasService.registrar(true, tiempoMs);


        Map<String, Object> resultado = respuesta(
                "CON CACHE",
                productos.size(),
                tiempoMs
        );

        resultado.putAll(
                estadisticasService.obtenerPorTipo(true)
        );

        return resultado;

    }

    // CONSULTAR TODAS LAS ESTADISTICAS
    @GetMapping("/estadisticas")
    public Map<String, Object> estadisticas() {
        return estadisticasService.obtenerEstadisticas();
    }

    // REINICIAR ESTADISTICAS
    @PostMapping("/reiniciar-estadisticas")
    public Map<String, String> reiniciarEstadisticas() {

        estadisticasService.reiniciar();

        return Map.of(
                "mensaje",
                "Estadisticas reiniciadas correctamente"
        );
    }

    // LIMPIAR CACHE
    @PostMapping("/limpiar-cache")
    public Map<String, String> limpiarCache() {

        Cache cache = cacheManager.getCache("productos");

        if (cache != null) {
            cache.clear();
        }

        return Map.of(
                "mensaje",
                "Cache limpiada correctamente"
        );
    }

    // RESPUESTA DE CADA CONSULTA
    private Map<String, Object> respuesta(
            String tipo,
            int totalProductos,
            double tiempoMs) {

        Map<String, Object> resultado =
                new LinkedHashMap<>();

        resultado.put("tipo", tipo);
        resultado.put("totalProductos", totalProductos);
        resultado.put("tiempoMs", tiempoMs);

        return resultado;
    }
}
