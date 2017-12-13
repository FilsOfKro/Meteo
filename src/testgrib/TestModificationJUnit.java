package testgrib;

import static org.junit.Assert.assertTrue;

import grib.parser.GribParser;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Prevision;
import model.Vent;
import modification.Contraste;
import modification.ServicesModification;
import net.sourceforge.jgrib.NoValidGribException;
import net.sourceforge.jgrib.NotSupportedException;

import org.junit.Before;
import org.junit.Test;

import visiteur.Visiteur;

public class TestModificationJUnit {

  protected ServicesModification servicesModification;
  protected Contraste contraste;
  protected GribParser gribParser;
  protected Prevision prevision;
  protected Visiteur visiteur;

  
  /**
   * Initialisation de la classe de test.
   * 
   * @throws Exception Renvoi une exception si un problème survient.
   */
  @Before
  public void setUp() throws Exception {

    this.servicesModification = new ServicesModification();
    this.contraste = Contraste.getInstance();
    this.gribParser = new GribParser();
    this.visiteur = new Visiteur();

    try {
      long start = System.currentTimeMillis();

      this.prevision = gribParser.parsePrevisionFromGrib("20171213_082957_.grb");

      System.out.println("Temps d'exécution : " + (System.currentTimeMillis() - start));

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NotSupportedException e) {
      e.printStackTrace();
    } catch (NoValidGribException e) {
      e.printStackTrace();
    }

    prevision.applique(visiteur);
  }

  @Test
  public void testAppliquerContrasteLineaire() {

    Vent ventAvantContraste = prevision.getPrevisionParDate().get(0).getVents()[0][0];
    double vitesseVentAvantContraste = (double) Math.round(
                                        ventAvantContraste.getVitesse() * 100) / 100;
    double directionVentAvantContraste = (double) Math.round(
                                        ventAvantContraste.getDirection() * 100) / 100;

    assertTrue(vitesseVentAvantContraste == 41.77);
    assertTrue(directionVentAvantContraste == 227.91);

    servicesModification.definirSeuilEtCoefficient(contraste, 0.0, 100.0);

    assertTrue(contraste.getSeuil() == 00.00);
    assertTrue(contraste.getCoefficient() == 100.00);

    servicesModification.appliquerContrasteLineaire(prevision, contraste);

    Vent ventApresContraste = prevision.getPrevisionParDate().get(0).getVents()[0][0];
    double vitesseVentApresContraste = (double) Math.round(
                                        ventApresContraste.getVitesse() * 100) / 100;
    double directionVentApresContraste = (double) Math.round(
                                          ventApresContraste.getDirection() * 100) / 100;

    assertTrue(vitesseVentApresContraste == 83.55);
    assertTrue(directionVentApresContraste == 227.91);

    prevision.applique(visiteur);

    System.out.println("Avant avoir appliqué le contraste : ");
    System.out.println("U=" + directionVentAvantContraste 
                     + "  | V=" + vitesseVentAvantContraste + "\n\n");

    System.out.println("Après avoir appliqué le contraste (seuil=0 , coefficient=100%) : ");
    System.out.println("U=" + directionVentApresContraste + "  | V=" + vitesseVentApresContraste);
  }
}
