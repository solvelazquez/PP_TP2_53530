import modelo.Estudiante;
import modelo.EventoUniversitario;
import modelo.Sala;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import excepciones.CupoExcedidoException;

import java.io.IOException;

import modelo.Inscripcion;
import modelo.certificacion.Certificable;
import modelo.actividades.Actividad;

public class App {
    public static void main(String[] args) {
        // CREAR UNO O MAS EVENTOS
        EventoUniversitario evento1;
        int id;

        // ==================CREACION DE ESTUDIANTES=====================
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        id = 1;
        List<Estudiante> estudiantes = new ArrayList<>();
        System.out.println("REGISTRO DE ESTUDIANTES: ");
        System.out.println("========================");

        while (continuar) {
            System.out.println("Ingrese legajo del estudiante: ");
            String legajo = scanner.nextLine();

            System.out.println("Ingrese nombre y apellido del estudiante: ");
            String apenomb = scanner.nextLine();

            estudiantes.add(
                    new Estudiante(legajo, apenomb)
            );

            System.out.println("¿Desea crear otro estudiante S/N?");
            String respuesta =
                    scanner.nextLine().trim().toLowerCase();

            continuar =
                    respuesta.equals("s")
                            || respuesta.equals("si")
                            || respuesta.equals("sí");
        }


        // ==================CREACION DE EVENTOS==================
        System.out.println("\n\nREGISTRO DE EVENTOS: ");
        System.out.println("====================");
        continuar = true;

        while (continuar) {
            // --------------DATOS DEL EVENTO------------------
            System.out.println("Ingrese un título para el evento: ");
            String titulo = scanner.nextLine();

            System.out.println("Ingrese el costo base: ");
            double costoBase = scanner.nextDouble();
            scanner.nextLine();
            System.out.println(
                    "¿El evento tendrá costo para los participantes S/N?"
            );
            String respuesta =
                    scanner.nextLine().trim().toLowerCase();

            boolean esGratuito = true;
            if (respuesta.equals("s")
                    || respuesta.equals("si")
                    || respuesta.equals("sí")) {
                esGratuito = false;
            }

            // -------------------CREAR EVENTO----------------
            EventoUniversitario evento =
                    new EventoUniversitario(
                            "EVT-" + id,
                            titulo,
                            costoBase,
                            esGratuito
                    );

            // -------------CREAR Y ASIGNAR SALA---------------
            System.out.println(
                    "Ingrese el nombre de la sala donde se realizará el evento: "
            );
            String nombreSala = scanner.nextLine();
            Sala sala =
                    new Sala(id, nombreSala);
            evento.asignarSala(sala);

            // ================CREAR ACTIVIDADES================
            System.out.println(
                    "\n\nREGISTRO DE ACTIVIDADES PARA EL EVENTO "
                            + evento.getTitulo()
            );
            System.out.println(
                    "=========================================================="
            );

            int idActividad = 1;
            continuar = true;

            while (continuar) {
                System.out.println(
                        "Ingrese el título de la actividad: "
                );
                String tituloActividad =
                        scanner.nextLine();

                System.out.println(
                        "Ingrese el cupo máximo de estudiantes: "
                );
                int cupo = scanner.nextInt();
                scanner.nextLine();


                // --------------------ELEGIR TIPO DE ACTIVIDAD----------------
                System.out.println(
                        "Ingrese el tipo de actividad:"
                );

                System.out.println(
                        "1 - modelo.actividades.Charla"
                );

                System.out.println(
                        "2 - modelo.actividades.Taller"
                );
                System.out.println(
                        "3 - modelo.actividades.Curso"
                );

                int opcionTipo =
                        scanner.nextInt();
                scanner.nextLine();


                String tipoActividad;
                boolean requiereNotebook = false;
                int nivel = 0;
                if (opcionTipo == 1) {
                    tipoActividad = "modelo.actividades.Charla";
                } else if (opcionTipo == 2) {
                    tipoActividad = "modelo.actividades.Taller";
                    System.out.println("¿El taller requiere notebook? S/N");
                    String respuestaNotebook =
                            scanner.nextLine().trim().toLowerCase();

                    requiereNotebook =
                            respuestaNotebook.equals("s")
                                    || respuestaNotebook.equals("si")
                                    || respuestaNotebook.equals("sí");
                } else if (opcionTipo == 3) {
                tipoActividad = "modelo.actividades.Curso";
                System.out.println("Ingrese el nivel del curso:");
                nivel = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Opción de actividad inválida.");
                continue;
            }

                // ---------------CREAR ACTIVIDAD------------------
                evento.crearActividad(
                        idActividad,
                        tituloActividad,
                        cupo,
                        tipoActividad,
                        requiereNotebook,
                        nivel
                );

                System.out.println(
                        "modelo.actividades.Actividad creada correctamente."
                );

                System.out.println(
                        "¿Desea crear otra actividad para el evento "
                                + evento.getTitulo()
                                + " S/N?"
                );

                respuesta =
                        scanner.nextLine()
                                .trim()
                                .toLowerCase();

                continuar =
                        respuesta.equals("s")
                                || respuesta.equals("si")
                                || respuesta.equals("sí");
                ++idActividad;
            }


            // =============INSCRIPCION DE ESTUDIANTES==================
            System.out.println(
                    "\n\nINSCRIPCION DE ESTUDIANTES EN ACTIVIDADES DEL EVENTO "
                            + evento.getTitulo()
            );

            System.out.println(
                    "==============================================================="
            );
            continuar = true;

            while (continuar) {
                System.out.println(
                        "Ingrese legajo del estudiante a inscribir: "
                );
                String legajo =
                        scanner.nextLine();

                System.out.println(
                        "Ingrese ID de la actividad: "
                );
                idActividad =
                        scanner.nextInt();
                scanner.nextLine();


                // --------------BUSCAR ESTUDIANTE----------------
                for (Estudiante estudiante : estudiantes) {
                    if (estudiante.getLegajo().equals(legajo)) {
                        try {
                            evento.getActividades()
                                    .get(idActividad - 1)
                                    .inscribir(estudiante);

                            System.out.println(
                                    "modelo.Estudiante inscripto correctamente."
                            );

                        } catch (CupoExcedidoException e) {
                            System.out.println(
                                    "Error al inscribir: " + e.getMessage()
                            );
                        }

                    }
                }
                //PERSISTENCIA DEL EVENTO
                try {
                    System.out.println("\nPERSISTENCIA DEL EVENTO");
                    System.out.println("======================");
                    String nombreArchivo = "evento_" + evento.getId() + ".dat";

                    // GUARDAR EVENTO
                    evento.persistirEvento(nombreArchivo);
                    System.out.println("Evento guardado correctamente.");
                    // RECUPERAR EVENTO
                    EventoUniversitario eventoRecuperado =
                            EventoUniversitario.recuperarEvento(nombreArchivo);
                    System.out.println("\nEVENTO RECUPERADO");
                    System.out.println("================");
                    eventoRecuperado.mostrarDatos();

                } catch (IOException e) {
                    System.out.println(
                            "Error de entrada/salida: " + e.getMessage()
                    );

                } catch (ClassNotFoundException e) {
                    System.out.println(
                            "Error al recuperar la clase del evento: "
                                    + e.getMessage()
                    );

                } finally {
                    System.out.println("\nFINALIZÓ EL PROCESO DE PERSISTENCIA.");
                }

                System.out.println(
                        "¿Desea generar otra inscripción S/N?"
                );

                respuesta =
                        scanner.nextLine()
                                .trim()
                                .toLowerCase();
                continuar =
                        respuesta.equals("s")
                                || respuesta.equals("si")
                                || respuesta.equals("sí");
            }

            // ============GENERAR CERTIFICADOS==================
            System.out.println("\n\nCERTIFICADOS EMITIDOS");
            System.out.println("====================");

            for (Actividad actividad : evento.getActividades()) {

                if (actividad instanceof Certificable certificable) {

                    System.out.println(
                            "Certificados para la actividad: "
                                    + actividad.getTitulo()
                    );

                    for (Inscripcion inscripcion : actividad.getInscripciones()) {

                        String certificado =
                                certificable.generarCertificado(
                                        inscripcion.getEstudiante()
                                );

                        System.out.println(certificado);
                    }
                }
            }

            // ============MOSTRAR DATOS DEL EVENTO==================
            System.out.println(
                    "\n\nDATOS DEL EVENTO"
            );
            System.out.println(
                    "================"
            );
            evento.mostrarDatos();


            // ===================COSTO DEL EVENTO================
            System.out.println(
                    "Costo estimado del evento: $"
                            + evento.calcularCostoEstimado()
            );


            // ==================CREAR OTRO EVENTO=================
            System.out.println(
                    "\n\n¿Desea crear otro evento S/N?"
            );
            respuesta =
                    scanner.nextLine()
                            .trim()
                            .toLowerCase();
            continuar =
                    respuesta.equals("s")
                            || respuesta.equals("si")
                            || respuesta.equals("sí");
            id++;
        }

        // ===================TOTAL DE EVENTOS================================
        System.out.println(
                "\n\nTOTAL DE EVENTOS CREADOS: "
                        + EventoUniversitario.getCantidadEventos()
        );

        scanner.close();
    }
}