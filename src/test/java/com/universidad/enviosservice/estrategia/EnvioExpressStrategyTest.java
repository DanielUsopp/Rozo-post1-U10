package com.universidad.enviosservice.estrategia;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnvioExpressStrategyTest {

    private final EnvioExpressStrategy estrategia = new EnvioExpressStrategy();

    @ParameterizedTest(name = "{0} kg a {1} km -> {2}")
    @CsvSource({
        "3.0,  100.0, 156.0",    // 120 * 1.3
        "10.0, 100.0, 165.75",   // (120 + 5*1.5) * 1.3
        "30.0, 100.0, 211.25",   // (120 + 22.5 + 10*2.0) * 1.3
        "3.0,  600.0, 968.5"     // (720 + 25) * 1.3: el recargo tambien se multiplica
    })
    void calcular_tramosYRecargo_aplicanElFactorDePrioridadAlFinal(double pesoKg, double distanciaKm,
                                                                    double esperado) {
        assertEquals(esperado, estrategia.calcular(pesoKg, distanciaKm), 0.01);
    }
}
