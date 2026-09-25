package modelo.actividades;

public class Charla extends Actividad {
    private String disertante;

    public Charla(int id, String titulo, String disertante, int cupo) {
        super(id,cupo,titulo); //sirve para llamar al constructor de la clase padre.
        this.disertante = disertante;
    }

    public String getDisertante() {
        return disertante;
    }

    public void setDisertante(String disertante) {

        if (disertante == null || disertante.isBlank()) {
            return;
        }
        this.disertante = disertante;
    }

    @Override
    public double calcularCostoMateriales() {
        return 0;
    }

    @Override
    public String getTipo() {
        return this.getClass().getSimpleName();
    }

}
