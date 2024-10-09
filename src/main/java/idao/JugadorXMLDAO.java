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
 * @author Vespertino
 */
public class JugadorXMLDAO extends JugadorDao {
    private static final String FICHERO_XML = "jugadores.xml";

    @Override
    public void añadirJugador(Jugador jugador) throws IOException {
        try {
            // Crear un nuevo documento o cargar el existente
            DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = fabrica.newDocumentBuilder();
            Document doc;
            File archxml = new File(FICHERO_XML);
            if (archxml.exists()) {
                doc = builder.parse(archxml);
            } else {
                doc = builder.newDocument();
                Element rootElement = doc.createElement("jugadores");
                doc.appendChild(rootElement);
            }

            // Crear un nuevo elemento jugador
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

            // Añadir el jugador al documento
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

    @Override
    public void eliminarJugador(int id) throws Exception {
        Document doc = getDocument();
        NodeList jugadores = doc.getElementsByTagName("jugador");
        
        for (int i = 0; i < jugadores.getLength(); i++) {
            Element jugadorElement = (Element) jugadores.item(i);
            if (Integer.parseInt(jugadorElement.getAttribute("id")) == id) {
                jugadorElement.getParentNode().removeChild(jugadorElement);
                break;
            }
        }
        
        saveDocument(doc);
    }

    @Override
    public void modificarJugador(Jugador jugador) throws Exception {
        eliminarJugador(jugador.getId());
        añadirJugador(jugador);
    }

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

    // Crear el objeto Document para manipular el XML
    private Document getDocument() throws Exception {
        DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = fabrica.newDocumentBuilder();
        
        File xmlFile = new File("jugadores.xml");
        if (!xmlFile.exists()) {
            Document doc = builder.newDocument();
            Element root = doc.createElement("jugadores");
            doc.appendChild(root);
            saveDocument(doc);
        }
        
        return builder.parse(xmlFile);
    }

    // Crear un elemento jugador en XML
    private Element createJugadorElement(Document doc, Jugador jugador) {
        Element jugadorElement = doc.createElement("jugador");
        jugadorElement.setAttribute("id", String.valueOf(jugador.getId()));

        Element nickElement = doc.createElement("nick");
        nickElement.appendChild(doc.createTextNode(jugador.getNick()));
        
        Element experienceElement = doc.createElement("experience");
        experienceElement.appendChild(doc.createTextNode(String.valueOf(jugador.getExperience())));
        
        Element lifeLevelElement = doc.createElement("life_level");
        lifeLevelElement.appendChild(doc.createTextNode(String.valueOf(jugador.getLifeLevel())));
        
        Element coinsElement = doc.createElement("coins");
        coinsElement.appendChild(doc.createTextNode(String.valueOf(jugador.getCoins())));
        
        jugadorElement.appendChild(nickElement);
        jugadorElement.appendChild(experienceElement);
        jugadorElement.appendChild(lifeLevelElement);
        jugadorElement.appendChild(coinsElement);
        
        return jugadorElement;
    }

    // Guardar el documento XML en un archivo
    private void saveDocument(Document doc) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("jugadores.xml"));
        transformer.transform(source, result);
    }

    // Convertir un elemento XML a un objeto Jugador
    private Jugador parseJugadorElement(Element jugadorElement) {
        int id = Integer.parseInt(jugadorElement.getAttribute("id"));
        String nick = jugadorElement.getElementsByTagName("nick").item(0).getTextContent();
        int experience = Integer.parseInt(jugadorElement.getElementsByTagName("experience").item(0).getTextContent());
        int lifeLevel = Integer.parseInt(jugadorElement.getElementsByTagName("life_level").item(0).getTextContent());
        int coins = Integer.parseInt(jugadorElement.getElementsByTagName("coins").item(0).getTextContent());

        return new Jugador(id, nick, experience, lifeLevel, coins);
    }
}
