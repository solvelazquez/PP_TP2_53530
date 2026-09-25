**Trabajo Práctico N.º 2 de Paradigmas de
Programación**
A lo largo del trabajo se incorporan:

-   Manejo de excepciones.
-   Serialización y recuperación de objetos.
-   Interfaces.
-   Clases genéricas y wildcards.
-   Clases anidadas.
-   Hilos y ejecución concurrente.

La idea es que el programa pueda administrar eventos, actividades,
estudiantes e inscripciones.
------------------------------------------------------------------------
# Ejercicio 1 - Excepciones y persistencia

### Cupo excedido

La clase `Actividad` tiene el método:

``` java
public Inscripcion inscribir(Estudiante estudiante)
        throws CupoExcedidoException
```

Antes de realizar la inscripción se controla si la actividad alcanzó su
cupo máximo.

Si no hay lugares disponibles, se lanza:

``` java
throw new CupoExcedidoException(...);
```

La excepción se captura posteriormente desde `App`, mostrando un mensaje
claro por consola.

De esta manera, un error controlado no provoca que el programa termine
inesperadamente.

### Persistencia

También se implementó la posibilidad de guardar un `EventoUniversitario`
en un archivo mediante **serialización**.

Para guardar el evento se utiliza:

``` java
ObjectOutputStream
```

y para recuperarlo:

``` java
ObjectInputStream
```

El programa también toma en cuenta diferentes errores relacionados con la
lectura y escritura de archivos mediante bloques `try-catch`.

Finalmente, se utiliza `finally` para indicar que el proceso de
persistencia terminó, independientemente de si se produjo una excepción.
------------------------------------------------------------------------
# Ejercicio 2
Se agregó la interfaz:

``` java
Certificable
```

Esta define el comportamiento necesario para generar certificados.

Las actividades se comportan de la siguiente manera:

-   `Taller` → es certificable.
-   `Curso` → es certificable.
-   `Charla` → no es certificable.

Por este motivo, `Taller` y `Curso` implementan la interfaz:

``` java
implements Certificable
```
Mientras que `Charla` solamente hereda de `Actividad`.

En `App` se recorren las actividades y se generan certificados para las
inscripciones confirmadas de las actividades que implementan
`Certificable`.

Esto nos permite utilizar el polimorfismo y trabajar con diferentes tipos de
actividades sin tener que tratar cada clase de manera independiente.
------------------------------------------------------------------------
# Ejercicio 3 
En este ejercicio se agregan dos funcionalidades a
`EventoUniversitario`.

## Filtrar actividades

Se implementó:

``` java
public <T extends Actividad> List<T>
filtrarActividadesPorTipo(Class<T> tipo)
```

El método permite obtener solamente las actividades del tipo solicitado.

Por ejemplo:

``` java
List<Charla> charlas =
        evento.filtrarActividadesPorTipo(Charla.class);

List<Taller> talleres =
        evento.filtrarActividadesPorTipo(Taller.class);

List<Curso> cursos =
        evento.filtrarActividadesPorTipo(Curso.class);
```

Esto permite trabajar con listas correctamente tipadas.

## Calcular costo de materiales

También se implementó:

``` java
public double calcularCostoMateriales(
        List<? extends Actividad> actividades)
```

El uso de `? extends Actividad` permite recibir una lista de `Actividad`
o una lista de cualquiera de sus clases hijas.

Por ejemplo, el método puede recibir:

``` java
List<Charla>
List<Taller>
List<Curso>
```

sin necesidad de crear un método diferente para cada tipo.
------------------------------------------------------------------------
# Ejercicio 4

En el último ejercicio se incorporan tickets de acceso y ejecución
concurrente.

## Ticket de acceso

La clase:

``` java
TicketDeAcceso
```

se encuentra definida como una **clase anidada dentro de
`Inscripcion`**.

Esto tiene sentido porque un ticket pertenece específicamente a una
inscripción.

El ticket solamente puede generarse cuando la inscripción está
confirmada.

## Envío de tickets

Para realizar el envío se creó:

``` java
EnvioTicketsThread
```

Esta clase se encuentra en el paquete `hilos` y extiende:

``` java
Thread
```

Desde `App` se inicia mediante:

``` java
hiloTickets.start();
```

De esta forma, el envío de tickets se ejecuta en un hilo separado del
hilo principal.

Mientras el hilo de tickets realiza los envíos, el programa principal
continúa mostrando información de los eventos, actividades e
inscripciones.

En la consola se identifican ambos flujos mediante mensajes como:

``` text
[HILO PRINCIPAL]
[HILO TICKETS]
```

Esto permite observar de manera sencilla que los dos procesos están
ejecutándose de forma concurrente.
------------------------------------------------------------------------