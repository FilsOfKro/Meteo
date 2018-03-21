package model;

import java.sql.Date;

import facade.MeteoFacade;

public class InterpolationPoint {


	private Vent wind;
	private WindBarb barbule;
	
	public boolean InterpolateOneWind(Grille g, double x, double y, Date d) {
		
		boolean ret = true;
		
		if((x < g.getLonHautGauche()) && (y < g.getLatHautGauche())) {
			ret = false;
		}
		if((x < (g.getLonHautGauche()+(g.getDx()*g.getNbx()))) && (y < (g.getLonHautGauche()+(g.getDy()*g.getNby())))) {
			ret = false;
		}
		
		if(ret) {
			
			//on positionne le point � interpoler dans la grille
			double posx = x-g.getLonHautGauche();
			double posy = y-g.getLatHautGauche();
			
			//on cherche entre quel colonne et ligne se trouve le point
			double posxGrille = posx/g.getDx();
			double posyGrille = posy/g.getDy();
			
			//recherche des points voisin
			//xb1 == partie entiere de posxGrille
			//xb2 == partie entiere de posxGrill + pasx
			//ya1 == partie entiere de posyGrille
			//ya2 == partie entiere de posygrille + pasy
			
			int xB = (int) posxGrille;
			int xBB = (int) (posxGrille+g.getDx());
			
			int yA = (int) posyGrille;
			int yAA = (int) (posyGrille + g.getDy());
			
			//creation du vent creer par interpolation grace au coordonn�es
			PrevisionParDate prev = MeteoFacade.getInstance().getCurrentPrevision().getPrevisionParDate(d);
			if(prev != null) {
				
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
