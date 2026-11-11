package com.universidad.enviosservice.estrategia;

public class EnvioEstandarStrategy implements EstrategiaEnvio {

    private static final TarifaPorPeso TARIFA = new TarifaPorPeso(0.5, 0, 0.8, 1.2);
    private static final double DISTANCIA_CON_RECARGO_KM = 500;
    private static final double RECARGO_LARGA_DISTANCIA = 10;

    @Override
    public double calcular(double pesoKg, double distanciaKm) {
        double costo = TARIFA.costoBase(pesoKg, distanciaKm);
        if (distanciaKm > DISTANCIA_CON_RECARGO_KM) {
            costo += RECARGO_LARGA_DISTANCIA;
        }
        return costo;
    }
}
