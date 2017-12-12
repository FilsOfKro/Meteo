package model;

public class Vent {
  private double ventU;
  private double ventV;
  
  public Vent(double ventU, double ventV) {
    this.ventU = ventU;
    this.ventV = ventV;
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
    
    sb.append(ventU).append(" | V=").append(ventV);
    
    return sb.toString();
  }
}
