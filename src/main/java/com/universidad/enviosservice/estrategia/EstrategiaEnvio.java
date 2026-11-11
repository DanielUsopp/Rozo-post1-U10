package com.universidad.enviosservice.estrategia;

/**
 * Fórmula de costo propia de un método de envío. Cada implementación recibe
 * solo lo que necesita para su cálculo; los ajustes comunes a todos los
 * métodos (fragilidad y tipo de cliente) se aplican en ServicioCalculoEnvio.
 */
public interface EstrategiaEnvio {
    double calcular(double pesoKg, double distanciaKm);
}
