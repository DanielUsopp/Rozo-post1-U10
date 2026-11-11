package com.universidad.enviosservice.estrategia;

public class EnvioInternacionalStrategy implements EstrategiaEnvio {

    private static final TarifaPorPeso TARIFA = new TarifaPorPeso(1.8, 50, 3.0, 4.5);
    private static final double DISTANCIA_CON_RECARGO_KM = 5000;
    private static final double RECARGO_LARGA_DISTANCIA = 100;

    @Override
    public double calcular(double pesoKg, double distanciaKm) {
        double costo = TARIFA.costoBase(pesoKg, distanciaKm);
        if (distanciaKm > DISTANCIA_CON_RECARGO_KM) {
            costo += RECARGO_LARGA_DISTANCIA;
        }
        return costo;
    }
}
