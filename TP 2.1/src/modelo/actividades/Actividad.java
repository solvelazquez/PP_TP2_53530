package modelo.actividades;
import modelo.Inscripcion;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import modelo.Estudiante;
import excepciones.CupoExcedidoException;


public abstract class Actividad implements java.io.Serializable{
    private int id;
    private String titulo;
    private int cupoMaximo;
    public static final int CUPO_MINIMO = 5;
    private List<Inscripcion> inscripciones ;


    public Actividad(int id, int cupoMaximo, String titulo) {
        this.id = id;
        this.cupoMaximo = cupoMaximo;
        this.titulo = titulo;

        this.inscripciones = new ArrayList<>();
    }

    public Actividad() {
        this.inscripciones = new ArrayList<>();
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            return;
        }
        this.titulo = titulo;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }
    public void setCupoMaximo(int cupo) {
        this.cupoMaximo = (cupo > CUPO_MINIMO) ? cupo : CUPO_MINIMO;
    }

    public Inscripcion inscribir(Estudiante estudiante) throws CupoExcedidoException{
        if (inscripciones.size() >= cupoMaximo) {
            throw new CupoExcedidoException(
                    "Se excedió el cupo máximo de la actividad."
            );
        }
        Inscripcion inscripcion = new Inscripcion(this, estudiante, LocalDate.now(), "REGISTRADA");
        inscripciones.add( inscripcion);
        return inscripcion;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    //--------------------------------
    public abstract double calcularCostoMateriales();
    public abstract String getTipo();

    public final void mostrarIdentificacion() {
        System.out.println(
                "ID: " + id +
                        " | Tipo: " + getTipo() +
                        " | Título: " + titulo
        );
    }
    //--------------------------------

    public void mostrarInscripciones() {
        if (inscripciones.isEmpty()) {
            System.out.println("Sin inscripciones registradas.");
            return;
        }
        System.out.println("   Inscripciones registradas:");
        for (Inscripcion inscripcion : inscripciones) {
            System.out.println("   " + inscripcion.getFecha() +" - "+  inscripcion.getEstado()+ " - " + inscripcion.getEstudiante().getNombre() + " (Legajo: " + inscripcion.getEstudiante().getLegajo() + ")");
        }
    }


}
