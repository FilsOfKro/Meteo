package editionXML;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
  protected Date date_fin = new Date();
  
  
  protected Vent vent1;
  protected Vent vent2;
  
  protected Editionxml creationXML;
  protected Lirexml lectureXML;
  
  
  @Before
  public void avantTests() {
    zone_prevision = new Grille(10, 10, 49.00, 5.00, 0.25, 0.25, true);
    vent1 = new Vent(40.0, 35.0); //creation vent1
    vent2 = new Vent(20.0, 22.5); //creation vent2
    prevision = new Prevision(zone_prevision);
    previsionDate = prevision.buildPrevisionParDate(date_debut);
 
  }
  
  @After
  public void apresTests() {
      //System.out.println("Fin du test");
  }
  
  @Test
  public void ajoutPrevision() {
    previsionDate.addVent(5, 5, vent1);
    previsionDate.addVent(3, 2, vent2);
    
    assertTrue(previsionDate.getNombreVent() == 2);
    
    prevision.addPrevision(previsionDate); //ajout de la prévision    
    assertTrue(prevision.getListeDates().size() == 1);
    
    date_fin.setHours(date_fin.getHours()+1);
    creationXML = new Editionxml(zone_prevision, prevision, previsionDate, date_fin);
    
  }
  
  
  
  @Test
  public void LireXML() {
    lectureXML = new Lirexml();    
  }
  


}
