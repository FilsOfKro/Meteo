package facade;

import java.io.IOException;
import java.util.NoSuchElementException;

import model.Prevision;
import net.sourceforge.jgrib.NoValidGribException;
import net.sourceforge.jgrib.NotSupportedException;

public class Launcher {

  public static void main(String[] args)
      throws NoSuchElementException, IOException, NoValidGribException, NotSupportedException {
    Facade facade = new Facade();
    Prevision prev = facade.loadGrib("TTxOcMxLToSYmtRzKDl0e75I4HAjqqDApv_.grb");

    facade.displayDate(prev, prev.getListeDates().get(0));
  }

}
