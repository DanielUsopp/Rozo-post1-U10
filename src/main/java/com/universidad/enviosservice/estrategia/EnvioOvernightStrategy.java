package com.universidad.enviosservice.estrategia;

public class EnvioOvernightStrategy implements EstrategiaEnvio {

    private static final TarifaPorPeso TARIFA = new TarifaPorPeso(2.0, 20, 2.5, 3.0);
    private static final double DISTANCIA_MAXIMA_KM = 300;

    @Override
    public double calcular(double pesoKg, double distanciaKm) {
        if (distanciaKm > DISTANCIA_MAXIMA_KM) {
            throw new IllegalArgumentException("OVERNIGHT no disponible para mas de 300 km");
        }
        return TARIFA.costoBase(pesoKg, distanciaKm);
    }
}
