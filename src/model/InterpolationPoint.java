package model;

import math.Vecteur;
import math.VecteurCalcul;
import math.impl.VecteurCalculImpl;

public class InterpolationPoint implements math.Interpolation{


	private Vent wind;
	
	
	/**
	 * creer un vent en fonction de 4 vecteurs
	 * @param v1 premier vecteur 
	 * @param v2 second vecteur
	 * @param v3 troisieme vecteur 
	 * @param v4 quatrieme vecteur
	 * @return le vent calculer par rapport aux 4 autres vecteurs
	 */
	public Vent interpolerUnVentGraceASeseVoisin(Vecteur v1,Vecteur v2, Vecteur v3, Vecteur v4) {
		
		Vent ventInterpoler;
		VecteurCalcul calc = new VecteurCalculImpl();
		
		//recuperation des composantes U et V des diff�rents vecteurs pass� en parametres
		double Uv1 = calc.calculerComposanteU(v1.getVitesse(), v1.getDirection());
		double Vv1 = calc.calculerComposanteV(v1.getVitesse(), v1.getDirection());
		double Uv2 = calc.calculerComposanteU(v2.getVitesse(), v2.getDirection());
		double Vv2 = calc.calculerComposanteV(v2.getVitesse(), v2.getDirection());
		double Uv3 = calc.calculerComposanteU(v3.getVitesse(), v3.getDirection());
		double Vv3 = calc.calculerComposanteV(v3.getVitesse(), v3.getDirection());
		double Uv4 = calc.calculerComposanteU(v4.getVitesse(), v4.getDirection());
		double Vv4 = calc.calculerComposanteV(v4.getVitesse(), v4.getDirection());

		//creation du vent se trouvant � l'intersection des 4 vecteurs
		double UDeVentInterpoler = (Uv1+Uv2+Uv3+Uv4)/4;
		double VDeVentInterpoler = (Vv1+Vv2+Vv3+Vv4)/4;
		ventInterpoler = Vent.buildVentFromUAndV(UDeVentInterpoler, VDeVentInterpoler);
		
		return ventInterpoler;
	}
	
	/**
	 * retourne le vent
	 */
	public Vent getVent() {
		return wind;
	}

	

	
	

}
