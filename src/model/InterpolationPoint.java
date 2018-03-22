package model;

import java.util.Date;

import facade.MeteoFacade;

public class InterpolationPoint {


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
		System.out.println("taille longeur : "+(p.getGrille().getLonHautGauche()+(p.getGrille().getDx()*p.getGrille().getNbx())));
		System.out.println("taille lateur : "+(p.getGrille().getLatHautGauche()+(p.getGrille().getDy()*p.getGrille().getNby())));
		System.out.println("x : "+x);
		System.out.println("y : "+y);
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
				
				//calcul qui pu
				double dir = (((xBB-posxGrille)/(xBB-xB))*prev.getVents()[yA][xB].getDirection()+((posxGrille-xB)/(xBB-xB))*prev.getVents()[yA][xBB].getDirection())*((yAA-posyGrille)/(yAA-yA))+ (((xBB-posxGrille)/(xBB-xB))*prev.getVents()[yAA][xB].getDirection()+((posxGrille-xB)/(xBB-xB))*prev.getVents()[yAA][xBB].getDirection())*((posyGrille-yA)/(yAA-yA));
				double vitesse = (((xBB-posxGrille)/(xBB-xB))*prev.getVents()[yA][xB].getVitesse()+((posxGrille-xB)/(xBB-xB))*prev.getVents()[yA][xBB].getVitesse())*((yAA-posyGrille)/(yAA-yA))+ (((xBB-posxGrille)/(xBB-xB))*prev.getVents()[yAA][xB].getVitesse()+((posxGrille-xB)/(xBB-xB))*prev.getVents()[yAA][xBB].getVitesse())*((posyGrille-yA)/(yAA-yA));
			
				
				wind = new Vent(vitesse, dir, 4);
		        barbule = new WindBarb(y, x, wind.getDirection(), wind.getVitesse());

			}else {
				ret=false;
			}
			
		}
		
		return ret;
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
