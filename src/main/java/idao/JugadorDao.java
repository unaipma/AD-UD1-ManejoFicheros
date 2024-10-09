/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package idao;

import java.util.List;
import modelo.Jugador;

/**
 *
 * @author Vespertino
 */
public abstract class JugadorDao {
    // Definición de los métodos CRUD abstractos
    public abstract void añadirJugador(Jugador jugador) throws Exception;
    public abstract void eliminarJugador(int id) throws Exception;
    public abstract void modificarJugador(Jugador jugador) throws Exception;
    public abstract Jugador buscarPorID(int id) throws Exception;
    public abstract List<Jugador> listarJugadores() throws Exception;
    
}
