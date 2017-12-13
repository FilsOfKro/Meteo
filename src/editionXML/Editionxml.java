package editionXML;

import java.io.File;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import model.Grille;
import model.Prevision;
import model.PrevisionParDate;
import model.Vent;

import org.w3c.dom.Document;
import org.w3c.dom.Element;



public class Editionxml {

  protected Grille zonePrevision;
  protected Prevision prevision;
  protected PrevisionParDate previsionDate;
  protected Date datefin;

  /**
   * Classe pour creer un fichier xml a partir d'une prevision.
   */
  public Editionxml(Grille zonePrevision, Prevision prevision, 
      PrevisionParDate previsionDate, Date datefin) {
    this.zonePrevision = zonePrevision;
    this.prevision = prevision;
    this.previsionDate = previsionDate;
    this.datefin = datefin;

    final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    try {
      final DocumentBuilder builder = factory.newDocumentBuilder();
      final Document document = builder.newDocument();
      final Element zone = document.createElement("zone");
      document.appendChild(zone);
      final Element point1X = document.createElement("point1X");
      point1X.appendChild(document.createTextNode("" + zonePrevision.getLonHG()));

      final Element point1Y = document.createElement("point1Y");
      point1Y.appendChild(document.createTextNode("" + zonePrevision.getLatHG()));

      final Element nombreX = document.createElement("nombreX");
      nombreX.appendChild(document.createTextNode("" + zonePrevision.getNbx()));

      final Element nombreY = document.createElement("nombreY");
      nombreY.appendChild(document.createTextNode("" + zonePrevision.getNby()));

      final Element deltaX = document.createElement("deltaX");
      deltaX.appendChild(document.createTextNode("" + zonePrevision.getDx()));

      final Element deltaY = document.createElement("deltaY");
      deltaY.appendChild(document.createTextNode("" + zonePrevision.getDy()));

      zone.appendChild(point1X);
      zone.appendChild(point1Y);
      zone.appendChild(nombreX);
      zone.appendChild(nombreY);
      zone.appendChild(deltaX);
      zone.appendChild(deltaY);

      // boucle
      int i = 1;
      for (Vent[] tabVent : previsionDate.getVents()) {
        for (Vent vent : tabVent) {
          if (vent != null) {

            final Element nom_vent = document.createElement("vent");
            nom_vent.setAttribute("numero", "" + i++);
            zone.appendChild(nom_vent);

            final Element direction = document.createElement("direction");
            direction.appendChild(document.createTextNode("" + vent.getDirection()));

            final Element vitesse = document.createElement("vitesse");
            vitesse.appendChild(document.createTextNode("" + vent.getVitesse()));

            final Element date_debut = document.createElement("date_debut");
            date_debut.appendChild(document.createTextNode("" + previsionDate.getDate()));

            final Element date_fin = document.createElement("date_fin");
            date_fin.appendChild(document.createTextNode("" + datefin));

            nom_vent.appendChild(direction);
            nom_vent.appendChild(vitesse);
            nom_vent.appendChild(date_debut);
            nom_vent.appendChild(date_fin);

          }
        }
      }

      // affichage
      final TransformerFactory transformerFactory = TransformerFactory.newInstance();
      final Transformer transformer = transformerFactory.newTransformer();
      final DOMSource source = new DOMSource(document);
      final StreamResult sortie = new StreamResult(new File("file.xml"));
      // final StreamResult result = new StreamResult(System.out);

      // prologue
      transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
      transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
      transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

      // formatage
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

      // sortie
      transformer.transform(source, sortie);
    } catch (final ParserConfigurationException e) {
      e.printStackTrace();
    } catch (TransformerConfigurationException e) {
      e.printStackTrace();
    } catch (TransformerException e) {
      e.printStackTrace();
    }
  }

}
