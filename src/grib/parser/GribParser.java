package grib.parser;

import java.io.IOException;
import java.util.Date;
import java.util.NoSuchElementException;

import model.Grille;
import model.Prevision;
import model.PrevisionParDate;
import model.Vent;
import net.sourceforge.jgrib.GribFile;
import net.sourceforge.jgrib.GribRecord;
import net.sourceforge.jgrib.GribRecordGDS;
import net.sourceforge.jgrib.NoValidGribException;
import net.sourceforge.jgrib.NotSupportedException;

/**
 * 
 * @author Sebastien Palud
 */
public class GribParser {

  /**
   * Parse un fichier .grb et retourne l'objet prévision lu si aucun problème ne se passe
   * @param filename Le nom du fichier à parser
   * @return L'objet prévision équivalent aux données présentes dans le fichier .grb
   * @throws NoSuchElementException
   * @throws IOException
   * @throws NoValidGribException
   * @throws NotSupportedException
   */
  public Prevision parsePrevisionFromGrib(String filename) throws NoSuchElementException, IOException, NoValidGribException, NotSupportedException {
    GribFile grb = new GribFile(filename);
    GribRecordGDS gridRecords = grb.getGrids()[0];

    Grille grille = getGrille(gridRecords);
    
    Prevision prevision = new Prevision(grille);

    for (int i = 1 ; i <= grb.getRecordCount() ; i+=2) {

      GribRecord ventUGrid = grb.getRecord(i);
      GribRecord ventVGrid = grb.getRecord(i+1);
      
      PrevisionParDate previsionParDate = getPrevisionParDate(prevision, ventUGrid, ventVGrid);
                      
      prevision.addPrevision(previsionParDate);
    }
    
    return prevision;
  }
  
  /**
   * Retourne un objet Grille en lisant les paramètres de la grille du fichier .grb
   * @param gridRecords
   * @return
   */
  private Grille getGrille(GribRecordGDS gridRecords) {
    int nbx = gridRecords.getGridNX();
    int nby = gridRecords.getGridNY();

    double lonHG = gridRecords.getGridLon1();
    double latHG = gridRecords.getGridLat1();

    double dx = gridRecords.getGridDX();
    double dy = gridRecords.getGridDY();
    
    boolean UVOrienteEst = gridRecords.isUVEastNorth();
    
    Grille grille = new Grille(nbx, nby, lonHG, latHG, dx, dy, UVOrienteEst);
    
    return grille;
  }
  
  /**
   * Retourne un objet PrevisionParDate qui contient un tableau à deux dimensions des vents passés en paramètre
   * Utilise un objet prévision pour avoir les informations de la grille
   * @param prevision
   * @param ventUGrid
   * @param ventVGrid
   * @return
   * @throws NoValidGribException
   */
  private PrevisionParDate getPrevisionParDate(Prevision prevision, GribRecord ventUGrid, GribRecord ventVGrid) throws NoValidGribException {
    Date date = ventUGrid.getTime().getTime();
    PrevisionParDate previsionParDate = prevision.buildPrevisionParDate(date);
    
    for (int y = 0 ; y < prevision.getGrille().getNby() ; y++) {
      for (int x = 0 ; x < prevision.getGrille().getNbx() ; x++) {

        double ventU = ventUGrid.getValue(x, y);
        double ventV = ventVGrid.getValue(x, y);
        
        Vent vent = new Vent(ventU, ventV);
        
    /*    System.out.println(date.toString());
        System.out.println("(" + prevision.getGrille().getLongitude(x) + "," + prevision.getGrille().getLatitude(y) + ")");
        System.out.println(vent.getVitesse());
        System.out.println(vent.getDirection());
        System.out.println(prevision.getGrille().isUVOrienteEst());
        System.out.println("--------");*/
        previsionParDate.addVent(x, y, vent);
      }
    }
    
    return previsionParDate;
  }
  
}
