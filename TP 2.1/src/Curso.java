public class Curso extends Actividad implements Certificable {
    private int nivel;
    public Curso(int id, String titulo, int cupo, int nivel) {
        super(id, cupo, titulo);
        this.nivel = nivel;



    }

    @Override
    public double calcularCostoMateriales() {
        return 0;
    }

    @Override
    public String getTipo() {
        return "Curso";
    }

    @Override
    public String emitirCertificado(Estudiante estudiante) {
        return "Certificado de asistencia - Curso: " + getTitulo()
        + " - Estudiante: "
                + estudiante.getNombre();
    }
}














