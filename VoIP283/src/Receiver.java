import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * 
 * @author Jenna
 * 
 * Receiver accepts connection to a sender and reliably receives
 * 		    data
 * Can receive byteArrays as they come in
 * (Need a buffer for data to be sent to DataOut? In case it's
 * 		 receiving faster than it can pass along to DataOut?)
 * 
 * 
 * Currently has code from my Lab 5 RDTReceiver class
 *
 */


/**
 * TODO:  Change MAXBUFSIZE and all buffer sizes to appropriate values
 *
 */


// what buffer are we sending into this to be filled? Speaker's buffer?


public class Receiver {
	InStream istream;
	final static int MAXBUFSIZE = 1025;
	
	
	public class InStream extends ByteArrayInputStream{
		DatagramSocket dsocket;
		byte lastSequence;
		
		// ---------------------- CONSTRUCTOR ---------------------------------
		public InStream(byte[] buf,InetAddress serverIP, InetAddress remoteAddr, int PortNo) {
			super(buf);
			try {
				dsocket = new DatagramSocket(PortNo,serverIP);
				dsocket.connect(remoteAddr,PortNo);
			} catch (SocketException e) {
				e.printStackTrace();
			}
			lastSequence = 1;
		}
		
		
		
		// --------------------  READ -----------------------------------------
		public int read(byte[] b, int offset, int length){
			byte[] ack = new byte[1];
			ack[0] = 0;
			byte[] dgArray;
			
			try{
				DatagramPacket recvDGPacket = new DatagramPacket(this.buf,MAXBUFSIZE);
					
				while(true){
					dsocket.receive(recvDGPacket);
					dgArray = recvDGPacket.getData();
					
					//b = dgArray.clone();

					for(int i = 0; i < length; i++){
						b[i] = dgArray[i];
					}
						
					if(dgArray[dgArray.length - 1] != lastSequence){
						ack[0] = 1;
						DatagramPacket ackPacket = new DatagramPacket(ack,
																	  ack.length,
																	  dsocket.getInetAddress(),
																	  dsocket.getPort());
						if(lastSequence == 0){
							lastSequence = 1;
						}else{
							lastSequence = 0;
						}
						
						dsocket.send(ackPacket);
						return b.length;										
					}else{
						if(ack[0] == 1){
							// they just sent the same packet, resend
							DatagramPacket ackPacket = new DatagramPacket(ack,
																		  ack.length,
																		  dsocket.getInetAddress(),
																		  dsocket.getPort());
							dsocket.send(ackPacket); //resend ack
						}
					}
				}
			}catch(IOException e1){
				e1.printStackTrace();
			}
			return (MAXBUFSIZE - 1);	
		}
	}

	
	
	
	public Receiver(byte[] buf, InetAddress serverIP, InetAddress clientIP, int serverPort) {
		istream = new InStream(buf,serverIP,clientIP,serverPort);
	}

	public ByteArrayInputStream recvData() {
		return istream;
	}
}
