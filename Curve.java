package ee402;

import java.awt.Color;
import java.util.Vector;

public class Curve {
	
	private Color curvecolor;
	private Vector <CurvePoints> serie;
	private float temps;
	
	public Curve() //Constructor of the class
	{
		this.serie = new Vector<CurvePoints>();
		this.temps = 1;
		this.curvecolor = new Color(0, 0, 255); //blue curve
		//this.curvecolor2 = new Color(0, 255, 0); //green curve
		//this.curvecolor3 = new Color(255, 0, 0); //red curve
	}
	
	public void createPoints(ReadValue s, ReadValue position1) //Function who create the points of the curve
	{
		//Defining the position of a point (abcissa and ordinate)
		int abci = 700 - (int)(((position1.getTheDay().getTime() - s.getTheDay().getTime())/1000.0f) / (temps/(690.0f/20.0f)));
		int ord = 450 - (int)((s.getValue()/50)*410.0f);
		
		CurvePoints place = new CurvePoints(abci, ord);
		this.serie.add(place);
	}
	
	public void traceCurve(Vector<ReadValue> pastValue)
	{
		this.serie.clear(); //We need to erase all the elements of the vector 
		int a = (pastValue.size())-1;
		if (pastValue.size() > 1)
		{
			while(a != 0)
			{
				a--;
				this.createPoints(pastValue.get(a), pastValue.get(pastValue.size()-1));
			}
		}
	}
	
	public Color getCurveColor()
	{
		return (this.curvecolor);
	}
	
	public Vector<CurvePoints> getSerie()
	{
		return (this.serie);
	}
}