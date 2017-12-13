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

  /**
   * Retourne la liste des dates qui existent dans les données.
   * 
   * @return La liste des dates trouvées
   */
  public List<Date> getListeDates() {
    List<Date> dates = new ArrayList<Date>();

    for (PrevisionParDate prevision : previsionsParDate) {
      dates.add(prevision.getDate());
    }

    return dates;
  }

  /**
   * Retourne la prévision pour la date passée en paramètre si elle existe, sinon null.
   * 
   * @param date La date recherchée
   * @return La PrevisionParDate trouvée
   */
  public PrevisionParDate getPrevisionParDate(Date date) {

    for (PrevisionParDate unePrevision : previsionsParDate) {
      if (unePrevision.estLaMemeDate(date)) {
        return unePrevision;
      }
    }

    return null;
  }

  public Grille getGrille() {
    return grille;
  }

  /**
   * Retourne une représentation en chaine de caractère de cette Prevision.
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("Prévisions présentes : ")
        .append(previsionsParDate.size()).append("\n");
    sb.append("Informations sur la grille :\n").append(grille.toString()).append("\n\n");

    for (PrevisionParDate prevision : previsionsParDate) {
      sb.append(prevision.toString()).append("\n");
    }

    return sb.toString();
  }
}
