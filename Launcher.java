package controller;

/**
 * Launch the simulation
 * 
 * @author David J. Barnes and Michael Kolling. Modified ZHENG yujie, SU yu
 * @version 2017.03.25
 */
public class Launcher {

	/**
	 * Launch the simulation
	 * @param args not used
	 */
	public static void main(String[] args) {
	    new Simulation();
		//new ConfigReader();
		Simulation.run();
	}

	
}
