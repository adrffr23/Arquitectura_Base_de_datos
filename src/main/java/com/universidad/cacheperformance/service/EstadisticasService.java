
package com.universidad.cacheperformance.service;

import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class EstadisticasService {

    private long ejecucionesSinCache = 0;
    private long ejecucionesConCache = 0;

    private double totalSinCache = 0;
    private double totalConCache = 0;

    private double ultimoSinCache = 0;
    private double ultimoConCache = 0;

    public synchronized Map<String, Object> obtenerPorTipo(
            boolean conCache) {

        Map<String, Object> datos = new LinkedHashMap<>();

        if (conCache) {
            datos.put("ejecuciones", ejecucionesConCache);
            datos.put("promedioMs",
                    ejecucionesConCache == 0
                            ? null
                            : totalConCache / ejecucionesConCache);
        } else {
            datos.put("ejecuciones", ejecucionesSinCache);
            datos.put("promedioMs",
                    ejecucionesSinCache == 0
                            ? null
                            : totalSinCache / ejecucionesSinCache);
        }

        return datos;
    }
    // Registrar una ejecucion
    public synchronized void registrar(
            boolean conCache, double tiempoMs) {

        if (conCache) {
            ejecucionesConCache++;
            totalConCache += tiempoMs;
            ultimoConCache = tiempoMs;
        } else {
            ejecucionesSinCache++;
            totalSinCache += tiempoMs;
            ultimoSinCache = tiempoMs;
        }
    }

    // Obtener estadisticas actualizadas
    public synchronized Map<String, Object> obtenerEstadisticas() {

        Map<String, Object> resultado =
                new LinkedHashMap<>();

        resultado.put("sinCache", crearDatos(
                ejecucionesSinCache,
                totalSinCache,
                ultimoSinCache
        ));

        resultado.put("conCache", crearDatos(
                ejecucionesConCache,
                totalConCache,
                ultimoConCache
        ));

        if (ejecucionesSinCache > 0 &&
                ejecucionesConCache > 0) {

            double promedioSin =
                    totalSinCache / ejecucionesSinCache;

            double promedioCon =
                    totalConCache / ejecucionesConCache;

            double mejora = promedioSin > 0
                    ? (1 - promedioCon / promedioSin) * 100
                    : 0;

            resultado.put("mejoraPorcentaje", mejora);
        } else {
            resultado.put("mejoraPorcentaje", null);
        }

        return resultado;
    }

    private Map<String, Object> crearDatos(
            long ejecuciones,
            double total,
            double ultimo) {

        Map<String, Object> datos =
                new LinkedHashMap<>();

        datos.put("ejecuciones", ejecuciones);

        datos.put("ultimoTiempoMs",
                ejecuciones == 0 ? null : ultimo);

        datos.put("promedioMs",
                ejecuciones == 0
                        ? null
                        : total / ejecuciones);

        return datos;
    }

    // Reiniciar las estadisticas
    public synchronized void reiniciar() {

        ejecucionesSinCache = 0;
        ejecucionesConCache = 0;

        totalSinCache = 0;
        totalConCache = 0;

        ultimoSinCache = 0;
        ultimoConCache = 0;
    }
}
