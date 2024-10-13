/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package idao;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import modelo.Jugador;

/**
 * 
 *
 * @author Vespertino
 */
public class JugadorXMLDAO extends JugadorDao {
    private static final String FICHERO_XML = "jugadores.xml";//archivo donde se guardan los jugadores

    /**
     * Añade un nuevo jugador al archivo XML.
     *
     * @param jugador el jugador que se añade.
     * @throws IOException en caso de que haya algún problema cuando se añade.
     */
    @Override
    public void añadirJugador(Jugador jugador) throws IOException {
        try {
            DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = fabrica.newDocumentBuilder();
            
            File archxml = new File(FICHERO_XML);
            Document doc= builder.parse(archxml);

            // creo el elemento/objeto jugador en el xml
            Element jugadorElement = doc.createElement("jugador");
            jugadorElement.setAttribute("id", String.valueOf(jugador.getId()));

            Element nick = doc.createElement("nick");
            nick.setTextContent(jugador.getNick());
            jugadorElement.appendChild(nick);

            Element experience = doc.createElement("experience");
            experience.setTextContent(String.valueOf(jugador.getExperience()));
            jugadorElement.appendChild(experience);

            Element lifeLevel = doc.createElement("life_level");
            lifeLevel.setTextContent(String.valueOf(jugador.getLifeLevel()));
            jugadorElement.appendChild(lifeLevel);

            Element coins = doc.createElement("coins");
            coins.setTextContent(String.valueOf(jugador.getCoins()));
            jugadorElement.appendChild(coins);

            // se añade el jugador
            doc.getDocumentElement().appendChild(jugadorElement);

            // Guardar el documento XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("jugadores.xml"));
            transformer.transform(source, result);

        } catch (Exception e) {
            throw new IOException("Error al agregar jugador al fichero XML", e);
        }
    }

    /**
     * Elimina un jugador del archivo XML según su ID.
     *
     * @param id el ID del jugador a eliminar.
     * @throws Exception por si hay algun problema.
     */
    @Override
    public void eliminarJugador(int id) throws Exception {
        Document doc = getDocument();
        NodeList jugadores = doc.getElementsByTagName("jugador");//obtengo los jugadores del xml
        
        for (int i = 0; i < jugadores.getLength(); i++) {//recorro la lista
            Element jugadorElement = (Element) jugadores.item(i);
            if (Integer.parseInt(jugadorElement.getAttribute("id")) == id) {//si es el id deseado se elimina
                jugadorElement.getParentNode().removeChild(jugadorElement);
                break;
            }
        }
        
        saveDocument(doc);
    }

    /**
     * Modifica los datos de un jugador machacando el antiguo y creo uno nuevo.
     *
     * @param jugador El jugador con los datos actualizados.
     * @throws Exception por si hay algún problema al eliminar el jugador.
     */
    @Override
    public void modificarJugador(Jugador jugador) throws Exception {
        eliminarJugador(jugador.getId());
        añadirJugador(jugador);
    }

    /**
     * Busca un jugador en el archivo XML por su ID.
     *
     * @param id El ID del jugador a buscar.
     * @return El jugador encontrado o null si no existe(Jugador).
     * @throws Exception por si hay algun problema al buscar el jugador.
     */
    @Override
    public Jugador buscarPorID(int id) throws Exception {
        Document doc = getDocument();
        NodeList jugadores = doc.getElementsByTagName("jugador");
        
        for (int i = 0; i < jugadores.getLength(); i++) {
            Element jugadorElement = (Element) jugadores.item(i);
            if (Integer.parseInt(jugadorElement.getAttribute("id")) == id) {
                return parseJugadorElement(jugadorElement);
            }
        }
        
        return null;
    }

    /**
     * Lista todos los jugadores almacenados en el archivo XML.
     *
     * @return Una lista de todos los jugadores(List).
     * @throws Exception Si ocurre un error al listar los jugadores.
     */
    @Override
    public List<Jugador> listarJugadores() throws Exception {
        List<Jugador> jugadores = new ArrayList<>();
        Document doc = getDocument();
        NodeList jugadoresList = doc.getElementsByTagName("jugador");
        
        for (int i = 0; i < jugadoresList.getLength(); i++) {
            Element jugadorElement = (Element) jugadoresList.item(i);
            jugadores.add(parseJugadorElement(jugadorElement));
        }
        
        return jugadores;
    }

    /**
     * Obtiene el documento XML para manipularlo.
     * Si el archivo XML no existe, lo crea.
     *
     * @return El Document parseado xml.
     * @throws Exception por si ocurre algún fallo al crear el archivo.
     */
    private Document getDocument() throws Exception {
        DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = fabrica.newDocumentBuilder();
        
        File xmlFile = new File(FICHERO_XML);
        if (!xmlFile.exists()) {
            Document doc = builder.newDocument();
            Element root = doc.createElement("jugadores");
            doc.appendChild(root);
            saveDocument(doc);
        }
        
        return builder.parse(xmlFile);
    }

    /**
     * Guarda la info en un xml.
     *
     * @param doc El objeto Document que representa el archivo XML actualizado.
     * @throws TransformerException Si ocurre un error al guardar el documento XML.
     */
    private void saveDocument(Document doc) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(FICHERO_XML));
        transformer.transform(source, result);
    }

    /**
     * Convierte un elemento XML en un objeto Jugador.
     *
     * @param jugadorElement el objeto XML que representa un jugador.
     * @return El Jugador correspondiente.
     */
    private Jugador parseJugadorElement(Element jugadorElement) {
        int id = Integer.parseInt(jugadorElement.getAttribute("id"));
        String nick = jugadorElement.getElementsByTagName("nick").item(0).getTextContent();
        int experience = Integer.parseInt(jugadorElement.getElementsByTagName("experience").item(0).getTextContent());
        int lifeLevel = Integer.parseInt(jugadorElement.getElementsByTagName("life_level").item(0).getTextContent());
        int coins = Integer.parseInt(jugadorElement.getElementsByTagName("coins").item(0).getTextContent());

        return new Jugador(id, nick, experience, lifeLevel, coins);
    }
}
