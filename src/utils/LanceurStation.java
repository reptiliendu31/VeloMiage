package utils;

import gestionnaire.moteur.Gestionnaire;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import bdd.objetsbdd.StationBD;
import bdd.objetsdao.StationDAO;
import station.moteur.Station;
import station.moteur.ihm.StationIHM;

public class LanceurStation {

	public static void main(String[] args) {
		try {
			/*LocateRegistry.createRegistry(1099);
			Gestionnaire gestionnaire = new Gestionnaire();
			Naming.rebind("GestionStat", gestionnaire ); // Choix du nom du*/

			StationDAO daoStation = new StationDAO();
			for (StationBD s : daoStation.getInstances()) {
					Station station = new Station(s.getId());
					StationIHM ihm = new StationIHM(station);
					ihm.setVisible(true);
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	}
}