package model;

public class Util {
  public static final Double MS_TO_KMH = 3.6;
  public static final Double MS_TO_KN = 1.94384;
  
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
  
  public static Double kmhToKnot(Double speed) {
    return kmhToMs(speed) * MS_TO_KN;
  }
  
  public static Double kmhToMs(Double speed) {
    return speed / MS_TO_KMH;
  }
}
