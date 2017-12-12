package facade;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;

import grib.parser.GribParser;
import model.Prevision;
import model.PrevisionParDate;
import model.Vent;
import model.WindBarb;
import net.sourceforge.jgrib.NoValidGribException;
import net.sourceforge.jgrib.NotSupportedException;
import view.FlatWorld;
import view.FlatWorld.AppFrame;

public class Facade {
  GribParser parser;
  AppFrame appframe;

  Facade() {
    FlatWorld.main(null);
    this.appframe = (AppFrame) FlatWorld.getFrame();
    try {
      long start = System.currentTimeMillis();
      this.parser = new GribParser();

      Prevision prevision = parser.parsePrevisionFromGrib("TTxOcMxLToSYmtRzKDl0e75I4HAjqDApv2c.grb");

      System.out.println("Temps d'exécution : " + (System.currentTimeMillis() - start));
      System.out.println(prevision.toString());
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

  public void loadAndDisplayGrib(String filename)
      throws NoSuchElementException, IOException, NoValidGribException, NotSupportedException {
    Prevision prevision = parser.parsePrevisionFromGrib(filename);
    PrevisionParDate myPrevision = prevision.getPrevisionsParDate(prevision.getListeDates().get(0));

    ArrayList<WindBarb> windbarbs = new ArrayList<>();
    for (int y = 0; y < myPrevision.getVents().length; y++) {
      for (int x = 0; x < myPrevision.getVents()[y].length; x++) {
        Vent vent = myPrevision.getVents()[y][x];
        Double latitude = prevision.getGrille().getLatitude(y);
        Double longitude = prevision.getGrille().getLongitude(x);
        windbarbs.add(new WindBarb(latitude, longitude, vent.getDirection(), vent.getVitesse()));
      }
    }
    appframe.displayWindbarbs(windbarbs);

  }

  public void displayDate(Date date) {

  }

}
