package math;

public interface VecteurCalcul {

  /**
   * Calcule la direction en fonction des deux vecteurs en entrée L'unité de la direction retournée
   * est en degré
   *
   * @param vecteurU
   * @param vecteurV
   * @return
   */
  public double calculerDirection(double vecteurU, double vecteurV);

  /**
   * Calcule la vitesse en fonction des deux vecteurs en entrée La vitesse est calculée en mètres
   * par seconde
   * 
   * @param vecteurU
   * @param vecteurV
   * @return
   */
  public double calculerVitesse(double vecteurU, double vecteurV);

  /**
   * Calculer la composante U (axe x) d'un vecteur.
   *
   * @param force La force (ou vitesse) du vecteur
   * @param direction La direction du vecteur
   * @return La composante U
   */
  public double calculerComposanteU(double force, double direction);

  /**
   * Calculer la composante V (axe y) d'un vecteur.
   *
   * @param force La force (ou vitesse) du vecteur
   * @param direction La direction du vecteur
   * @return La composante V
   */
  public double calculerComposanteV(double force, double direction);
}
