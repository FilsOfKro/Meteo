package editionXML;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Classe pour lire un fichier xml.
 * @author Adrien
 *
 */
public class Lirexml {

  public Lirexml() {
    /*
     * Etape 1 : récupération d'une instance de la classe "DocumentBuilderFactory"
     */
    final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    try {
      /*
       * Etape 2 : création d'un parseur
       */
      final DocumentBuilder builder = factory.newDocumentBuilder();

      /*
       * Etape 3 : création d'un Document
       */
      final Document document = builder.parse(new File("file.xml"));

      /*
       * Etape 4 : récupération de l'Element racine
       */
      final Element racine = document.getDocumentElement();

      // Affichage des donnees
      System.out.println("*************DONNEES************");
      System.out.println(racine.getTextContent());

    } catch (final ParserConfigurationException e) {
      e.printStackTrace();
    } catch (final SAXException e) {
      e.printStackTrace();
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }
}
