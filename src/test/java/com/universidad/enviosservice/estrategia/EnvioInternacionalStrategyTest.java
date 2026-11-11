package com.universidad.enviosservice.estrategia;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnvioInternacionalStrategyTest {

    private final EnvioInternacionalStrategy estrategia = new EnvioInternacionalStrategy();

    @ParameterizedTest(name = "{0} kg a {1} km -> {2}")
    @CsvSource({
        "3.0,  1000.0, 1850.0",    // 1800 + 50
        "10.0, 1000.0, 1865.0",    // 1850 + 5*3.0
        "25.0, 1000.0, 1917.5",    // 1850 + 15*3.0 + 5*4.5
        "3.0,  5000.0, 9050.0",    // 5000 km exactos: sin recargo
        "3.0,  6000.0, 10950.0"    // 10800 + 50 + 100
    })
    void calcular_tramosYRecargoPorMasDe5000Km(double pesoKg, double distanciaKm, double esperado) {
        assertEquals(esperado, estrategia.calcular(pesoKg, distanciaKm), 0.01);
    }
}
