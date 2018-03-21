package model;

public class Grille {

  private int nbx;
  private int nby;

  private double lonHautGauche;
  private double latHautGauche;

  private double dx;
  private double dy;

  private boolean uvOrienteEst;

  /**
   * Construit un objet Grille à partir des informations en paramètre.
   * 
   * Cet objet Grille permet de faire le lien entre la représentation logicielle des données (un tableau de vent à deux dimensions),
   *  et la représentation réelle des données, soit des points avec une latitude et une longitude 
   * 
   * @param nbx
   *          Le nombre de colonnes de la grille
   * @param nby
   *          Le nombre de lignes de la grille
   * @param lonHautGauche
   *          La longitude du point de référence en haut à gauche (0,0)
   * @param latHautGauche
   *          La latitude du point de référence en haut à gauche (0,0)
   * @param dx
   *          Le décalage (delta) horizontal entre chaque case
   * @param dy
   *          Le décalage (delta) vertical entre chaque case
   * @param uvOrienteEst
   *          Faux si l'orientation des vecteurs U et V suivent une architecture
   *          en grille
   */
  public Grille(int nbx, int nby, double lonHautGauche, double latHautGauche, double dx, double dy,
      boolean uvOrienteEst) {
    this.nbx = nbx;
    this.nby = nby;
    this.lonHautGauche = lonHautGauche;
    this.latHautGauche = latHautGauche;
    this.dx = dx;
    this.dy = dy;
    this.uvOrienteEst = uvOrienteEst;
  }

  /**
   * Le nombre de colonnes de la grille (donc du tableau)
   * @return
   */
  public int getNbx() {
    return nbx;
  }

  public void setNbx(int nbx) {
    this.nbx = nbx;
  }

  /**
   * Le nombre de lignes de la grille (donc du tableau)
   * @return
   */
  public int getNby() {
    return nby;
  }

  public void setNby(int nby) {
    this.nby = nby;
  }

  /**
   * Retourne la longitude du point en haut à gauche de la grille (0,0)
   * Sert de référence pour les calculs de longitude des points suivants
   * @return
   */
  public double getLonHautGauche() {
    return lonHautGauche;
  }

  public void setLonHautGauche(double lonHautGauche) {
    this.lonHautGauche = lonHautGauche;
  }

  /**
   * Retourne la latitude du point en haut à gauche de la grille (0,0)
   * Sert de référence pour calculs de latitude des points suivants
   * @return
   */
  public double getLatHautGauche() {
    return latHautGauche;
  }

  public void setLatHautGauche(double latHautGauche) {
    this.latHautGauche = latHautGauche;
  }

  /**
   * Présent dans les données GRIB mais on ne s'en sert pas
   * @return
   */
  public boolean isUvOrienteEst() {
    return uvOrienteEst;
  }

  public void setUvOrienteEst(boolean uvOrienteEst) {
    this.uvOrienteEst = uvOrienteEst;
  }

  /**
   * Le décalage horizontal entre 2 points 
   * Sert à calculer la longitude
   * @return
   */
  public double getDx() {
    return dx;
  }

  public void setDx(double dx) {
    this.dx = dx;
  }

  /**
   * Le décalage vertical entre 2 points
   * Sert à calculer la latitude
   * @return
   */
  public double getDy() {
    return dy;
  }

  public void setDy(double dy) {
    this.dy = dy;
  }

  /**
   * Retourne la latitude d'un point dans la grille de vents à partir de sa
   * coordonnée Y et des paramètres de la grille.
   * 
   * @param y
   *          la position Y du point recherché
   * @return La latitude calculée
   */
  public double getLatitude(int y) {
    return latHautGauche + (y * dy);
  }

  /**
   * Retourne la longitude d'un point dans la grille de vents à partir de sa
   * coordonnée X et des paramètres de la grille.
   * 
   * @param x
   *          la position X du point recherché
   * @return La longitude calculée
   */
  public double getLongitude(int x) {
    return lonHautGauche + (x * dx);
  }

  /**
   * Retourne une représentation en chaine de caractère de cette grille.
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("");

    sb.append("\tNombre de x : ").append(nbx).append("\n");
    sb.append("\tNombre de y : ").append(nby).append("\n");

    sb.append("\tLatitude du point en haut à gauche : ").append(latHautGauche).append("\n");
    sb.append("\tLongitude du point en haut à gauche : ").append(lonHautGauche).append("\n");

    sb.append("\tDelta en x : ").append(dx).append("\n");
    sb.append("\tDelta en y : ").append(dy);

    return sb.toString();
  }

}
