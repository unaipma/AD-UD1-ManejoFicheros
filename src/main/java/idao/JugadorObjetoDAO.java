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
 * 
 * @author Vespertino
 */
public class JugadorObjetoDAO extends JugadorDao {

    private final String filename = "jugadoresobj.dat";//String del nombre archivo donde se guardan los jugadores
    File archivo = new File("jugadoresobj.dat");//File de donde se guardan  los jugadores

    /**
     * COnstructor que crea el archivo si no existe
     * 
     * @throws IOException si hay un fallo.
     */
    public JugadorObjetoDAO() throws IOException {
        if (!archivo.exists()) {
            archivo.createNewFile();
        }
    }

    /**
     * Añade un nuevo jugador al archivo binario .dat.
     * 
     * @param jugador El jugador que se desea añadir.
     * @throws IOException si hay un fallo.
     */
    @Override
    public void añadirJugador(Jugador jugador) throws IOException {
        List<Jugador> jugadores = listarJugadores();
        jugadores.add(jugador);
        saveAllJugadores(jugadores);
    }

    /**
     * Elimina un jugador del archivo binario según su id.
     * 
     * @param id El ID del jugador a eliminar (int).
     * @throws IOExceptionsi hay un fallo.
     */
    @Override
    public void eliminarJugador(int id) throws IOException {
        List<Jugador> jugadores = listarJugadores();
        jugadores.removeIf(jugador -> jugador.getId() == id);
        saveAllJugadores(jugadores);
    }

    /**
     * Modifica un jugador.
     * 
     * @param jugador el jugador con la información actualizada.
     * @throws IOException si hay un fallo.
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
        saveAllJugadores(jugadores);
    }

    /**
     * Busca un jugador por su ID.
     * 
     * @param id El ID del jugador a buscar.
     * @return el jugador si lo encuentra (Jugador) o null
     * @throws IOException si hay un fallo.
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
     * Lista todos los jugadores 
     * 
     * @return Una lista de jugadores (List<Jugador>).
     * @throws IOException si hay un fallo.
     */
    @Override
    public List<Jugador> listarJugadores() throws IOException {
        List<Jugador> jugadores = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            jugadores = (List<Jugador>) ois.readObject();
        } catch (EOFException | ClassNotFoundException ignored) {} // Se ignora cuando no hay más datos para leer
        return jugadores;
    }

    /**
     * Guarda todos los jugadores en el archivo binario .dat.
     * 
     * @param jugadores La lista de jugadores que se va a guardar(List<jugador>).
     * @throws IOException si hay un fallos.
     */
    private void saveAllJugadores(List<Jugador> jugadores) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(jugadores);
        }
    }
}
