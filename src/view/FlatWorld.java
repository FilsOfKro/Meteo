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
import gov.nasa.worldwind.layers.LatLonGraticuleLayer;
import gov.nasa.worldwind.layers.RenderableLayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import cursor.DateCursor;
import model.Prevision;
import model.Util;
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

	protected static JPanel jpl;

	protected static JMenuBar menuBar;
	protected static JButton btnEdition;
	protected static JButton btnModification;
	protected static JButton btnImporterGRIB;
	protected static JButton btnImporterXML;
	protected static JButton btnExporterXML;

	protected static JPanel menuDateJPanel;
	protected static JMenu mnDate;
	protected static JMenuItem mntmDate;

	protected static JPanel dateCursorPanel;
	protected static DateCursor dateCursor;

	protected static ButtonGroup bg;
	protected static JRadioButton rdbtnNoeud;
	protected static JRadioButton rdbtnKmh;
	protected static JRadioButton rdbtnMs;
	// protected static boolean rdbtn = true;

	protected static JCheckBox btnAdvancedMenu;
	protected static boolean advancedMode = false;
	
	protected static JComboBox listeDate;

	/**
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args) { // Adjust configuration values before instantiation
		Configuration.setValue(AVKey.GLOBE_CLASS_NAME, EarthFlat.class.getName());
		frame = (AppFrame) start("LEMOLAS Wind", AppFrame.class);

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

			jpl = new JPanel();

			// menu
			menuBar = new JMenuBar();
			this.setJMenuBar(menuBar);

			// options avancées
			btnAdvancedMenu = new JCheckBox("Avancé", advancedMode);
			jpl.add(btnAdvancedMenu);

			btnAdvancedMenu.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					toggleAdvancedMode();
				}
			});

			if (advancedMode) {
				addAdvancedMode();
			}
			
			btnEdition = new JButton("Edition");
			menuBar.add(btnEdition);
			jpl.add(btnEdition);

			// boutons
			btnModification = new JButton("Modification");
			menuBar.add(btnModification);
			jpl.add(btnModification);
			btnModification.setMnemonic(KeyEvent.VK_I);
			btnModification.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new VueModification();
				}
			});

		
			btnImporterGRIB = new JButton("Importer grib");
			menuBar.add(btnImporterGRIB);
			jpl.add(btnImporterGRIB);
			
			btnExporterXML = new JButton("Exporter xml");
			
			btnImporterXML = new JButton("Importer xml");
			menuBar.add(btnImporterXML);
			jpl.add(btnImporterXML);

			// echelle vitesse
			bg = new ButtonGroup();

			rdbtnNoeud = new JRadioButton("Noeud");
			bg.add(rdbtnNoeud);
			

			rdbtnKmh = new JRadioButton("km/h");
			bg.add(rdbtnKmh);
			

			rdbtnMs = new JRadioButton("m/s");
			bg.add(rdbtnMs);
			



		      if (MeteoFacade.getInstance().currentUnit.equals(Util.UNIT.knots)) {
		    	  	rdbtnNoeud.setSelected(true);
		      }
		      
		      rdbtnNoeud.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		          MeteoFacade.getInstance().changeUnitToKnot();
		        }
		      });
		      
		      if (MeteoFacade.getInstance().currentUnit.equals(Util.UNIT.kmh)) {
		    	  rdbtnKmh.setSelected(true);
		      }
		      rdbtnKmh.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		          MeteoFacade.getInstance().changeUnitToKmh();
		        }
		      });
		      
		      if (MeteoFacade.getInstance().currentUnit.equals(Util.UNIT.ms)) {
		    	  rdbtnMs.setSelected(true);
		      }
		      rdbtnMs.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		          MeteoFacade.getInstance().changeUnitToMs();
		        }
		      });
			
			menuDateJPanel = new JPanel();

			dateCursorPanel = new JPanel();
			dateCursor = new DateCursor();
			
			btnImporterGRIB.addActionListener(new ActionListener() {
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
							//listeDate.removeAll();
							
							listeDate = new JComboBox(dates.toArray());							
							listeDate.addActionListener(new ActionListener() {
								public void actionPerformed(java.awt.event.ActionEvent e) {
									MeteoFacade.getInstance().setCurrentDate((Date)listeDate.getModel().getSelectedItem());
									dateCursor.updateSlider(dates.size(), (Date)listeDate.getModel().getSelectedItem());
									MeteoFacade.getInstance().refreshWindbarbs();
								}
							});
							
							menuBar.add(btnExporterXML);
							jpl.add(btnExporterXML);

							jpl.add(listeDate);
							dateCursorPanel.add(dateCursor.getSlider());
							
							jpl.add(dateCursorPanel);
							
							menuBar.add(rdbtnMs);
							menuBar.add(rdbtnKmh);
							menuBar.add(rdbtnNoeud);
							
							jpl.add(rdbtnMs);
							jpl.add(rdbtnKmh);
							jpl.add(rdbtnNoeud);
						}
					} catch (URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
			});
			
			//ajouter tout au jpanel
			menuBar.add(jpl);

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
		 *            La liste des barbules à afficher
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
		
		


		public void updateDateCursor() {
			MeteoFacade facade = MeteoFacade.getInstance();

			Prevision currentPrevision = facade.getCurrentPrevision();			
			List<Date> dates = facade.getDates(currentPrevision);
						
			Date currentDate = facade.getCurrentDate();

			dateCursor.setNewDates(dates, currentDate);

			this.revalidate();
			this.repaint();
		}
		
	    public void updateSelectedDateLabel() { 
	        listeDate.getModel().setSelectedItem(MeteoFacade.getInstance().getCurrentDate()); 
	      } 

	}
}