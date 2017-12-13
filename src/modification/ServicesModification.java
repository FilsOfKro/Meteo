package modification;

import java.util.Date;

import model.Prevision;
import model.PrevisionParDate;
import model.Vent;

public class ServicesModification {

  /**
   * Méthode applicant un contraste linéaire à des vents.
   * 
   * @param prevision Prevision a laquelle appliquer le contraste.
   * @param contraste Contraste a appliquer.
   */
  public void appliquerContrasteLineaire(Prevision prevision, Contraste contraste) {
    
    for (PrevisionParDate previsionParDate : prevision.getPrevisionParDate()) {
      
      appliquerContrasteLineaire(previsionParDate, contraste);
    }
  }
  
  /**
   * Méthode applicant un contraste linéaire à des vents.
   * 
   * @param previsionParDate PrevisionParDate a laquelle appliquer le contraste.
   * @param contraste Contraste a appliquer.
   */
  public void appliquerContrasteLineaire(PrevisionParDate previsionParDate, Contraste contraste) {
    
    for (Vent[] tabVent : previsionParDate.getVents()) {
      for (Vent vent : tabVent) {
        
        appliquerContrasteLineaire(vent, contraste);
      }
    }
  }
  
  /**
   * Méthode applicant un contraste linéaire à des vents.
   * 
   * @param vent Vent auquel appliquer le contraste.
   * @param contraste Contraste a appliquer.
   */
  public boolean appliquerContrasteLineaire(Vent vent, Contraste contraste) {
    
    boolean isValid = ((Double)contraste.getCoefficient() != null 
                     & (Double)contraste.getSeuil() != null ? true : false);

    double seuil = contraste.getSeuil();
    double coefficient = contraste.getCoefficient();
    
    double vitesse = vent.getVitesse();
    double nouvelleVitesse;
    
    if (vitesse > seuil) {
      
      nouvelleVitesse = vitesse * (1 + coefficient / 100);
    } else if (vitesse < seuil) {
      
      nouvelleVitesse = vitesse * (1 - coefficient / 100);
    } else {
      
      nouvelleVitesse = vitesse;
    }
    
    vent.setVitesse(nouvelleVitesse);
    
    return isValid;
  }
  
  /**
   * Méthode permettant de définir le seuil et coefficient d'un contraste.
   * 
   * @param contraste Contraste dont on souhaite définir le seuil et coefficient.
   * @param seuil Valeur du seuil.
   * @param coefficient Valeur du coefficient.
   * 
   * @return Retourne vrai si le changement a bien était éffectué.
   */
  public boolean definirSeuilEtCoefficient(Contraste contraste, double seuil, double coefficient) {
    
    boolean isValid = (coefficient >= 0 && coefficient <= 100 && seuil >= 0 ? true : false);
    
    if (isValid) {
      
      contraste.setCoefficient(coefficient);
      contraste.setSeuil(seuil);
    }
    
    return isValid;
  }
  
  /**
   * Méthode permettant de définir la plage horaire d'un contraste.
   * 
   * @param contraste Contraste dont on souhaite définir la plage horaire.
   * @param dateDebut Date de début de la plage horaire.
   * @param dateFin Date de fin de la plage horaire.
   * 
   * @return Retourne vrai si le changement a bien était éffectué.
   */
  public boolean definirPlageHoraire(Contraste contraste, Date dateDebut, Date dateFin) {
    
    boolean isValid = (dateDebut.before(dateFin) && dateFin.before(new Date()) ? true : false);
    
    if (isValid) {
      PlageHoraire plageHoraire = new PlageHoraire();
      plageHoraire.setDateDebut(dateDebut);
      plageHoraire.setDateFin(dateFin);
      
      contraste.setPlageHoraire(plageHoraire);
    }
    
    return isValid;
  }
  
  /**
   * Méthode permettant de définir une zone de prévision.
   * 
   * @param contraste Contraste dont on souhaite définir la zone de prévision.
   * @param lonHg Longitude du point haut gauche de la zone de prévision.
   * @param latHg Latitude du point haut gauche de la zone de prévision.
   * @param lonBd Longitude du point bas droit de la zone de prévision.
   * @param latBd Latitude du point bas droit de la zone de prévision.
   * 
   * @return Retourne vrai si le changement a bien était éffectué.
   */
  public boolean definirZonePrevision(Contraste contraste, 
                                      double lonHg, double latHg, double lonBd, double latBd) {
    
    boolean isValid = (isLongitudeOrLattitude(lonHg) && isLongitudeOrLattitude(latHg) 
                    && isLongitudeOrLattitude(lonBd) && isLongitudeOrLattitude(latBd) 
                    ? true : false);
    
    if (isValid) {
      ZonePrevision zonePrevision = new ZonePrevision();
      zonePrevision.setLonHg(lonHg);
      zonePrevision.setLatHg(latHg);
      zonePrevision.setLonBd(lonBd);
      zonePrevision.setLatBd(latBd);
      
      contraste.setZonePrevision(zonePrevision);
    }
    
    return isValid;
  }
  
  private boolean isLongitudeOrLattitude(double value) {
    
    return (value >= -180 && value <= 180 ? true : false);
  }
}
