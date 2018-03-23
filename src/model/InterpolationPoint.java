package model;

import java.util.Date;

import facade.MeteoFacade;

public class InterpolationPoint{


	private Vent wind;
	private WindBarb barbule;
	
	/**
	 * function that allows the user to interpolate a point 
	 * @param p.getGrille() the grid of winds to see if the point is in it
	 * @param x the longitude of the point to interpolate
	 * @param y the latitude of the point to interpolate
	 * @param d the date of the point to interpolate
	 * @return true if the interpolation has been made, false else
	 */
	
	public boolean InterpolateOneWind(Prevision p, double x, double y, Date d) {
		
		boolean ret = true;
		
		if((x < p.getGrille().getLonHautGauche()) && (y < p.getGrille().getLatHautGauche())) {
			ret = false;
		}
		if((x >= (p.getGrille().getLonHautGauche()+(p.getGrille().getDx()*p.getGrille().getNbx()))) && (y >= (p.getGrille().getLatHautGauche()+(p.getGrille().getDy()*p.getGrille().getNby())))) {
			ret = false;
		}
		
		if(ret) {
			
			//on positionne le point � interpoler dans la grille
			double posx = x-p.getGrille().getLonHautGauche();
			double posy = y-p.getGrille().getLatHautGauche();
			//on cherche entre quel colonne et ligne se trouve le point
			double posxGrille = posx;
			double posyGrille = posy;
			//recherche des points voisin
			
			int xB = (int) Math.floor(posxGrille);
			int xBB = (int) (posxGrille+p.getGrille().getDx());
			
			int yA = (int) Math.floor(posyGrille);
			int yAA = (int) (posyGrille + p.getGrille().getDy());

			//creation du vent creer par interpolation grace au coordonn�es
			PrevisionParDate prev = p.getPrevisionParDate(d);
			if(prev != null) {
				//https://fr.wikihow.com/faire-une-double-interpolation-lin�aire
				System.out.println(prev.toString());
				
				//calcul qui pu utilisation fct
				
				wind = null;
		        barbule = new WindBarb(y, x, wind.getDirection(), wind.getVitesse());

			}else {
				ret=false;
			}
			
		}
		
		return ret;
	}

	
	public Vent interpolerUnVentGraceASeseVoisin(Vent v1,Vent v2, Vent v3, Vent v4) {
		
		Vent ventInterpoler;
		
		double Uv1 = calculerComposanteU(v1.getVitesse(), v1.getDirection());
		double Vv1 = calculerComposanteU(v1.getVitesse(), v1.getDirection());
		double Uv2 = calculerComposanteU(v1.getVitesse(), v1.getDirection());
		double Vv2 = calculerComposanteU(v1.getVitesse(), v1.getDirection());
		double Uv3 = calculerComposanteU(v1.getVitesse(), v1.getDirection());
		double Vv3 = calculerComposanteU(v1.getVitesse(), v1.getDirection());
		double Uv4 = calculerComposanteU(v1.getVitesse(), v1.getDirection());
		double Vv4 = calculerComposanteU(v1.getVitesse(), v1.getDirection());

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
