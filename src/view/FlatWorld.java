/*
 * Copyright (C) 2012 United States Government as represented by the Administrator of the National
 * Aeronautics and Space Administration. All Rights Reserved.
 */

package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
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
import java.net.*;
import java.io.*;

/**
 * Example of displaying a flat globe instead of a round globe. The flat globe displays elevation in
 * the same way as the round globe (mountains rise out of the globe). One advantage of using a flat
 * globe is that a user can see the entire globe at once. The globe can be configured with different
 * map projections.
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
  protected static JButton btnDownload;
  protected static JMenu mnDate;
  protected static JMenuItem mntmDate;
  protected static JMenuItem mntmDate_1;
  protected static JMenuItem mntmDate_2;

  protected static ButtonGroup bg;
  protected static JRadioButton rdbtnNoeud;
  protected static JRadioButton rdbtnKmh;
  protected static JRadioButton rdbtnMs;

  protected static JCheckBox btnAdvancedMenu;
  protected static boolean advancedMode = true;

  /**
   * @wbp.parser.entryPoint
   */
  public static void main(String[] args) { // Adjust configuration values before instantiation
    Configuration.setValue(AVKey.GLOBE_CLASS_NAME, EarthFlat.class.getName());
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
      super(true, true, false);
      this.init();
    }

    protected void init() {
      // menu
      menuBar = new JMenuBar();
      this.setJMenuBar(menuBar);

      btnModification = new JButton("Modification");
      menuBar.add(btnModification);
      btnModification.setMnemonic(KeyEvent.VK_I);
      btnModification.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          new VueModification();
        }
      });

      btnEdition = new JButton("Edition");
      menuBar.add(btnEdition);

      btnImporter = new JButton("Importer fichier grib");
      menuBar.add(btnImporter);

      btnDownload = new JButton("Télécharger un fichier grib");
      menuBar.add(btnDownload);

      // echelle vitesse
      bg = new ButtonGroup();

      rdbtnNoeud = new JRadioButton("Noeud");
      bg.add(rdbtnNoeud);
      menuBar.add(rdbtnNoeud);

      rdbtnKmh = new JRadioButton("km/h");
      bg.add(rdbtnKmh);
      menuBar.add(rdbtnKmh);

      rdbtnMs = new JRadioButton("m/s");
      bg.add(rdbtnMs);
      menuBar.add(rdbtnMs);

      btnAdvancedMenu = new JCheckBox("Avancé", advancedMode);
      btnAdvancedMenu.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          toggleAdvancedMode();
        }
      });

      if (advancedMode) {
        addAdvancedMode();
      }

      rdbtnNoeud.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          MeteoFacade.getInstance().changeUnit(e);
        }
      });
      rdbtnKmh.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          MeteoFacade.getInstance().changeUnit(e);
        }
      });
      rdbtnMs.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          MeteoFacade.getInstance().changeUnit(e);
        }
      });



      mnDate = new JMenu("Date");
      menuBar.add(mnDate);

      menuBar.add(btnAdvancedMenu);

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
              MeteoFacade.getInstance().setCurrentPrevision(prevv);

              List<Date> dates = MeteoFacade.getInstance().getDates(prevv);
              mnDate.removeAll();
              for (Date d : dates) {
                JMenuItem da = new JMenuItem(d.toString());

                da.addActionListener(new ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent e) {
                    JMenuItem selected = (JMenuItem) e.getSource();
                    System.out.println(selected.getText());
                    MeteoFacade.getInstance().setCurrentDate(d);
                    MeteoFacade.getInstance().refreshWindbarbs();
                    lblDate.setText("Date sélectionnée : " + d.toString());
                  }
                });
                lblDate.setText(
                    "Date sélectionnée : " + MeteoFacade.getInstance().getCurrentDate().toString());
                lblDate.setAlignmentY(CENTER_ALIGNMENT);
                mnDate.add(da);
              }
            }
          } catch (URISyntaxException e1) {
            e1.printStackTrace();
          }
        }
      });

      // a l'appuye sur le btn  ouverture d'un s�lecteur pour selectinn�e une date voulu
      btnDownload.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String[] tabYear = {"2003","2004", "2005"};
            JComboBox yearField = new JComboBox(tabYear);
            
            String[] tabMonth = {"01", "02", "03", "04", "05", "06", "07", "08","09","10","11","12"};
            JComboBox monthField = new JComboBox(tabMonth);
            
            String[] tabDay = {"01", "02", "03", "04", "05", "06", "07", "08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
            JComboBox dayField = new JComboBox(tabDay);


            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Ann�e:"));
            myPanel.add(yearField);
            myPanel.add(new JLabel("Mois:"));
            myPanel.add(monthField);
            myPanel.add(new JLabel("jour:"));
            myPanel.add(dayField);

            int result = JOptionPane.showConfirmDialog(null, myPanel, "Entrer une date : ", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
               System.out.println("year value: " + yearField.getSelectedItem());
               System.out.println("month value: " + monthField.getSelectedItem());
               System.out.println("day value: " + dayField.getSelectedItem());

               String year = String.valueOf(yearField.getSelectedItem());
               String month = String.valueOf(monthField.getSelectedItem());
               String day = String.valueOf(dayField.getSelectedItem());

               System.out.println("DAAAAAATE=" + year + "/" + month + "/" + day);

               StringBuilder url = new StringBuilder().append("https://nomads.ncdc.noaa.gov/data/gfs/");
               url.append(year);
               url.append(month);
               url.append('/');
               url.append(year);
               url.append(month);
               url.append(day);
               boolean exist;
               
               //check if url exist
               
             	//envoi url avec jour pour savoir si il existe
                   url.append("/gfs-avn_201_");
                   url.append(year);
                   url.append(month);
                   url.append(day);
                   // url.append(gribZoneNumber);
                   // url.append('_');
                   url.append("_0000_000.grb");
                   String urlExist =  url.toString();
                   System.out.println("url to check : "+urlExist);

                   try {
                       HttpURLConnection.setFollowRedirects(false);
                       // note : you may also need
                       //        HttpURLConnection.setInstanceFollowRedirects(false)
                       HttpURLConnection con = (HttpURLConnection) new URL(urlExist).openConnection();
                       con.setRequestMethod("HEAD");
                       con.setInstanceFollowRedirects(false);
                       exist = (con.getResponseCode() != 404);
                       System.out.println("code de reponse : "+con.getResponseCode());
                       System.out.println("existe ou pas : " + exist);
                     }
                     catch (Exception e1) {
                        e1.printStackTrace();
                        System.out.println("la bite");
                        exist = false;
                     }
                   if(exist) {
                   try {
                     URL website = new URL(url.toString());
                     ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                     FileOutputStream fos = new FileOutputStream("latest.grb");
                     //recup taille file
                     //recup data t�l�charger
                     
                     // mettre �a dans un thread
                    // ImageIcon icon = new ImageIcon(Test.class.getResource("loading.gif").getFile());
                     
                     
                   System.out.println("debut tel");

                     fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                     System.out.println("fin tel");
                     fos.close();
                   } catch (IOException e2) {
                     // TODO Auto-generated catch block
                     e2.printStackTrace();
                   }

                   File file = new File("latest.grb");

                   System.out.println("Opening: " + file.getAbsolutePath());
                   //ici faut parser le fichier avec methode grib2
                   
                   Prevision prevv = MeteoFacade.getInstance().loadGrib(file.getAbsolutePath());
                   MeteoFacade.getInstance().setCurrentPrevision(prevv);

                   List<Date> dates = MeteoFacade.getInstance().getDates(prevv);
                   mnDate.removeAll();
                   for (Date d : dates) {
                     JMenuItem da = new JMenuItem(d.toString());

                     da.addActionListener(new ActionListener() {
                       public void actionPerformed(java.awt.event.ActionEvent e) {
                         JMenuItem selected = (JMenuItem) e.getSource();
                         System.out.println(selected.getText());
                         MeteoFacade.getInstance().setCurrentDate(d);
                         MeteoFacade.getInstance().refreshWindbarbs();
                         lblDate.setText("Date sélectionnée : " + d.toString());
                       }
                     });
                     lblDate.setText(
                         "Date sélectionnée : " + MeteoFacade.getInstance().getCurrentDate().toString());
                     lblDate.setAlignmentY(CENTER_ALIGNMENT);
                     mnDate.add(da);
                   }
               }else {
            	   JOptionPane errorPan= new JOptionPane();

            	   errorPan.showMessageDialog(null, "Le jeux de donn�e n'existe pas", "Erreur", JOptionPane.ERROR_MESSAGE);
               }
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
     * @param windbarbs La liste des barbules à afficher
     */
    public void displayWindbarbs(ArrayList<WindBarb> windbarbs) {
      // TODO : Be sure this clears the old windbarb off the ram
      windBarbLayer.dispose();
      for (WindBarb windBarb : windbarbs) {
        windBarbLayer.addRenderable(windBarb);
      }
    }


    protected void toggleAdvancedMode() {
      advancedMode = !advancedMode;

      if (advancedMode) {
        addAdvancedMode();
      } else {
        removeAdvancedMode();
      }

      this.revalidate();
      this.repaint();
    }

    protected void addAdvancedMode() {
      this.layerPanel = new view.LayerPanel(getWwd());
      this.controlPanel.add(this.layerPanel, BorderLayout.CENTER);
    }

    protected void removeAdvancedMode() {
      this.controlPanel.remove(this.layerPanel);
    }

  }
}
