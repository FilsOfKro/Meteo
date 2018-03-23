package math;

public interface Vecteur {

  /**
   * Retourne la composante U (axe x) du vecteur.
   *
   * @return La composante U
   */
  public double getComposanteU();

  /**
   * Retourne la composante V (axe y) du vecteur.
   *
   * @return La composante V
   */
  public double getComposanteV();

  /**
   * Retourne la vitesse du vecteur.
   *
   * @return La force du vecteur.
   */
  public double getVitesse();

  /**
   * Retourne la direction du vecteur.
   *
   * @return La direction du vecteur.
   */
  public double getDirection();
}
