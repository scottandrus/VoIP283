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
	
	//ctor gather all data necessary to set up and start a microphone
	//		is there platform-specific stuff that we have to do here?
	//		or anything we need to pass in as params?
	// the microphone is only set up here, it is not started
	// 	   until main event loop when the client is ready to
	//	   receive the data (so until the clients are connected)
	public DataIn(){
		try {
			// this sample rate gives a higher quality of audio, but may be too much
			//		for the connection (should introduce a congestion control where
			//		the sample rate is lowered before the audio cuts out (hopefully
			//		it doesn't cut out at all!))
			// TODO introduce congestion control in relation to sampling rate
			//							sampl Rate & bits, #Channels, signed?, bigEndian?
			AudioFormat format = new AudioFormat(16000.0f, 16, 1, true, true);
			
			// gets a dataLine (input) corresponding to this format
			microphone = AudioSystem.getTargetDataLine(format);
			micData = new byte[microphone.getBufferSize()];
		} catch (LineUnavailableException e) {
			// TODO is there anything else we should do if the microphone 
			//		can't be connected?
			
			// TODO handle this exception to determine why the line is unavailable
			//		and return the correct information to user
			
			// http://stackoverflow.com/questions/14348169/understanding-java-sound-api-finding-mic-on-mixer
			e.printStackTrace();
		}
	}
	
	
	// are there any checks we need to do before starting or stopping?
	public void startMic(){
		microphone.start();
	}
	
	public void stopMic(){
		microphone.stop();
	}
}







































