/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package idao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import modelo.Jugador;

/**
 * 
 *
 * @author Vespertino
 */
public class JugadorTextoDAO extends JugadorDao {
    private final String filename = "jugadores.txt";//String del nombre archivo donde se guardan los jugadores
    File archivo = new File("jugadores.txt");//archivo File del archivo txt

    /**
     * Si el archvo txt no existe se crea
     *
     * @throws IOException Si ocurre un error al crear el archivo.
     */
    public JugadorTextoDAO() throws IOException {
        if (!archivo.exists()) {
            archivo.createNewFile();
        }
    }

    /**
     * Añade un nuevo jugador al txt.
     *
     * @param jugador El jugador a añadir.
     * @throws IOException Si ocurre un problema.
     */
    @Override
    public void añadirJugador(Jugador jugador) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(jugadorToTexto(jugador) + "\n");
        }
    }

    /**
     * Elimina un jugador del txt buscandolo por su ID.
     *
     * @param id El ID del jugador a eliminar.
     * @throws IOException Si ocurre un problema.
     */
    @Override
    public void eliminarJugador(int id) throws IOException {
        List<Jugador> jugadores = listarJugadores();
        jugadores.removeIf(jugador -> jugador.getId() == id);
        guardarTXT(jugadores);
    }

    /**
     * Modifica los datos de un jugador.
     *
     * @param jugador El jugador con los datos actualizados.
     * @throws IOException Si ocurre un error al modificar el jugador.
     */
    @Override
    public void modificarJugador(Jugador jugador) throws IOException {
        List<Jugador> jugadores = listarJugadores();
        for (Jugador jugadore : jugadores) {
            if (jugadore.getId() == jugador.getId()) {
                jugadores.set(jugadore.getId(), jugador);
                break;
            }
        }
        guardarTXT(jugadores);
    }

    /**
     * Busca el jugador en el txt por su ID.
     *
     * @param id El ID del jugador a buscar.
     * @return El jugador encontrado o null si no existe(Jugador).
     * @throws IOException si hay algun problema.
     */
    @Override
    public Jugador buscarPorID(int id) throws IOException {
        List<Jugador> jugadores = listarJugadores();
        for (Jugador jugador : jugadores) {
            if (jugador.getId() == id) {
                return jugador;
            }
        }
        return null;
    }

    /**
     * Lista todos los jugadores del txt.
     *
     * @return Una lista de todos los jugadores(List).
     * @throws IOException si hay algún fallo.
     */
    @Override
    public List<Jugador> listarJugadores() throws IOException {
        List<Jugador> jugadores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jugadores.add(txtJugador(line));
            }
        }
        return jugadores;
    }

    /**
     * Convierte un objeto Jugador a un String.
     *
     * @param jugador el jugador deseadp.
     * @return un string con la info del jugador(String).
     */
    private String jugadorToTexto(Jugador jugador) {
        return "[USER_ID=" + jugador.getId() + ", NICK_NAME=" + jugador.getNick() + ", EXPERIENCE=" + jugador.getExperience()
                + ", LIFE_LEVEL=" + jugador.getLifeLevel() + ", COINS=" + jugador.getCoins() + "]";
    }

    /**
     * Convierte una línea de texto en un Jugador.
     *
     * @param line La línea que representa a un jugador.
     * @return El objeto Jugador correspondiente(Jugador).
     */
    private Jugador txtJugador(String line) {
        String[] parts = line.replace("[", "").replace("]", "").split(", ");
        int id = Integer.parseInt(parts[0].split("=")[1]);
        String nick = parts[1].split("=")[1];
        int experience = Integer.parseInt(parts[2].split("=")[1]);
        int lifeLevel = Integer.parseInt(parts[3].split("=")[1]);
        int coins = Integer.parseInt(parts[4].split("=")[1]);
        return new Jugador(id, nick, experience, lifeLevel, coins);
    }

    /**
     * Guarda todos los jugadores en eltxt.
     *
     * @param jugadores la lista de jugadores.
     * @throws IOException si hay algún error.
     */
    private void guardarTXT(List<Jugador> jugadores) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Jugador jugador : jugadores) {
                writer.write(jugadorToTexto(jugador) + "\n");
            }
        }
    }
}