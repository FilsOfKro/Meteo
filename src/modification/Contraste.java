package modification;

public class Contraste {

  private double seuil;
  private double coefficient;
  
  private ZonePrevision zonePrevision;
  private PlageHoraire plageHoraire;
  
  private static volatile Contraste instance = null;
  
  private Contraste(){
  }
  
  public double getSeuil() {
    
    return seuil;
  }
  
  public void setSeuil(double seuil) {
    
    this.seuil = seuil;
  }
  
  public double getCoefficient() {
    
    return coefficient;
  }
  
  public void setCoefficient(double coefficient) {
    
    this.coefficient = coefficient;
  }
  
  public ZonePrevision getZonePrevision() {
    
    return zonePrevision;
  }

  public void setZonePrevision(ZonePrevision zonePrevision) {
    
    this.zonePrevision = zonePrevision;
  }

  public PlageHoraire getPlageHoraire() {
    
    return plageHoraire;
  }

  public void setPlageHoraire(PlageHoraire plageHoraire) {
    
    this.plageHoraire = plageHoraire;
  }

  /**
   * Méthode retournant l'instance de la classe, si elle n'existe pas, elle est créée. 
   * 
   * @return L'instance de la classe.
   */
  public static final Contraste getInstance() {
    
    if (Contraste.instance == null) {
      
      Contraste.instance = new Contraste();
    }
    
    return Contraste.instance;
  }
}
