/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;


/**
 * Clase que representa a un Jugador .
 * 
 * @author Vespertino
 */
public class Jugador implements Serializable {
    private int id;
    private String nick;
    private int experience;
    private int lifeLevel;
    private int coins;

    /**
     * Constructor completo para inicializar un jugador con todos sus atributos.
     * 
     * @param id El ID único del jugador.
     * @param nick El apodo del jugador.
     * @param experience La experiencia acumulada del jugador.
     * @param lifeLevel El nivel de vida del jugador.
     * @param coins La cantidad de monedas del jugador.
     */
    public Jugador(int id, String nick, int experience, int lifeLevel, int coins) {
        this.id = id;
        this.nick = nick;
        this.experience = experience;
        this.lifeLevel = lifeLevel;
        this.coins = coins;
    }

    /**
     * Constructor por defecto que crea un jugador sin inicializar sus atributos.
     */
    public Jugador() {
    }

    /**
     * Obtiene el ID del jugador.
     * 
     * @return El ID del jugadorv(int).
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID del jugador.
     * 
     * @param id El ID que va a tener el jugador(int).
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el apodo (Nick) del jugador.
     * 
     * @return El Nick del jugado(String).
     */
    public String getNick() {
        return nick;
    }

    /**
     * Establece el apodo (Nick) del jugador.
     * 
     * @param nick El apodo que va a tener el jugador(String).
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Obtiene la experiencia del jugador.
     * 
     * @return La experiencia del jugador(int).
     */
    public int getExperience() {
        return experience;
    }

    /**
     * Establece la experiencia del jugador.
     * 
     * @param experience La experiencia que va a tener el jugador(int).
     */
    public void setExperience(int experience) {
        this.experience = experience;
    }

    /**
     * Obtiene el nivel de vida del jugador.
     * 
     * @return El nivel de vida del jugador.
     */
    public int getLifeLevel() {
        return lifeLevel;
    }

    /**
     * Establece el nivel de vida del jugador.
     * 
     * @param lifeLevel El nivel de vida que va a tener el jugador(int).
     */
    public void setLifeLevel(int lifeLevel) {
        this.lifeLevel = lifeLevel;
    }

    /**
     * Obtiene la cantidad de monedas del jugador.
     * 
     * @return La cantidad de monedas del jugador(int).
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Establece la cantidad de monedas del jugador.
     * 
     * @param coins Las monedas que va a tener el jugador(int).
     */
    public void setCoins(int coins) {
        this.coins = coins;
    }

    /**
     *Muestra la información del jugador
     * 
     * @return Una cadena de texto con todos los atributos del jugador(Strings).
     */
    @Override
    public String toString() {
        return "Jugador [ID=" + id + ", Nick=" + nick + ", Experience=" + experience 
               + ", Life Level=" + lifeLevel + ", Coins=" + coins + "]";
    }
}