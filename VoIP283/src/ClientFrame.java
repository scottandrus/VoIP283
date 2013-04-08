import javax.swing.*;
public class ClientFrame extends JFrame implements ActionListener {

	// http://forum.codecall.net/topic/41873-basic-jframe-with-a-basic-jbutton/
	
	public Main() {
		/*
		 * JFrame.
		 */
		setSize(600,600);//Size of JFrame
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);//Sets if its visible.
		/*
		 * JButton.
		 */
		JButton startButton = new JButton("Start");//The JButton name.
		add(startButton);//Add the button to the JFrame.
		startButton.addActionListener(this);//Reads the action.
	}
	
	/* 
	 *The main method.
	 */
	public static void main(String[] args){ 
		new Main();//Reads method main()
	}

	/* 
	 *What the button does.
	 */
	public void actionPerformed(ActionEvent e) {
		System.out.println("The Button Works!");//what the button says when clicked.
	}
	
}