/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package idao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import modelo.Jugador;

/**
 *
 * @author Vespertino
 */
public class JugadorBinarioDAO extends JugadorDao {

    private static final String FICHERO_BINARIO = "jugadores.dat";
    File archivo = new File("jugadores.dat");

    public JugadorBinarioDAO() throws IOException {
        if (!archivo.exists()) {
            archivo.createNewFile();
        }
    }

    @Override
    public void addJugador(Jugador jugador) throws IOException {
        // Abrimos el fichero en modo append (añadir al final).

        try (DataOutputStream datosout = new DataOutputStream(new FileOutputStream(FICHERO_BINARIO, true))) {
            datosout.writeInt(jugador.getId());
            datosout.writeUTF(jugador.getNick());
            datosout.writeInt(jugador.getExperience());
            datosout.writeInt(jugador.getLifeLevel());
            datosout.writeInt(jugador.getCoins());
        }
    }

    @Override
    public void deleteJugador(int id) throws IOException {

        List<Jugador> jugadores = getAllJugadores(); // Leer todos los jugadores
        try (DataOutputStream datosout = new DataOutputStream(new FileOutputStream(FICHERO_BINARIO))) {
            for (Jugador jugador : jugadores) {
                if (jugador.getId() != id) {
                    datosout.writeInt(jugador.getId());
                    datosout.writeUTF(jugador.getNick());
                    datosout.writeInt(jugador.getExperience());
                    datosout.writeInt(jugador.getLifeLevel());
                    datosout.writeInt(jugador.getCoins());
                }
            }
        }
    }

    @Override
    public void modifyJugador(Jugador jugador) throws IOException {
        List<Jugador> jugadores = getAllJugadores(); // Leer todos los jugadores
        try (DataOutputStream datosout = new DataOutputStream(new FileOutputStream(FICHERO_BINARIO))) {
            for (Jugador j : jugadores) {
                if (j.getId() == jugador.getId()) {
                    // Modificar el jugador encontrado
                    datosout.writeInt(jugador.getId());
                    datosout.writeUTF(jugador.getNick());
                    datosout.writeInt(jugador.getExperience());
                    datosout.writeInt(jugador.getLifeLevel());
                    datosout.writeInt(jugador.getCoins());
                } else {
                    // Escribir el resto de los jugadores sin cambios
                    datosout.writeInt(j.getId());
                    datosout.writeUTF(j.getNick());
                    datosout.writeInt(j.getExperience());
                    datosout.writeInt(j.getLifeLevel());
                    datosout.writeInt(j.getCoins());
                }
            }
        }

    }

    @Override
    public Jugador getJugadorById(int id) throws IOException {

        try (DataInputStream datosinput = new DataInputStream(new FileInputStream(FICHERO_BINARIO))) {
            while (datosinput.available() > 0) {
                int jugadorId = datosinput.readInt();
                String nick = datosinput.readUTF();
                int experience = datosinput.readInt();
                int lifeLevel = datosinput.readInt();
                int coins = datosinput.readInt();

                if (jugadorId == id) {
                    return new Jugador(jugadorId, nick, experience, lifeLevel, coins);
                }
            }
        }
        return null; // Si no se encuentra, retornar null.
    }

    @Override
    public List<Jugador> getAllJugadores() throws IOException {

        List<Jugador> jugadores = new ArrayList<>();
        try (DataInputStream datosinput = new DataInputStream(new FileInputStream(FICHERO_BINARIO))) {
            while (datosinput.available() > 0) {
                int id = datosinput.readInt();
                String nick = datosinput.readUTF();
                int experience = datosinput.readInt();
                int lifeLevel = datosinput.readInt();
                int coins = datosinput.readInt();
                jugadores.add(new Jugador(id, nick, experience, lifeLevel, coins));
            }
        }
        return jugadores;
    }
}
