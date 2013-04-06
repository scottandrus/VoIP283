import java.io.FileInputStream;
import java.lang.Math;
import java.net.*;
import java.util.Properties;

/**
 * 
 * @author Jenna
 * 
 * Client has both a Sender and Receiver and connects them to
 * 		  a DataIn and DataOut object that has been initialized
 * 		  correctly for the machine the client is on
 * 
 *
 */
public class Client 
{
	private static DataInThread input;
	private static DataOutThread output;
	
	// constant port number that our program will broadcast to.
	private static final int PORT = 8000;
	private static final int MAXBUFSIZE = 1024;
	
	private static InetAddress clientIP;
	private static InetAddress serverIP;

	public static void main(String[] args) throws Exception {
		// get all information from a config file
		// config file contains: SERVER_IP, CLIENT_IP, (and what else?)
		Properties prop = new Properties();
		prop.load(new FileInputStream("src/config.properties"));
		clientIP = InetAddress.getByName(prop.getProperty("CLIENT_IP"));
		serverIP = InetAddress.getByName(prop.getProperty("SERVER_IP"));
		
		DatagramSocket outSocket = new DatagramSocket(PORT,clientIP);
		
		DatagramSocket inSocket = new DatagramSocket();
		inSocket.connect(serverIP,PORT);
		
		
		//configure datain/out first
		output = new DataOutThread(outSocket);
		input = new DataInThread(inSocket);
		
		output.startSpeakers();
		input.startMic();


//		input.stopMic();
//		output.stopSpeakers();
	}

}
