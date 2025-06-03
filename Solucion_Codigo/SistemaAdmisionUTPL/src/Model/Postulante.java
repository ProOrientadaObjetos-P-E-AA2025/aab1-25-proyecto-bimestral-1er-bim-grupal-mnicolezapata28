package Model;

public class Postulante {

    private String nombre;
    private String cedula;
    private Carrera carrera;
    private double puntajeExamen;
    private boolean esAbanderado;
    private boolean carreraAfin;
    private boolean capacidadEspecial;

    public Postulante(String nombre, String cedula, Carrera carrera, double puntajeExamen,
            boolean esAbanderado, boolean carreraAfin, boolean capacidadEspecial) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.carrera = carrera;
        this.puntajeExamen = puntajeExamen;
        this.esAbanderado = esAbanderado;
        this.carreraAfin = carreraAfin;
        this.capacidadEspecial = capacidadEspecial;
    }

    public double calcularPuntajeTotal() {
        double adicional = 0;
        if (esAbanderado) {
            adicional += 5;
        }
        if (carreraAfin) {
            adicional += 2;
        }
        if (capacidadEspecial) {
            adicional += 3;
        }
        return puntajeExamen + adicional;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public double getPuntajeExamen() {
        return puntajeExamen;
    }

    public boolean isAbanderado() {
        return esAbanderado;
    }

    public boolean isCarreraAfin() {
        return carreraAfin;
    }

    public boolean isCapacidadEspecial() {
        return capacidadEspecial;
    }

    public String detallePuntaje() {
        String detalle = String.format("%.2f examen", puntajeExamen);
        if (esAbanderado) {
            detalle += " + 5 por abanderado";
        }
        if (carreraAfin) {
            detalle += " + 2 por carrera afín";
        }
        if (capacidadEspecial) {
            detalle += " + 3 por capacidad especial";
        }
        detalle += " = " + calcularPuntajeTotal();
        return detalle;
    }

}
