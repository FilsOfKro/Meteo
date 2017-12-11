package testgrib;

import java.io.FileNotFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;
 
import net.sourceforge.jgrib.GribFile;
import net.sourceforge.jgrib.GribRecord;
import net.sourceforge.jgrib.GribRecordGDS;
import net.sourceforge.jgrib.NoValidGribException;
import net.sourceforge.jgrib.NotSupportedException;

public class Test1 {
   
  
      public static void main(String[] args)
      {
          try
          {
              GribFile grb = new GribFile("TTxOcMxLToSYmtRzKDl0e75I4HAjqDApv2c.grb");
   
              GribRecordGDS r2 = grb.getGrids()[0];
              GribRecord ventU = grb.getRecord(1);
              GribRecord ventV = grb.getRecord(2);
              
              int nbx = r2.getGridNX();
              int nby = r2.getGridNY();
              for (int i = 0; i < nbx; i++)
              {
                  for (int j = 0; j < nby; j++)
                  {
                      // affichage des donnees (i,j)
                      try
                      {
                        System.out.println("description ventu :"+ventU.getDescription());

                        System.out.print("["+ventU.getGridCoords()[i]+":"+ventU.getGridCoords()[j]+"] : ");
                        System.out.print("time:"+ventU.getTime().getTime()+"::");
                          System.out.println(ventU.getValue(i, j) + " ");
                          
                          System.out.println();

                          System.out.println("description ventV :"+ventV.getDescription());
                          System.out.print("["+ventV.getGridCoords()[i]+":"+ventV.getGridCoords()[j]+"] : ");
                          System.out.print(" time: "+ventU.getTime().getTime()+" :: ");

                          System.out.println(ventV.getValue(i, j) + " ");
                          System.out.println("-----------------------------------------");


                      }
                      catch (NoValidGribException e)
                      {
                          e.printStackTrace();
                      }
                  }
                  System.out.println();
              }
              // affichage de l'unite des donnees
              System.out.println(ventV.getUnit());
              // description de la donnee
              System.out.println(ventU.getPDS());
              System.out.println(grb.toString());
System.out.println( grb.getGrids().length);
System.out.println( r2.getGridDX());
System.out.println( r2.getGridDY());


          }
          catch (FileNotFoundException e)
          {
              e.printStackTrace();
          }
          catch (IOException e)
          {
              e.printStackTrace();
          }
          catch (NotSupportedException e)
          {
              e.printStackTrace();
          }
          catch (NoValidGribException e)
          {
              e.printStackTrace();
          }
      }
  

}
