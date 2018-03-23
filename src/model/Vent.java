package model;

import math.Vecteur;
import math.VecteurCalcul;
import math.impl.VecteurCalculImpl;

public class Vent implements Vecteur {
	private double direction;
	private double vitesse;

	/**
	 * Used for cloning purpose
	 */
	public Vent() {

	}

	/**
	 * Build a wind from two vectors, U and V
	 * Calculates the wind's direction and velocity from these two vectors (see {@link Vent#calculerDirection(double, double)} and {@link Vent#calculerVitesse(double, double)}
	 * @param ventU
	 * @param ventV
	 */
	public Vent(double ventU, double ventV) {
		VecteurCalcul calc = new VecteurCalculImpl();

		this.direction = calc.calculerDirection(ventU, ventV);
		this.vitesse = calc.calculerVitesse(ventU, ventV);
	}

	/**
	 * Build a wind directly from its velocity and direction
	 * The third parameter is useless and only serves the purpose of having a different signature
	 * @param vit
	 * @param dir
	 * @param a Any value, will not change the resulting wind
	 */
	public Vent(double vit, double dir, int a) {
		this.vitesse = vit;
		this.direction = dir;
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {

		this.direction = direction;
	}

	public double getVitesse() {
		return vitesse;
	}

	public void setVitesse(double vitesse) {

		this.vitesse = vitesse ;
	}

	/**
	 * Retourne une représentation en chaine de caractère de ce vent.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder("Direction=");

		sb.append(direction).append(" | Vitesse=").append(vitesse);

		return sb.toString();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Vent v = new Vent();
		v.direction = this.direction;
		v.vitesse = this.vitesse;
		return v;
	}

	@Override
	public double getComposanteU() {
		VecteurCalcul calc = new VecteurCalculImpl();
		return calc.calculerComposanteU(vitesse, direction);
	}

	@Override
	public double getComposanteV() {
		VecteurCalcul calc = new VecteurCalculImpl();
		return calc.calculerComposanteV(vitesse, direction);
	}
}
