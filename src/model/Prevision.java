package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Prevision {
  private Grille grille;
  private List<PrevisionParDate> previsionsParDate;
  
  public Prevision(Grille grille) {
    previsionsParDate = new ArrayList<PrevisionParDate>();
    this.grille = grille;
  }
  
  public PrevisionParDate buildPrevisionParDate(Date date) {
    return new PrevisionParDate(date, grille.getNbx(), grille.getNby());
  }
  
  public void addPrevision(PrevisionParDate prevision) {
    previsionsParDate.add(prevision);
  }
  
  public Grille getGrille() {
    return grille;
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder("Prévisions présentes : ").append(previsionsParDate.size()).append("\n");
    sb.append("Informations sur la grille :\n").append(grille.toString()).append("\n\n");
    
    for(PrevisionParDate prevision : previsionsParDate) {
      sb.append(prevision.toString()).append("\n");
    }
    
    return sb.toString();
  }
}
