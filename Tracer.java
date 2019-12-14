package ee402;


import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Tracer extends JPanel //For the use of the method paint (drawing the curve and the graph)
{
	private Map<String, Curve> myothersclients;
	private ClientConstru myClient;
	
	//Pas sûr ici
	//public boolean firstC;
	//public boolean secondC;
	//public boolean thirdC;
	//public boolean fourthC;
	
	public Tracer(ClientConstru firstC, Map<String, Curve> myothersclients) //Constructor of the Tracer class
	{
		this.myothersclients = myothersclients; //Initialization of the others possible clients
		this.myClient = firstC;
	}
	
	public void paint(Graphics graph)
	{
		//ESSAYER 
		//JFrame frame = new JFrame("EE402 Representation of several clients");
		//frame.setSize(650, 500);
		super.paint(graph);
		//graph.setColor(Color.white); A EFFACER SI CA MARCHE
		//graph.setTitle("EE402 Representation of several clients");
		graph.drawString("EE402 Representation of the temperature on several clients", 2, 2);
		
		//Draw the graph
		graph.setColor(Color.darkGray); //Set the color of the lines
		graph.drawLine(12, 38, 12, 420); //Abcissa
		graph.drawLine(12, 420, 640, 420); //Ordinate
		
		Set<String> testIn = this.myothersclients.keySet();
		Iterator<String> it = testIn.iterator();
		int ab = 0;
		
		while(it.hasNext())
		{
			ab=ab+1;
			
			Curve myCurve = myothersclients.get(it.next());
			Vector<CurvePoints> myPoint = myCurve.getSerie();
			Color curvecolor = myCurve.getCurveColor();
			
			//For only one client firstly
			if(ab==1 && myPoint.size()>1 && myClient.firstC == 1)
			{
				for(int a = 0; a<myPoint.size()-1; a++)
				{
					//Tracing the points of firstC
					CurvePoints firstP = myPoint.get(a);
					CurvePoints secondP = myPoint.get(a+1);
					
					//Get the placement of each point
					int abc1 = (int)firstP.getAbci();
					int abc2 = (int)secondP.getAbci();
					int ord1 = (int)firstP.getOrd();
					int ord2 = (int)secondP.getOrd();
					
					//Connect with a line the different points
					
					//ESSAYER DE CHANGER CA (valeurs -4)
					
					graph.setColor(curvecolor);
					graph.drawLine(abc1, ord1, abc2, ord2);
					graph.setColor(curvecolor);
					graph.drawLine(abc1-4, ord1, abc2-4, ord2);
					graph.drawLine(abc1, ord1-4, abc2, ord2-4);
				}
			}
		}
		
		
		
	}
}