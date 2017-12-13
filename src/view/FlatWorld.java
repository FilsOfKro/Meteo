/*
 * Copyright (C) 2012 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package view;

import facade.MeteoFacade;
import gov.nasa.worldwind.Configuration;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.EarthFlat;
import gov.nasa.worldwind.globes.projections.ProjectionMercator;
import gov.nasa.worldwind.globes.projections.ProjectionModifiedSinusoidal;
import gov.nasa.worldwind.layers.LatLonGraticuleLayer;
import gov.nasa.worldwind.layers.RenderableLayer;

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
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

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
  
  protected static final String SURFACE_POLYGON_IMAGE_PATH = "gov/nasa/worldwindx/examples/images/georss.png";
  
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
    Configuration.setValue(AVKey.PROJECTION_NAME, ProjectionMercator.class.getName());
    frame = (AppFrame) start("World Wind Flat World", AppFrame.class);
  }

  public static view.ApplicationTemplate.AppFrame getFrame() {
    return frame;
  }

  public static class AppFrame extends ApplicationTemplate.AppFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    RenderableLayer windBarbLayer;

    public AppFrame() {
      this.makePaths();
    }

    protected void makePaths() {
      insertBeforePlacenames(getWwd(), new LatLonGraticuleLayer());
      RenderableLayer layer = new RenderableLayer();
      layer.setName("Paths");
      ArrayList<RenderableLayer> layers = new ArrayList<>();
      layers.add(layer);
      List l = new List();
      l.add(layer.getName());
      this.getControlPanel().add(l);
      
    //menu
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
      
      JPanel jpl = new JPanel();
      menuBar.add(jpl);

      JLabel lblDate = new JLabel("Date sélectionnée : ");
      lblDate.setHorizontalAlignment(SwingConstants.CENTER);
      jpl.add(lblDate);
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
                lblDate.setAlignmentY(CENTER_ALIGNMENT);
                mnDate.add(da);
              }
            }
          } catch (URISyntaxException e1) {
            e1.printStackTrace();
          }
        }
      });

      locations = Arrays.asList(Position.fromDegrees(20, -170, 100e3), Position.fromDegrees(15, 170, 100e3),
          Position.fromDegrees(10, -175, 100e3), Position.fromDegrees(5, 170, 100e3),
          Position.fromDegrees(0, -170, 100e3), Position.fromDegrees(20, -170, 100e3));
      shape = new Path(locations);
      shape.setAttributes(attrs);
      layer.addRenderable(shape);

      // Path around the north pole.
      attrs = new BasicShapeAttributes();
      attrs.setOutlineMaterial(new Material(WWUtil.makeColorBrighter(Color.GREEN)));
      attrs.setInteriorOpacity(0.5);
      attrs.setOutlineOpacity(0.8);
      attrs.setOutlineWidth(3);

      locations = Arrays.asList(Position.fromDegrees(80, 0, 100e3), Position.fromDegrees(80, 90, 100e3),
          Position.fromDegrees(80, 180, 100e3),
          // Position.fromDegrees(80, -180, 100e3),
          Position.fromDegrees(80, -90, 100e3), Position.fromDegrees(80, 0, 100e3));
      shape = new Path(locations);
      shape.setAttributes(attrs);
      layer.addRenderable(shape);

      // this.controlPanel.add(new view.LayerPanel(getWwd()), BorderLayout.SOUTH);
      ApplicationTemplate.insertBeforePlacenames(this.getWwd(), layer);

      // Initialise point of view
      getWwd().getView().setEyePosition(Position.fromDegrees(48.39039, -4.486076, 42000));
    }
  }

    /**
     * Affiche les barbules sur la carte.
     * 
     * @param windbarbs
     *          La liste des barbules à afficher
     */
    public void displayWindbarbs(ArrayList<WindBarb> windbarbs) {
      // TODO : Be sure this clears the old windbarb off the ram
      windBarbLayer.dispose();
      for (WindBarb windBarb : windbarbs) {
        windBarbLayer.addRenderable(windBarb);
      }
    }
  }
}
