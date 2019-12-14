package ee402;

import java.net.*;
import java.io.*;
import java.util.Map;
import java.util.Vector;

public class ThreadedConnectionHandler extends Thread
{
    private Socket clientSocket = null;				// Client socket object
    private ObjectInputStream is = null;			// Input stream
    private ObjectOutputStream os = null;			// Output stream
    private DateTimeService theDateService;
    private Map<String, Curve> myothersclients;		// Multiple Connections
    private Vector<ReadValue> pastValue;
    
	// The constructor for the connection handler
    public ThreadedConnectionHandler(Socket clientSocket, Map<String, Curve> myothersclients) {
        this.clientSocket = clientSocket;
        //Set up a service object to get the current date and time
        theDateService = new DateTimeService();
        
        this.myothersclients = myothersclients; //Constructor
        this.pastValue = new Vector<ReadValue>();
    }

    // Will eventually be the thread execution method - can't pass the exception back
    public void run() {
         try {
            this.is = new ObjectInputStream(clientSocket.getInputStream());
            this.os = new ObjectOutputStream(clientSocket.getOutputStream());
            while (this.readCommand()) {}
         } 
         catch (IOException e) 
         {
        	System.out.println("XX. There was a problem with the Input/Output Communication:");
            e.printStackTrace();
         }
    }

    // Receive and process incoming string commands from client socket 
    private boolean readCommand() {
        ReadValue s = null;
        float rand = 0;
        try {
            s = (ReadValue) is.readObject();
            rand = (float)(Math.random()*50);
            s.setDegree(rand);
            System.out.println(s.test());
            
            if (pastValue.size() == 20)
            {
            	pastValue.removeElementAt(0);
            }
            
            else if (!this.myothersclients.containsKey(s.getInfoClient()))
            {
            	Curve myCurve = new Curve();
            	this.myothersclients.put(s.getInfoClient(), myCurve);
            }
            pastValue.add(s);
            Curve test1 = myothersclients.get(s.getInfoClient());
            test1.traceCurve(pastValue);
            
            System.out.println("How many past value : " + pastValue.size()); // size of the vector
        } 
        catch (Exception e){    // catch a general exception
        	this.closeSocket();
            return false;
        }
        System.out.println("01. <- Received a String object from the client (" + s + ").");
        
        // At this point there is a valid String object
        // invoke the appropriate function based on the command 
        /*if (s.equalsIgnoreCase("GetDate")){ 
            this.getDate(); 
        }       
        else { 
            this.sendError("Invalid command: " + s); 
        }*/
        this.freq();
        return true;
    }
    
    private void freq()
    {
    	this.send(1000);
    }

    // Use our custom DateTimeService Class to get the date and time
    private void getDate() {	// use the date service to get the date
        String currentDateTimeText = theDateService.getDateAndTime();
        this.send(currentDateTimeText);
    }

    // Send a generic object back to the client 
    private void send(Object o) {
        try {
            System.out.println("02. -> Sending (" + o +") to the client.");
            this.os.writeObject(o);
            this.os.flush();
        } 
        catch (Exception e) {
            System.out.println("XX." + e.getStackTrace());
        }
    }
    
    // Send a pre-formatted error message to the client 
    public void sendError(String message) { 
        this.send("Error:" + message);	//remember a String IS-A Object!
    }
    
    // Close the client socket 
    public void closeSocket() { //gracefully close the socket connection
        try {
            this.os.close();
            this.is.close();
            this.clientSocket.close();
        } 
        catch (Exception e) {
            System.out.println("XX. " + e.getStackTrace());
        }
    }
}