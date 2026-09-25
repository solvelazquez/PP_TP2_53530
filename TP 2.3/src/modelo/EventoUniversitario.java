package modelo;

import modelo.actividades.Actividad;
import modelo.actividades.Charla;
import modelo.actividades.Taller;
import modelo.actividades.Curso;

import java.util.List;
import java.util.ArrayList;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class EventoUniversitario implements java.io.Serializable{
    private final String id;
    private String titulo;
    private double costoBase;
    private boolean gratuito;

    private static int cantidadEventos=0;

    private Sala sala;
    private  List <Actividad> actividades;

    //PONEMOS CONSTRUCTOR
    public EventoUniversitario(String id, String titulo, double costoBase, boolean gratuito) {
        this.id = id;
        this.titulo = titulo;
        this.costoBase = costoBase;
        this.gratuito = gratuito;
        cantidadEventos++;
        this.actividades = new ArrayList<>();
    }

    //CONSTRUCTOR DE COPIA
    public EventoUniversitario(EventoUniversitario otroEvento) {
        this.id = otroEvento.id;
        this.titulo=otroEvento.titulo;
        this.costoBase=otroEvento.costoBase;
        this.gratuito= otroEvento.gratuito;
        cantidadEventos++;
        this.actividades = new ArrayList<>();
    }


    //CALCULAR COSTO ESTIMADO
    public double calcularCostoEstimado(){
        if (gratuito) {
            return 0;
        }

        double costoTotal = costoBase;
        for (Actividad actividad : actividades) {
            costoTotal += actividad.calcularCostoMateriales();
        }
        return costoTotal * 1.21;
    }

    //ASIGNAR SALA
    public void asignarSala(Sala Sala){
        this.sala=Sala;
        System.out.println( "modelo.Sala asignada al evento "+titulo);          //"sout" atajo para system.....
    }

    //CREAR ACTIVIDAD
    public void crearActividad(int idActividad, String tituloActividad, int cupo, String tipoActividad, boolean requiereNotebook, int nivel) {
        if (tipoActividad.equalsIgnoreCase("modelo.actividades.Charla")) {
            Charla charla = new Charla(
                    idActividad,
                    tituloActividad,
                    "sin especificar",
                    cupo
            );
            actividades.add(charla);

        }
        else if (tipoActividad.equalsIgnoreCase("modelo.actividades.Taller")) {
            Taller taller = new Taller(
                    idActividad,
                    tituloActividad,
                    requiereNotebook,
                    cupo
            );
            actividades.add(taller);
        }
        else if (tipoActividad.equalsIgnoreCase("modelo.actividades.Curso")) {
            Curso curso = new Curso(
                    idActividad,
                    tituloActividad,
                    cupo,
                    nivel
            );

            actividades.add(curso);
        }
    }

    public List<Actividad> getActividades() {
        return actividades;
    }
    public <T extends Actividad> List<T> filtrarActividadesPorTipo(Class<T> tipo) {
        List<T> actividadesFiltradas = new ArrayList<>();
        for (Actividad actividad : actividades) {
            if (tipo.isInstance(actividad)) {
                actividadesFiltradas.add(tipo.cast(actividad));
            }
        }
        return actividadesFiltradas;
    }
    public double calcularCostoMateriales(List<? extends Actividad> actividades) {
        double costoTotal = 0;
        for (Actividad actividad : actividades) {
            costoTotal += actividad.calcularCostoMateriales();
        }
        return costoTotal;
    }


    //MOSTRAR DATOS
    public void mostrarDatos() {
        System.out.println("--------------");
        System.out.println("Evento codigo " + id);
        System.out.println("Titulo = " + titulo);
        System.out.println("Costo = " + this.calcularCostoEstimado());
        System.out.println("Actividades:");
        for (Actividad actividad : actividades) {
            actividad.mostrarIdentificacion();
        }
    }
    //
    public String getId() {return id;}
    public static int getCantidadEventos(){
        return cantidadEventos;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

//.....
    public void persistirEvento(String nombreArchivo) throws IOException {
        try (ObjectOutputStream salida =
                     new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {

            salida.writeObject(this);
        }
    }

    public static EventoUniversitario recuperarEvento(String nombreArchivo)
            throws IOException, ClassNotFoundException {
        try (ObjectInputStream entrada =
                     new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            return (EventoUniversitario) entrada.readObject();
        }
    }
}
