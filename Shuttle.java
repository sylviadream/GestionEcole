package model;
import java.awt.Image;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

import javax.swing.ImageIcon;
    
/**
 * A shuttle is able to carry multiple passengers.
 * This implementation is non-functional.
 * 
 * @author David J. Barnes and Michael Kolling. Modified ZHENG yujie, SU yu
 * @version 2017.03.25
 */
public class Shuttle extends Vehicle implements DrawableItem
{
    // The maximum number of passengers for this shuttle.
    private final int MAXPASSENGERS;
    private Passenger passenger;
    // The list of destinations for the shuttle.
    private List<Location> destinations;
    // The list of passengers on the shuttle.
    private List<Passenger> passengers;
    private int nbPassenger;
 

    private Image  shuttleImage;
    private Image  passengerImage;
    /**
     * Constructor for objects of class Shuttle
     * @param maxPassengers The max number of passengers. Must be positive.
     * @param company The taxi company. Must not be null.
     * @param location The vehicle's starting point. Must not be null.
  
     * @throws NullPointerException If company or location is null.
     */
    
    public Shuttle( TaxiCompany company, Location location)
    {
        super(company, location);
        MAXPASSENGERS = 10;
        destinations = new LinkedList<>();
        passengers = new LinkedList<>();
        shuttleImage = new ImageIcon(getClass().getResource(
                "/images/bus.jpg")).getImage();
        passengerImage = new ImageIcon(getClass().getResource(
                "/images/bus+persons.jpg")).getImage();
    }
    public Shuttle(int maxPassengers, TaxiCompany company, Location location, Image PassengerImage)
    {
        super(company, location);
        MAXPASSENGERS = maxPassengers;
        destinations = new LinkedList<>();
        passengers = new LinkedList<>();
        shuttleImage = new ImageIcon(getClass().getResource(
                "/images/taxi.jpg")).getImage();
        passengerImage = new ImageIcon(getClass().getResource(
                "/images/bus+persons.jpg")).getImage();
    }

    /**
     * Carry out a shuttle's actions.
     */
    public void act()
    {
    	chooseTargetLocation();
        Location target = super.getTargetLocation();
        if(target != null) {
            // Find where to move to next.
            Location next = getLocation().nextLocation(target);
            setLocation(next);
            if(next.equals(target)) {
                if(passenger != null) {
                    notifyPassengerArrival(passenger);
                    offloadPassenger();
                }
                else {
                    notifyPickupArrival();
                }
            }
        }
        else {
            incrementIdleCount();
        }
    }

    /**
     * @return Whether or not this vehicle is free.
     */
    public boolean isFree()
    {
        if (nbPassenger<MAXPASSENGERS){
        return true;}
        return false;
    }
    
    /**
     * Receive a pickup location.
     * @location The pickup location.
     */
    public void setPickupLocation(Location location)
    {
        destinations.add(location);
        chooseTargetLocation();
    }
    
    /**
     * Receive a passenger.
     * Add the destination to the list.
     * @param passenger The passenger.
     */
    public void pickup(Passenger passenger)
    {
    	this.passenger = passenger;
        passengers.add(passenger);
        destinations.remove(0);
        if (!destinations.contains(passenger.getDestination())){
        	destinations.add(passenger.getDestination());
        }
        
    }

    /**
     * Decide where to go next, based on the list of
     * possible destinations.
     */
    private void chooseTargetLocation()
    {
    	
    	if(!destinations.isEmpty()){
    		super.setTargetLocation(destinations.get(0));
    	}
    }

    /**
     * Offload all passengers whose destination is the
     * current location.
     */
    public void offloadPassenger()
    {
    	Iterator< Passenger> it = passengers.iterator();
    	while (it.hasNext()){
    		Passenger passengerShutter=it.next();
        	if (passengerShutter.getDestination().equals(super.getLocation())){
        		passengers.remove(passengerShutter);
        		destinations.remove(super.getLocation());
        	}
    	}
    	 passenger = null;
         clearTargetLocation();
    }
    
    public Image getImage()
    {
        if(passengers.isEmpty()) {
            return  shuttleImage ;
        }
        else {
            return passengerImage;
        }
    }

	/**
	 * @return The maximum number of passengers for this shuttle
	 */
    public int getMaxPassengers() {
		return MAXPASSENGERS;
	}
    
    
}
