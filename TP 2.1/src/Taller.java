public class Taller extends Actividad implements Certificable {
    private boolean requiereNotebook;

    public Taller (int id, String titulo, boolean requiereNotebook, int cupo){
        super(id, cupo, titulo);
        this.requiereNotebook=requiereNotebook;
    }

    public boolean isRequiereNotebook() {
        return requiereNotebook;
    }

    public void setRequiereNotebook(boolean requiereNotebook) {
        this.requiereNotebook = requiereNotebook;
    }


    @Override
    public double calcularCostoMateriales() {
        if (requiereNotebook) {
            return 5000;
        }
        return 2000;
    }

    @Override
    public String getTipo() {
        return "Taller";
    }

    @Override
    public String emitirCertificado(Estudiante estudiante) {
        return "Certificado de asistencia - Taller: "
                + getTitulo()
        + " - Estudiante: "
                + estudiante.getNombre();
    }
}

