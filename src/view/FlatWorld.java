/*
 * Copyright (C) 2012 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

import facade.MeteoFacade;
import gov.nasa.worldwind.Configuration;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.EarthFlat;
import gov.nasa.worldwind.layers.LatLonGraticuleLayer;
import gov.nasa.worldwind.layers.RenderableLayer;
import model.Prevision;
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
  protected static JMenu mnDate;
  protected static JMenuItem mntmDate;
  protected static JMenuItem mntmDate_1;
  protected static JMenuItem mntmDate_2;

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
          // new Edition();
        }
      });

      btnImporter = new JButton("Importer fichier grib");
      menuBar.add(btnImporter);

      mnDate = new JMenu("Date");
      menuBar.add(mnDate);
      this.setVisible(true);

      JLabel label = new JLabel(
          "                                                                                                     ");
      menuBar.add(label);

      JLabel lblDate = new JLabel("Date sélectionnée : ");
      menuBar.add(lblDate);
      btnImporter.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            // On récupère le chemin du projet pour lancer le FileChooser à cet endroit là
            URL u = getClass().getProtectionDomain().getCodeSource().getLocation();
            File f = new File(u.toURI());

            final JFileChooser gribFileChooser = new JFileChooser();
            gribFileChooser.setCurrentDirectory(f.getParentFile());
            
            FileNameExtensionFilter filter = new FileNameExtensionFilter("GRIB FILES", "grb");
            gribFileChooser.setFileFilter(filter);
            
            int returnVal = gribFileChooser.showOpenDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
              File file = gribFileChooser.getSelectedFile();

              System.out.println("Opening: " + file.getAbsolutePath());
              Prevision prevv = MeteoFacade.getInstance().loadGrib(file.getAbsolutePath());
              List<Date> dates = MeteoFacade.getInstance()
                  .getDates(prevv);
              mnDate.removeAll();
              for (Date d : dates) {
                JMenuItem da = new JMenuItem(d.toString());

                da.addActionListener(new ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent e) {
                    JMenuItem selected = (JMenuItem) e.getSource();
                    System.out.println(selected.getText());
                    MeteoFacade.getInstance().displayDate(prevv, d);
                    lblDate.setText("Date sélectionnée : " + d.toString());
                  }
                });
                lblDate.setText("Date sélectionnée : " + dates.get(0).toString());
                mnDate.add(da);
              }
            }
          } catch (URISyntaxException e1) {
            e1.printStackTrace();
          }
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

    /**
     * Affiche les barbules sur la carte.
     * 
     * @param windbarbs
     *          La liste des barbules à afficher
     */
    public void displayWindbarbs(ArrayList<WindBarb> windbarbs) {
      windBarbLayer.dispose();
      for (WindBarb windBarb : windbarbs) {
        windBarbLayer.addRenderable(windBarb);
      }
    }
  }
}
