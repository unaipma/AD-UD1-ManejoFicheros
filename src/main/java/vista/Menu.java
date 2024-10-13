/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import idao.JugadorAleatorioDAO;
import idao.JugadorBinarioDAO;
import idao.JugadorDao;
import idao.JugadorObjetoDAO;
import idao.JugadorTextoDAO;
import idao.JugadorXMLDAO;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import modelo.Jugador;

/**
 * 
 * 
 * @author Vespertino
 */
public class Menu {

    private JugadorDao jugadorDAO; // Instancia de JugadorDAO configurada según el tipo de fichero
    private Scanner scanner;

    /**
     * Constructor que inicia el programa con el txt de manera predeterminada para almacenar.
     * 
     * @throws IOException Si ocurre un error al inicializar el almacenamiento.
     */
    public Menu() throws IOException {
        scanner = new Scanner(System.in);
        jugadorDAO = new JugadorTextoDAO(); // Almacenamiento predeterminado
    }

    /**
     * Método principal que muestra el menú para la gestión de los jugadores y recibe la orden del usuario
     * 
     * @throws Exception Si ocurre un error durante la ejecución de las operaciones.
     */
    public void iniciar() throws Exception {
        int opcion;
        do {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Alta de Jugador");
            System.out.println("2. Baja de Jugador");
            System.out.println("3. Modificación de Jugador");
            System.out.println("4. Listado por ID");
            System.out.println("5. Listado General");
            System.out.println("6. Configuración de Almacenamiento");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea después del entero

            switch (opcion) {
                case 1 -> altaJugador();
                case 2 -> bajaJugador();
                case 3 -> modificarJugador();
                case 4 -> listadoPorId();
                case 5 -> listadoGeneral();
                case 6 -> configurarAlmacenamiento();
                case 7 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 7);
    }

    /**
     * Método que muestra un submenú para seleccionar el tipo de almacenamiento 
     * de jugadores 
     * 
     * @throws IOException Si ocurre un error al configurar el almacenamiento.
     */
    private void configurarAlmacenamiento() throws IOException {
        System.out.println("\n--- Configuración de Almacenamiento ---");
        System.out.println("1. Fichero de texto ");
        System.out.println("2. Fichero binario ");
        System.out.println("3. Fichero de objetos ");
        System.out.println("4. Fichero de acceso aleatorio ");
        System.out.println("5. Fichero XML ");
        System.out.print("Seleccione el tipo de almacenamiento: ");

        int opcion = scanner.nextInt();
        switch (opcion) {
            case 1:
                jugadorDAO = new JugadorTextoDAO();
                break;
            case 2:
                jugadorDAO = new JugadorBinarioDAO();
                break;
            case 3:
                jugadorDAO = new JugadorObjetoDAO();
                break;
            case 4:
                jugadorDAO = new JugadorAleatorioDAO();
                break;
            case 5:
                jugadorDAO = new JugadorXMLDAO();
                break;
            default:
                System.out.println("Opción inválida. Se mantendrá el almacenamiento actual.");
                break;
        }
        System.out.println("Configuración de almacenamiento actualizada.");
    }

    /**
     * Método para registrar un nuevo jugador en el sistema.
     * 
     * @throws Exception Si ocurre un error al agregar el jugador.
     */
    private void altaJugador() throws Exception {
        File archivo = new File("jugadores.txt");
        int id = 0, idmayor = 0;
        String nick;
        if (!archivo.exists()) {
            archivo.createNewFile();
        }
        List<Jugador> lista = jugadorDAO.listarJugadores();
        int tamaño = lista.size();
        if (tamaño != 0) {
            for (Jugador jugador : lista) {
                if (jugador.getId() > idmayor) {
                    id = jugador.getId();
                }
            }
            id++;
        } else {
            id = 0;
        }

        do {
            System.out.print("Ingrese el Nick del jugador: ");
            nick = scanner.nextLine();
        } while (nick.length() < 1);

        System.out.print("Ingrese la experiencia del jugador: ");
        int experience = compruebaNumero();
        System.out.print("Ingrese el nivel de vida del jugador: ");
        int lifeLevel = compruebaNumero();
        System.out.print("Ingrese la cantidad de monedas del jugador: ");
        int coins = compruebaNumero();

        Jugador nuevoJugador = new Jugador(id, nick, experience, lifeLevel, coins);
        try {
            jugadorDAO.añadirJugador(nuevoJugador);
            System.out.println("Jugador agregado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al agregar el jugador: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    /**
     * Método para eliminar un jugador  según su id
     */
    private void bajaJugador() {
        System.out.print("Ingrese el ID del jugador a eliminar: ");
        int id = scanner.nextInt();
        try {
            if (jugadorDAO.buscarPorID(id) == null) {
                System.out.println("No existe el jugador con id:" + id);
            } else {
                jugadorDAO.eliminarJugador(id);
                System.out.println("El jugador con id:" + id + " se ha eliminado correctamente.");
            }
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    /**
     * Método para modificar los datos de un jugador existente por su id
     */
    private void modificarJugador() {
        System.out.print("Ingrese el ID del jugador a modificar: ");
        int id = compruebaNumero();
        scanner.nextLine(); // Consumir el salto de línea

        try {
            Jugador jugador = jugadorDAO.buscarPorID(id);
            if (jugador != null) {
                System.out.println("Jugador encontrado: " + jugador);

                System.out.print("Nuevo Nick (actual: " + jugador.getNick() + "): ");
                String nick = scanner.nextLine();
                if (!nick.isEmpty()) {
                    jugador.setNick(nick);
                }

                System.out.print("Nueva experiencia (actual: " + jugador.getExperience() + "): ");
                int expInput = compruebaNumero();
                jugador.setExperience(expInput);

                System.out.print("Nuevo nivel de vida (actual: " + jugador.getLifeLevel() + "): ");
                int lifeInput = compruebaNumero();
                jugador.setLifeLevel(lifeInput);

                System.out.print("Nueva cantidad de monedas (actual: " + jugador.getCoins() + "): ");
                int coinsInput = compruebaNumero();
                jugador.setCoins(coinsInput);

                jugadorDAO.modificarJugador(jugador);
                System.out.println("Jugador modificado correctamente.");
            } else {
                System.out.println("Jugador con ID " + id + " no encontrado.");
            }
        } catch (IOException e) {
            System.out.println("Error al modificar el jugador: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    /**
     * Método para mostar la info de un jugador según su id.
     */
    private void listadoPorId() {
        System.out.print("Ingrese el ID del jugador a consultar: ");
        int id = compruebaNumero();
        try {
            Jugador jugador = jugadorDAO.buscarPorID(id);
            if (jugador != null) {
                System.out.println("Datos del jugador: " + jugador);
            } else {
                System.out.println("Jugador con ID " + id + " no encontrado.");
            }
        } catch (IOException e) {
            System.out.println("Error al listar el jugador: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    /**
     * Método para listar todos los jugadores
     */
    private void listadoGeneral() {
        try {
            List<Jugador> jugadores = jugadorDAO.listarJugadores();
            System.out.println("\n--- Listado de Jugadores ---");
            for (Jugador jugador : jugadores) {
                System.out.println(jugador);
            }
        } catch (IOException e) {
            System.out.println("Error al listar jugadores: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    /**
     * Método que valida si la entrada es un número válido mayor o igual a 0 y comprueba que sea un int.
     * 
     * @return Un número int válido ingresado por el usuario.
     */
    public static int compruebaNumero() {
        int num;
        Scanner t = new Scanner(System.in);
        do {
            while (!t.hasNextInt()) {
                System.out.println("Por favor, introduce un número válido.");
                t.nextLine(); // Limpiar el buffer del scanner
            }
            num = t.nextInt();
            t.nextLine();
            if (num < 0) {
                System.out.println("El número debe ser mayor que 0.");
            }
        } while (num < 0);
        return num;
    }
}
