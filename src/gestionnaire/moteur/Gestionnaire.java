package gestionnaire.moteur;

import gestionnaire.GestionnaireProxy;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeMap;

import utils.*;
import bdd.objetsbdd.*;
import bdd.objetsdao.*;

public class Gestionnaire extends UnicastRemoteObject implements GestionnaireProxy {

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
	 * @throws abonnementException 
	 */
	public synchronized int[] creerAbonnement(boolean isTech) throws RemoteException, demandeAboException {
		
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

		System.out.println("info abonn� cr�� : id = " + abonne.toString());
		abo[0] = abonne.getId();
		abo[1] = abonne.getCode();
		if(abo.length == 2){
			return abo;
		}
		else{
			throw new demandeAboException();
		}
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
	public int idValidation(int id) throws RemoteException {

		Abonne abonne = daoAbonne.find(id);

		// r�cup�ration date du jour
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		return (now.before(abonne.getDateAboFin()) ? abonne.getCode() : 0);

	}

	/**
	 * WIP <St�fan> - 21/03/2015 - Etape 2
	 * 
	 * @throws RemoteException
	 * @throws listeVeloException 
	 */
	public ArrayList<Velo> listeVelo(int idStation) throws RemoteException, listeVeloException {
		try{
		StationBD st = daoStationBD.find(idStation);
		return st.getVelosStation();
		}catch (Exception e){
			throw new listeVeloException();
		}
		
	}
	
	/**
	 * WIP <St�fan> - 21/03/2015 - Etape 2
	 * 
	 * @throws RemoteException
	 * @throws locationException 
	 */
	public boolean location(int idStation,int idClient, int idVelo, Timestamp dateLoc) throws RemoteException, locationException {
		try{
		StationBD st = daoStationBD.find(idStation);
		Abonne ab = daoAbonne.find(idClient);
		Velo v = daoVelo.find(idVelo);
		
		// enlever v�lo de table posseder
		daoStationBD.removeVelo(st, v, dateLoc);
		// ajouter v�lo table louer
		daoAbonne.addVelo(ab, v, dateLoc);
		System.out.println("V�lo retir�");
		return true;
		}catch (Exception e){
			throw new locationException();
		}
	}

	/**
	 * WIP <St�fan> - 21/03/2015 - Etape 2
	 * 
	 * @throws RemoteException
	 * @throws retourVeloException 
	 */
	public boolean retour(int idStation,int idVelo, Timestamp dateRetour) throws RemoteException, retourVeloException {
		try{
			StationBD st = daoStationBD.find(idStation);
			Velo v = daoVelo.find(idVelo);
			// ajouter v�lo de table posseder
			daoStationBD.addVelo(st, v, dateRetour);
			// retirer v�lo table louer
			v = daoVelo.depositVelo(v, dateRetour);
			System.out.println("V�lo rendu");
			return true;
		}catch (Exception e){
			throw new retourVeloException();
		}
	}

	/**
	 * WIP <St�fan> - 21/03/2015 - Etape 4
	 * @throws IdVeloException 
	 * 
	 * @throws RemoteException
	 */
	public void getInfoEtatVelo(int idVelo) throws IdVeloException {
		try{
			Velo v = daoVelo.find(idVelo);
			System.out.println(v.toString());
		}catch (Exception e){
			throw new IdVeloException();
		}
		
	}

	/**
	 * WIP <St�fan> - 21/03/2015 - Etape 5
	 * 
	 * @throws RemoteException
	 * @throws demandeStationException 
	 */
	public String[] demandeStationProche(int idStation, boolean demandeLocation)throws RemoteException, demandeStationException {
		
		// r�cup�ration des lattitudes et longi de la station courante
		StationBD station = daoStationBD.find(idStation);
		TreeMap<Double, StationBD> listDistStation = new TreeMap<Double, StationBD>();

		// cr�ation variable r�sultat
		String res[] = new String[3];

		// r�cup�ration de la longitude et latitude de la station 1
		double latStation1 = station.getLat();
		double longStation1 = station.getLon();

		// r�cup�ration des stations et de la distance avec la station 1
		Iterator<StationBD> it = daoStationBD.getInstances().iterator();
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
			/*int placeDispo = sDist.getPlaceDispo(); 
			if(placeDispo> 0 && demandeLocation){ 
				if(placeDispo != sDist.getPlaceMax()){ 
					res[0] = "" + sDist.getId();
					res[1] = "" + sDist.getLat();
					res[2] = "" + sDist.getLon();
					break; 
				}
			}else if(placeDispo>0){
					res[0] = "" + sDist.getId();
					res[1] = "" + sDist.getLat();
					res[2] = "" + sDist.getLon(); 
			break; }
			 */
		}
		if(res.length == 3){
			return res;
		}else{
			throw new demandeStationException();
		}
		
	}
}
