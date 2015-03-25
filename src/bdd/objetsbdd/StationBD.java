package bdd.objetsbdd;

import java.util.ArrayList;

public class StationBD {
	
	private int id;
	private double lat,lon;
	private ArrayList<Velo> velosStation;
	//  
	
	public StationBD() {
		velosStation = new ArrayList<Velo>();
	}
	
	public StationBD(int id, double lat, double lon) {
		this.id = id;
		this.lat = lat;
		this.lon = lon;
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
	
	@Override
	public String toString() {
		String s = "Station n�" + id + " , latitude : " + lat + ", longitude : " + lon;
		
		for (Velo velo : velosStation) {
			s+="\n - " + velo.toString();
		}
		
		return s;
	}
	
}