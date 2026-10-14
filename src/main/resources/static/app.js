
const API = "/api/productos";

const elemento = (id) => document.getElementById(id);

const formatoMs = (valor) => {
    if (valor === null || valor === undefined) {
        return "--";
    }
    return Number(valor).toFixed(4) + " ms";
};

function mostrarMensaje(texto) {
    elemento("mensaje").textContent = texto;
}

async function solicitar(ruta, metodo = "GET") {
    const respuesta = await fetch(API + ruta, {
        method: metodo,
        cache: "no-store"
    });

    if (!respuesta.ok) {
        throw new Error("Error HTTP " + respuesta.status);
    }

    return respuesta.json();
}

function pintarGrupo(prefijo, datos) {
    elemento("ultimo" + prefijo).textContent =
        formatoMs(datos.ultimoTiempoMs);

    elemento("promedio" + prefijo).textContent =
        formatoMs(datos.promedioMs);

    elemento("ejecuciones" + prefijo).textContent =
        datos.ejecuciones;
}

function pintarGrafica(sin, conCache) {
    const a = sin.promedioMs;
    const b = conCache.promedioMs;

    elemento("barraValorSin").textContent = formatoMs(a);
    elemento("barraValorCon").textContent = formatoMs(b);

    const maximo = Math.max(a || 0, b || 0);

    elemento("barraSin").style.width =
        maximo > 0 && a !== null
            ? (a / maximo * 100) + "%"
            : "0%";

    elemento("barraCon").style.width =
        maximo > 0 && b !== null
            ? (b / maximo * 100) + "%"
            : "0%";
}

function pintarMejora(datos) {
    const mejora = datos.mejoraPorcentaje;

    if (mejora === null || mejora === undefined) {
        elemento("mejora").textContent = "--";
        elemento("descripcionMejora").textContent =
            "Ejecuta ambos tipos de consulta para comparar.";
        return;
    }

    elemento("mejora").textContent =
        mejora.toFixed(2) + "%";

    elemento("descripcionMejora").textContent =
        mejora >= 0
            ? "Menor tiempo promedio con caché."
            : "El promedio con caché es mayor por ahora.";
}

async function actualizarEstadisticas() {
    const datos = await solicitar("/estadisticas");

    pintarGrupo("Sin", datos.sinCache);
    pintarGrupo("Con", datos.conCache);

    pintarGrafica(datos.sinCache, datos.conCache);
    pintarMejora(datos);

    elemento("estadoServidor").textContent = "API conectada";
    elemento("estadoServidor").classList.add("conectado");
}

async function ejecutarConsulta(tipo) {
    const boton = elemento(tipo === "sin-cache" ? "btnSin" : "btnCon");

    boton.disabled = true;

    try {
        const datos = await solicitar("/" + tipo);

        elemento("totalProductos").textContent =
            datos.totalProductos.toLocaleString("es-ES");

        await actualizarEstadisticas();

        mostrarMensaje(
            datos.tipo + " | Tiempo: " +
            formatoMs(datos.tiempoMs) +
            " | Ejecución " + datos.ejecuciones
        );
    } catch (error) {
        mostrarMensaje("Error: " + error.message);
    } finally {
        boton.disabled = false;
    }
}

async function realizarAccion(ruta, mensaje) {
    try {
        await solicitar(ruta, "POST");
        await actualizarEstadisticas();
        mostrarMensaje(mensaje);
    } catch (error) {
        mostrarMensaje("Error: " + error.message);
    }
}

elemento("btnSin").addEventListener("click", () => {
    ejecutarConsulta("sin-cache");
});

elemento("btnCon").addEventListener("click", () => {
    ejecutarConsulta("con-cache");
});

elemento("btnActualizar").addEventListener("click", async () => {
    try {
        await actualizarEstadisticas();
        mostrarMensaje("Estadísticas actualizadas.");
    } catch (error) {
        mostrarMensaje("Error: " + error.message);
    }
});

elemento("btnLimpiar").addEventListener("click", () => {
    realizarAccion(
        "/limpiar-cache",
        "Caché limpiada. La próxima consulta volverá a H2."
    );
});

elemento("btnReiniciar").addEventListener("click", () => {
    realizarAccion(
        "/reiniciar-estadisticas",
        "Estadísticas reiniciadas correctamente."
    );
});

// Obtener las estadísticas al entrar al dashboard
actualizarEstadisticas().catch((error) => {
    elemento("estadoServidor").textContent = "Sin conexión";
    mostrarMensaje("No se pudo conectar: " + error.message);
});
