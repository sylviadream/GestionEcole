package view;

import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controller.Simulation;
import model.Actor;
import model.City;
import model.DrawableItem;
import model.Item;
import model.Location;
import model.PassengerSource;
import model.TaxiCompany;
import model.Vehicle;

/**
 * Provide a view of the vehicles and passengers in the city.
 * 
 * @author Yujie ZHENG, Yu SU
 * @version 2016.12.30
 */
public class CityGUI extends JFrame implements Actor 
{	
	private static final long serialVersionUID = 20131230;

	private JTextField nbTaxi=new JTextField() ;
	private JTextField nbNavette=new JTextField() ;
	private JTextField dimY=new JTextField() ;
	private JTextField dimX=new JTextField() ;

	private City city;
	private CityView cityView;
	private static int NUMBER_OF_TAXIS = 5;
	private static int NUMBER_OF_NAVETTE = 1;
	private static int cityWidth=35;
	private static int cityHeight=35;
	
// initialier les parametre de phase 3

	

	/**
	 * Constructor for objects of class CityGUI
	 * 
	 * @param city : the city whose state is to be displayed.
	 * 
	 * 
	 * 
	 */
	public CityGUI(City city)
	{
		super("Simulation of taxis operating on a city grid");
		build(city);
	}
	
	public void build (City city)
	{
		
		// Create and set up the window
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Init attributes
		this.city = city;
		cityView = new CityView(city.getWidth(), city.getHeight());
		cityView.setLayout(null);	
		
		nbTaxi.setBounds(580, 20, 50, 20);
		cityView.add(nbTaxi);
        JLabel lbNombreTaxi = new JLabel("nombreTaxi");
		lbNombreTaxi.setBounds(580, 0, 100, 20);
		cityView.add(lbNombreTaxi);
		
		  nbNavette.setBounds(660, 20, 50, 20);
		  cityView.add(nbNavette);					
	        JLabel lbNbNavette = new JLabel("nombreNavette");
			lbNbNavette.setBounds(660, 0, 100, 20);
			cityView.add(lbNbNavette);
          
          
          dimX.setBounds(660, 60, 50, 20);
          cityView.add(dimX);					
	        JLabel lbdimX = new JLabel("cityWidth");
			lbdimX.setBounds(660, 40, 80, 20);
			cityView.add(lbdimX);
			
          
          dimY.setBounds(580, 60, 50, 20);
          cityView.add(dimY);					
	        JLabel lbdimY = new JLabel("cityHeight");
			lbdimY.setBounds(580, 40, 80, 20);
			cityView.add(lbdimY);
			
			JButton bChange = new JButton("Changer");
			 bChange.setBounds(650, 100, 90, 25);
			 cityView.add( bChange);
			
			bChange.addActionListener(new ActionListener(){  //etablise un event d'écouter
	        	public void actionPerformed(ActionEvent e){
	        		bChangePerformed(e);
	        	}

	        });
		
		createContentPane(); 
		displayGUI();
		
	}
	
	/**
	 * Create and set up the content pane 
	 * @return 
	 * @return 
	 */
	
	private void createContentPane() {

		  Container container = getContentPane();
       // content.setBorder(BorderFactory.createEmptyBorder(11,11,11,11));
		  container.add(cityView, BorderLayout.CENTER);

	}
	/*private void createContentPane() {

		getContentPane().add(cityView, BorderLayout.CENTER);

	}*/
	
	//private void buildContentPane
	
	/**
	 * Size and display this frame
	 */
	private void displayGUI() {
		// Set up an initial size (90% of the screen height)
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int frameHeight = (int) (screenSize.height * 1.00); 
		int frameWidth = frameHeight;
		setPreferredSize(new Dimension(frameWidth, frameHeight));
		// Size this window to fit the preferred size and layouts 
		// of its components
		pack();
		// Center on the screen
		setLocationRelativeTo(null);
		// Display
		setVisible(true);
	}

	/**
	 * Display the current state of the city.
	 */
	public void act() {
		
		cityView.preparePaint();

		List<Item> items = city.getItems();
		for (Item item : items) {
			if (item instanceof DrawableItem) {
				DrawableItem it = (DrawableItem) item;
				Location location = it.getLocation();
				cityView.drawImage(location.getX(), location.getY(),
						it.getImage());
			}
		}

		cityView.repaint();
		
//******ici, c'est pour le phase 2, on affiche les information dans un JTextArea****************
		String afficher=city.toString()+" nombre de taxi: "+getNUMBER_OF_TAXIS()+"\n nombre de taxi libre:"+TaxiCompany.getNombrelibre()+"\n Miss Pickup:"+PassengerSource.getMissedPickups()+"  idleCount:"+Vehicle.getIdleCount();
        JTextArea areaFiftyOne = new JTextArea();
		areaFiftyOne.append(afficher);		
		cityView.add(areaFiftyOne);
		areaFiftyOne.setBounds(5,10,200,50);
	}
	

	public static int getNUMBER_OF_NAVETTE() {
		return NUMBER_OF_NAVETTE;
	}

	private void bChangePerformed(ActionEvent e) {
		NUMBER_OF_TAXIS =Integer.parseInt(nbTaxi.getText());
		NUMBER_OF_NAVETTE=Integer.parseInt(nbNavette.getText());
		cityWidth=Integer.parseInt(dimX.getText());
		cityHeight=Integer.parseInt(dimY.getText());
		
		this.dispose();
		new Simulation();
		//Simulation.run();
		
		
		

	}

	public static int getNUMBER_OF_TAXIS() {
		return NUMBER_OF_TAXIS;
	}

	public static void setNUMBER_OF_TAXIS(int nUMBER_OF_TAXIS) {
		NUMBER_OF_TAXIS = nUMBER_OF_TAXIS;
	}

	public static int getCityWidth() {
		return cityWidth;
	}



	public static int getCityHeight() {
		return cityHeight;
	}



	/**
	 * Provide a graphical view of a rectangular city. This is a nested class (a
	 * class defined inside a class) which defines a custom component for the
	 * user interface. This component displays the city. This is rather advanced
	 * GUI stuff - you can ignore this for your project if you like.
	 */
	private class CityView extends JPanel 
	{
		static final long serialVersionUID = 20131230;

		private final int VIEW_SCALING_FACTOR = 10;

		private int cityWidth, cityHeight;
		private int xScale, yScale; // panel size / city size
		private Dimension size; 	// size of this panel
		private Graphics g;
		private Image cityImage;

		/**
		 * Create a new CityView component.
		 */
		public CityView(int cityWidth, int cityHeight) {
			this.cityWidth = cityWidth;
			this.cityHeight = cityHeight;
			setBackground(Color.white);
			size = new Dimension(0, 0);

			
            
          

			
		}

		public void preparePaint() {
			// If the size has changed...
			if (!size.equals(getSize())) {
				size = getSize();
				cityImage = cityView.createImage(size.width, size.height);
				g = cityImage.getGraphics();
          
				xScale = size.width / cityWidth;
				if (xScale < 1) {
					xScale = VIEW_SCALING_FACTOR;
				}
				yScale = size.height / cityHeight;
				if (yScale < 1) {
					yScale = VIEW_SCALING_FACTOR;
				}
				/** 
				 *  * D‘apres le mail de monsieur LANDSCHOOT on interger le ConfigReader dans le fenetre existant,
				 * le code ci-dessous est pour saisir les infromation
				 *
				*/     
				  		
					

					
			}

			// Draw the grid

			g.setColor(Color.white);
			g.fillRect(0, 0, size.width, size.height);
			g.setColor(Color.gray);
			for (int i = 0, x = 0; x < size.width; i++, x = i * xScale) {
				g.drawLine(x, 0, x, size.height - 1);
			}
			for (int i = 0, y = 0; y < size.height; i++, y = i * yScale) {
				g.drawLine(0, y, size.width - 1, y);
				

			}
		}

		/**
		 * Draw the image for a particular item.
		 */
		public void drawImage(int x, int y, Image image) {
		g.drawImage(image, x * xScale + 1, y * yScale + 1, 
			xScale - 1,	yScale - 1, this);
		}
		


		/**
		 * The city view component needs to be redisplayed. Copy the internal
		 * image to screen.
		 */
		

		@Override
		public void paintComponent(Graphics g) {
			if (cityImage != null) {
				g.drawImage(cityImage, 0, 0, null);
			}
		}

	} // End internal class CityView

	/*************************************************************************/

} // End class CityGUI
