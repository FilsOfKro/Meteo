package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;

import facade.MeteoFacade;
import modification.Contraste;
import modification.ServicesModification;

import javax.swing.JButton;

public class VueModification implements ActionListener{

  private JFrame frame;
  protected ServicesModification servicesModification;
  protected Contraste contraste;
  
  protected JSpinner spinner;
  protected JSlider slider;

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
   */
  public VueModification() {
    
    this.servicesModification = new ServicesModification();
    this.contraste = Contraste.getInstance();
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 450, 279);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
    //slider.setSnapToTicks(true);
    slider.setValue(0);
    slider.setBounds(159, 80, 254, 44);
    frame.getContentPane().add(slider);
    
    JButton btnRafraichir = new JButton("Rafraichir");
    btnRafraichir.addActionListener(this);
    btnRafraichir.setBounds(160, 165, 115, 29);
    frame.getContentPane().add(btnRafraichir);
    
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
    
    servicesModification.definirSeuilEtCoefficient(contraste, (double)spinner.getValue(), slider.getValue());
    servicesModification.appliquerContrasteLineaire(MeteoFacade.getInstance().getCurrentPrevision(), contraste);
  }
}
