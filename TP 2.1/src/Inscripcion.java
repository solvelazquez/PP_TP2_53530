import java.io.Serializable;
import java.time.LocalDate;
import java.io.Serializable;
public class Inscripcion implements Serializable {

    private static final long serialVersionUID = 1L;
    private LocalDate fecha;
    private String estado;


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

    public void confirmar() {this.estado = "CONFIRMADA";}
}
