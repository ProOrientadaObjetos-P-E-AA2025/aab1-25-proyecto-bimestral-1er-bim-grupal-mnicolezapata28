package Model;

public class Carrera {
    private String nombre;
    private int cupos;
    private int cuposAdicionales;
    private boolean altaDemanda;
    private double puntajeMinimo;

    public Carrera(String nombre, int cupos, int cuposAdicionales, boolean altaDemanda, double puntajeMinimo) {
        this.nombre = nombre;
        this.cupos = cupos;
        this.cuposAdicionales = cuposAdicionales;
        this.altaDemanda = altaDemanda;
        this.puntajeMinimo = puntajeMinimo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCupos() {
        return cupos;
    }

    public int getCuposAdicionales() {
        return cuposAdicionales;
    }

    public boolean isAltaDemanda() {
        return altaDemanda;
    }

    public double getPuntajeMinimo() {
        return puntajeMinimo;
    }
}
