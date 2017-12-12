package model;

public class Grille {

  private int nbx;
  private int nby;

  private double lonHG;
  private double latHG;

  private double dx;
  private double dy;

  public Grille(int nbx, int nby, double lonHG, double latHG, double dx, double dy) {
    this.nbx = nbx;
    this.nby = nby;
    this.lonHG = lonHG;
    this.latHG = latHG;
    this.dx = dx;
    this.dy = dy;
  }

  public int getNbx() {
    return nbx;
  }

  public void setNbx(int nbx) {
    this.nbx = nbx;
  }

  public int getNby() {
    return nby;
  }

  public void setNby(int nby) {
    this.nby = nby;
  }

  public double getLonHG() {
    return lonHG;
  }

  public void setLonHG(double lonHG) {
    this.lonHG = lonHG;
  }

  public double getLatHG() {
    return latHG;
  }

  public void setLatHG(double latHG) {
    this.latHG = latHG;
  }

  public double getDx() {
    return dx;
  }

  public void setDx(double dx) {
    this.dx = dx;
  }

  public double getDy() {
    return dy;
  }

  public void setDy(double dy) {
    this.dy = dy;
  }

  /**
   * Retourne la latitude d'un point dans la grille de vents à partir de sa coordonnée Y et des paramètres de la grille.
   * @param y la position Y du point recherché
   * @return
   */
  public double getLatitude(int y) {
    return latHG + (y * dy);
  }

  /**
   * Retourne la longitude d'un point dans la grille de vents à partir de sa coordonnée X et des paramètres de la grille.
   * @param x la position X du point recherché
   * @return
   */
  public double getLongitude(int x) {
    return lonHG + (x * dx);
  }

  public String toString() {
    StringBuilder sb = new StringBuilder("");

    sb.append("\tNombre de x : ").append(nbx).append("\n");
    sb.append("\tNombre de y : ").append(nby).append("\n");

    sb.append("\tLatitude du point en haut à gauche : ").append(latHG).append("\n");
    sb.append("\tLongitude du point en haut à gauche : ").append(lonHG).append("\n");

    sb.append("\tDelta en x : ").append(dx).append("\n");
    sb.append("\tDelta en y : ").append(dy);

    return sb.toString();
  }

}
