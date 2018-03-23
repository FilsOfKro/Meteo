package model;

import java.util.Date;

import facade.MeteoFacade;
import math.Vecteur;
import math.VecteurCalcul;
import math.impl.VecteurCalculImpl;

public class InterpolationPoint{


	private Vent wind;
	private WindBarb barbule;
	
	
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
		
		//recuperation des composantes U et V des différents vecteurs passé en parametres
		double Uv1 = calc.calculerComposanteU(v1.getVitesse(), v1.getDirection());
		double Vv1 = calc.calculerComposanteV(v1.getVitesse(), v1.getDirection());
		double Uv2 = calc.calculerComposanteU(v1.getVitesse(), v1.getDirection());
		double Vv2 = calc.calculerComposanteV(v1.getVitesse(), v1.getDirection());
		double Uv3 = calc.calculerComposanteU(v1.getVitesse(), v1.getDirection());
		double Vv3 = calc.calculerComposanteV(v1.getVitesse(), v1.getDirection());
		double Uv4 = calc.calculerComposanteU(v1.getVitesse(), v1.getDirection());
		double Vv4 = calc.calculerComposanteV(v1.getVitesse(), v1.getDirection());

		//creation du vent se trouvant à l'intersection des 4 vecteurs
		double UDeVentInterpoler = (Uv1+Uv2+Uv3+Uv4)/4;
		double VDeVentInterpoler = (Vv1+Vv2+Vv3+Vv4)/4;
		ventInterpoler = new Vent(UDeVentInterpoler, VDeVentInterpoler);
		
		return ventInterpoler;
	}
	public Vent getWind() {
		return wind;
	}

	public void setWind(Vent wind) {
		this.wind = wind;
	}

	public WindBarb getBarbule() {
		return barbule;
	}

	public void setBarbule(WindBarb barbule) {
		this.barbule = barbule;
	}

	
	

}
