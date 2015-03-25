package gestionnaire.moteur;

import gestionnaire.GestionnaireProxy;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeMap;

import bdd.DAO;
import bdd.objetsbdd.Abonne;
import bdd.objetsbdd.StationBD;
import bdd.objetsdao.AbonneDAO;
import bdd.objetsdao.StationDAO;
import bdd.objetsdao.VeloDAO;

public class Gestionnaire extends UnicastRemoteObject implements GestionnaireProxy {
	
	private HashMap<String, StationBD> listeStation;
	private HashMap<Integer, Abonne> listeAbonne;
	private HashMap<String, String> listeVelos;
	private StationDAO daoStationBD;
	private AbonneDAO daoAbonne;
	private VeloDAO daoVelo;

	/**
	 * <M�lanie&St�fan> - 19/03/2015 - Etape 1
	 * 
	 * @throws RemoteException
	 */
	public Gestionnaire() throws RemoteException {
		super();
		// listeStation = new HashMap<String, Station>();
		listeAbonne = new HashMap<Integer, Abonne>();
		listeVelos = new HashMap<String, String>();
		// r�cup�ration de la liste de station
		daoAbonne = new AbonneDAO();
		daoStationBD = new StationDAO();
		daoVelo = new VeloDAO();

	}

	/**
	 * <M�lanie&St�fan> - 19/03/2015 - Etape 1
	 * 
	 * @param isTech
	 *            technicien ou non
	 * @return abonne cr��
	 * @throws RemoteException
	 */
	public synchronized int[] creerAbonnement(boolean isTech)
			throws RemoteException {
		
		int abo[] = new int[2];
		Random rand = new Random();
		// creation mdp
		int codeConf = rand.nextInt(9999) + 1000;

		// r�cup�ration date du jour
		Timestamp sqlDate = new Timestamp(System.currentTimeMillis());
		// +1 jour
		Timestamp sqlDateFin = new Timestamp(
				System.currentTimeMillis() + 86400000);

		System.out.println("test de la date: " + sqlDate.toString()
				+ "date fin = " + sqlDateFin.toString());
		// creation de l'abonne
		Abonne abonne = new Abonne();
		abonne.setCode(codeConf);
		abonne.setDateAboDebut(sqlDate);
		abonne.setTechnicien(isTech);
		abonne.setDateAboFin(sqlDateFin);

		abonne = daoAbonne.create(abonne);

		// ajout dans la liste
		listeAbonne.put(abonne.getId(), abonne);

		System.out.println("info abonn� cr�� : id = " + abonne.toString());
		abo[0] = abonne.getId();
		abo[1] = abonne.getCode();
		return abo;
	}

	/**
	 * <M�lanie&St�fan> - 19/03/2015 - Etape 1
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		LocateRegistry.createRegistry(1099); // creation du proxy sur le port
												// 1099
		Naming.rebind("GestionStat", new Gestionnaire()); // Choix du nom du
															// proxy
		System.out.println("Gestionnaire est enregistr�e");
	}

	/**
	 * WIP <St�fan> - 21/03/2015 - Etape 2
	 * 
	 * @throws RemoteException
	 */
	public boolean idValidation(int id) throws RemoteException {

		Abonne abonne = daoAbonne.find(id);

		// r�cup�ration date du jour
		Timestamp now = new Timestamp(System.currentTimeMillis());

		return (now.before(abonne.getDateAboFin()) ? true : false);

	}

	/**
	 * WIP <St�fan> - 21/03/2015 - Etape 2
	 * 
	 * @throws RemoteException
	 */
	public void location(String idVelo) throws RemoteException {
		// add location
		// change value in bd --> MAJ CACHE
		System.out.println("V�lo retir�");
	}

	/**
	 * WIP <St�fan> - 21/03/2015 - Etape 2
	 * 
	 * @throws RemoteException
	 */
	public void retour(String idVelo) throws RemoteException {
		// change value in bd of the velo --> MAJ CACHE
		System.out.println("V�lo rendu");
	}

	/**
	 * WIP <St�fan> - 21/03/2015 - Etape 4
	 * 
	 * @throws RemoteException
	 */
	public void getInfoEtatVelo(String idVelo) {
		// System.out.println("V�lo " + bikeList.get(idVelo).getEtat());
	}

	public HashMap<String, StationBD> getStations() {
		return listeStation;
	}

	/**
	 * WIP <St�fan> - 21/03/2015 - Etape 5
	 * 
	 * @throws RemoteException
	 */
	public String[] demandeStationProche(int idStation, boolean demandeLocation)
			throws RemoteException {
		// r�cup�ration des lattitudes et longi de la station
		StationBD station = new StationBD();
		station = daoStationBD.find(idStation);
		TreeMap<Double, StationBD> listDistStation = new TreeMap<Double, StationBD>();

		// cr�ation variable r�sultat
		String res[] = new String[3];

		// r�cup�ration de la longitude et latitude de la station 1
		double latStation1 = station.getLat();
		double longStation1 = station.getLon();

		// r�cup�ration des stations et de la distance avec la station 1
		Iterator<StationBD> it = listeStation.values().iterator();
		while (it.hasNext()) {
			StationBD s = it.next();
			double distStation = utils.Distance.distanceInKilometers(
					latStation1, longStation1, s.getLat(), s.getLon());
			listDistStation.put(distStation, s);
		}

		// choix de la station ayant des places
		Iterator<Double> itDist = listDistStation.keySet().iterator();
		while (itDist.hasNext()) {
			double dist = itDist.next();
			StationBD sDist = listDistStation.get(dist);
			/*
			 * int placeDispo = sDist.getPlaceDispo(); if(placeDispo> 0 &&
			 * !demandeLocation){ if(placeDispo != sDist.getPlaceMax()){ res[0]
			 * = "" + sDist.getId(); res[1] = "" + sDist.getLat(); res[2] = "" +
			 * sDist.getLon(); break; } }else if(placeDispo>0){ res[0] = "" +
			 * sDist.getId(); res[1] = "" + sDist.getLat(); res[2] = "" +
			 * sDist.getLon(); break; }
			 */
		}
		return res;
	}
}
