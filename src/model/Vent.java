package model;

import math.Vecteur;
import math.VecteurCalcul;
import math.impl.VecteurCalculImpl;

public class Vent implements Vecteur {
	private double direction;
	private double vitesse;

	/**
	 * Build a wind from two vectors, U and V
	 * Calculates the wind's direction and velocity from these two vectors (see {@link Vent#calculerDirection(double, double)} and {@link Vent#calculerVitesse(double, double)}
	 * @param ventU
	 * @param ventV
	 */
	@Deprecated
	public Vent(double composanteU, double composanteV) {
		VecteurCalcul calc = new VecteurCalculImpl();

		this.direction = calc.calculerDirection(composanteU, composanteV);
		this.vitesse = calc.calculerVitesse(composanteU, composanteV);
	}

	/**
	 *  Build an empty wind
	 */
	private Vent() {
		
	}
	
	/**
	 * Build a wind from two vectors, U and V
	 * Calculates the wind's direction and velocity from these two vectors
	 * @param composanteU
	 * @param composanteV
	 */
	public static Vent buildVentFromUAndV(double composanteU, double composanteV) {
		return new Vent(composanteU, composanteV);
	}
	
	/**
	 * Build a wind from the velocity and the direction
	 * @param velocity
	 * @param direction
	 */
	public static Vent buildVentFromVelocityAndDirection(double velocity, double direction) {
		Vent ret = new Vent();
		
		ret.vitesse = velocity;
		ret.direction = direction;
		
		return ret;
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
