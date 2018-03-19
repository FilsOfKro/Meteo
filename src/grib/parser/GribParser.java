package grib.parser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Calendar;
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
 * Classe pour parser un fichier Grib et le mettre au format du modèle de prévision.
 * 
 * @author Sebastien Palud
 */
public class GribParser {

  /**
   * Parse un fichier .grb et retourne l'objet prévision lu si aucun problème ne se passe
   * 
   * @param filename Le nom du fichier à parser
   * @return L'objet prévision équivalent aux données présentes dans le fichier .grb
   * @throws NoSuchElementException Exception JGrib
   * @throws IOException Exception JGrib
   * @throws NoValidGribException Exception JGrib
   * @throws NotSupportedException Exception JGrib
   */
  public Prevision parsePrevisionFromGrib(String filename)
      throws NoSuchElementException, IOException, NoValidGribException, NotSupportedException {
    GribFile grb = new GribFile(filename);
    GribRecordGDS gridRecords = grb.getGrids()[0];

    Grille grille = getGrille(gridRecords);

    Prevision prevision = new Prevision(grille);

    for (int i = 1; i <= grb.getRecordCount(); i += 2) {

      GribRecord ventUGrid = grb.getRecord(i);
      GribRecord ventVGrid = grb.getRecord(i + 1);

      PrevisionParDate previsionParDate = getPrevisionParDate(prevision, ventUGrid, ventVGrid);

      prevision.addPrevision(previsionParDate);
    }

    return prevision;
  }

  /**
   * Retourne un objet Grille en lisant les paramètres de la grille du fichier .grb
   * 
   * @param gridRecords L'objet GribRecordGDS récupéré par JGrib
   * @return La grille lue dans le fichier
   */
  private Grille getGrille(GribRecordGDS gridRecords) {
    int nbx = gridRecords.getGridNX();
    int nby = gridRecords.getGridNY();

    double lonHg = gridRecords.getGridLon1();
    double latHg = gridRecords.getGridLat1();

    double dx = gridRecords.getGridDX();
    double dy = gridRecords.getGridDY();

    boolean uvOrienteEst = gridRecords.isUVEastNorth();

    Grille grille = new Grille(nbx, nby, lonHg, latHg, dx, dy, uvOrienteEst);

    return grille;
  }

  /**
   * Retourne un objet PrevisionParDate qui contient un tableau à deux dimensions des vents passés
   * en paramètre. Utilise un objet prévision pour avoir les informations de la grille
   * 
   * @param prevision l'objet Prevision en cours de création
   * @param ventUGrid les vecteurs U du vent
   * @param ventVGrid les vecteurs V du vent
   * @return l'objet PrevisionParDate créé
   * @throws NoValidGribException erreur JGrib
   */
  private PrevisionParDate getPrevisionParDate(Prevision prevision, GribRecord ventUGrid,
      GribRecord ventVGrid) throws NoValidGribException {
    Date date = ventUGrid.getTime().getTime();
    PrevisionParDate previsionParDate = prevision.buildPrevisionParDate(date);

    for (int y = 0; y < prevision.getGrille().getNby(); y++) {
      for (int x = 0; x < prevision.getGrille().getNbx(); x++) {

        double ventU = ventUGrid.getValue(x, y);
        double ventV = ventVGrid.getValue(x, y);

        Vent vent = new Vent(ventU, ventV);

        previsionParDate.addVent(x, y, vent);
      }
    }

    return previsionParDate;
  }

  public void download(Date date, String gribZoneNumber) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int year = Calendar.YEAR;
    int month = Calendar.MONTH + 1;
    int day = Calendar.DAY_OF_MONTH;

    StringBuilder url = new StringBuilder().append("https://nomads.ncdc.noaa.gov/data/gfs/");
    url.append(year);
    url.append(month);
    url.append('/');
    url.append(year);
    url.append(month);
    url.append(day);
    url.append("/gfs-avn_");
    url.append(gribZoneNumber);
    url.append('_');
    url.append(year);
    url.append(month);
    url.append(day);
    url.append("_0000_000.grb");
    try {
      URL website = new URL(url.toString());
      ReadableByteChannel rbc = Channels.newChannel(website.openStream());
      FileOutputStream fos = new FileOutputStream("information.html");
      fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
      fos.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
