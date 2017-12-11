/*
 * Copyright (C) 2012 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package view;

import javax.swing.JPanel;

import gov.nasa.worldwind.Configuration;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.globes.EarthFlat;
import gov.nasa.worldwind.globes.FlatGlobe;
import gov.nasa.worldwind.globes.projections.ProjectionMercator;
import gov.nasa.worldwind.layers.mercator.MercatorSector;

/**
 * @see gov.nasa.worldwind.globes.FlatGlobe
 * @see EarthFlat
 */
public class MeteoView extends JPanel {

  public static class AppFrame extends ApplicationTemplate.AppFrame {
    private WorldWindow wwd;

    public AppFrame(WorldWindow wwd) {

      this.wwd = wwd;
      this.beFlat();
    }

    public void beFlat() {
      FlatGlobe flatGlobe = (FlatGlobe) wwd.getModel().getGlobe();
      flatGlobe.setProjection(new ProjectionMercator());

      this.wwd.redraw();
    }

    public static void main(String[] args) {
      // Adjust configuration values before instantiation
      Configuration.setValue(AVKey.GLOBE_CLASS_NAME, EarthFlat.class.getName());
      Configuration.setValue(AVKey.GEOGRAPHIC_PROJECTION_CLASS_NAME, MercatorSector.class.getName());
      // start("World Wind Flat World", AppFrame.class);

    }
  }
}
