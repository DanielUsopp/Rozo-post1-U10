package com.universidad.enviosservice.estrategia;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnvioEstandarStrategyTest {

    private final EnvioEstandarStrategy estrategia = new EnvioEstandarStrategy();

    @ParameterizedTest(name = "{0} kg a {1} km -> {2}")
    @CsvSource({
        "5.0,  100.0, 50.0",    // limite superior del primer tramo
        "20.0, 100.0, 62.0",    // limite superior del tramo medio: 50 + 15*0.8
        "25.0, 100.0, 68.0"     // tramo alto: 50 + 12 + 5*1.2
    })
    void calcular_cadaTramoDePeso_aplicaSuTarifa(double pesoKg, double distanciaKm, double esperado) {
        assertEquals(esperado, estrategia.calcular(pesoKg, distanciaKm), 0.01);
    }

    @ParameterizedTest(name = "{0} km -> {1}")
    @CsvSource({
        "500.0, 250.0",   // 500 km exactos: todavia sin recargo
        "501.0, 260.5"    // pasa de 500 km: 250.5 + 10
    })
    void calcular_distanciaAlrededorDe500Km_soloRecargaPorEncima(double distanciaKm, double esperado) {
        assertEquals(esperado, estrategia.calcular(3.0, distanciaKm), 0.01);
    }
}
