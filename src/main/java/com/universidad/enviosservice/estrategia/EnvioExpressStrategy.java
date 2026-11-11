package com.universidad.enviosservice.estrategia;

public class EnvioExpressStrategy implements EstrategiaEnvio {

    private static final TarifaPorPeso TARIFA = new TarifaPorPeso(1.2, 0, 1.5, 2.0);
    private static final double DISTANCIA_CON_RECARGO_KM = 500;
    private static final double RECARGO_LARGA_DISTANCIA = 25;
    private static final double FACTOR_PRIORIDAD = 1.3;

    @Override
    public double calcular(double pesoKg, double distanciaKm) {
        double costo = TARIFA.costoBase(pesoKg, distanciaKm);
        if (distanciaKm > DISTANCIA_CON_RECARGO_KM) {
            costo += RECARGO_LARGA_DISTANCIA;
        }
        return costo * FACTOR_PRIORIDAD;
    }
}
