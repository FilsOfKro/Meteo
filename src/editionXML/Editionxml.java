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
import model.PrevisionParDate;
import model.Vent;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Editionxml {

  protected PrevisionParDate previsionDate;
  protected Date datefin;

  /**
   * Classe pour creer un fichier xml a partir d'une prevision.
   */
  public static void sauvergarderXML(Grille aZonePrevision, PrevisionParDate aPrevisionDate,
      String path) {
    Grille grille = aZonePrevision;
    PrevisionParDate previsionDate = aPrevisionDate;

    final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    try {
      final DocumentBuilder builder = factory.newDocumentBuilder();
      final Document document = builder.newDocument();
      final Element eGrille = document.createElement("grille");
      document.appendChild(eGrille);

      final Element eProprietesGrille = document.createElement("proprietesGrille");
      eGrille.appendChild(eProprietesGrille);

      final Element eLonHautGauche = document.createElement("lonHautGauche");
      eLonHautGauche.appendChild(document.createTextNode("" + grille.getLonHautGauche()));

      final Element eLatHautGauche = document.createElement("latHautGauche");
      eLatHautGauche.appendChild(document.createTextNode("" + grille.getLatHautGauche()));

      final Element eNbx = document.createElement("nbx");
      eNbx.appendChild(document.createTextNode("" + grille.getNbx()));

      final Element eNby = document.createElement("nby");
      eNby.appendChild(document.createTextNode("" + grille.getNby()));

      final Element eDx = document.createElement("dx");
      eDx.appendChild(document.createTextNode("" + grille.getDx()));

      final Element eDy = document.createElement("dy");
      eDy.appendChild(document.createTextNode("" + grille.getDy()));

      eProprietesGrille.appendChild(eLonHautGauche);
      eProprietesGrille.appendChild(eLatHautGauche);
      eProprietesGrille.appendChild(eNbx);
      eProprietesGrille.appendChild(eNby);
      eProprietesGrille.appendChild(eDx);
      eProprietesGrille.appendChild(eDy);

      final Element ePrevision_date = document.createElement("previsionDate");
      ePrevision_date.setAttribute("date_debut", "" + previsionDate.getDate());
      eGrille.appendChild(ePrevision_date);

      // boucle
      for (Vent[] tabVent : previsionDate.getVents()) {
        for (Vent vent : tabVent) {
          if (vent != null) {

            final Element eNom_vent = document.createElement("vent");
            ePrevision_date.appendChild(eNom_vent);

            final Element eDirection = document.createElement("direction");
            eDirection.appendChild(document.createTextNode("" + vent.getDirection()));

            final Element eVitesse = document.createElement("vitesse");
            eVitesse.appendChild(document.createTextNode("" + vent.getVitesse()));

            eNom_vent.appendChild(eDirection);
            eNom_vent.appendChild(eVitesse);
          }
        }
      }

      // affichage
      final TransformerFactory transformerFactory = TransformerFactory.newInstance();
      final Transformer transformer = transformerFactory.newTransformer();
      final DOMSource source = new DOMSource(document);
      final StreamResult sortie = new StreamResult(new File(path));
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
