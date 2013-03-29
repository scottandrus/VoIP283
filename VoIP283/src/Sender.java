import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * 
 * @author Jenna
 * 
 * Sender takes care of connecting to receiver and reliably sending
 * 		  data across the connection
 * Accepts a byte array of data to be sent & sends the data
 * (Or can it continuously accept byte arrays and also handle buffering the 
 * 	   data that can't be sent yet to be sent as soon as possible?) 
 *
 *
 *	Currently has code from my Lab 5 RDTSender class
 */

/**
 * TODO:  Change MAXBUFSIZE and all buffer sizes to appropriate values
 *
 */


// changed ctor



public class Sender {
	OutStream ostream;
	final static int MAXBUFSIZE = 1025;
	
	public class OutStream extends ByteArrayOutputStream{
		DatagramSocket dsocket;
		byte curSequence;
		
		
		// ----------------------- CONSTRUCTOR --------------------------------
		public OutStream(InetAddress serverIP, InetAddress remoteAddr, int PortNo) {
			super();
			try {
				dsocket = new DatagramSocket(PortNo,serverIP);
				dsocket.connect(remoteAddr,PortNo);
			} catch (SocketException e) {
				e.printStackTrace();
			}
			curSequence = 0;
		}
		
		
		
		
		// ---------------------- WRITE ---------------------------------------
		public void write(byte[] b, int offset, int length){
			// attach sequence number to buffer
			byte[] newb = new byte[b.length+1];
			for(int i = 0; i  < b.length; i++){
				newb[i] = b[i];
			}
			newb[newb.length - 1] = curSequence;

			DatagramPacket dpacket = new DatagramPacket(newb,
														newb.length,
														dsocket.getInetAddress(),
														dsocket.getPort());

			try {
				DatagramPacket recvDGPacket = new DatagramPacket(new byte[MAXBUFSIZE],MAXBUFSIZE);
				dsocket.setSoTimeout(1);
				dsocket.send(dpacket);

				while(true){
					try{
						dsocket.receive(recvDGPacket);
						break;
					}catch(SocketTimeoutException e){
						dsocket.send(dpacket);
					}
				}
			} catch (SocketException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			// update sequence number
			if(curSequence == 0){
				curSequence = 1;
			}else{
				curSequence = 0;
			}
		}
	}

	
	
	
	// set up datagram socket
	public Sender(InetAddress serverIP, InetAddress clientIP, int PortNo) {
		ostream = new OutStream(serverIP,clientIP,PortNo);

		
	}
	
	public ByteArrayOutputStream sendData() {
		return ostream;
	}
}
