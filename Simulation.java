package controller;

import java.util.LinkedList;
import java.util.List;

import model.Actor;
import model.City;
import model.PassengerSource;
import model.TaxiCompany;

import view.CityGUI;

/**
 * Run the simulation by asking a collection of actors to act.
 * 
 * @author Yujie ZHENG, Su YU
 * @version 2017.03.15
 */
public class Simulation
{
    private static List<Actor> actors;

    /**
     * Create the initial set of actors for the simulation.
     */
    public Simulation()
    {   
        actors = new LinkedList<>();
        City city = new City(CityGUI.getCityWidth(),CityGUI.getCityHeight());
        TaxiCompany company = new TaxiCompany(city);
        PassengerSource source = new PassengerSource(city, company);
        
        actors.addAll(company.getVehicles());
        actors.add(source);
        actors.add(new CityGUI(city));
    }
    
  
    
    /**
     * Run the simulation for a fixed number of steps.
     * Pause after each step to allow the GUI to keep up.
     */
    public static void run()
    {
    	
        for(int i = 0; i< 300; i++) {
            step();
            wait(100);
        }

    }





	/**
     * Take a single step of the simulation.
     */
    public static void step()
    {
        for(Actor actor : actors) {
        	actor.act();
        }
    }
    
    /**
     * Wait for a specified number of milliseconds before finishing.
     * This provides an easy way to cause a small delay.
     * @param milliseconds The number of milliseconds to wait.
     */
    private static void wait(int milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        } 
        catch (InterruptedException e)
        {
            // ignore the exception
        }
    }
}
