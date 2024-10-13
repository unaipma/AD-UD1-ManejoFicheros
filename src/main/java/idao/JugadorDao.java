/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package idao;

import java.util.List;
import modelo.Jugador;

/**
 * 
 * 
 * @author Vespertino
 */
public abstract class JugadorDao {
    
    /**
     * Añade un nuevo jugador.
     * 
     * @param jugador El jugador que se va a añadir(Jugador).
     * @throws Exception si hay algún fallo.
     */
    public abstract void añadirJugador(Jugador jugador) throws Exception;
    
    /**
     * Elimina un jugador buscandolo por su id.
     * 
     * @param id El ID del jugador que se desea eliminar(int).
     * @throws Exception si hay algún fallo.
     */
    public abstract void eliminarJugador(int id) throws Exception;
    
    /**
     * Modifica la información de un jugador 
     * 
     * @param jugador El jugador con la info actualizada(Jugador)
     * @throws Exception  si hay algún fallo.
     */
    public abstract void modificarJugador(Jugador jugador) throws Exception;
    
    /**
     * Busca un jugador por su ID.
     * 
     * @param id El ID del jugador que se desea buscar(int).
     * @return El jugador correspondiente(Jugador).
     * @throws Exception  si hay algún fallo.
     */
    public abstract Jugador buscarPorID(int id) throws Exception;
    
    /**
     * Lista todos los jugadores
     * 
     * @return Una lista de jugadores (List<Jugador>).
     * @throws Exception  si hay algún fallo.
     */
    public abstract List<Jugador> listarJugadores() throws Exception;
    
}

