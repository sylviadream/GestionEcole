package model;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import view.CityGUI;


/**
 * Model the operation of a taxi company, operating different
 * types of vehicle. This version operates a only taxis.
 * 
 * @author David J. Barnes and Michael Kolling. Modified ZHENG yujie, SU yu
 * @version 2017.03.25
 */
public class TaxiCompany  
{
    
	// The vehicles operated by the company.
    private List<Vehicle> vehicles;
    
    private City city;
    private static int nombrelibre=0;
    
    // The associations between vehicles and the passengers
    // they are to pick up.
    private Map<Vehicle, Passenger> assignments;
    
    /**
     * @param city The city.
     */
    public TaxiCompany(City city)
    {
        this.city = city;
        vehicles = new LinkedList<>();
        assignments = new HashMap<>();
        setupVehicles();
    }

    /**
     * Request a pickup for the given passenger.
     * @param passenger The passenger requesting a pickup.
     * @return Whether a free vehicle is available.
     */
    public boolean requestPickup(Passenger passenger)
    {
        Vehicle vehicle = scheduleVehicle(passenger);
        if(vehicle != null) {
            assignments.put(vehicle, passenger);
            vehicle.setPickupLocation(passenger.getPickupLocation());
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * A vehicle has arrived at a pickup point
     * (where a passenger is supposed to be waiting).
     * @param The vehicle at the pickup point.
     */
    public void arrivedAtPickup(Vehicle vehicle) 
    {
        Passenger passenger = (Passenger) assignments.remove(vehicle);
        city.removeItem(passenger);
        vehicle.pickup(passenger);
    }
    
    /**
     * A vehicle has arrived at a passenger's destination.
     * @param The vehicle at the destination.
     * @param The passenger being dropped off.
     */
    
    public void arrivedAtDestination(Vehicle vehicle,
            List<Passenger> passengers)
{
}
    
    
    public void arrivedAtDestination(Vehicle vehicle,
                                     Passenger passenger)
    {
    }
    
    
    
    
    /**
     * @return The list of vehicles.
     */
    public List<Vehicle> getVehicles()
    {
        return vehicles;
    }
    
    /**
     * Find a free vehicle, if any.
     * @return A free vehicle, or null if there is none.
     */
    private Vehicle scheduleVehicle(Passenger passenger)
    {   int temps=400;
        nombrelibre=0;
        Vehicle vehiActu = null;
        Iterator<Vehicle> it = vehicles.iterator();
        while(it.hasNext()) {
            Vehicle vehicle = it.next();
            if(vehicle.isFree()) {
            	nombrelibre++;
            	Location locationAct=vehicle.getLocation();
            	Location locPassen=passenger.getPickupLocation();
            	if (temps>locationAct.distance(locPassen)){
            		temps=locationAct.distance(locPassen);
            		vehiActu=vehicle;
            		
            	}           	
            }
        }
        setNombrelibre(nombrelibre);
        return vehiActu;
    }

    public static int getNombrelibre() {
		return nombrelibre;
	}

	public static void setNombrelibre(int nombrelibre) {
		TaxiCompany.nombrelibre = nombrelibre;
	}

	/**
     * Set up this company's vehicles. The optimum number of
     * vehicles should be determined by analysis of the
     * data gathered from the simulation.
     *
     * Vehicles start at random locations.
     */
    private void setupVehicles()
    {
        int cityWidth = city.getWidth();
        int cityHeight = city.getHeight();

        // Use a fixed random seed for predictable behavior.
        // Or use different seeds for less predictable behavior.
        Random rand = new Random(12345);

        // Create the taxis.
        for(int i = 0; i < CityGUI.getNUMBER_OF_TAXIS(); i++){
            Taxi taxi =
                new Taxi(this,
                         new Location(rand.nextInt(cityWidth),
                                      rand.nextInt(cityHeight)));
       
            vehicles.add(taxi); 

            city.addItem(taxi);
        
        }
        

        for(int j = 0; j < CityGUI.getNUMBER_OF_NAVETTE();j++){
       
        Shuttle shuttle =new Shuttle(this, new Location(rand.nextInt(cityWidth),rand.nextInt(cityHeight)));
        
        vehicles.add(shuttle);
        
        city.addItem(shuttle);
        
        }
       
   }
    

}

