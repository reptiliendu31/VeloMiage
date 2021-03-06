package station.ihm.panels;

import javax.swing.JPanel;

import station.ihm.Etat;
import station.ihm.StationIHM;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class PanelDemandeAbo extends JPanel {
	protected StationIHM modele;
	private PanelDemandeAbo myself = this;
	private JLabel lbl_idClient,lbl_codeConf,lbl_consigne;
	
	public PanelDemandeAbo(StationIHM m) {
		modele = m;
		
		setLayout(null);
		
		JLabel lblDemandeAbo = new JLabel("Demande d'abonnement :");
		lblDemandeAbo.setBounds(10, 11, 169, 14);
		add(lblDemandeAbo);
		
		JLabel lblNumID = new JLabel("Votre num\u00E9ro d'identification est : ");
		lblNumID.setBounds(72, 58, 169, 14);
		add(lblNumID);
		
		JLabel lblCodeConf = new JLabel("Votre code confidentiel est : ");
		lblCodeConf.setBounds(72, 83, 169, 14);
		add(lblCodeConf);
		
		JButton btnLocation = new JButton("Louer un v\u00E9lo");
		btnLocation.setBounds(10, 156, 107, 23);
		add(btnLocation);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.setBounds(127, 156, 107, 23);
		add(btnRetour);
		
		lbl_idClient = new JLabel("");
		lbl_idClient.setBounds(251, 58, 46, 14);
		add(lbl_idClient);
		
		lbl_codeConf = new JLabel("");
		lbl_codeConf.setBounds(251, 83, 46, 14);
		add(lbl_codeConf);
		
		lbl_consigne = new JLabel("");
		lbl_consigne.setForeground(Color.RED);
		lbl_consigne.setBounds(72, 108, 303, 14);
		add(lbl_consigne);

		
		
		// gestion comportement des boutons
		btnLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modele.changerPanel(Etat.Identification);
			}
		});
		
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int reponse = JOptionPane.showConfirmDialog(myself, "Voulez réellemment vous déconnecter ?", "Déconnexion ?", JOptionPane.YES_NO_OPTION);
				
				if (reponse == JOptionPane.YES_OPTION) 
				{
					modele.changerPanel(Etat.Menu);
				}
			}
		});
	}
	
	public void afficherAboGenere(int idClient, int code) {
		lbl_idClient.setText(""+idClient);
		lbl_codeConf.setText(""+code);
		lbl_consigne.setText("Veuillez ne pas communiquer vos identifiants");
		
	}
}
