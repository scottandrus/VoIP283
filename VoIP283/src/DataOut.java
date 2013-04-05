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
	
	public DataOut(){
		try {
			AudioFormat format = new AudioFormat(16000.0f, 16, 1, true, true);
			speakers = AudioSystem.getSourceDataLine(format);
		} catch (LineUnavailableException e) {
			// TODO Handle if the speakers are unavailable
			e.printStackTrace();
		}
	}
	
	
	//speaker.write(data, 0, Math.min( numBytesRead, speaker.getBufferSize()) );
	public void write(byte[] data, int offset, int numBytesRead){
		speakers.write(
				data, 
				offset, 
				Math.min( numBytesRead, speakers.getBufferSize()));
	}
	
	
	
	// is there anything else we should do before stopping or starting?
	public void startSpeakers(){
		speakers.start();
	}
	
	public void stopSpeakers(){
		speakers.stop();
	}
}
