import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class EventoUniversitario implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String id;
    private String titulo;
    private double costoBase;
    private boolean gratuito;

    private static int cantidadEventos = 0;

    private Sala sala;
    private List<Actividad> actividades;


    //PONEMOS CONSTRUCTOR
    public EventoUniversitario(String id, String titulo, double costoBase, boolean gratuito) {
        this.id = id;
        this.titulo = titulo;
        this.costoBase = costoBase;
        this.gratuito = gratuito;
        cantidadEventos++;
        this.actividades = new ArrayList<>();

    }
    public String getId() {
        return id;
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
        System.out.println( "Sala asignada al evento "+titulo);          //"sout" atajo para system.....
    }

    //CREAR ACTIVIDAD
    public void crearActividad(int idActividad, String tituloActividad, int cupo, String tipoActividad, boolean requiereNotebook,  int nivel ) {

        if (tipoActividad.equalsIgnoreCase("Charla")) {
            Charla charla = new Charla(
                    idActividad,
                    tituloActividad,
                    "sin especificar",
                    cupo
            );
            actividades.add(charla);

        } else if (tipoActividad.equalsIgnoreCase("Taller")) {
            Taller taller = new Taller(
                    idActividad,
                    tituloActividad,
                    requiereNotebook,
                    cupo
            );
            actividades.add(taller);
        }
        else if (tipoActividad.equalsIgnoreCase("Curso")) {
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
    public static int getCantidadEventos(){
        return cantidadEventos;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public boolean persistirEvento() {

        try (ObjectOutputStream salida =
                     new ObjectOutputStream(
                             new FileOutputStream(id + ".dat"))) {

            salida.writeObject(this);

            return true;

        } catch (IOException e) {

            System.out.println(
                    "Error al guardar el evento: " + e.getMessage()
            );

            return false;
        }
    }
    public static EventoUniversitario recuperarEvento(String id) {

        try (ObjectInputStream entrada =
                     new ObjectInputStream(
                             new FileInputStream(id + ".dat"))) {

            return (EventoUniversitario) entrada.readObject();

        } catch (IOException e) {

            System.out.println(
                    "Error al leer el archivo del evento: "
                            + e.getMessage()
            );

        } catch (ClassNotFoundException e) {

            System.out.println(
                    "Error: no se encontró la clase del objeto guardado."
            );
        }

        return null;
    }

}
