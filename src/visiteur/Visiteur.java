package visiteur;

import model.Prevision;

public class Visiteur implements IVisiteur {

  public void agitSurPrevision(Prevision prevision) {
    
    System.out.println(prevision.afficherVentPrevision());
  }
}
