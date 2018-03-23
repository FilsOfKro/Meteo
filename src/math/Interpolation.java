package math;

import math.impl.VecteurCalculImpl;
import model.Vent;
import model.WindBarb;

public interface Interpolation {
		/**
		 * creer un vent en fonction de 4 vecteurs
		 * @param v1 premier vecteur 
		 * @param v2 second vecteur
		 * @param v3 troisieme vecteur 
		 * @param v4 quatrieme vecteur
		 * @return le vent calculer par rapport aux 4 autres vecteurs
		 */
		public Vent interpolerUnVentGraceASeseVoisin(Vecteur v1,Vecteur v2, Vecteur v3, Vecteur v4);
		/**
		 * rend le vent
		 * @return le vent
		 */
		public Vent getVent();
}
