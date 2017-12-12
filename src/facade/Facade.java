package facade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import gov.nasa.worldwind.geom.Position;
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
    this.parser = new GribParser();
  }

  public Prevision loadGrib(String filename) {
    Prevision prevision = null;
    long start = System.currentTimeMillis();

    try {
      prevision = parser.parsePrevisionFromGrib(filename);
    } catch (NoSuchElementException | IOException | NoValidGribException | NotSupportedException e) {
      e.printStackTrace();
    }
    System.out.println("Temps d'exécution : " + (System.currentTimeMillis() - start));
    System.out.println(prevision.toString());
    return prevision;
  }

  public List<Date> getDates(Prevision prevision) {
    return prevision.getListeDates();
  }

  public void displayDate(Prevision prev, Date date) {
    PrevisionParDate myPrevision = prev.getPrevisionParDate(date);

    ArrayList<WindBarb> windbarbs = new ArrayList<>();
    windbarbs.add(new WindBarb(Position.fromDegrees(48, -4), 25.0, 25.0));
    for (int y = 0; y < myPrevision.getVents().length; y++) {
      for (int x = 0; x < myPrevision.getVents()[y].length; x++) {
        Vent vent = myPrevision.getVents()[y][x];
        Double latitude = prev.getGrille().getLatitude(y);
        Double longitude = prev.getGrille().getLongitude(x);
        windbarbs.add(new WindBarb(latitude, longitude, vent.getDirection(), vent.getVitesse()));
      }
    }
    appframe.displayWindbarbs(windbarbs);
  }

}
