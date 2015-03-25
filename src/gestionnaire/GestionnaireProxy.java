package gestionnaire;

import java.rmi.RemoteException;
import java.util.jar.JarException;

public interface GestionnaireProxy extends java.rmi.Remote{
	/**
	 * <M�lanie&St�fan> - 19/03/2015 - Step 1
	 * @throws RemoteException
	 */
	public int[] creerAbonnement(boolean isTech)throws java.rmi.RemoteException;
	
	/**
	 * <St�fan> - 21/03/2015 - Step 2
	 * @throws RemoteException
	 */
	public boolean idValidation(int id)throws java.rmi.RemoteException;

	public void location(String idVelo) throws java.rmi.RemoteException;
	public void retour(String idVelo) throws java.rmi.RemoteException;
	
	public String[] demandeStationProche(int idStation, boolean demandeLocation) throws java.rmi.RemoteException;
}
