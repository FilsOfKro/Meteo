package math.impl;

import math.VecteurCalcul;

public class VecteurCalculImpl implements VecteurCalcul{

	@Override
	public double calculerDirection(double vecteurU, double vecteurV) {
	    return (Math.atan2(vecteurU, vecteurV) + Math.PI) * 180 / Math.PI;
	}

	@Override
	public double calculerVitesse(double vecteurU, double vecteurV) {
	    return Math.sqrt(Math.pow(vecteurU, 2) + Math.pow(vecteurV, 2));
	}

	@Override
	public double calculerComposanteU(double vitesse, double direction) {
		return vitesse * Math.cos(direction);

	}

	@Override
	public double calculerComposanteV(double vitesse, double direction) {
		return vitesse * Math.sin(direction);
	}

}
