package model;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import facade.MeteoFacade;

public class Util {
  public static final Double MS_TO_KMH = 3.6;
  public static final Double MS_TO_KN = 1.94384;
  
  public static final String KMH = "km/h";
  
  public static final String MS = "m/s";
  
  public static final String KNOTS = "Noeuds";
  
  public static Double msToKnots(Double speed) {
    return MS_TO_KN * speed;
  }
  
  public static Double msToKmh(Double speed) {
    return MS_TO_KMH * speed;
  }
  
  public static Double knotsToMs(Double speed) {
    return speed / MS_TO_KN;
  }
  
  public static Double knotsToKmh(Double speed) {
    return knotsToMs(speed) * MS_TO_KMH;
  }
  
  public static Double kmhToKnots(Double speed) {
    return kmhToMs(speed) * MS_TO_KN;
  }
  
  public static Double kmhToMs(Double speed) {
    return speed / MS_TO_KMH;
  }
  
  public static double convertWindSpeed(Vent vent, Util.UNIT newUnit) {
	  double vitesse = vent.getVitesse();
	  
	  double ret = vitesse;
	  
	  DecimalFormat df = new DecimalFormat("#.##");
	  df.setRoundingMode(RoundingMode.HALF_EVEN);
	  
	  Util.UNIT currentUnit = MeteoFacade.getInstance().defaultUnit;
	  switch (currentUnit) {
		case kmh:
			if (newUnit.equals(currentUnit)) {
				break;
			} else if (newUnit.equals(Util.UNIT.ms)){
				// ms
				ret = kmhToMs(vitesse);
			} else {
				// knots
				ret = kmhToKnots(vitesse);
			}
		case knots:
			if (newUnit.equals(currentUnit)) {
				// knots, do nothing
				break;
			} else if (newUnit.equals(Util.UNIT.ms)){
				// ms
				ret = knotsToMs(vitesse);
			} else {
				// kmh
				ret = knotsToKmh(vitesse);
			}
		case ms:
			if (newUnit.equals(currentUnit)) {
				// ms, do nothing
				break;
			} else if (newUnit.equals(Util.UNIT.knots)){
				// knots
				ret = msToKnots(vitesse);
			} else {
				// kmh
				ret = msToKmh(vitesse);
			}
		default:
			break;
	  
	  }
	  
	  return Double.parseDouble(df.format(ret));
  }
  
  public enum UNIT {
	  knots(Util.KNOTS),
	  ms(Util.MS),
	  kmh(Util.KMH);
	  
	  private UNIT(String text) {
		  this.text = text;
	  }
	  
	  public String text;
  }
}
