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
 * @author Vespertino
 */
public class JugadorTextoDAO extends JugadorDao {

    private final String filename = "jugadores.txt";
    File archivo = new File("jugadores.txt");

    public JugadorTextoDAO() throws IOException {
        if (!archivo.exists()) {
            archivo.createNewFile();
        }
    }

    @Override
    public void añadirJugador(Jugador jugador) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(jugadorToLine(jugador) + "\n");
        }
    }

    @Override
    public void eliminarJugador(int id) throws IOException {

        List<Jugador> jugadores = listarJugadores();
        jugadores.removeIf(jugador -> jugador.getId() == id);
        saveAllJugadores(jugadores);
    }

    @Override
    public void modificarJugador(Jugador jugador) throws IOException {
        List<Jugador> jugadores = listarJugadores();
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getId() == jugador.getId()) {
                jugadores.set(i, jugador);
                break;
            }
        }
        saveAllJugadores(jugadores);

    }

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

    @Override
    public List<Jugador> listarJugadores() throws IOException {
        
        List<Jugador> jugadores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jugadores.add(lineToJugador(line));
            }
        }
        return jugadores;
    }

    // Convertir un objeto Jugador a una línea de texto
    private String jugadorToLine(Jugador jugador) {
        return "[USER_ID=" + jugador.getId() + ", NICK_NAME=" + jugador.getNick() + ", EXPERIENCE=" + jugador.getExperience()
                + ", LIFE_LEVEL=" + jugador.getLifeLevel() + ", COINS=" + jugador.getCoins() + "]";
    }

    // Convertir una línea de texto a un objeto Jugador
    private Jugador lineToJugador(String line) {
        String[] parts = line.replace("[", "").replace("]", "").split(", ");
        int id = Integer.parseInt(parts[0].split("=")[1]);
        String nick = parts[1].split("=")[1];
        int experience = Integer.parseInt(parts[2].split("=")[1]);
        int lifeLevel = Integer.parseInt(parts[3].split("=")[1]);
        int coins = Integer.parseInt(parts[4].split("=")[1]);
        return new Jugador(id, nick, experience, lifeLevel, coins);
    }

    // Guardar todos los jugadores en el fichero
    private void saveAllJugadores(List<Jugador> jugadores) throws IOException {
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Jugador jugador : jugadores) {
                writer.write(jugadorToLine(jugador) + "\n");
            }
        }
    }
}
