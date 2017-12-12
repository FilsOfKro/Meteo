package model;

public class Vent {
  private double direction;
  private double vitesse;
  
  public Vent(double ventU, double ventV) {
    this.direction = ventU;
    this.vitesse = ventV;
  }
  
  /* TODO faire ces deux méthodes */
  public double getDirection() {
    return 0.0;
  }
  
  public double getVitesse() {
    return 0.0;
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder("U=");
    
    sb.append(direction).append(" | V=").append(vitesse);
    
    return sb.toString();
  }
}
