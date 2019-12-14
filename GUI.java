package ee402;


import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import javax.swing.*;

public class GUI extends JFrame //implements ItemListener
{
	private ThreadedServer myThread;
	private Map<String, Curve> myothersclients;
	
	private Tracer myTrace;
	private ClientConstru myClient;
	
	private Checkbox cb1, cb2, cb3, cb4;
	//private Checkbox cb2;
	//private Checkbox cb3;
	//private Checkbox cb4;
	
	private JPanel myPanel;
	private JPanel allThePanels;
	
	public GUI() //Constructor of the GUI class
	{
		// A EFFACER !!
		//this.setTitle("Assignment");
		
		this.setSize(650, 500);
		
		// A EFFACER si pas besoin !!
		this.setResizable(false);
		
		//Exit the window
		//this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //enlever si l'autre marche
		
		//A EFFACER si pas besoin !!
		this.setLocationRelativeTo(null);
		
		this.myClient = new ClientConstru();
		this.myothersclients = new HashMap<String, Curve>(); //Stores pairs of items 
		this.myTrace = new Tracer(myClient, this.myothersclients);
		this.myPanel = new JPanel(new GridLayout(1, 0)); //display of the GUI 
		
		this.allThePanels = new JPanel(new BorderLayout());
		this.allThePanels.add(myPanel, BorderLayout.NORTH);
		this.allThePanels.add(this.myTrace, BorderLayout.CENTER);
		
		this.myThread = new ThreadedServer(this.myothersclients);
		this.myThread.start();
		
		this.getContentPane().add(this.allThePanels);
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				System.exit(0);
			}
		});
		
	}

}

