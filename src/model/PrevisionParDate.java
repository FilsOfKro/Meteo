package model;

import java.util.Date;

public class PrevisionParDate {
  private Vent[][] vents;

  Date date;

  public PrevisionParDate(Date date, int nbx, int nby) {
    this.date = date;
    vents = new Vent[nby][nbx];
  }

  public void addVent(int x, int y, Vent vent) {
    vents[y][x] = vent;
  }

  public Date getDate() {
    return date;
  }

  /**
   * Retourne vrai si la date passée en paramètre est égale à la date de la
   * prévision.
   * 
   * @param date La date à comparer
   * @return vria ou faux si la date est égale
   */
  public boolean estLaMemeDate(Date date) {
    return this.date.equals(date);
  }

  /**
   * Retourne une représentation en chaine de caractère de cette PrevisionParDate.
   */
  public String toString() {
    StringBuilder sb = new StringBuilder(date.toString() + " - Grille de taille [");
    sb.append(vents.length).append(",").append(vents[0].length).append("]\n");

    return sb.toString();
  }

  public Vent[][] getVents() {
    return vents;
  }
  
  public int getNombreVent() {
    int cpt = 0;
    for(Vent[] tabVent : getVents()) {
      for(Vent vent : tabVent) {
        if(vent != null) cpt++;
      }
    }
    return cpt++;
  }
  
}
