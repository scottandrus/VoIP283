import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.net.InetAddress;
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
public class Client {
	private static Sender sender;
	private static Receiver receiver;
	private static DataIn input;
	private static DataOut output;
	
	private static InetAddress clientIP;
	private static InetAddress serverIP;
	

	
	// main -- initialize all data

	public static void main(String[] args) throws Exception {
		// get all information from a config file
		// config file contains: SERVER_IP, CLIENT_IP, (and what else?)
		Properties prop = new Properties();
		prop.load(new FileInputStream("config.properties"));
		clientIP = InetAddress.getByName(prop.getProperty("CLIENT_IP"));
		serverIP = InetAddress.getByName(prop.getProperty("SERVER_IP"));
		
		//configure datain/out first
		input = new DataIn();
		input.startMic();
		output = new DataOut();
		output.startSpeakers();
		
		sender = new Sender(serverIP, clientIP, 0); // 0 for PortNo allows for computer to
													// dynamically choose a port
		ByteArrayOutputStream ostream = sender.sendData();
		// byte[] buf,InetAddress serverIP, InetAddress remoteAddr, int PortNo
		receiver = new Receiver(output.speakerData,serverIP,clientIP,0);
		ByteArrayInputStream istream = receiver.recvData();
		
		
		// need a thread to read and a thread to write
		
		// what buffer is going where and why?
		// DataIn buffer should go to sender (to be sent)
		//		read from mic, write from sender
		// DataOut buffer should go to receiver (to be filled)
		//		read from receiver, write from speakers
		
		
		while(true){
			// EVENTUALLY
			// have one thread reading in from mic
			// another waiting for stuff to write
			int numBytesRead = input.read();
			byte[] buf = input.getNextArray();
			ostream.write(buf,0,buf.length);
			
			istream.read(output.speakerData,0,output.speakerData.length);
			output.write(numBytesRead);
			
		}
	}
	
	

}
