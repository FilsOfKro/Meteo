package model;

public class Vent {
  private double direction;
  private double vitesse;
  
  /**
   * Used for cloning purpose
   */
  private Vent() {
	  
  }
  
  /**
   * Build a wind from two vectors, U and V
   * Calculates the wind's direction and velocity from these two vectors (see {@link Vent#calculerDirection(double, double)} and {@link Vent#calculerVitesse(double, double)}
   * @param ventU
   * @param ventV
   */
  public Vent(double ventU, double ventV) {
    this.direction = Vent.calculerDirection(ventU, ventV);
    this.vitesse = Vent.calculerVitesse(ventU, ventV);
  }
  
  /**
   * Build a wind directly from its velocity and direction
   * The third parameter is useless and only serves the purpose of having a different signature
   * @param vit
   * @param dir
   * @param a Any value, will not change the resulting wind
   */
  public Vent(double vit, double dir, int a) {
	  this.vitesse = vit;
	  this.direction = dir;
	  }
  
  public double getDirection() {
    return direction;
  }
  
  public double getVitesse() {
    return vitesse;
  }
  
  /**
   * Calcule la direction en fonction des deux vecteurs en entrée
   * La direction est exprimée en degrés
   * @param vecteurU
   * @param vecteurV
   * @return
   */
  public static double calculerDirection(double vecteurU, double vecteurV) {
    return (Math.atan2(vecteurU, vecteurV) + Math.PI) * 180 / Math.PI;
  }
  
  /**
   * Calcule la vitesse en fonction des deux vecteurs en entrée
   * La vitesse est calculée en mètres par seconde
   * @param vecteurU
   * @param vecteurV
   * @return
   */
  public static double calculerVitesse(double vecteurU, double vecteurV) {
    return Math.sqrt(Math.pow(vecteurU, 2) + Math.pow(vecteurV, 2));
  }
  
  /**
   * Retourne une représentation en chaine de caractère de ce vent.
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("Direction=");
    
    sb.append(direction).append(" | Vitesse=").append(vitesse);
    
    return sb.toString();
  }
  
  public void setVitesse(double vitesse) {
    
    this.vitesse = vitesse;
  }
  
  @Override
	protected Object clone() throws CloneNotSupportedException {
		Vent v = new Vent();
		v.direction = this.direction;
		v.vitesse = this.vitesse;
		return v;
	}
}
