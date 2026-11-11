package com.universidad.enviosservice.estrategia;

/**
 * Tabla de tarifas con la misma estructura en los cuatro métodos de envío:
 * un valor por kilómetro, un cargo fijo y dos tarifas por kilo según el
 * tramo de peso (de 5 a 20 kg y más de 20 kg). Cada estrategia define sus
 * valores; el cálculo por tramos existe una sola vez.
 */
public record TarifaPorPeso(double valorPorKm, double cargoFijo,
                            double valorKgTramoMedio, double valorKgTramoAlto) {

    private static final double LIMITE_TRAMO_BAJO = 5;
    private static final double LIMITE_TRAMO_MEDIO = 20;

    public double costoBase(double pesoKg, double distanciaKm) {
        double costo = distanciaKm * valorPorKm + cargoFijo;
        if (pesoKg <= LIMITE_TRAMO_BAJO) {
            return costo;
        }
        if (pesoKg <= LIMITE_TRAMO_MEDIO) {
            return costo + (pesoKg - LIMITE_TRAMO_BAJO) * valorKgTramoMedio;
        }
        return costo + (LIMITE_TRAMO_MEDIO - LIMITE_TRAMO_BAJO) * valorKgTramoMedio
                + (pesoKg - LIMITE_TRAMO_MEDIO) * valorKgTramoAlto;
    }
}
