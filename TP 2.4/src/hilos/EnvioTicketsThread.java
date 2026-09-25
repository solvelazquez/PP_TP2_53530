package hilos;

import modelo.EventoUniversitario;

public class EnvioTicketsThread extends Thread {
    private EventoUniversitario evento;

    public EnvioTicketsThread(EventoUniversitario evento) {
        this.evento = evento;
    }

    @Override
    public void run() {
        System.out.println(
                "[HILO TICKETS] Iniciando envío de tickets..."
        );

        for (var actividad : evento.getActividades()) {
            for (var inscripcion : actividad.getInscripciones()) {
                if ("CONFIRMADA".equals(inscripcion.getEstado())
                        && inscripcion.getTicket() != null) {
                    System.out.println(
                            "[HILO TICKETS] Enviando "
                                    + inscripcion.getTicket().getTicket()
                    );
                    inscripcion.getTicket().enviarTicket();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        System.out.println(
                "[HILO TICKETS] Finalizó el envío de tickets."
        );
    }
}