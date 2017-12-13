package facade;

public class Launcher {

  /**
   * Lanceur de l'application meteo.
   * @param args les arguments passés à l'application
   */
  public static void main(String[] args) {
    MeteoFacade facade = MeteoFacade.getInstance();
  }

}
