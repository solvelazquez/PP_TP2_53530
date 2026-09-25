package modelo;

import modelo.actividades.Actividad;

import java.time.LocalDate;

public class Inscripcion implements java.io.Serializable{
    private LocalDate fecha;
    private String estado;
    private TicketDeAcceso ticket;

    private Actividad actividad;
    private Estudiante estudiante;


    public Inscripcion(Actividad actividad, Estudiante estudiante,LocalDate fecha, String estado) {
        this.actividad= actividad;
        this.estudiante=estudiante;
        this.fecha = fecha;
        this.estado = estado;
}

    public Actividad getActividad() {
        return actividad;
    }
    public Estudiante getEstudiante() {
        return estudiante;
    }
    public  LocalDate getFecha() {return fecha;}
    public String getEstado() {return estado;}
    public TicketDeAcceso getTicket() {return ticket;}

    public void confirmar() {this.estado = "CONFIRMADA";}

    //
    public TicketDeAcceso generarTicket() {
        if (!"CONFIRMADA".equals(estado)) {
            return null;
        }

        ticket = new TicketDeAcceso(
                "TICKET-" + estudiante.getLegajo()
                        + "-" + actividad.getId()
        );

        return ticket;
    }

    public class TicketDeAcceso implements java.io.Serializable {
        private String ticket;
        private LocalDate fechaEmision;

        public TicketDeAcceso(String ticket) {
            this.ticket = ticket;
            this.fechaEmision = LocalDate.now();
        }

        public String getTicket() {
            return ticket;
        }

        public LocalDate getFechaEmision() {
            return fechaEmision;
        }

        public void enviarTicket() {
            System.out.println(
                    "Enviando ticket " + ticket
                            + " al estudiante "
                            + estudiante.getNombre()
            );
        }
    }
}
