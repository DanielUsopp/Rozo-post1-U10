# Envios Service — Análisis de Métricas de Calidad

## Descripción
Repositorio del post-contenido de la Unidad 10 de Patrones de Diseño de Software. Proyecto Maven plano (`envios-service`, sin Spring Boot) cuya clase `ServicioCalculoEnvio` calcula el costo de un envío según el método de transporte, el peso, la distancia, la fragilidad y el tipo de cliente. En la Parte 1 se analiza el código tal como se entregó, con SonarQube y JaCoCo, para ubicar con datos dónde está el problema.

## Entorno de análisis
### Decisión de diseño 1 — SonarQube local con Docker
Se eligió SonarQube Community Build en Docker (`sonarqube:community`, versión 26.11 al momento del análisis) en lugar de SonarCloud, por dos criterios de la tabla del enunciado:

- **Control total de la configuración.** La Parte 2 exige duplicar el perfil de calidad de Java, activar una regla con un umbral propio y crear un Quality Gate con condiciones específicas. En la instancia local todo eso se hace con la cuenta de administrador y sin depender de lo que permita el plan gratuito de un servicio externo.
- **Sin depender de conectividad ni de la visibilidad del proyecto.** El análisis se ejecuta contra `localhost:9000` y los resultados no quedan publicados; en SonarCloud un proyecto gratuito es público.

El costo aceptado es el que indica la misma tabla: el contenedor consume memoria de la máquina y tiene que estar encendido durante todo el laboratorio, incluido el segundo análisis de la Parte 2.

### Decisión de diseño 2 — Exclusiones del análisis
No se excluyó ninguna clase (`sonar.exclusions` queda comentado en `sonar-project.properties`). En este proyecto no existe clase de arranque, ni código generado, ni DTOs: la única clase de producción es `ServicioCalculoEnvio` y es precisamente la que se quiere medir. Excluir algo solo tendría sentido si una clase sin lógica bajara el porcentaje de cobertura sin decir nada sobre la calidad del código de negocio, y ese caso no existe aquí. Las pruebas no necesitan exclusión porque `sonar.tests` ya las separa del código analizado.

Un detalle de configuración: `mvn sonar:sonar` no lee `sonar-project.properties` (ese archivo lo usa el SonarScanner de línea de comandos). Por eso las mismas propiedades `sonar.*` están también en el `pom.xml`, que es de donde las toma el scanner de Maven.

## Estado inicial del análisis
Valores reales del primer análisis (commit `test: agregar suite inicial de ServicioCalculoEnvioTest y configurar SonarQube`), con el perfil por defecto de Java:

| Métrica | Valor real observado |
|---------|----------------------|
| Complejidad ciclomática de calcularCosto() | 20 |
| Complejidad cognitiva de calcularCosto() | 32 |
| Cobertura de líneas de ServicioCalculoEnvio | 23,8 % (10 de 42 líneas) |
| Cobertura de ramas de ServicioCalculoEnvio | 18,4 % (7 de 38 condiciones) |
| Code Smells reportados | 0 |
| Duplicación | 0,0 % |
| Pruebas | 2 |

## Interpretación
**Qué método concentra el problema.** `calcularCosto()` es el único método de la clase, así que la complejidad del archivo (20) es la del método. El valor coincide con el conteo manual de la guía: 1 camino base + 4 por la cadena de métodos de envío + 12 por los tramos de peso y los recargos o límites de distancia de cada rama + 1 por la fragilidad + 2 por el tipo de cliente.

**Qué ramas quedaron sin cubrir.** Las dos pruebas de partida usan el mismo envío (`ESTANDAR`, 3 kg, 100 km, no frágil) y solo cambian el tipo de cliente. En el reporte de JaCoCo las únicas ramas en verde son: la condición `ESTANDAR` en verdadero, `pesoKg <= 5` en verdadero, `distanciaKm > 500` en falso, `esFragil` en falso, `PREMIUM` en verdadero y en falso, y `CORPORATIVO` en falso. Todo lo demás está en rojo: las ramas completas de `EXPRESS`, `OVERNIGHT` e `INTERNACIONAL`, el error de método no soportado, los tramos de 5 a 20 kg y de más de 20 kg, el recargo por distancia, el ajuste por fragilidad y el descuento `CORPORATIVO`.

**Por qué coinciden la complejidad y la falta de cobertura.** Coinciden en `calcularCosto()` porque son la misma causa vista desde dos lados: 20 caminos independientes exigen al menos 20 casos de prueba para cubrir todas las ramas, y la suite tiene 2. Cada condicional que se agrega al método sube la complejidad y, si no se escribe la prueba correspondiente, baja la cobertura de ramas.

**Hallazgo: el perfil por defecto no reporta nada.** El análisis terminó con 0 Code Smells. En esta versión de SonarQube el perfil por defecto de Java es "Sonar way core" y no trae activas ni la regla de complejidad cognitiva (S3776) ni la de complejidad ciclomática por método (S1541). Un método con complejidad 20 y cognitiva 32 pasa sin ninguna alerta, y el Quality Gate por defecto queda en verde. Las métricas existen en la pestaña Measures, pero no se convierten en hallazgos. Esto confirma la razón de configurar un perfil propio en la Parte 2.

### Decisión de diseño 3 — Métrica usada como señal principal: complejidad cognitiva
SonarQube expone tres señales que aquí apuntan al mismo método: complejidad ciclomática (20), complejidad cognitiva (32) y cobertura de ramas (18,4 %). Se tomó la **complejidad cognitiva** como señal principal para decidir qué intervenir, y las otras dos con un papel definido:

- **Cognitiva (señal principal).** Mide lo que cuesta leer y modificar el método: penaliza el anidamiento, y aquí cada tramo de peso está anidado dentro de una rama de método de envío. Su valor (32) es más del doble del umbral de referencia que usa SonarQube para esa regla (15). El problema que se quiere resolver es de mantenimiento, no de cantidad de caminos.
- **Ciclomática (dimensionar las pruebas).** Dice cuántos casos de prueba hacen falta como mínimo para cubrir todas las ramas, y es la que se usa como meta de reducción en la Parte 2 (al menos 50 %).
- **Cobertura de ramas (medir el riesgo).** Confirma que el método más complejo es además el menos probado, que es la combinación más peligrosa para modificarlo.

## Capturas del análisis inicial
![Dashboard SonarQube](docs/sonar-dashboard-inicial.png)
![Reporte JaCoCo](docs/jacoco-inicial.png)

## Herramientas utilizadas
- Java 21, Apache Maven, JUnit 5
- SonarQube Community Build (Docker), JaCoCo
- Git, GitHub
