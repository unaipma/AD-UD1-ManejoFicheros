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

    private final String filename = "jugadorerd.dat";
    File archivo = new File("jugadorerd.dat");

    public JugadorAleatorioDAO() throws IOException {
        if (!archivo.exists()) {
            archivo.createNewFile();
        }
    }

    @Override
    public void añadirJugador(Jugador jugador) throws IOException {

        try (RandomAccessFile rand = new RandomAccessFile(filename, "rw")) {
            rand.seek(rand.length()); // Mueve el puntero al final del archivo
            writeJugadorToFile(rand, jugador);
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
        
        for (Jugador jugadore : jugadores) {
            if (jugadore.getId()==jugador.getId()) {
                jugadores.set(jugadore.getId(), jugador);
            }
        }
         saveAllJugadores(jugadores);

    }

    @Override
    public Jugador buscarPorID(int id) throws IOException {
       
        try (RandomAccessFile rand = new RandomAccessFile(filename, "r")) {
            while (rand.getFilePointer() < rand.length()) {
                int currentId = rand.readInt();
                Jugador jugador = readJugadorFromFile(rand);
                if (currentId == id) {
                    return jugador;
                }
            }
        }
        return null;
    }

    @Override
    public List<Jugador> listarJugadores() throws IOException {
       
        List<Jugador> jugadores = new ArrayList<>();
        try (RandomAccessFile rand = new RandomAccessFile(filename, "r")) {
            while (rand.getFilePointer() < rand.length()) {
                int id = rand.readInt();
                Jugador jugador = readJugadorFromFile(rand);
                jugador.setId(id);
                jugadores.add(jugador);
            }
        }
        return jugadores;
    }

    // Escribir un objeto Jugador en el archivo
    private void writeJugadorToFile(RandomAccessFile raf, Jugador jugador) throws IOException {
        
        raf.writeInt(jugador.getId());
        raf.writeUTF(jugador.getNick()); // Nick con tamaño variable
        raf.writeInt(jugador.getExperience());
        raf.writeInt(jugador.getLifeLevel());
        raf.writeInt(jugador.getCoins());
    }

    // Leer un objeto Jugador del archivo
    private Jugador readJugadorFromFile(RandomAccessFile raf) throws IOException {
        
        String nick = raf.readUTF();
        int experience = raf.readInt();
        int lifeLevel = raf.readInt();
        int coins = raf.readInt();
        return new Jugador(0, nick, experience, lifeLevel, coins);
    }

    // Guardar todos los jugadores en el fichero
    private void saveAllJugadores(List<Jugador> jugadores) throws IOException {
        
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            raf.setLength(0); // Vaciar el archivo antes de guardar todos los jugadores
            for (Jugador jugador : jugadores) {
                writeJugadorToFile(raf, jugador);
            }
        }
    }
}
