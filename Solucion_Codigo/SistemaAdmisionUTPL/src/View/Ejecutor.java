package View;

import Controller.Controller;
import Model.Carrera;
import Model.Postulante;

import java.util.List;
import java.util.Scanner;

public class Ejecutor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Controller controller = new Controller();
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== SISTEMA DE ADMISION UTPL ===");
            System.out.println("1. Gestión de Carreras");
            System.out.println("2. Registro de Postulantes");
            System.out.println("3. Procesar Admisiones");
            System.out.println("4. Ver Reportes");
            System.out.println("5. Cargar carreras y postulantes de prueba");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    menuCarreras(scanner, controller);
                    break;
                case 2:
                    menuPostulantes(scanner, controller);
                    break;
                case 3:
                    controller.procesarAdmisiones();
                    System.out.println("Admisión procesada con éxito.");
                    break;
                case 4:
                    menuReportes(controller);
                    break;
                case 5:
                    controller.cargarPostulantesYCarrerasDePrueba();
                    System.out.println("Carreras y Postulantes de prueba cargados.");
                    break;
                case 6:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }

    }

    public static void menuCarreras(Scanner scanner, Controller controller) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE CARRERAS ---");
            System.out.println("1. Agregar carrera");
            System.out.println("2. Listar carreras");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    System.out.print("Nombre de la carrera: ");
                    String nombre = scanner.nextLine();

                    System.out.print("¿Alta demanda? (s/n): ");
                    boolean altaDemanda = scanner.nextLine().equalsIgnoreCase("s");

                    System.out.print("Cupos disponibles: ");
                    int cupos = Integer.parseInt(scanner.nextLine());

                    System.out.print("Cupos adicionales (2-5): ");
                    int cuposAdicionales = Integer.parseInt(scanner.nextLine());

                    double puntajeMinimo = 0;
                    if (altaDemanda) {
                        System.out.print("Puntaje mínimo requerido: ");
                        puntajeMinimo = Double.parseDouble(scanner.nextLine());
                    }

                    controller.agregarCarrera(new Carrera(nombre, cupos, cuposAdicionales, altaDemanda, puntajeMinimo));
                    System.out.println("Carrera agregada con éxito.");
                    break;

                case 2:
                    List<Carrera> carreras = controller.getCarreras();
                    if (carreras.isEmpty()) {
                        System.out.println("No hay carreras registradas.");
                    } else {
                        for (int i = 0; i < carreras.size(); i++) {
                            Carrera c = carreras.get(i);
                            System.out.println((i + 1) + ". " + c.getNombre() + " | Alta demanda: " + c.isAltaDemanda() + " | Cupos: " + c.getCupos());
                        }
                    }
                    break;

                case 3:
                    volver = true;
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    public static void menuPostulantes(Scanner scanner, Controller controller) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- REGISTRO DE POSTULANTES ---");
            System.out.println("1. Agregar postulante");
            System.out.println("2. Listar postulantes");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    List<Carrera> carreras = controller.getCarreras();
                    if (carreras.isEmpty()) {
                        System.out.println("Debe registrar carreras primero.");
                        break;
                    }

                    System.out.println("Carreras disponibles:");
                    for (int i = 0; i < carreras.size(); i++) {
                        System.out.println((i + 1) + ". " + carreras.get(i).getNombre());
                    }

                    System.out.print("Seleccione la carrera (número): ");
                    int indexCarrera = Integer.parseInt(scanner.nextLine()) - 1;
                    Carrera carreraSeleccionada = carreras.get(indexCarrera);

                    System.out.print("Nombre del postulante: ");
                    String nombreP = scanner.nextLine();

                    System.out.print("Cédula: ");
                    String cedula = scanner.nextLine();

                    System.out.print("Puntaje del examen: ");
                    double puntaje = Double.parseDouble(scanner.nextLine());

                    System.out.print("¿Es abanderado? (s/n): ");
                    boolean esAbanderado = scanner.nextLine().equalsIgnoreCase("s");

                    System.out.print("¿Carrera afín? (s/n): ");
                    boolean carreraAfin = scanner.nextLine().equalsIgnoreCase("s");

                    System.out.print("¿Tiene discapacidad o capacidad especial? (s/n): ");
                    boolean discapacidad = scanner.nextLine().equalsIgnoreCase("s");

                    controller.agregarPostulante(new Postulante(nombreP, cedula, carreraSeleccionada, puntaje, esAbanderado, carreraAfin, discapacidad));
                    System.out.println("Postulante registrado.");
                    break;

                case 2:
                    List<Postulante> postulantes = controller.getPostulantes();
                    if (postulantes.isEmpty()) {
                        System.out.println("No hay postulantes registrados.");
                    } else {
                        for (int i = 0; i < postulantes.size(); i++) {
                            Postulante p = postulantes.get(i);
                            System.out.println((i + 1) + ". " + p.getNombre() + " | Puntaje total: " + p.detallePuntaje() + " | Carrera: " + p.getCarrera().getNombre());
                        }
                    }
                    break;

                case 3:
                    volver = true;
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    public static void menuReportes(Controller controller) {
        System.out.println("\n--- Reporte General ---");
        List<String> resumen = controller.generarReporteGeneral();
        for (int i = 0; i < resumen.size(); i++) {
            System.out.println(resumen.get(i));
        }

        System.out.println("\n--- Carreras que no llenaron el 50% de cupos ---");
        List<String> carreras50 = controller.generarReporteCarrerasIncompletas();
        for (int i = 0; i < carreras50.size(); i++) {
            System.out.println(carreras50.get(i));
        }

        System.out.println("\n--- Postulantes Rechazados ---");
        List<String> rechazados = controller.generarReportePostulantesRechazados();
        for (int i = 0; i < rechazados.size(); i++) {
            System.out.println(rechazados.get(i));
        }

        System.out.println("\n--- Postulantes Admitidos con Cupo Adicional ---");
        List<String> cuposExtra = controller.generarReporteCupoAdicional();
        for (int i = 0; i < cuposExtra.size(); i++) {
            System.out.println(cuposExtra.get(i));
        }

        System.out.println("\n--- Carreras sin ningún postulante ---");
        List<String> sinPostulantes = controller.generarReporteCarrerasSinPostulantes();
        for (int i = 0; i < sinPostulantes.size(); i++) {
            System.out.println(sinPostulantes.get(i));
        }

        System.out.println("\n--- Carreras que llenaron todos sus cupos ---");
        List<String> cuposLlenos = controller.generarReporteCarrerasOcupadas();
        for (int i = 0; i < cuposLlenos.size(); i++) {
            System.out.println(cuposLlenos.get(i));
        }
    }

}
