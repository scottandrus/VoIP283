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
public class DataOut {
	private SourceDataLine speakers;
	public byte[] speakerData; // <--- is this ok?  Should DataOut be internal to client?
	
	public DataOut(){
		try {
			AudioFormat format = new AudioFormat(16000.0f, 16, 1, true, true);
			speakers = AudioSystem.getSourceDataLine(format);
			speakerData = new byte[speakers.getBufferSize()];
		} catch (LineUnavailableException e) {
			// TODO Handle if the speakers are unavailable
			e.printStackTrace();
		}
	}
	
	
	// is there anything else we should do before stopping or starting?
	public void startSpeakers(){
		speakers.start();
	}
	
	public void stopSpeakers(){
		speakers.stop();
	}
}
