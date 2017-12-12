package facade;

import view.FlatWorld;

public class Launcher {

  public static void main(String[] args) {
    FlatWorld.main(args);
    Facade facade = new Facade();

  }

}
