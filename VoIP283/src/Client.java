/**
 * 
 * @author Jenna
 * 
 * Client has both a Sender and Receiver and connects them to
 * 		  a DataIn and DataOut object that has been initialized
 * 		  correctly for the machine the client is on
 * 
 * (This is our main, the core for pure communication between
 * 	     two clients)
 *
 */
public class Client {
	private Sender sender;
	private Receiver receiver;
	private DataIn input;
	private DataOut output;
	
	// init -- properly set up all private variables
	// have 2 separate ones for sender / dataIn & receiver / dataOut ?
	public void initSender(){
		
	}
	
	// main w/ info from cmd line
	//		call init with proper info
	// 		contains main event loop
	//			think about threads for sending out and receiving data
	public static void main(String[] args) throws Exception {
		if (args.length != 3){
			// wrong number of input
			// should be <sender/receiver?> <IP> <PortNo>
			
			// or should it be all information (and filler info if
			//				   it should only be one or the other?)
		}
	}
	
	

}
