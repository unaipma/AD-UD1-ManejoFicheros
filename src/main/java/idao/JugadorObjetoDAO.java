/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package idao;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import modelo.Jugador;

/**
 *
 * @author Vespertino
 */
public class JugadorObjetoDAO extends JugadorDao {

    private final String filename = "jugadoresobj.dat";
    File archivo = new File("jugadoresobj.dat");

    public JugadorObjetoDAO() throws IOException {
        if (!archivo.exists()) {
            archivo.createNewFile();
        }
    }

    @Override
    public void addJugador(Jugador jugador) throws IOException {

        List<Jugador> jugadores = getAllJugadores();
        jugadores.add(jugador);
        saveAllJugadores(jugadores);
    }

    @Override
    public void deleteJugador(int id) throws IOException {

        List<Jugador> jugadores = getAllJugadores();
        jugadores.removeIf(jugador -> jugador.getId() == id);
        saveAllJugadores(jugadores);
    }

    @Override
    public void modifyJugador(Jugador jugador) throws IOException {
        List<Jugador> jugadores = getAllJugadores();
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getId() == jugador.getId()) {
                jugadores.set(i, jugador);
                break;
            }
        }
        saveAllJugadores(jugadores);

    }

    @Override
    public Jugador getJugadorById(int id) throws IOException {
        
        List<Jugador> jugadores = getAllJugadores();
        for (Jugador jugador : jugadores) {
            if (jugador.getId() == id) {
                return jugador;
            }
        }
        return null;
    }

    @Override
    public List<Jugador> getAllJugadores() throws IOException {
        
        List<Jugador> jugadores = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            jugadores = (List<Jugador>) ois.readObject();
        } catch (EOFException | ClassNotFoundException ignored) {
            // EOFException occurs when there are no more objects to read
        }
        return jugadores;
    }

    // Guardar todos los jugadores en el fichero binario de objetos
    private void saveAllJugadores(List<Jugador> jugadores) throws IOException {
       
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(jugadores);
        }
    }
}
