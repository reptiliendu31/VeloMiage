package gestionnaire.moteur.ihm;


import gestionnaire.moteur.Gestionnaire;
import gestionnaire.moteur.ihm.panels.PanelTechnicien;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import bdd.objetsbdd.Abonne;
import bdd.objetsbdd.StationBD;
import station.ihm.Etat;
import station.ihm.panels.PanelAccueil;
import station.ihm.panels.PanelDemandeAbo;
import station.ihm.panels.PanelIdentification;
import station.ihm.panels.PanelRetourVelo;
import utils.exceptions.EssaisEcoulesException;
import utils.exceptions.LocationEnCoursException;
import utils.exceptions.StationPleineException;
import utils.exceptions.VeloPasLoueException;







import java.awt.GridLayout;
import java.net.IDN;
import java.rmi.RemoteException;
import java.util.HashMap;

public class TechnicienIHM extends JFrame {
	private Abonne a ;
	private JPanel panelCourant;
	private JPanel contentPane;
	private PanelTechnicien panelTech;
	
	public TechnicienIHM(HashMap<Integer, String> tab,Abonne ab) {
		a = ab;
		try {
			// Permet de prendre l'apparence du syst�me h�te
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e){}
		
		setTitle("Compte technicien " + a.getId()); 
		setSize(470, 220); 
		setLocationRelativeTo(null); 
		setResizable(false); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 


		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		contentPane = new JPanel();
		contentPane.setLayout(new GridLayout());
		panelTech = new PanelTechnicien(a, tab, this);
		
		this.setContentPane(contentPane); // on r�cup�re l'abonn� courant
		panelCourant = panelTech;
	
		//panelCourant.setVisible(false);
		//contentPane.remove(panelCourant);
		//panelCourant = p;
		contentPane.add(panelCourant);
		panelCourant.setVisible(true);

	}
	
	public void notifierTech(HashMap<Integer, String> tab) {
		panelTech.rechargerTableau(tab);
	}

}
