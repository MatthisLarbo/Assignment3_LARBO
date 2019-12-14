package ee402;

public class CurvePoints
{
	private double abci; //Abcissa
	private double ord; //Ordinate
	
	//Constructor of the CurvePoints Class
	public CurvePoints(double abci, double ord)
	{
		this.abci=abci; //initialization of the abcissa axe
		this.ord=ord; //initialization of the ordinate axe
	}
	
	public double getAbci() //Getter of the abcissa axe
	{
		return (this.abci);
	}
	
	public double getOrd() //Getter of the ordinate axe
	{
		return (this.ord);
	}
}