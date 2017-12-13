package testgrib;

import grib.parser.GribParser;

import java.io.FileNotFoundException;

import java.io.IOException;

import model.Prevision;
import modification.Contraste;
import modification.ServicesModification;
import net.sourceforge.jgrib.NoValidGribException;
import net.sourceforge.jgrib.NotSupportedException;
import visiteur.Visiteur;

public class TestModification {


  /**
   * Méthode de test.
   * 
   * @param args .
   */
  public static void main(String[] args) {
    Visiteur visiteur = new Visiteur();
    
    ServicesModification servicesModification = new ServicesModification();
    Contraste contraste = Contraste.getInstance();
    
    try {
      long start = System.currentTimeMillis();
      GribParser gribParser = new GribParser();
      
      Prevision prevision = gribParser.parsePrevisionFromGrib("20171213_082957_.grb");
      
      System.out.println("Temps d'exécution : " + (System.currentTimeMillis() - start));
      
      prevision.applique(visiteur);
      
      servicesModification.definirSeuilEtCoefficient(contraste, 0.0, 100.0);
      
      servicesModification.appliquerContrasteLineaire(prevision, contraste);
      
      prevision.applique(visiteur);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NotSupportedException e) {
      e.printStackTrace();
    } catch (NoValidGribException e) {
      e.printStackTrace();
    }
  }
}
