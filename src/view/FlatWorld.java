/*
 * Copyright (C) 2012 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JMenuBar;

import facade.MeteoFacade;
import gov.nasa.worldwind.Configuration;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.EarthFlat;
import gov.nasa.worldwind.layers.LatLonGraticuleLayer;
import gov.nasa.worldwind.layers.RenderableLayer;
import model.WindBarb;

/**
 * Example of displaying a flat globe instead of a round globe. The flat globe
 * displays elevation in the same way as the round globe (mountains rise out of
 * the globe). One advantage of using a flat globe is that a user can see the
 * entire globe at once. The globe can be configured with different map
 * projections.
 *
 * @author Patrick Murris
 * @version $Id: FlatWorld.java 2219 2014-08-11 21:39:44Z dcollins $
 * @see gov.nasa.worldwind.globes.FlatGlobe
 * @see EarthFlat
 */
public class FlatWorld extends ApplicationTemplate {
  public static view.FlatWorld.AppFrame frame;
  protected static JMenuBar menuBar;
  protected static JButton btnEdition;
  protected static JButton btnModification;
  protected static JButton btnImporter;

  /**
   * @wbp.parser.entryPoint
   */
  public static void main(String[] args) { // Adjust configuration values before instantiation
    Configuration.setValue(AVKey.GLOBE_CLASS_NAME, EarthFlat.class.getName());
    Configuration.setValue(AVKey.PROJECTION_NAME,
        gov.nasa.worldwind.globes.projections.ProjectionMercator.class.getName());
    frame = (AppFrame) start("World Wind Flat World", AppFrame.class);
  }

  public static view.ApplicationTemplate.AppFrame getFrame() {
    return frame;
  }

  public static class AppFrame extends ApplicationTemplate.AppFrame {
    RenderableLayer windBarbLayer;

    public AppFrame() {
      this.init();
    }

    protected void init() {
      // menu
      menuBar = new JMenuBar();
      this.setJMenuBar(menuBar);

      btnModification = new JButton("Modification");
      menuBar.add(btnModification);

      btnEdition = new JButton("Edition");
      menuBar.add(btnEdition);
      btnEdition.setMnemonic(KeyEvent.VK_I);
      btnEdition.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          new Edition();
        }
      });

      btnImporter = new JButton("Importer fichier grib");
      menuBar.add(btnImporter);
      btnImporter.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          MeteoFacade.getInstance().loadGrib("TTxOcMxLToSYmtRzKDl0e75I4HAjqqDApv_.grb");
        }
      });

      // Initialise graticule
      insertBeforePlacenames(getWwd(), new LatLonGraticuleLayer());
      // Initialise layer
      windBarbLayer = new RenderableLayer();
      windBarbLayer.setName("Winds");

      insertBeforePlacenames(getWwd(), windBarbLayer);

      // Initialise point of view
      getWwd().getView().setEyePosition(Position.fromDegrees(48.39039, -4.486076, 42000));
    }

    public void displayWindbarbs(ArrayList<WindBarb> windbarbs) {
      for (WindBarb windBarb : windbarbs) {

        windBarbLayer.addRenderable(windBarb);
      }
      windBarbLayer.addRenderable(new WindBarb(Position.fromDegrees(48, -4), 25.0, 25.0));

    }
  }
}
