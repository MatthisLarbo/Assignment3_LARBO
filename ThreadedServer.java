package ee402;

import java.net.*;
import java.io.*;
import java.util.Map;


public class ThreadedServer extends Thread
{
	private static int portNumber = 5050;
    private Map<String, Curve> myothersclients;
    
    //Constructor of ThreadedServer
    public ThreadedServer (Map<String, Curve> myothersclients)
    {
    	this.myothersclients = myothersclients;
    }
        
    //Execute the process
    public void run() { //Overriding 
      
    	ServerSocket serverSocket = null;
    	boolean listening = true;
        
    	try
    	{
    		serverSocket = new ServerSocket(portNumber); //
    		System.out.println("Detection of a new server port number " + portNumber);
    	}
    	
    	catch (IOException e)
    	{
    		System.out.println("XX. Accept failed: " + portNumber + e);
    		System.exit(1);
    	}
    	
        // Server is now listening for connections or would not get to this point
        while (listening) // almost infinite loop - loop once for each client request
        {
            Socket clientSocket = null;
            try{
                System.out.println("**. Listening for a connection...");
                clientSocket = serverSocket.accept();
                System.out.println("00. <- Accepted socket connection from a client: ");
                System.out.println("    <- with address: " + clientSocket.getInetAddress().toString());
                System.out.println("    <- and port number: " + clientSocket.getPort());
            } 
            catch (IOException e){
                System.out.println("XX. Accept failed: " + portNumber + e);
                listening = false;   // end the loop - stop listening for further client requests
            }        
            
            ThreadedConnectionHandler con = new ThreadedConnectionHandler(clientSocket, myothersclients);
            con.start(); 
            System.out.println("Communication with the client " + clientSocket.getInetAddress().toString() + " done");
        }
            try
            {
            	serverSocket.close(); //No more communication with the client
            }
            
            catch (IOException ex)
            {
            	System.err.println("Can't close serverSocket" + ex.getMessage());
            }
        
    }
}