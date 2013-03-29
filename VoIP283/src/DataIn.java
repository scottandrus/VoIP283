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

public class DataIn {
	private TargetDataLine microphone;
	private byte[] micData;
	
	// ctor
	public DataIn(){
		try {

			//							sampl Rate & bits, #Channels, signed?, bigEndian?
			AudioFormat format = new AudioFormat(16000.0f, 16, 1, true, true);
			microphone = AudioSystem.getTargetDataLine(format);
			micData = new byte[microphone.getBufferSize()];
		} catch (LineUnavailableException e) {
			// TODO is there anything else we should do if the microphone 
			//		can't be connected?
			
			// http://stackoverflow.com/questions/14348169/understanding-java-sound-api-finding-mic-on-mixer
			e.printStackTrace();
		}
	}
	
	
	// should there be a buffer for data that hasn't been sent in time?
	
	//numBytesRead = microphone.read(data, 0, data.length);
	public int read(){
		int numBytesRead = microphone.read(micData, 0, micData.length);
		return numBytesRead;
	}
	
	
	public byte[] getNextArray(){
		// gives the most recent byte[] to send out (if there is one);
		return micData;
	}
	
	
	
	// are there any checks we need to do before starting or stopping?
	public void startMic(){
		microphone.start();
	}
	
	public void stopMic(){
		microphone.stop();
	}
}







































