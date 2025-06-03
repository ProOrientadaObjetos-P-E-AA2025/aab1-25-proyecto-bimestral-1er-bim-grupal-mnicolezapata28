package Controller;

import Model.Carrera;
import Model.Postulante;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private List<Carrera> carreras;
    private List<Postulante> postulantes;
    private List<Postulante> admitidos;
    private List<Postulante> rechazados;

    public Controller() {
        carreras = new ArrayList<>();
        postulantes = new ArrayList<>();
        admitidos = new ArrayList<>();
        rechazados = new ArrayList<>();
    }

    public void agregarCarrera(Carrera carrera) {
        carreras.add(carrera);
    }

    public List<Carrera> getCarreras() {
        return carreras;
    }

    public void agregarPostulante(Postulante postulante) {
        postulantes.add(postulante);
    }

    public List<Postulante> getPostulantes() {
        return postulantes;
    }

    public List<Postulante> getAdmitidos() {
        return admitidos;
    }

    public List<Postulante> getRechazados() {
        return rechazados;
    }

    public void procesarAdmisiones() {//
        admitidos.clear();
        rechazados.clear();

        for (int i = 0; i < carreras.size(); i++) {
            Carrera carrera = carreras.get(i);
            List<Postulante> postulantesCarrera = new ArrayList<>();

            for (int j = 0; j < postulantes.size(); j++) {
                Postulante p = postulantes.get(j);
                if (p.getCarrera().getNombre().equals(carrera.getNombre())) {
                    if (!carrera.isAltaDemanda() || p.calcularPuntajeTotal() >= carrera.getPuntajeMinimo()) {
                        postulantesCarrera.add(p);
                    } else {
                        rechazados.add(p); 
                    }
                }
            }

            // Ordenar por puntaje total descendente
            for (int x = 0; x < postulantesCarrera.size() - 1; x++) {
                for (int y = x + 1; y < postulantesCarrera.size(); y++) {
                    if (postulantesCarrera.get(y).calcularPuntajeTotal() > postulantesCarrera.get(x).calcularPuntajeTotal()) {
                        Postulante temp = postulantesCarrera.get(x);
                        postulantesCarrera.set(x, postulantesCarrera.get(y));
                        postulantesCarrera.set(y, temp);
                    }
                }
            }

            int cuposNormales = carrera.getCupos();
            int cuposExtra = carrera.getCuposAdicionales();
            int totalAsignables = cuposNormales + cuposExtra;

            for (int k = 0; k < postulantesCarrera.size(); k++) {
                Postulante p = postulantesCarrera.get(k);

                if (k < cuposNormales) {
                    admitidos.add(p);
                } else if (k < totalAsignables && tieneMerito(p)) {
                    admitidos.add(p);
                } else {
                    rechazados.add(p);
                }
            }
        }
    }

    private boolean tieneMerito(Postulante p) {
        return p.isAbanderado() || p.isCarreraAfin() || p.isCapacidadEspecial();
    }

    public List<String> generarReporteCarrerasIncompletas() {
        List<String> reporte = new ArrayList<>();

        for (int i = 0; i < carreras.size(); i++) {
            Carrera c = carreras.get(i);
            int contador = 0;

            for (int j = 0; j < admitidos.size(); j++) {
                Postulante p = admitidos.get(j);
                if (p.getCarrera().getNombre().equals(c.getNombre())) {
                    contador++;
                }
            }

            if (contador < (c.getCupos() / 2.0)) {
                reporte.add("Carrera " + c.getNombre() + " no llenó el 50% de sus cupos (" + contador + " de " + c.getCupos() + ")");
            }
        }

        return reporte;
    }

    public List<String> generarReportePostulantesRechazados() {
        List<String> reporte = new ArrayList<>();

        for (int i = 0; i < rechazados.size(); i++) {
            Postulante p = rechazados.get(i);
            reporte.add(p.getNombre() + " fue rechazado de " + p.getCarrera().getNombre() + " con " + p.calcularPuntajeTotal() + " puntos.");
        }

        return reporte;
    }

    public List<String> generarReporteCupoAdicional() {
        List<String> reporte = new ArrayList<>();

        for (int i = 0; i < carreras.size(); i++) {
            Carrera c = carreras.get(i);
            int contador = 0;
            int normales = c.getCupos();

            for (int j = normales; j < admitidos.size(); j++) {
                Postulante p = admitidos.get(j);
                if (p.getCarrera().getNombre().equals(c.getNombre()) && tieneMerito(p)) {
                    reporte.add(p.getNombre() + " fue admitido en " + c.getNombre() + " por mérito usando cupo adicional (" + p.detallePuntaje() + ")");
                    contador++;
                }
            }
        }

        if (reporte.isEmpty()) {
            reporte.add("No se usaron cupos adicionales.");
        }

        return reporte;
    }

    public List<String> generarReporteCarrerasSinPostulantes() {
        List<String> reporte = new ArrayList<>();

        for (int i = 0; i < carreras.size(); i++) {
            Carrera c = carreras.get(i);
            boolean tiene = false;

            for (int j = 0; j < postulantes.size(); j++) {
                if (postulantes.get(j).getCarrera().getNombre().equals(c.getNombre())) {
                    tiene = true;
                    break;
                }
            }

            if (!tiene) {
                reporte.add("Carrera " + c.getNombre() + " no recibió ningún postulante.");
            }
        }

        return reporte;
    }

    public List<String> generarReporteCarrerasOcupadas() {
        List<String> reporte = new ArrayList<>();

        for (int i = 0; i < carreras.size(); i++) {
            Carrera c = carreras.get(i);
            int contador = 0;

            for (int j = 0; j < admitidos.size(); j++) {
                Postulante p = admitidos.get(j);
                if (p.getCarrera().getNombre().equals(c.getNombre())) {
                    contador++;
                }
            }

            if (contador >= c.getCupos()) {
                reporte.add("Carrera " + c.getNombre() + " llenó todos sus cupos normales.");
            }
        }

        return reporte;
    }

    public List<String> generarReporteGeneral() {
        List<String> reporte = new ArrayList<>();
        reporte.add("Total de carreras registradas: " + carreras.size());
        reporte.add("Total de postulantes: " + postulantes.size());
        reporte.add("Postulantes admitidos: " + admitidos.size());
        reporte.add("Postulantes rechazados: " + rechazados.size());
        return reporte;
    }

    public void cargarPostulantesYCarrerasDePrueba() {
        //  Carrera que NO alcanza el 50% de cupos (1 de 6 cupos usados)
        agregarCarrera(new Carrera("Filosofía", 6, 2, false, 0));
        agregarPostulante(new Postulante("Mario Vargas", "2001", buscarCarrera("Filosofía"), 40, false, false, false));

        // Carrera con RECHAZOS por falta de cupos (2 normales + 1 adicional, 4 postulantes)
        agregarCarrera(new Carrera("Psicología", 2, 1, true, 70));
        agregarPostulante(new Postulante("Nicolás Pérez", "2020", buscarCarrera("Psicología"), 75, false, false, false));
        agregarPostulante(new Postulante("Esteban Salas", "2021", buscarCarrera("Psicología"), 74, true, false, false)); // adicional
        agregarPostulante(new Postulante("Lucía Vega", "2022", buscarCarrera("Psicología"), 72, false, false, true));   // adicional
        agregarPostulante(new Postulante("Tamara Llumiquinga", "2023", buscarCarrera("Psicología"), 70, false, false, false)); // rechazada

        // Carrera que UTILIZA CUPOS ADICIONALES (3 normales + 3 adicionales)
        agregarCarrera(new Carrera("Enfermería", 3, 3, true, 70));
        agregarPostulante(new Postulante("Sofía Ramos", "2010", buscarCarrera("Enfermería"), 74, true, true, true)); // normal
        agregarPostulante(new Postulante("Andrés Molina", "2011", buscarCarrera("Enfermería"), 72, false, true, true)); // normal
        agregarPostulante(new Postulante("Daniela García", "2012", buscarCarrera("Enfermería"), 71, false, false, true)); // normal
        agregarPostulante(new Postulante("Luis Andrade", "2013", buscarCarrera("Enfermería"), 69, true, false, false)); // adicional
        agregarPostulante(new Postulante("Elena Muñoz", "2014", buscarCarrera("Enfermería"), 68, true, false, false)); // adicional
        agregarPostulante(new Postulante("Kevin Cedillo", "2015", buscarCarrera("Enfermería"), 65, true, false, false)); // adicional

        // Carrera SIN RECHAZOS y SIN llenar cupos (3 cupos, 2 postulantes)
        agregarCarrera(new Carrera("Derecho", 3, 0, false, 0));
        agregarPostulante(new Postulante("Sandra Torres", "2101", buscarCarrera("Derecho"), 65, false, false, false));
        agregarPostulante(new Postulante("Raúl Lima", "2102", buscarCarrera("Derecho"), 72, false, false, false));

        //  Carrera que mezcla cupos normales, adicionales y rechazos
        agregarCarrera(new Carrera("Computación", 2, 2, true, 68));
        agregarPostulante(new Postulante("Pedro Lema", "1109", buscarCarrera("Computación"), 70, true, true, false)); // normal
        agregarPostulante(new Postulante("Daniel Castillo", "1111", buscarCarrera("Computación"), 80, false, true, false)); // normal
        agregarPostulante(new Postulante("Laura Gómez", "2201", buscarCarrera("Computación"), 67, true, false, false)); // adicional
        agregarPostulante(new Postulante("Milton Puma", "2202", buscarCarrera("Computación"), 66, false, false, false)); // rechazado
    }

    private Carrera buscarCarrera(String nombreCarrera) {
        for (int i = 0; i < carreras.size(); i++) {
            if (carreras.get(i).getNombre().equalsIgnoreCase(nombreCarrera)) {
                return carreras.get(i);
            }
        }
        return null;
    }

}
