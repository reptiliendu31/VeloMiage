package bdd.objetsbdd;

import java.io.Serializable;
import java.util.ArrayList;

public class StationBD implements Serializable {
	
	private int id;
	private double lat,lon;
	private ArrayList<Velo> velosStation;
	private int nbPlaceMax;
	
	public StationBD() {
		velosStation = new ArrayList<Velo>();
	}
	
	public StationBD(int id, double lat, double lon, int nbplaces) {
		this.id = id;
		this.lat = lat;
		this.lon = lon;
		this.nbPlaceMax = nbplaces;
		this.velosStation = new ArrayList<Velo>();
	}
	
	public ArrayList<Velo> getVelosStation() {
		return velosStation;
	}
	
	public void setVelosStation(ArrayList<Velo> velosStation) {
		this.velosStation = velosStation;
	}
	
	public boolean putVelo(Velo v) {
		return velosStation.add(v);
	}
		
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public double getLat() {
		return lat;
	}
	
	public void setLat(double lat) {
		this.lat = lat;
	}
	
	public double getLon() {
		return lon;
	}
	
	public void setLon(double lon) {
		this.lon = lon;
	}
	
	public int getNbPlace(){
		return nbPlaceMax;
	}
	
	public void setNbPlace(int place){
		this.nbPlaceMax = place;
	}
	
	public int getPlaceDispo(){
		return (nbPlaceMax - velosStation.size());
	}
	
	@Override
	public String toString() {
		String s = "Station n�" + id + " , latitude : " + lat + ", longitude : " + lon;
		
		for (Velo velo : velosStation) {
			s+="\n - " + velo.toString();
		}
		
		return s;
	}
	
}
