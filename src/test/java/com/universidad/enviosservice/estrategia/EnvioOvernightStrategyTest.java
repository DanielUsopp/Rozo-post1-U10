package com.universidad.enviosservice.estrategia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class EnvioOvernightStrategyTest {

    private final EnvioOvernightStrategy estrategia = new EnvioOvernightStrategy();

    @ParameterizedTest(name = "{0} kg a {1} km -> {2}")
    @CsvSource({
        "3.0,  100.0, 220.0",   // 200 + 20
        "8.0,  100.0, 227.5",   // 220 + 3*2.5
        "25.0, 100.0, 272.5",   // 220 + 15*2.5 + 5*3.0
        "3.0,  300.0, 620.0"    // 300 km exactos todavia se permiten
    })
    void calcular_dentroDeLos300Km_aplicaTarifaConCargoFijo(double pesoKg, double distanciaKm, double esperado) {
        assertEquals(esperado, estrategia.calcular(pesoKg, distanciaKm), 0.01);
    }

    @Test
    void calcular_masDe300Km_rechazaElEnvio() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
            () -> estrategia.calcular(3.0, 301.0));
        assertEquals("OVERNIGHT no disponible para mas de 300 km", error.getMessage());
    }
}
