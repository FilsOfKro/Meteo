package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SpinnerNumberModel;

import editionXML.Editionxml;
import facade.MeteoFacade;
import model.Grille;
import model.Prevision;
import model.PrevisionParDate;
import modification.Contraste;
import modification.ServicesModification;

import javax.swing.JButton;
import javax.swing.JFileChooser;

public class VueModification {

  private JFrame frame;
  protected ServicesModification servicesModification;
  protected Contraste contraste;

  protected JSpinner spinner;
  protected JSlider slider;

  protected Prevision previsionsAvantContraste;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          VueModification window = new VueModification();

          window.frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   * 
   * @throws CloneNotSupportedException
   */
  public VueModification() {
    this.servicesModification = new ServicesModification();
    this.contraste = Contraste.getInstance();

    try {

      this.previsionsAvantContraste = (Prevision) MeteoFacade.getInstance().getCurrentPrevision().clone();
    } catch (CloneNotSupportedException e) {

      e.printStackTrace();
    }

    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 450, 279);
    frame.getContentPane().setLayout(null);

    spinner = new JSpinner();
    spinner.setModel(new SpinnerNumberModel(new Double(0), new Double(0), null, new Double(1)));
    spinner.setBounds(185, 33, 210, 26);
    frame.getContentPane().add(spinner);

    JLabel lblSeuil = new JLabel("Seuil");
    lblSeuil.setBounds(15, 36, 69, 20);
    frame.getContentPane().add(lblSeuil);

    JLabel lblCoefficient = new JLabel("Coefficient (en %)");
    lblCoefficient.setBounds(15, 89, 129, 20);
    frame.getContentPane().add(lblCoefficient);

    slider = new JSlider();
    slider.setToolTipText("");
    slider.setMinorTickSpacing(10);
    slider.setMajorTickSpacing(10);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);
    // slider.setSnapToTicks(true);
    slider.setValue(0);
    slider.setBounds(159, 80, 254, 44);
    frame.getContentPane().add(slider);

    JButton btnRafraichir = new JButton("Rafraichir");
    btnRafraichir.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        
        servicesModification.definirSeuilEtCoefficient(contraste, (double) spinner.getValue(), slider.getValue());
        servicesModification.appliquerContrasteLineaire(MeteoFacade.getInstance().getCurrentPrevision(), contraste);
        MeteoFacade.getInstance().refreshWindbarbs();
      }
    });
    btnRafraichir.setBounds(160, 165, 115, 29);
    frame.getContentPane().add(btnRafraichir);
    
    JButton btnSauvegarder = new JButton("Sauvergarder");
    btnSauvegarder.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        JFileChooser choix = new JFileChooser();
        int retour=choix.showSaveDialog(null);
        
        if(retour==JFileChooser.APPROVE_OPTION) {

           String path = choix.getSelectedFile().getAbsolutePath();
           Date date = MeteoFacade.getInstance().getCurrentDate();
           Grille grille = MeteoFacade.getInstance().getCurrentPrevision().getGrille();
           PrevisionParDate previsionParDate = MeteoFacade.getInstance().getCurrentPrevision().getPrevisionParDate(date);
           Editionxml.sauvergarderXML(grille, previsionParDate, path);
        } 
      }
    });
    btnSauvegarder.setBounds(280, 165, 115, 29);
    frame.getContentPane().add(btnSauvegarder);

    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        Contraste.getInstance().setCoefficient(0);
        Contraste.getInstance().setSeuil(0);
        MeteoFacade.getInstance().setCurrentPrevision(previsionsAvantContraste);
        MeteoFacade.getInstance().refreshWindbarbs();
        e.getWindow().dispose();
      }
    });

    frame.setAlwaysOnTop(true);
    frame.setVisible(true);
  }
}
