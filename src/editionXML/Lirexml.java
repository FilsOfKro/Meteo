package editionXML;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import facade.MeteoFacade;
import model.Grille;
import model.Prevision;
import model.PrevisionParDate;
import model.Vent;

/**
 * Classe pour lire un fichier xml.
 *
 */
public class Lirexml {

  @SuppressWarnings("deprecation")
  public static void chargerXML(String path){
    /*
     * Etape 1 : r�cup�ration d'une instance de la classe
     * "DocumentBuilderFactory"
     */
    final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    try {
      /*
       * Etape 2 : cr�ation d'un parseur
       */
      final DocumentBuilder builder = factory.newDocumentBuilder();

      /*
       * Etape 3 : cr�ation d'un Document
       */
      final Document document = builder.parse(new File(path));

      /*
       * Etape 4 : r�cup�ration de l'Element racine
       */
      //final Element racine = document.getDocumentElement();

      System.out.println("*************DONNEES************");
      
      Integer nbx = null;
      Integer nby = null;

      Double lonHautGauche = null;
      Double latHautGauche = null;

      Double dx = null;
      Double dy = null;
      
      NodeList nListProprietesGrille = document.getElementsByTagName("proprietesGrille");
      for (int temp = 0; temp < nListProprietesGrille.getLength(); temp++) {
        Node nProprietesGrille = nListProprietesGrille.item(temp);

        if (nProprietesGrille.getNodeType() == Node.ELEMENT_NODE) {
          Element eProprietesGrille = (Element) nProprietesGrille;

          System.out.println("Longitude haut gauche : "
              + eProprietesGrille.getElementsByTagName("lonHautGauche").item(0).getTextContent());
          lonHautGauche = Double.parseDouble(eProprietesGrille.getElementsByTagName("lonHautGauche").item(0).getTextContent());
          
          System.out.println("Latitude haut gauche : "
              + eProprietesGrille.getElementsByTagName("latHautGauche").item(0).getTextContent());
          latHautGauche = Double.parseDouble(eProprietesGrille.getElementsByTagName("lonHautGauche").item(0).getTextContent());
          
          System.out.println("Nombre X : "
              + eProprietesGrille.getElementsByTagName("nbx").item(0).getTextContent());
          nbx = Integer.parseInt(eProprietesGrille.getElementsByTagName("nbx").item(0).getTextContent());

          System.out.println("Nombre Y : "
              + eProprietesGrille.getElementsByTagName("nby").item(0).getTextContent());
          nby = Integer.parseInt(eProprietesGrille.getElementsByTagName("nby").item(0).getTextContent());

          System.out.println("Delta X : "
              + eProprietesGrille.getElementsByTagName("dx").item(0).getTextContent());
          dx = Double.parseDouble(eProprietesGrille.getElementsByTagName("dx").item(0).getTextContent());

          System.out.println("Delta Y : "
              + eProprietesGrille.getElementsByTagName("dy").item(0).getTextContent());
          dy = Double.parseDouble(eProprietesGrille.getElementsByTagName("dy").item(0).getTextContent());
        }
      }
      
      Grille grille = new Grille(nbx, nby, lonHautGauche, latHautGauche, dx, dy, true);
      
      Date date = null;
      
      NodeList nListPrevisionDate = document.getElementsByTagName("previsionDate");
      for (int temp = 0; temp < nListPrevisionDate.getLength(); temp++) {
        Node nPrevisionDate = nListPrevisionDate.item(temp);

        if (nPrevisionDate.getNodeType() == Node.ELEMENT_NODE) {
          Element ePrevisionDate = (Element) nPrevisionDate;

          System.out.println("Date : " + ePrevisionDate.getAttribute("date_debut"));
          date = new Date(ePrevisionDate.getAttribute("date_debut"));
          // date = new Date();
        }
      }
      
      PrevisionParDate previsionParDate = new PrevisionParDate(date, nbx, nby);
      
      int x = 0;
      int y = 0;
      
      NodeList nListVent = document.getElementsByTagName("vent");
      for (int temp = 0; temp < nListVent.getLength(); temp++) {
        Node nVent = nListVent.item(temp);

        if (nVent.getNodeType() == Node.ELEMENT_NODE) {
          Element eVent = (Element) nVent;

          System.out.println("Direction : " + eVent.getElementsByTagName("direction").item(0).getTextContent());
          System.out.println("Vitesse : " + eVent.getElementsByTagName("vitesse").item(0).getTextContent());
          
          Double direction = Double.parseDouble(eVent.getElementsByTagName("direction").item(0).getTextContent());
          Double vitesse = Double.parseDouble(eVent.getElementsByTagName("vitesse").item(0).getTextContent());
          
          Vent ventTmp = new Vent();
          ventTmp.setDirection(direction);
          ventTmp.setVitesse(vitesse);
          
          x = (int) (temp%grille.getNbx());
          
          if(temp != 0 && x == 0){
            y++;
            y = (int) (y%grille.getNby());
          }
          
          previsionParDate.addVent(x, y, ventTmp);
        }
      }
      
      Prevision prevision = new Prevision(grille);
      prevision.getPrevisionParDate().add(previsionParDate);
      
      for(Vent[] listVents : previsionParDate.getVents()){
        for(Vent vent : listVents){
          System.out.println("\n\n\nDirection : " + vent.getDirection());
          System.out.println("Vitesse : " + vent.getVitesse());
        }
      }
      
      MeteoFacade.getInstance().setCurrentDate(previsionParDate.getDate());
      MeteoFacade.getInstance().setCurrentPrevision(prevision);

      // Affichage des donnees
      // System.out.println(racine.getTextContent());

    } catch (final ParserConfigurationException e) {
      e.printStackTrace();
    } catch (final SAXException e) {
      e.printStackTrace();
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }
}
