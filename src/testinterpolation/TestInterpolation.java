package testinterpolation;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import model.Grille;
import model.InterpolationPoint;
import model.Prevision;
import model.PrevisionParDate;
import model.Vent;

/**
 * @author Utilisateur
 *
 */
public class TestInterpolation {
	protected Vent v1;
	protected Vent v2;
	protected Vent v3;
	protected Vent v4;
	protected Grille g;
	protected InterpolationPoint vent_sup;
	protected Date d;
	protected Prevision maprevision;
	
	@Test
	public void testPointVentIdentique() {
		g = new Grille(5, 5	, 2.0, 5.0, 5, 2, false);
		
		maprevision = new Prevision(g);
		
		d = new Date();
		
		v1 = new Vent(25.0, 25.0, 1);
		v2 = new Vent(25.0, 25.0,1);
		v3 = new Vent(25.0, 25.0,1);
		v4 = new Vent(25.0, 25.0,1);
		
		vent_sup = new InterpolationPoint();
		
		PrevisionParDate ppd = maprevision.buildPrevisionParDate(d);
		
		maprevision.addPrevision(ppd);
		
		ppd.addVent(1, 0, v1);
		ppd.addVent(1, 2, v2);
		ppd.addVent(3, 0, v3);
		ppd.addVent(3, 2, v4);
		
		vent_sup.InterpolateOneWind(maprevision, 2.0, 1.0, d);
		
		assertTrue((vent_sup.getWind().getVitesse() == v1.getVitesse()) 
				&& (vent_sup.getWind().getDirection() == v1.getDirection()));
	}
	
}
