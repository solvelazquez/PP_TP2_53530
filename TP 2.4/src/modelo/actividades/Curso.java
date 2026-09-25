package modelo.actividades;

import modelo.certificacion.Certificable;
import modelo.Estudiante;

public class Curso extends Actividad implements Certificable {
    private int nivel;

    public Curso(int id, String titulo, int cupo, int nivel) {
        super(id, cupo, titulo);
        this.nivel = nivel;
    }

    @Override
    public double calcularCostoMateriales() {
        switch (nivel) {
            case 1:
                return 1000.0;
            case 2:
                return 2000.0;
            case 3:
                return 3000.0;
            default:
                return 0.0;
        }
    }

    @Override
    public String getTipo() {
        return "Curso";
    }

    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "Certificado de asistencia - "
                + estudiante.getNombre()
                + " - Actividad: "
                + getTitulo()
                + " - " + ENTIDAD_EMISORA;
    }

}