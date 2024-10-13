/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package idao;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import modelo.Jugador;


/**
 *
 * @author Vespertino
 */
public class JugadorAleatorioDAO extends JugadorDao {
    private final String filename = "jugadorerd.dat";//nombre del archivo donde se guarda
    File archivo = new File(filename);//el archivo donde se guarda en formato File

    /**
     * Constructor de la clase JugadorAleatorioDAO.Si no existe el archivo lo crea
     * 
     * @throws IOException Si ocurre un error al crear el archivo.
     */
    public JugadorAleatorioDAO() throws IOException {
        if (!archivo.exists()) {
            archivo.createNewFile();
        }
    }

    /**
     * Añade un nuevo jugador al archivo escribiendolo en el archivo.
     * 
     * @param jugador El jugador a añadir(Jugador).
     * @throws IOException Si ocurre un error .
     */
    @Override
    public void añadirJugador(Jugador jugador) throws IOException {
        try (RandomAccessFile rand = new RandomAccessFile(filename, "rw")) {
            rand.seek(rand.length()); // para mover el puntero al final del arch
            writeJugadorToFile(rand, jugador);
        }
    }

    /**
     * Elimina un jugador del archivo .dat.
     * 
     * @param id El ID del jugador a eliminar.
     * @throws IOException Si ocurre un error.
     */
    @Override
    public void eliminarJugador(int id) throws IOException {
        List<Jugador> jugadores = listarJugadores(); 
        jugadores.removeIf(jugador -> jugador.getId() == id); 
        saveAllJugadores(jugadores); 
    }

    /**
     * Modifica los datos de un jugador en el archivo .dat.
     * 
     * @param jugador El jugador con los datos modificados(Jugador).
     * @throws IOException Si ocurre un error .
     */
    @Override
    public void modificarJugador(Jugador jugador) throws IOException {
        List<Jugador> jugadores = listarJugadores(); 
        for (Jugador jugadore : jugadores) {
            if (jugadore.getId() == jugador.getId()) {
                jugadores.set(jugadore.getId(), jugador); 
            }
        }
        saveAllJugadores(jugadores); 
    }

    /**
     * Busca un jugador en el archivo según su ID.
     * 
     * @param id El ID del jugador a buscar(int).
     * @return El jugador encontrado(Jugador), o null si no se encuentra.
     * @throws IOException Si ocurre un error de E/S durante la operación.
     */
    @Override
    public Jugador buscarPorID(int id) throws IOException {
        try (RandomAccessFile rand = new RandomAccessFile(filename, "r")) {
            while (rand.getFilePointer() < rand.length()) {
                int currentId = rand.readInt(); 
                Jugador jugador = leerRandDelArch(rand); 
                if (currentId == id) {
                    return jugador; 
                }
            }
        }
        return null; 
    }

    /**
     * Lista todos los jugadores almacenados en el .dat
     * 
     * @return Una lista de jugadores.
     * @throws IOException Si ocurre un error .
     */
    @Override
    public List<Jugador> listarJugadores() throws IOException {
        List<Jugador> jugadores = new ArrayList<>();
        try (RandomAccessFile rand = new RandomAccessFile(filename, "r")) {
            while (rand.getFilePointer() < rand.length()) {
                int id = rand.readInt();
                Jugador jugador = leerRandDelArch(rand); 
                jugador.setId(id); 
                jugadores.add(jugador); 
            }
        }
        return jugadores; 
    }

    /**
     * Escribe los datos del Jugador en el archivo.
     * 
     * @param raf El RandomAccessFile utilizado para la escritura.
     * @param jugador El jugador (Jugador).
     * @throws IOException Si ocurre un error.
     */
    private void writeJugadorToFile(RandomAccessFile raf, Jugador jugador) throws IOException {
        raf.writeInt(jugador.getId()); // Escribir el ID del jugador
        raf.writeUTF(jugador.getNick()); // Escribir el Nickname del jugador
        raf.writeInt(jugador.getExperience()); // Escribir la experiencia
        raf.writeInt(jugador.getLifeLevel()); // Escribir el nivel de vida
        raf.writeInt(jugador.getCoins()); // Escribir las monedas
    }

    /**
     * Lee un objeto Jugador del archivo.
     * 
     * @param raf El RandomAccessFile utilizado.
     * @return El jugador(Jugador).
     * @throws IOException Si ocurre un error.
     */
    private Jugador leerRandDelArch(RandomAccessFile raf) throws IOException {
        String nick = raf.readUTF(); 
        int experience = raf.readInt(); 
        int lifeLevel = raf.readInt();
        int coins = raf.readInt(); 
        return new Jugador(0, nick, experience, lifeLevel, coins);
    }

    /**
     * Guarda todos los jugadores en el archivo, borrando y escribiendo todo cada vez que se ejecuta.
     * 
     * @param jugadores Lista de jugadores a guardar(List<Jugador>).
     * @throws IOException Si ocurre un error.
     */
    private void saveAllJugadores(List<Jugador> jugadores) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            raf.setLength(0); //aquí se borra todo
            for (Jugador jugador : jugadores) {
                writeJugadorToFile(raf, jugador); 
            }
        }
    }
}