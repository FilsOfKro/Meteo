package facade;

import java.io.IOException;
import java.util.NoSuchElementException;

import net.sourceforge.jgrib.NoValidGribException;
import net.sourceforge.jgrib.NotSupportedException;

public class Launcher {

  public static void main(String[] args)
      throws NoSuchElementException, IOException, NoValidGribException, NotSupportedException {
    Facade facade = new Facade();
    facade.loadAndDisplayGrib("TTxOcMxLToSYmtRzKDl0e75I4HAjqqDApv_.grb");
  }

}
