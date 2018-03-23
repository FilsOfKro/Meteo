package math;

public interface VecteurCalcul {

	  /**
	   * Calcule la direction en fonction des deux vecteurs en entrée
	   * La direction est exprimée en degrés
	   * @param vecteurU
	   * @param vecteurV
	   * @return
	   */
	  public double calculerDirection(double vecteurU, double vecteurV);
	  
	  /**
	   * Calcule la vitesse en fonction des deux vecteurs en entrée
	   * La vitesse est calculée en mètres par seconde
	   * @param vecteurU
	   * @param vecteurV
	   * @return
	   */
	  public double calculerVitesse(double vecteurU, double vecteurV);
	  
	  public double calculerComposanteU(double vitesse, double direction);
	  
	  public double calculerComposanteV(double vitesse, double direction);
}
