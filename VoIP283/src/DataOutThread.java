import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Vector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * 
 * @author Jenna
 * 
 * Handles sending data out to speakers and that OutputStream
 * 
 * See DataIn for more relevant comments
 *
 */
public class DataOutThread implements Runnable{
	private Thread outputThread;
	private SourceDataLine speakers;
	private DatagramSocket dSocket;
	private static final int MAXBUFSIZE = 1024;
	
	
	// CTOR
	public DataOutThread(DatagramSocket socket){
		try {
			AudioFormat format = new AudioFormat(16000.0f, 16, 1, true, true);
			speakers = AudioSystem.getSourceDataLine(format);
			speakers.open(format);
			dSocket = socket;
			outputThread = new Thread(this);
		} catch (LineUnavailableException e) {
			// TODO Handle if the speakers are unavailable
			e.printStackTrace();
		}
	}
	
	
	
	// RUN
	public void run(){
		byte[] responseBuf = new byte[MAXBUFSIZE];
		DatagramPacket dp = new DatagramPacket(
				responseBuf,
				Math.min(MAXBUFSIZE, responseBuf.length));
		while(true){
			try {
				dSocket.receive(dp);
				write(dp.getData(), 0, dp.getLength());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	
	
	// WRITE out to speakers
	public void write(byte[] data, int offset, int numBytesRead){
		speakers.write(
				data, 
				offset, 
				Math.min( numBytesRead, speakers.getBufferSize()));
	}
	
	
	
	// is there anything else we should do before stopping or starting?
	public void startSpeakers(){
		speakers.start();
		outputThread.start();
		//outputThread.run();
	}
	
	public void stopSpeakers(){
		speakers.stop();
	}
}
