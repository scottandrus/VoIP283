import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 * 
 * @author Jenna
 * 
 * ????????????????????????????????????????????????????????????
 * Not sure if necessary, but shows how the mic would be set up
 * 		and necessary info for it
 * ????????????????????????????????????????????????????????????
 * 
 * Handles data coming in from microphone and that inputStream
 *
 */

public class DataInThread implements Runnable{
	Thread inputThread;
	private TargetDataLine microphone;
	private int lastNumBytesRead;
	private byte[] micData;
	private DatagramSocket dSocket;
	
	private static final int MAXBUFSIZE = 1024;
	
	// ctor
	public DataInThread(DatagramSocket socket){
		try {
			dSocket = socket;
			
			// sampl Rate & bits, #Channels, signed?, bigEndian?
			AudioFormat format = new AudioFormat(16000.0f, 16, 1, true, true);
			microphone = AudioSystem.getTargetDataLine(format);
			micData = new byte[MAXBUFSIZE];
			microphone.open(format);
			inputThread = new Thread(this);
		} catch (LineUnavailableException e) {
			// TODO is there anything else we should do if the microphone 
			//		can't be connected?
			
			// http://stackoverflow.com/questions/14348169/understanding-java-sound-api-finding-mic-on-mixer
			e.printStackTrace();
		}
	}
	
	
	// Thread logic
	public void run(){
		while(true){
			//micData = new byte[microphone.available()];
			lastNumBytesRead = microphone.read(micData, 0, micData.length);
			
			//dp.setData(micData);
			DatagramPacket dp = new DatagramPacket(
					micData,
					Math.min(MAXBUFSIZE, micData.length));

			try {
				//System.out.println("input: " + buf[5]);
				dp.setData(micData);
				dSocket.send(dp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	
	// should there be a buffer for data that hasn't been sent in time?

	
	
	// are there any checks we need to do before starting or stopping?
	public void startMic(){
		microphone.start();
		inputThread.start();
	}
	
	public void stopMic(){
		microphone.stop();
	}
}

