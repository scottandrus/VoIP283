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
	private static DataIn input;
	private static DataOut output;
	
	// constant port number that our program will broadcast to.
	private static final int PORT = 8000;
	private static final int MAXBUFSIZE = 1024;
	
	private static InetAddress clientIP;
	private static InetAddress serverIP;

	public static void main(String[] args) throws Exception {
		// get all information from a config file
		// config file contains: SERVER_IP, CLIENT_IP, (and what else?)
		Properties prop = new Properties();
		prop.load(new FileInputStream("config.properties"));
		clientIP = InetAddress.getByName(prop.getProperty("CLIENT_IP"));
		serverIP = InetAddress.getByName(prop.getProperty("SERVER_IP"));
		
		//configure datain/out first
		input = new DataIn();
		output = new DataOut();
		
		DatagramSocket socket = new DatagramSocket(PORT);
		
		input.startMic();
		output.startSpeakers();

		while(true){

			// 1) Send all IPs the first chunk of input data.
			int numBytesRead = input.read();
			byte[] buf = input.getNextArray();

			DatagramPacket dp = new DatagramPacket(
					buf,  
					Math.min(MAXBUFSIZE, numBytesRead));
					
			dp.setPort(PORT);

			// As Lum noted, we will probably need to throttle this 
			// to some extent with receive calls to prevent congestion
			
			// for (each of our clients)
				dp.setAddress(clientIP);
				socket.send(dp);

			// 2) Receive data from anyone and output it.
			byte[] responseBuf = new byte[MAXBUFSIZE];
			dp.setData(responseBuf);
			
			socket.setSoTimeout(100);
			try{
				socket.receive(dp);
			}catch(SocketTimeoutException e){
				System.out.println("Didn't receive anything");
			}
			
			output.write(dp.getData(), 0, dp.getLength());
			
		}

//		input.stopMic();
//		output.stopSpeakers();
	}

}
