# Post-contenido — Unidad 8: Patrones Arquitectónicos II

## Descripción

Repositorio del post-contenido de la Unidad 8 de Patrones de Diseño de Software — Sexto Semestre. Sistema de seguimiento de hallazgos de auditoría interna implementado con Clean Architecture (Parte 1) y extendido con dashboard agregado y bitácora de trazabilidad (Parte 2), sobre el mismo proyecto Spring Boot.

## Parte 1 — Clean Architecture (Hallazgos de Auditoría)

El proyecto organiza los cuatro círculos concéntricos: Entities (`domain/`, con el Aggregate Root `HallazgoAuditoria` y su máquina de estados `EstadoHallazgo`), Use Cases (`usecase/`, con los puertos y sus implementaciones), Interface Adapters (`adapter/`, con `HallazgoController` y `HallazgoRepositoryAdapter`) y Frameworks & Drivers (Spring Boot + JPA). La dependencia del código siempre apunta hacia adentro, hacia `domain/`.

## Parte 2 — Análisis costo-beneficio de CQRS/Event Sourcing

**1. Escala del sistema**

El sistema actual está orientado al seguimiento de hallazgos de auditoría interna y no presenta, por el momento, una necesidad de manejar grandes volúmenes de información ni una carga elevada de operaciones simultáneas. Su contexto de uso permite trabajar con una arquitectura sencilla, en la que las operaciones de escritura y consulta pueden mantenerse sobre la misma aplicación y base de datos. Implementar CQRS/Event Sourcing completos implicaría incorporar componentes adicionales, como modelos de lectura independientes, almacenamiento de eventos y mecanismos de sincronización, cuyo costo de mantenimiento no estaría justificado por la escala actual. Si el número de usuarios, hallazgos y consultas aumentara considerablemente, sería conveniente volver a evaluar esta decisión.

**2. Complejidad de las consultas**

Las consultas requeridas por el dashboard se concentran principalmente en presentar indicadores y agrupaciones de los hallazgos, por ejemplo, cantidades por estado o por severidad. Aunque estas consultas requieren operaciones de agregación, no representan una complejidad que obligue a separar completamente los modelos de escritura y lectura. Por ello, se optó por extender el repositorio existente con consultas específicas para obtener los datos del dashboard, evitando duplicar modelos y mantener sincronizados dos sistemas de persistencia. CQRS podría resultar conveniente si en el futuro aparecen consultas mucho más complejas, múltiples vistas especializadas o necesidades de optimización independientes de las operaciones de escritura.

**3. Consistencia de los datos**

El dominio contiene reglas que requieren consistencia inmediata, especialmente las transiciones permitidas entre estados y la relación entre un hallazgo y su plan de remediación. La solución implementada conserva estas reglas dentro del agregado `HallazgoAuditoria`, de modo que las operaciones que afectan sus invariantes se realizan bajo una misma transacción. Introducir CQRS con modelos de lectura independientes podría generar consistencia eventual entre las escrituras y las proyecciones del dashboard, lo que añadiría una complejidad innecesaria para las necesidades actuales. Por esta razón, se mantiene una persistencia compartida y se prioriza la consistencia transaccional del dominio.

**4. Naturaleza de la trazabilidad exigida**

El sistema necesita conservar información sobre los cambios de estado de los hallazgos para conocer su evolución y consultar su historial. Sin embargo, este requerimiento no implica necesariamente almacenar y reproducir todos los eventos que han ocurrido en el dominio para reconstruir el estado completo del sistema. La bitácora implementada mediante `HistorialCambioEstado` permite registrar los cambios relevantes y consultar su secuencia, ofreciendo trazabilidad suficiente para el alcance actual. Event Sourcing sería una alternativa más apropiada si se necesitara reconstruir cualquier estado histórico, auditar todas las operaciones del dominio o mantener un registro completo e inmutable de los eventos con requisitos estrictos de auditoría.

**5. Señales de sobreingeniería**

Adoptar CQRS/Event Sourcing completos en esta etapa podría introducir más complejidad que beneficios. Entre las señales de sobreingeniería se encuentran mantener un Event Store cuando únicamente se necesita consultar el historial de cambios de estado, duplicar modelos de lectura y escritura sin una necesidad de rendimiento demostrada, incorporar mecanismos de publicación y procesamiento de eventos sin consumidores independientes y gestionar problemas de sincronización entre proyecciones que podrían resolverse con consultas directas. De acuerdo con los criterios de evaluación de la sección 7 de la guía, la elección arquitectónica debe responder a necesidades reales del sistema y no a la incorporación de patrones por su complejidad o popularidad. Por tanto, la extensión liviana permite cumplir los requisitos sin asumir costos de desarrollo y mantenimiento innecesarios.

**Conclusión del análisis costo-beneficio**

Para el alcance actual del proyecto se justifica mantener la arquitectura Clean Architecture e incorporar una extensión liviana del repositorio existente, con consultas agregadas para el dashboard y una bitácora de trazabilidad para registrar los cambios de estado. No se considera necesario implementar CQRS/Event Sourcing completos, porque el volumen de información, la complejidad de las consultas y los requisitos de trazabilidad todavía pueden resolverse con una solución más sencilla. Esta decisión no descarta la evolución arquitectónica, sino que busca aplicar los patrones cuando las necesidades reales del sistema justifiquen su costo.

## Decisiones de diseño

**1. Severidad como enum simple vs. EstadoHallazgo como enum con máquina de estados**

Se decidió implementar `EstadoHallazgo` como un enum con comportamiento y `Severidad` como un enum simple, debido a que ambos representan conceptos diferentes dentro del dominio. `EstadoHallazgo` no es únicamente una clasificación, sino que define el ciclo de vida de un hallazgo y las transiciones permitidas entre sus estados. Por esta razón, incorpora el método `puedeTransicionarA(...)`, que encapsula la regla de negocio y evita que se realicen cambios de estado no válidos desde otras partes de la aplicación.

Por otro lado, `Severidad` representa únicamente el nivel de importancia o gravedad de un hallazgo mediante cuatro valores. No posee reglas propias que determinen transiciones entre sus valores ni restricciones para pasar de una severidad a otra. Por ello, se consideró suficiente utilizar un enum simple, sin agregar métodos que no representen una necesidad real del dominio. Esta diferencia permite asignar responsabilidades según el comportamiento de cada concepto y evita agregar complejidad innecesaria.

**2. PlanRemediacion como Value Object embebido vs. agregado separado**

Se decidió modelar `PlanRemediacion` como un Value Object embebido dentro del agregado `HallazgoAuditoria`, en lugar de convertirlo en un agregado independiente con su propio repositorio. Esta decisión se fundamenta en que existe una relación directa entre la existencia y validez del plan de remediación y las transiciones de estado del hallazgo.

Un hallazgo no puede pasar al estado `EN_REMEDIACION` sin tener asociado un plan válido, ni puede cerrarse sin que previamente exista un plan definido. Estas condiciones constituyen invariantes del dominio que deben cumplirse siempre. Si `PlanRemediacion` se manejara como un agregado independiente, sería necesario coordinar las operaciones entre ambos agregados, lo que podría generar ventanas de inconsistencia si alguna operación falla.

De acuerdo con el criterio de **límite de consistencia transaccional**, explicado en la sección 3.3 de la guía sobre Bounded Contexts y Agregados, las reglas que deben garantizarse de manera atómica deben mantenerse dentro de un mismo agregado. Por esta razón, `HallazgoAuditoria` es responsable de proteger estas invariantes y `PlanRemediacion` forma parte de su consistencia interna. Así, la creación o modificación del plan y los cambios de estado del hallazgo pueden realizarse dentro de la misma transacción.

**3. CQRS/Event Sourcing completos vs. extensión liviana del repositorio existente**

Se decidió mantener el repositorio existente y extenderlo con consultas agregadas para el dashboard, en lugar de implementar CQRS/Event Sourcing completos. Esta decisión considera los criterios de la sección 7 de la guía, especialmente la escala del sistema, la complejidad de las consultas, las necesidades de consistencia y el nivel de trazabilidad requerido. Actualmente, las consultas del dashboard pueden resolverse mediante operaciones de agregación sobre los datos existentes, mientras que las reglas del dominio requieren mantener la consistencia transaccional del agregado `HallazgoAuditoria`.

La implementación de CQRS/Event Sourcing completos supondría mantener modelos de escritura y lectura separados, un Event Store y mecanismos adicionales de sincronización y procesamiento de eventos. Estos elementos aumentarían el esfuerzo de desarrollo y mantenimiento sin aportar beneficios proporcionales a las necesidades actuales. Por tanto, se eligió una solución intermedia que satisface los requerimientos del dashboard y la trazabilidad, pero conserva la simplicidad de la arquitectura existente.

**4. Bitácora simple (`HistorialCambioEstado`) vs. Event Store completo**

Se decidió implementar una bitácora mediante `HistorialCambioEstado` en lugar de construir un Event Store completo. La necesidad actual consiste en conservar y consultar los cambios de estado de cada hallazgo, por lo que una estructura que registre su historial permite conocer su evolución sin necesidad de transformar todos los cambios del dominio en eventos persistentes capaces de reconstruir el sistema.

Esta decisión también considera las señales de sobreingeniería descritas en la sección 7.2 de la guía. Incorporar un Event Store completo implicaría asumir responsabilidades adicionales, como administrar eventos, controlar su evolución, reconstruir estados y mantener mecanismos de reproducción, aunque el sistema no requiera actualmente esas capacidades. En consecuencia, la bitácora simple ofrece una solución proporcional al requisito de trazabilidad, sin introducir una infraestructura cuyo costo supere su beneficio.

## Cómo ejecutar

```bash
mvn spring-boot:run
```

## Herramientas utilizadas

- Java 17, Spring Boot 3.x, Spring Data JPA, H2.
- Apache Maven, Postman/curl, Git, GitHub.

## Conclusiones

El desarrollo de ambas partes permitió comprender que Clean Architecture facilita la separación de responsabilidades y mantiene las reglas del negocio independientes de los detalles tecnológicos. También se evidenció que los límites de los agregados deben establecerse de acuerdo con las invariantes que necesitan protegerse dentro de una misma transacción. La extensión del sistema con un dashboard y una bitácora demostró que es posible incorporar capacidades de consulta y trazabilidad sin adoptar necesariamente CQRS/Event Sourcing completos. Si el sistema creciera en volumen de datos, concurrencia, complejidad de consultas o exigencias de auditoría que requirieran reconstruir estados históricos, sería pertinente reconsiderar estos patrones. La principal lección es que una buena decisión arquitectónica no consiste en utilizar la mayor cantidad de patrones, sino en seleccionar aquellos que resuelven necesidades concretas sin generar complejidad innecesaria.