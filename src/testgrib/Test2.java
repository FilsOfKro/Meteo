package testgrib;

import java.io.FileNotFoundException;

import java.io.IOException;

import grib.parser.GribParser;
import model.Prevision;
import net.sourceforge.jgrib.NoValidGribException;
import net.sourceforge.jgrib.NotSupportedException;

public class Test2 {


  public static void main(String[] args) {
    try {
      long start = System.currentTimeMillis();
      GribParser gribParser = new GribParser();
      
      Prevision prevision = gribParser.parsePrevisionFromGrib("TTxOcMxLToSYmtRzKDl0e75I4HAjqDApv2c.grb");

      System.out.println("Temps d'exécution : " + (System.currentTimeMillis() - start));
      System.out.println(prevision.toString());
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (NotSupportedException e) {
      e.printStackTrace();
    }
    catch (NoValidGribException e) {
      e.printStackTrace();
    }
  }
}
