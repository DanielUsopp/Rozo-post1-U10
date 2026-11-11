package com.universidad.enviosservice.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServicioCalculoEnvioTest {

    private final ServicioCalculoEnvio servicio = new ServicioCalculoEnvio();

    @Test
    void calculaEnvioEstandarPesoBajo() {
        double costo = servicio.calcularCosto("ESTANDAR", 3.0, 100.0, "REGULAR", false);
        assertEquals(50.0, costo, 0.01);
    }

    @Test
    void aplicaDescuentoClientePremium() {
        double costo = servicio.calcularCosto("ESTANDAR", 3.0, 100.0, "PREMIUM", false);
        assertEquals(45.0, costo, 0.01);
    }

    @Test
    void calcularCosto_paqueteFragil_sumaQuincePorCiento() {
        double costo = servicio.calcularCosto("ESTANDAR", 3.0, 100.0, "REGULAR", true);
        assertEquals(57.5, costo, 0.01);
    }

    @Test
    void calcularCosto_clienteCorporativo_descuentaQuincePorCiento() {
        double costo = servicio.calcularCosto("ESTANDAR", 3.0, 100.0, "CORPORATIVO", false);
        assertEquals(42.5, costo, 0.01);
    }

    @Test
    void calcularCosto_fragilYPremium_aplicaRecargoAntesDelDescuento() {
        double costo = servicio.calcularCosto("ESTANDAR", 3.0, 100.0, "PREMIUM", true);
        assertEquals(51.75, costo, 0.01);   // 50 * 1.15 * 0.9
    }

    @Test
    void calcularCosto_delegaEnLaEstrategiaDelMetodoElegido() {
        double costo = servicio.calcularCosto("OVERNIGHT", 8.0, 100.0, "REGULAR", false);
        assertEquals(227.5, costo, 0.01);
    }

    @Test
    void calcularCosto_metodoNoSoportado_lanzaExcepcion() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
            () -> servicio.calcularCosto("MARITIMO", 3.0, 100.0, "REGULAR", false));
        assertEquals("Metodo de envio no soportado: MARITIMO", error.getMessage());
    }
}
