package editionXML;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.Grille;
import model.Prevision;
import model.PrevisionParDate;
import model.Vent;

public class TestEdition {

  protected Grille zone_prevision;
  protected Prevision prevision;

  protected PrevisionParDate previsionDate;

  protected Date date_debut = new Date();
  
  protected String path;

  protected Vent vent1;
  protected Vent vent2;
  protected Vent vent3;
  protected Vent vent4;
  protected Vent vent5;
  protected Vent vent6;

  @Before
  public void avantTests() {
    zone_prevision = new Grille(3, 2, 49.00, 5.00, 0.25, 0.25, true);
    vent1 = new Vent(40.0, 35.0); // creation vent1
    vent2 = new Vent(20.0, 22.5); // creation vent2
    vent3 = new Vent(60.4, 54.1); // creation vent3
    vent4 = new Vent(12.0, 2.5); // creation vent4
    vent5 = new Vent(40.0, 35.0); // creation vent1
    vent6 = new Vent(20.0, 22.5); // creation vent2
    prevision = new Prevision(zone_prevision);
    previsionDate = prevision.buildPrevisionParDate(date_debut);
    path = new String("file.xml"); 
  }

  @After
  public void apresTests() {
    // System.out.println("Fin du test");
  }

  @Test
  public void ajoutPrevision() {
    previsionDate.addVent(0, 0, vent1);
    previsionDate.addVent(1, 0, vent2);
    previsionDate.addVent(2, 0, vent3);
    previsionDate.addVent(0, 1, vent4);
    previsionDate.addVent(1, 1, vent5);
    previsionDate.addVent(2, 1, vent6);
    
    assertTrue(previsionDate.getNombreVent() == 6);
    
    prevision.addPrevision(previsionDate); //ajout de la pr�vision    
    assertTrue(prevision.getListeDates().size() == 1);
    
    Editionxml.sauvergarderXML(zone_prevision, previsionDate, path);
  }

  @Test
  public void LireXML() {
    
    Lirexml.chargerXML(path);
  }

}
