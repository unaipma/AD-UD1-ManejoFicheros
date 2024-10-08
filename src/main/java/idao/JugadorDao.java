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
    public abstract void addJugador(Jugador jugador) throws Exception;
    public abstract void deleteJugador(int id) throws Exception;
    public abstract void modifyJugador(Jugador jugador) throws Exception;
    public abstract Jugador getJugadorById(int id) throws Exception;
    public abstract List<Jugador> getAllJugadores() throws Exception;
    
}
