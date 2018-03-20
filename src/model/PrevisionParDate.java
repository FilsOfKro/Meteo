package model;

import java.util.Date;

public class PrevisionParDate {
  private Vent[][] vents;

  Date date;

  /**
   * Used for cloning purpose
   */
  private PrevisionParDate() {
	  
  }
  
  /**
   * @param date La date associée aux prévisions
   * @param nbx Le nombre de colonne de la grille, donc du tableau
   * @param nby Le nombre de lignes de la grille, donc du tableau
   */
  public PrevisionParDate(Date date, int nbx, int nby) {
    this.date = date;
    vents = new Vent[nby][nbx];
  }

  /**
   * Ajoute un vent à la grile
   * @param x Coordonnée X du vent dans le tableau
   * @param y Coordonnée Y du vent dans le tableau
   * @param vent Le vent à ajouter
   */
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
   * @return vrai ou faux si la date est égale
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
  
  /**
   * Retourne le nombre de vents présents dans la prévision
   * @return
   */
  public int getNombreVent() {
    int cpt = 0;
    for(Vent[] tabVent : getVents()) {
      for(Vent vent : tabVent) {
        if(vent != null) cpt++;
      }
    }
    return cpt++;
  }
  
  @Override
	protected Object clone() throws CloneNotSupportedException {
		PrevisionParDate ppd = new PrevisionParDate();
		ppd.vents = this.vents.clone();
		ppd.date = (Date) this.date.clone();
		
		return ppd;
	}
  
}
