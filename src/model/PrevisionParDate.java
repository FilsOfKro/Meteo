package model;

import java.util.Date;

import facade.MeteoFacade;

public class PrevisionParDate {
  private Vent[][] vents;

  Date date;

  /**
   * Used for cloning purpose
   */
  private PrevisionParDate() {
    
  }
  
  public PrevisionParDate(Date date, int nbx, int nby) {
    this.date = date;
    this.vents = new Vent[nby][nbx];
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
  
  @Override
	protected Object clone() throws CloneNotSupportedException {
		PrevisionParDate ppd = new PrevisionParDate();
		
		int nbx = MeteoFacade.getInstance().getCurrentPrevision().getGrille().getNbx();
		int nby = MeteoFacade.getInstance().getCurrentPrevision().getGrille().getNby();

		ppd.vents = new Vent[nby][nbx];
		
		for(int i=0; i<this.vents.length; i++){
		  for(int j=0; j<this.vents[i].length; j++){
		    
		    ppd.vents[i][j] = (Vent) this.vents[i][j].clone();
		  }
		}
		// ppd.vents = this.vents.clone();
		ppd.date = (Date) this.date.clone();
		
		return ppd;
	}
  
}
